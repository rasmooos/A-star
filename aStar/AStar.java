package aStar;

import engine.core.Main;
import engine.core.Renderer;
import engine.rendering.Camera;
import engine.rendering.Screen;
import engine.util.Input.Keyboard;
import engine.util.vector.Vector2i;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class AStar extends Renderer {

    public static final int WIDTH = 1000, HEIGHT = 1000;

    public static final float wallPercentage = 0.30f;

    public static final int cols = 100;
    public static final int rows = 100;

    public static final Vector2i startPos = new Vector2i(50, 50);
    public static final Vector2i endPos = new Vector2i(5, 7);
    public static final boolean RANDOM = true;

    public static final int w = WIDTH / cols;
    public static final int h = HEIGHT / rows;

    public static final boolean DEBUG = true;

    private Node[][] grid;

    private List<Node> openSet = new ArrayList<>();
    private List<Node> closedSet = new ArrayList<>();
    private List<Node> path = new ArrayList<>();

    private Node start;
    private Node end;

    private boolean finished;
    private boolean found;

    private int winnerIndex = 0;

    private final Random random = new Random();

    public void init() {

        grid = new Node[cols][rows];

        setup();

    }

    private void setup() {
        finished = false;
        found = false;

        path.clear();
        openSet.clear();
        closedSet.clear();

        for(int i = 0; i < cols; i++) {
            for(int j = 0; j < rows; j++) {
                grid[i][j] = new Node(i, j);
            }
        }

        for(int i = 0; i < cols; i++) {
            for(int j = 0; j < rows; j++) {
                grid[i][j].addNeighbors(grid);
            }
        }

        if(RANDOM)
            start = grid[random.nextInt(cols)][random.nextInt(rows)];
        else {
            if((startPos.getX() < 0 || startPos.getX() > cols - 1 || startPos.getY() < 0 || startPos.getY() > rows - 1))
                start = grid[cols - 1][rows - 1];
            else {
                start = grid[startPos.getX()][startPos.getY()];
            }
        }

        if(RANDOM)
            end = grid[random.nextInt(cols)][random.nextInt(rows)];
        else {
            if((endPos.getX() < 0 || endPos.getX() > cols - 1 || endPos.getY() < 0 || endPos.getY() > rows - 1))
                end = grid[cols - 1][rows - 1];
            else {
                end = grid[endPos.getX()][endPos.getY()];
            }
        }

        start.setSolid(false);
        end.setSolid(false);

        openSet.add(start);
    }

    public void update() {
        if(Keyboard.one) Main.setSpeed(0.1f);
        if(Keyboard.two) Main.setSpeed(0.2f);
        if(Keyboard.three) Main.setSpeed(0.3f);
        if(Keyboard.four) Main.setSpeed(0.4f);
        if(Keyboard.five) Main.setSpeed(0.5f);
        if(Keyboard.six) Main.setSpeed(0.6f);
        if(Keyboard.seven) Main.setSpeed(0.7f);
        if(Keyboard.eight) Main.setSpeed(0.8f);
        if(Keyboard.nine) Main.setSpeed(1);
        if(Keyboard.space) Main.setSpeed(2);

        if(finished && Keyboard.enter_once) setup();

        if(openSet.size() > 0 && !finished) {

            winnerIndex = 0;

            for(int i = 0; i < openSet.size(); i++)
                if(openSet.get(i).getF() < openSet.get(winnerIndex).getF())
                    winnerIndex = i;

            Node current = openSet.get(winnerIndex);

            if(current == end) {
                path.clear();
                finished = true;
                found = true;
                Node temp = current;

                System.out.println("WE FOUND A PATH");

                path.add(current);
                while(temp.getPrevious() != null) {
                    path.add(temp.getPrevious());
                    temp = temp.getPrevious();
                }
            }

            closedSet.add(current);
            openSet.remove(current);

            List<Node> neighbors = current.getNeighbors();

            for(int i = 0; i < neighbors.size(); i++) {
                Node neighbor = neighbors.get(i);

                if(closedSet.contains(neighbor) || neighbor.isSolid())
                    continue;

                float tempG = current.getG() + 1;

                if(!openSet.contains(neighbor))
                    openSet.add(neighbor);
                else if(tempG >= neighbor.getG())
                    continue;

                neighbor.setG(tempG);
                neighbor.setH(taxiDistance(neighbor, end));
                neighbor.setF(neighbor.getG() + neighbor.getH());
                neighbor.setPrevious(current);
            }

        } else {
            finished = true;
            if(!found)
                System.out.println("No Path exists!");
        }
    }

    private float heuristic(Node a, Node b) {
        float xDif = a.getI() - b.getI();
        float yDif = a.getJ() - b.getJ();

        float d = (float) Math.sqrt(xDif * xDif + yDif * yDif);

        return d;
    }

    private float taxiDistance(Node a, Node b) {
        return Math.abs(a.getI() - b.getI()) + Math.abs(a.getJ() - b.getJ());
    }

    public void renderToWorld(Screen screen) {

    }

    public void renderToScreen(Screen screen) {
        screen.renderSquare(new Vector2i(0, 0), new Vector2i(WIDTH, HEIGHT), Color.WHITE);

        for(int i = 0; i < cols; i++) {
            for(int j = 0; j < rows; j++) {

                Node node = grid[i][j];
                Color color;

                if(openSet.contains(node))
                    color = Color.GREEN;
                else if(closedSet.contains(node))
                    color = Color.RED;
                else
                    color = Color.WHITE;

                if(!DEBUG) color = Color.WHITE;

                node.render(screen, color);
            }
        }

        for(int i = 0; i < path.size(); i++) {
            path.get(i).render(screen, Color.BLUE);
        }

        if(!finished)
            path.clear();

        Node temp;


        if(!(openSet.size() == 0 || winnerIndex >= openSet.size())) {

            if(!finished) temp = openSet.get(winnerIndex);
            else temp = end;

            if(!finished) {
                path.add(temp);
                while(temp.getPrevious() != null) {
                    path.add(temp.getPrevious());
                    temp = temp.getPrevious();
                }
            }
        }
        end.render(screen, Color.ORANGE);
        start.render(screen, Color.MAGENTA);
    }

    public Camera getCamera() {
        return new Camera();
    }
}

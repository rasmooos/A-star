package aStar;

import engine.rendering.Screen;
import engine.util.vector.Vector2i;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Node {

    private int i;
    private int j;

    private float f;
    private float g;
    private float h;

    private List<Node> neighbors = new ArrayList<>();
    private Node previous;

    private boolean solid;

    private Random random = new Random();

    public Node(int i, int j) {
        this.i = i;
        this.j = j;

        if(random.nextFloat() < AStar.wallPercentage)
            solid = true;
    }

    public void render(Screen screen, Color color) {
        if(solid)
            color = Color.BLACK;

        screen.renderSquare(new Vector2i(i * AStar.w, j * AStar.h), new Vector2i(AStar.w, AStar.h), color);
        screen.renderEmptySquare(new Vector2i(i * AStar.w, j * AStar.h), new Vector2i(AStar.w, AStar.h), Color.BLACK);
    }

    public void addNeighbors(Node[][] grid) {
        if(i < AStar.cols - 1)
            neighbors.add(grid[i + 1][j]);
        if(i > 0)
            neighbors.add(grid[i - 1][j]);
        if(j < AStar.rows - 1)
            neighbors.add(grid[i][j + 1]);
        if(j > 0)
            neighbors.add(grid[i][j - 1]);
    }

    public List<Node> getNeighbors() {
        return neighbors;
    }

    public void setPrevious(Node previous) {
        this.previous = previous;
    }

    public void setF(float f) {
        this.f = f;
    }

    public void setG(float g) {
        this.g = g;
    }

    public void setH(float h) {
        this.h = h;
    }

    public float getF() {
        return f;
    }

    public float getG() {
        return g;
    }

    public float getH() {
        return h;
    }

    public int getI() {
        return i;
    }

    public int getJ() {
        return j;
    }

    public Node getPrevious() {
        return previous;
    }

    public boolean isSolid() {
        return solid;
    }

    public void setSolid(boolean solid) {
        this.solid = solid;
    }
}

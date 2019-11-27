package engine.core;

import engine.rendering.Screen;
import engine.util.Input.Keyboard;
import engine.util.Input.Mouse;
import engine.util.Time;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferStrategy;

public class Main extends Canvas implements Runnable {

    private static JFrame frame;

    public static int WIDTH = 1440, HEIGHT = 800;
    public static String TITLE;

    private static float speed = 1;
    private float numUpdates;

    private static Renderer renderer;

    private Thread thread;
    private boolean running;

    private Screen screen;

    public Main() {
        frame = new JFrame(TITLE);

        frame.setPreferredSize(new Dimension(WIDTH, HEIGHT));
        frame.setMinimumSize(new Dimension(WIDTH, HEIGHT));
        frame.setMaximumSize(new Dimension(WIDTH, HEIGHT));
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setResizable(false);
        frame.setLocationRelativeTo(null);

        frame.add(this);
        frame.pack();
        frame.setVisible(true);

        start();
    }

    private void start() {
        addKeyListener(new Keyboard());
        addMouseListener(new Mouse());
        addMouseMotionListener(new Mouse());
        addMouseWheelListener(new Mouse());

        screen = new Screen(renderer, WIDTH, HEIGHT);

        thread = new Thread(this);
        running = true;

        thread.start();
    }

    public void run() {
        requestFocus();

        long lastTime = System.nanoTime();
        long timer = System.currentTimeMillis();
        double delta = 0;
        int frames = 0;
        int updates = 0;

        renderer.init();

        while (running) {

            numUpdates = 60 * speed;

            final double ns = 1000000000.0 / numUpdates;
            long now = System.nanoTime();
            delta += (now - lastTime) / ns;
            lastTime = now;

            while(delta >= 1) {
                update();
                updates++;
                delta--;
            }

            render();
            frames++;

            if(System.currentTimeMillis() - timer > 1000) {
                timer += 1000;
                //System.out.println("Updates: " + updates + ", FPS: " + frames);
                frame.setTitle(TITLE + "  |  Updates: " + updates + ", FPS:" + frames);
                updates = 0;
                frames = 0;
            }
        }

        System.exit(0);
    }

    private void update() {
        Time.update();
        Keyboard.update();
        renderer.update();
        Mouse.update();
    }

    private void render() {
        //Prepare for rendering
        BufferStrategy bs = getBufferStrategy();
        if(bs == null) {
            createBufferStrategy(3);
            return;
        }


        Graphics g = bs.getDrawGraphics();
        Graphics2D g2 = (Graphics2D) g;

        g.setColor(Color.BLACK);
        g.fillRect(0, 0, WIDTH, HEIGHT);

        screen.setGraphics(g2);
        screen.prepareRender();

        //Render to world

        renderer.renderToWorld(screen);

        screen.endRender();

        //Render to screen
        renderer.renderToScreen(screen);

        g.dispose();
        bs.show();
    }

    public static void createRenderer(Renderer renderer_, int width, int height, String title) {
        WIDTH = width;
        HEIGHT = height;
        TITLE = title;
        renderer = renderer_;
        renderer.setWidth(width);
        renderer.setHeight(height);
        new Main();
    }

    public static void setSpeed(float speed_) {
        speed = speed_;
    }

    public static float getSpeed() {
        return speed;
    }
}

package engine.util.Input;

import engine.util.vector.Vector2f;

import java.awt.event.*;

public class Mouse implements MouseListener, MouseMotionListener, MouseWheelListener {

    private static int mouseX = -1;
    private static int mouseY = -1;

    public static boolean leftClick, rightClick, middleClick;
    public static boolean leftClickOnce, rightClickOnce;

    private static boolean[] buttons = new boolean[10];
    private static boolean[] buttonspressed = new boolean[10];

    private static int dx, dy;

    private static float scroll;

    public void mouseClicked(MouseEvent e) {

    }

    public void mousePressed(MouseEvent e) {
        buttons[e.getButton()] = true;
    }

    public void mouseReleased(MouseEvent e) {
        buttons[e.getButton()] = false;
        buttonspressed[e.getButton()] = false;
    }

    public void mouseEntered(MouseEvent e) {
    }

    public void mouseExited(MouseEvent e) {
    }

    public void mouseDragged(MouseEvent e) {
        dx = e.getX() - mouseX;
        dy = e.getY() - mouseY;

        mouseX = e.getX();
        mouseY = e.getY();
    }

    public void mouseMoved(MouseEvent e) {
        dx = e.getX() - mouseX;
        dy = e.getY() - mouseY;

        mouseX = e.getX();
        mouseY = e.getY();
    }

    public static int getX() {
        return mouseX;
    }

    public static int getY() {
        return mouseY;
    }

    public static void update() {
        leftClick = buttons[1];
        rightClick = buttons[3];
        middleClick = buttons[2];

        leftClickOnce = leftClick && !buttonspressed[1];
        if(leftClickOnce && leftClick) buttonspressed[1] = true;

        rightClickOnce = rightClick && !buttonspressed[3];
        if(rightClickOnce && rightClick) buttonspressed[3] = true;

        scroll = 0;
        dx = 0;
        dy = 0;
    }

    @Override
    public void mouseWheelMoved(MouseWheelEvent e) {
        scroll += e.getPreciseWheelRotation();
    }

    public static float getScroll() {
        return scroll;
    }

    public static int getDx() {
        return dx;
    }

    public static int getDy() {
        return dy;
    }

    public static Vector2f getPosition() {
        return new Vector2f(mouseX, mouseY);
    }
}

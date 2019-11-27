package engine.rendering;

import engine.core.Renderer;
import engine.util.vector.Vector2f;
import engine.util.vector.Vector2i;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;

public class Screen {

    private Graphics2D g;

    private Renderer renderer;
    private Camera camera;
    private Vector2f screenSize;

    private AffineTransform transform;

    private boolean renderToWorld;

    public Screen(Renderer renderer, int width, int height) {
        this.renderer = renderer;
        screenSize = new Vector2f(width, height);
    }

    public void prepareRender() {
        transform = g.getTransform();

        if(camera == null) camera = renderer.getCamera();
        camera.prepare();

        g.setTransform(camera.getTransform());
        renderToWorld = true;
    }

    public void endRender() {
        g.setTransform(transform);
        renderToWorld = false;
    }

    /**
     * Renders a square
     *
     * @param position Position in world
     * @param size Size of square
     * @param color Color
     */

    public void renderSquare(Vector2i position, Vector2i size, Color color) {
        Vector2i screenPos = getScreenPos(position);

        g.setColor(color);
        g.fillRect(screenPos.getX(), screenPos.getY(), size.getX(), size.getY());
    }

    public void renderSquare(Vector2i position, Vector2i size, int color) {
        Vector2i screenPos = getScreenPos(position);
        Color color_ = new Color(color);


        g.setColor(color_);
        g.fillRect(screenPos.getX(), screenPos.getY(), size.getX(), size.getY());
    }

    public void renderEmptySquare(Vector2i position, Vector2i size, Color color) {
        Vector2i screenPos = getScreenPos(position);

        g.setColor(color);
        g.drawRect(screenPos.getX(), screenPos.getY(), size.getX(), size.getY());
    }

    public void renderCircle(Vector2i position, Vector2i size, Color color) {
        Vector2i screenPos = getScreenPos(position);

        g.setColor(color);
        g.fillOval(screenPos.getX(), screenPos.getY(), size.getX(), size.getY());
    }

    public void renderSquareEdge(Vector2i position, Vector2i size, Color color) {
        Vector2i screenPos = getScreenPos(position);

        g.setColor(color);
        g.drawRect(screenPos.getX(), screenPos.getY(), size.getX(), size.getY());
    }

    public void renderImage(BufferedImage image, Vector2i position) {
        Vector2i screenPos = getScreenPos(position);

        g.drawImage(image, screenPos.getX(), screenPos.getY(), null);
    }



    /**
     *  Renders a string to the screen
     * @param string the text to render
     * @param position position
     * @param font what font to use
     */

    public void renderText(String string, Vector2i position, Font font, int color) {
        Vector2i screenPos = getScreenPos(position);

        g.setFont(font);
        g.setColor(new Color(color));
        g.drawString(string, screenPos.getX(), screenPos.getY());
    }

    /**
     * Transforms a worldPos to a screenPos
     *
     * @param worldPos Position in world
     * @return screenPos Position on screen
     */

    private Vector2i getScreenPos(Vector2f worldPos) {
        if(!renderToWorld) return worldPos.toVec2i();

        int x = (int) (worldPos.getX() - camera.getPosition().getX() );
        int y = (int) (worldPos.getY() - camera.getPosition().getY() );

        return new Vector2i(x, y);
    }

    /**
     * Transforms a worldPos to a screenPos
     *
     * @param worldPos Position in world
     * @return screenPos Position on screen
     */

    private Vector2i getScreenPos(Vector2i worldPos) {
        if(!renderToWorld) return worldPos;

        int x = (int) (worldPos.getX() - camera.getPosition().getX() );
        int y = (int) (worldPos.getY() - camera.getPosition().getY() );

        return new Vector2i(x, y);
    }

    /**
     * Only works for objects in the world!
     *
     * @param worldPos position
     * @param size size of object
     * @return if the object is background
     */

    private boolean notInScreen(Vector2i worldPos, Vector2i size) {
        Vector2i screenPos = camera.worldToScreenPos(worldPos);

        return renderToWorld && screenPos.getX() + size.getX() * camera.getZoom() < 0 || screenPos.getY() + size.getY() * camera.getZoom() < 0 || screenPos.getX() > screenSize.getX() || screenPos.getY() > screenSize.getY();
    }

    public FontMetrics getFontMetrics(Font font) {
        return g.getFontMetrics(font);
    }

    public void setGraphics(Graphics2D g) {
        this.g = g;
    }

    public void scale(float scale) {
        g.scale(scale, scale);
    }
}

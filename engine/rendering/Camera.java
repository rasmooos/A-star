package engine.rendering;

import engine.core.Main;
import engine.util.vector.Vector2f;
import engine.util.vector.Vector2i;

import java.awt.geom.AffineTransform;

public class Camera {

    protected Vector2f position;
    protected float rotation;
    protected float zoom;

    private AffineTransform transform;
    private AffineTransform center;

    public Camera() {
        position = new Vector2f();

        transform = new AffineTransform();
        center = new AffineTransform();

        zoom = 1;
    }

    public void prepare() {
        center.setToIdentity();
        center.translate(Main.WIDTH / 2 + position.getX(), Main.HEIGHT / 2 + position.getY());

        transform.setToIdentity();
        transform.translate(-position.getX(), -position.getY());
        transform.rotate(rotation);
        transform.scale(zoom, zoom);
        transform.preConcatenate(center);

    }

    public void update() {}

    public void move(Vector2f position, float angle, float zoom) {
        setPosition(position);
        setRotation(angle);
        setZoom(zoom);
    }

    public Vector2i screenToWorldPos(Vector2i screenPos) {
        int x = (int) ((screenPos.getX() - (Main.WIDTH / 2)) / zoom + position.getX());
        int y = (int) ((screenPos.getY() - (Main.HEIGHT / 2)) / zoom + position.getY());

        return new Vector2i(x, y);
    }

    public Vector2f screenToWorldPos(Vector2f screenPos) {
        float x = (screenPos.getX() - (Main.WIDTH / 2)) / zoom + position.getX();
        float y = (screenPos.getY() - (Main.HEIGHT / 2)) / zoom + position.getY();

        return new Vector2f(x, y);
    }

    public Vector2f worldToScreenPos(Vector2f worldPos) {
        float x = zoom * (worldPos.getX() - position.getX()) + Main.WIDTH / 2;
        float y = zoom * (worldPos.getY() - position.getY()) + Main.HEIGHT / 2;

        return new Vector2f(x, y);
    }

    public Vector2i worldToScreenPos(Vector2i worldPos) {
        int x = (int) (zoom * (worldPos.getX() - position.getX())) + Main.WIDTH / 2;
        int y = (int) (zoom * (worldPos.getY() - position.getY())) + Main.HEIGHT / 2;

        return new Vector2i(x, y);
    }

    public void setPosition(Vector2f position) {
        this.position = position;
    }

    public void setRotation(float angle) {
        rotation = angle;
    }

    public void setZoom(float zoom) {
        this.zoom = zoom;
    }

    public AffineTransform getTransform() {
        return transform;
    }

    public Vector2f getPosition() {
        return position;
    }

    public float getZoom() {
        return zoom;
    }
}

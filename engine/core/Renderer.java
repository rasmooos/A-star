package engine.core;

import engine.rendering.Camera;
import engine.rendering.Screen;

public abstract class Renderer {

    protected Camera camera;

    protected int width, height;

    public abstract void init();
    public abstract void update();
    public abstract void renderToWorld(Screen screen);
    public abstract void renderToScreen(Screen screen);

    public abstract Camera getCamera();

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public void setHeight(int height) {
        this.height = height;
    }
}

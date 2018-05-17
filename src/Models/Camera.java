package Models;

import Utils.Constants;

public class Camera {
    public Vector2D position;
    private float x;
    private float y;

    public Camera() {
        this.position = new Vector2D();
    }

    public Vector2D getWorldToScreen(Vector2D worldPosition) {
        return new Vector2D(worldPosition.x - position.x,
                worldPosition.y - position.y);
    }

    public Vector2D getWorldToScreen(float worldX, float worldY) {
        x = worldX - position.x;
        y = worldY - position.y;

        x %= Constants.MAPSIZE;
        y %= Constants.MAPSIZE;
        if (x > Constants.WINDOW_SIZE_X - 200) x -= Constants.MAPSIZE;
        if (y > Constants.WINDOW_SIZE_Y) y -= Constants.MAPSIZE;

        if (x < -Constants.WINDOW_SIZE_X - 200 + Constants.WINDOW_SIZE_X / 2) x += Constants.MAPSIZE;
        if (y < -Constants.WINDOW_SIZE_Y) y += Constants.MAPSIZE;

        return new Vector2D(x, y);
    }

    public Vector2D getScreenToWorld(Vector2D screenPosition) {
        return new Vector2D(screenPosition.x + position.x,
                screenPosition.y + position.y);
    }

    public float getX() {
        return x;
    }

    public void setX(float x) {
        this.x = x;
    }

    public float getY() {
        return y;
    }

    public void setY(float y) {
        this.y = y;
    }
}

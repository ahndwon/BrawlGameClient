package Models;

import Utils.Constants;

public class Camera {
    public Vector2D position;

    public Camera() {
        this.position = new Vector2D();
    }

    public Vector2D getWorldToScreen(Vector2D worldPosition) {
        return new Vector2D(worldPosition.x - position.x,
                worldPosition.y - position.y);
    }

    public Vector2D getWorldToScreen(float worldX, float worldY) {
        float x = worldX - position.x;
        float y = worldY - position.y;

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
}

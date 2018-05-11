package Models;

import processing.core.PApplet;

public class MoveableView extends View {
    private Vector2D direction;
    private Vector2D velocity;
    private float speed;

    public MoveableView() {
        direction = new Vector2D(0, 0);
        velocity = new Vector2D(0, 0);
        speed = 0;
    }

    public Vector2D getDirection() {
        return direction;
    }

    public void setDirection(Vector2D direction) {
        this.direction = direction;
    }

    public float getSpeed() {
        return speed;
    }

    public void setSpeed(float speed) {
        this.speed = speed;
    }

    public Vector2D getVelocity() {
        return velocity;
    }

    public void setVelocity(Vector2D velocity) {
        this.velocity = velocity;
    }

    @Override
    public void render(PApplet pApplet) {

    }

    @Override
    public void onUpdate() {
        setVelocity(new Vector2D(speed * direction.x, speed * direction.y));
    }
}

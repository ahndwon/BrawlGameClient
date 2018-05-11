package Models;

import dwon.SpriteManager;
import processing.core.PApplet;

public class User extends View {
    private float x;
    private float y;
    private String name;
    private int direction;
    private int hp;
    private int state;
    private int tick;

    public User(float x, float y, String name, int direction, int hp, int state) {
        this.x = x;
        this.y = y;
        this.name = name;
        this.direction = direction;
        this.hp = hp;
        this.state = state;
    }

    @Override
    public void render(PApplet pApplet) {
        tick++;
        pApplet.image(SpriteManager.getImage(direction, tick / 10 % 4), x, y, 50, 50);
    }

    @Override
    public void onUpdate() {

    }

    public void setX(float x) {
        this.x = x;
    }

    public void setY(float y) {
        this.y = y;
    }

    public float getX() {
        return x;
    }

    public float getY() {
        return y;
    }

}

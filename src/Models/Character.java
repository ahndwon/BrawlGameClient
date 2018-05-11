package Models;

import processing.core.PApplet;

public class Character extends View {
    private float x;
    private float y;
    private String name;
    private int direction;
    private int hp;
    private int state;

    public Character(float x, float y, String name, int direction, int hp, int state) {
        this.x = x;
        this.y = y;
        this.name = name;
        this.direction = direction;
        this.hp = hp;
        this.state = state;
    }

    @Override
    public void render(PApplet pApplet) {

    }

    @Override
    public void onUpdate() {

    }
}

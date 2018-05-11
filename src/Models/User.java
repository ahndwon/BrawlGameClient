package Models;

import Utils.Constants;
import dwon.SpriteManager;
import processing.core.PApplet;

public class User extends View {
    private float x;
    private float y;
    private String name;
    private String direction;
    private int hp;
    private String state;
    private int tick;
    private int characterImage;

    public User(float x, float y, String name, String direction, int hp, String state) {
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

        pApplet.image(SpriteManager.getImage(characterImage, tick / 10 % 4), x, y, 50, 50);
    }

    @Override
    public void onUpdate() {
        switch (direction) {
            case "U" :
                characterImage = Constants.USER_UP;
                break;
            case "D" :
                characterImage = Constants.USER_DOWN;
                break;
            case "L":
                characterImage = Constants.USER_LEFT;
                break;
            case "R" :
                characterImage = Constants.USER_RIGHT;
                break;
        }

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

    public String getDirection() {
        return direction;
    }

    public void setDirection(String direction) {
        this.direction = direction;
    }
}

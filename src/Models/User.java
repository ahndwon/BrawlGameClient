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
    private int score;
    private String state;
    private int tick;
    private int characterImage;
    private boolean hit;
    private int hammerImage;
    private float hammerX;
    private float hammerY;

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
        if (hit)
        pApplet.image(SpriteManager.getImage(hammerImage, 0), hammerX, hammerY, 30, 30);

    }

    @Override
    public void onUpdate() {
        switch (direction) {
            case "UP" :
                characterImage = Constants.USER_UP;
                hammerImage = Constants.HAMMER_UP;
                hammerX = x;
                hammerY = y - 3;
                break;
            case "DOWN" :
                characterImage = Constants.USER_DOWN;
                hammerImage = Constants.HAMMER_DOWN;
                hammerX = x;
                hammerY = y + 3;
                break;
            case "LEFT":
                characterImage = Constants.USER_LEFT;
                hammerImage = Constants.HAMMER_LEFT;
                hammerX = x - 3;
                hammerY = y;
                break;
            case "RIGHT" :
                characterImage = Constants.USER_RIGHT;
                hammerImage = Constants.HAMMER_RIGHT;
                hammerX = x + 3;
                hammerY = y;
                break;
        }

    }


    public void setHit(boolean hit) {
        this.hit = hit;
    }

    public void setHp(int hp) {
        this.hp = hp;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public String getName() {
        return name;
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

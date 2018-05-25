package states;

import utils.Constants;

public class Update {
    private String user;
    private float x;
    private float y;
    private int hp;
    private int mana;
    private int stamina;
    private String direction;
    private int score;
    private String state;
    private int speed;
    private int characterImage;

    public Update() {

    }

    public Update(String user, float x, float y, int hp, int mana,
                  int stamina, String direction, int score, String state) {
        this.user = user;
        this.x = x;
        this.y = y;
        this.hp = hp;
        this.mana = mana;
        this.stamina = stamina;
        this.direction = direction;
        this.score = score;
        this.state = state;
        this.speed = Constants.PLAYER_SPEED;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
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

    public int getHp() {
        return hp;
    }

    public void setHp(int hp) {
        this.hp = hp;
    }

    public String getDirection() {
        return direction;
    }

    public void setDirection(String direction) {
        this.direction = direction;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public int getSpeed() {
        return speed;
    }

    public void setSpeed(int speed) {
        this.speed = speed;
    }


    public int getCharacterImage() {
        return characterImage;
    }

    public void setCharacterImage(int characterImage) {
        this.characterImage = characterImage;
    }

    public int getMana() {
        return mana;
    }

    public void setMana(int mana) {
        this.mana = mana;
    }

    public int getStamina() {
        return stamina;
    }

    public void setStamina(int stamina) {
        this.stamina = stamina;
    }
}

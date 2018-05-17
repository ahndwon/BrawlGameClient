package Models;

import Utils.Constants;
import dwon.SpriteManager;
import processing.core.PApplet;

public class User extends View implements Constants {
    private float x;
    private float y;
    private String name;
    private String direction;
    private int hp;
    private int score;
    private String state;
    private int tick;
    private int attackTick;
    private int characterImage;
    private boolean isAttack;
    private int time;
    private boolean isTime = false;
    private boolean isHit;
    private float posX, posY;
    private boolean me = false;

    public User(float x, float y, String name, String direction, int hp, int score, String state) {
        this.x = x;
        this.y = y;
        this.name = name;
        this.direction = direction;
        this.hp = hp;
        this.score = score;
        this.state = state;
    }

    public void setMe(boolean me) {
        this.me = me;
    }

    public boolean getMe() {
        return me;
    }

    public void setPosX(float x) {
        posX = x;
    }

    public void setPosY(float y) {
        posY = y;
    }

    @Override
    public void render(PApplet pApplet) {
        tick++;

        if (me) {
            pApplet.fill(255);
            pApplet.rect(400 - BLOCK_SIZE / 2, 300 - BLOCK_SIZE / 2 - 20, 50, 10);
            pApplet.fill(255, 0, 0);
            pApplet.rect(400 - BLOCK_SIZE / 2, 300 - BLOCK_SIZE / 2 - 20, hp / 2f, 10);

            pApplet.image(SpriteManager.getImage(characterImage, tick / 10 % 4),
                    400 - BLOCK_SIZE / 2, 300 - BLOCK_SIZE / 2, BLOCK_SIZE, BLOCK_SIZE);

            pApplet.fill(0);
            pApplet.textSize(10);
            pApplet.text(name, 400 - 20, 300 + 40);

            if (isHit) {
                pApplet.fill(255, 0, 0);
                pApplet.rect(400 - BLOCK_SIZE / 2, 300 - BLOCK_SIZE / 2, BLOCK_SIZE, BLOCK_SIZE);
                isHit = false;
            }


        } else {
            pApplet.fill(255);
            pApplet.rect(x - BLOCK_SIZE / 2 + posX, y - BLOCK_SIZE / 2 - 20 + posY, 50, 10);
            pApplet.fill(255, 0, 0);
            pApplet.rect(x - BLOCK_SIZE / 2 + posX, y - BLOCK_SIZE / 2 - 20 + posY, hp / 2f, 10);

            pApplet.image(SpriteManager.getImage(characterImage, tick / 10 % 4),
                    x - BLOCK_SIZE / 2 + posX, y - BLOCK_SIZE / 2 + posY, BLOCK_SIZE, BLOCK_SIZE);

            pApplet.fill(0);
            pApplet.textSize(10);
            pApplet.text(name, x - 20 + posX, y + 40 + posY);

            if (isHit) {
                pApplet.fill(255, 0, 0);
                pApplet.rect(x - BLOCK_SIZE / 2 + posX, y - BLOCK_SIZE / 2 + posY, BLOCK_SIZE, BLOCK_SIZE);
                isHit = false;
            }
        }

//        if (isHit) {
//            pApplet.fill(255, 0, 0);
//            pApplet.rect(x - BLOCK_SIZE / 2, y - BLOCK_SIZE / 2, BLOCK_SIZE, BLOCK_SIZE);
//            isHit = false;
//        }


        if (state.equals("ATTACK") && !isTime) {
            time = 0;
            isAttack = true;
        }


        if (isAttack) {
            time++;
            System.out.println("isAttack");
            attackTick++;

            switch (direction) {
                case PLAYER_DOWN:
                    pApplet.image(SpriteManager.getImage(FIST, attackTick / 10 % 6),
                            400 - BLOCK_SIZE / 2, 300 - BLOCK_SIZE / 2 + BLOCK_SIZE / 2 + BLOCK_SIZE, BLOCK_SIZE, BLOCK_SIZE);
                    break;
                case PLAYER_UP:
                    pApplet.image(SpriteManager.getImage(FIST, attackTick / 10 % 6),
                            400 - BLOCK_SIZE / 2, 300 - BLOCK_SIZE / 2- BLOCK_SIZE / 2 - BLOCK_SIZE, BLOCK_SIZE , BLOCK_SIZE);
                    break;
                case PLAYER_LEFT:
                    pApplet.image(SpriteManager.getImage(FIST, attackTick / 10 % 6),
                            400 - BLOCK_SIZE / 2 - BLOCK_SIZE / 2 - BLOCK_SIZE, 300 - BLOCK_SIZE / 2, BLOCK_SIZE, BLOCK_SIZE);
                    break;
                case PLAYER_RIGHT:
                    pApplet.image(SpriteManager.getImage(FIST, attackTick / 10 % 6),
                            400 - BLOCK_SIZE / 2 + BLOCK_SIZE / 2 + BLOCK_SIZE, 300 - BLOCK_SIZE / 2, BLOCK_SIZE, BLOCK_SIZE);
                    break;
            }

            if (attackTick > 60) {
                attackTick = 0;
                isAttack = false;
            }
        }


//            pApplet.image(SpriteManager.getImage(hammerImage, 0), hammerX, hammerY, 30, 30);
    }

    @Override
    public void onUpdate() {


        if (state.equals("MOVE")) {
            switch (direction) {
                case "UP":
                    characterImage = Constants.USER_UP;
                    y -= PLAYER_SPEED;
                    break;
                case "DOWN":
                    characterImage = Constants.USER_DOWN;
                    y += PLAYER_SPEED;
                    break;
                case "LEFT":
                    characterImage = Constants.USER_LEFT;
                    x -= PLAYER_SPEED;
                    break;
                case "RIGHT":
                    characterImage = Constants.USER_RIGHT;
                    x += PLAYER_SPEED;
                    break;
            }
        }
    }


    public void setHit(boolean isHit) {
        this.isHit = isHit;
    }

    public void setState(String state) {
        this.state = state;
    }

    public void setAttack(boolean isAttack) {
        this.isAttack = isAttack;
    }

    public void setHp(int hp) {
        System.out.println("hp : " + hp);
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

    public int getScore() {
        return score;
    }
}

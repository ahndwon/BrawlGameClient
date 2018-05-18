package Models;

import Utils.Constants;
import Utils.Util;
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
    private float userX, userY;
    private boolean me = false;
    private Vector2D pos;
    private int speed;

    public User(float x, float y, String name, String direction, int hp, int score, String state) {
        this.x = x;
        this.y = y;
        this.name = name;
        this.direction = direction;
        this.hp = hp;
        this.score = score;
        this.state = state;
        this.speed = Constants.PLAYER_SPEED;
    }

    public User(float x, float y, String name, String direction, int hp, int score, String state, boolean me) {
        this.x = x;
        this.y = y;
        this.name = name;
        this.direction = direction;
        this.hp = hp;
        this.score = score;
        this.state = state;
        this.me = true;
        userX = x;
        userY = y;
        pos = new Vector2D(x, y);
        this.speed = Constants.PLAYER_SPEED;
    }


    @Override
    public void render(PApplet pApplet) {
        tick++;

        pApplet.fill(255);
        pApplet.rect(pos.x - BLOCK_SIZE / 2, pos.y - BLOCK_SIZE / 2 - 20, 50, 10);
        pApplet.fill(255, 0, 0);
        pApplet.rect(pos.x - BLOCK_SIZE / 2, pos.y - BLOCK_SIZE / 2 - 20, hp / 2f, 10);

        pApplet.image(SpriteManager.getImage(characterImage, tick / 10 % 4),
                pos.x - BLOCK_SIZE / 2, pos.y - BLOCK_SIZE / 2, BLOCK_SIZE, BLOCK_SIZE);

        pApplet.fill(0);
        pApplet.textSize(10);
        pApplet.text(name, pos.x - 20, pos.y + 40);

        if (isHit) {
            pApplet.fill(255, 0, 0);
            pApplet.rect(pos.x - BLOCK_SIZE / 2, pos.y - BLOCK_SIZE / 2, BLOCK_SIZE, BLOCK_SIZE);
            isHit = false;
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
                            pos.x - BLOCK_SIZE / 2, pos.y - BLOCK_SIZE / 2 + BLOCK_SIZE / 2 + BLOCK_SIZE, BLOCK_SIZE, BLOCK_SIZE);
                    break;
                case PLAYER_UP:
                    pApplet.image(SpriteManager.getImage(FIST, attackTick / 10 % 6),
                            pos.x - BLOCK_SIZE / 2, pos.y - BLOCK_SIZE / 2 - BLOCK_SIZE / 2 - BLOCK_SIZE, BLOCK_SIZE, BLOCK_SIZE);
                    break;
                case PLAYER_LEFT:
                    pApplet.image(SpriteManager.getImage(FIST, attackTick / 10 % 6),
                            pos.x - BLOCK_SIZE / 2 - BLOCK_SIZE / 2 - BLOCK_SIZE, pos.y - BLOCK_SIZE / 2, BLOCK_SIZE, BLOCK_SIZE);
                    break;
                case PLAYER_RIGHT:
                    pApplet.image(SpriteManager.getImage(FIST, attackTick / 10 % 6),
                            pos.x - BLOCK_SIZE / 2 + BLOCK_SIZE / 2 + BLOCK_SIZE, pos.y - BLOCK_SIZE / 2, BLOCK_SIZE, BLOCK_SIZE);
                    break;
            }

            if (attackTick > 60) {
                attackTick = 0;
                isAttack = false;
            }
        }

//        if (me) {
//            pApplet.fill(0, 0, 255);
//            int index = Util.getIndexByPosForMiniMap();
//            pApplet.ellipse(Util.getPosXByIndexForMiniMap(index), Util.getPosYByIndexForMiniMap(index), 5, 5);
//        } else {
//            pApplet.fill(0);
//            int index = Util.getIndexByPosForMiniMap();
//            pApplet.ellipse(Util.getPosXByIndexForMiniMap(index), Util.getPosYByIndexForMiniMap(index), 5, 5);
//        }


//            pApplet.image(SpriteManager.getImage(hammerImage, 0), hammerX, hammerY, 30, 30);
    }

    @Override
    public void onUpdate(Camera camera) {

        if (state.equals("MOVE")) {
            switch (direction) {
                case "UP": {
                    characterImage = Constants.USER_UP;
                    y -= speed;
                    break;
                }
                case "DOWN": {
                    characterImage = Constants.USER_DOWN;
                    y += speed;
                    break;
                }
                case "LEFT": {
                    characterImage = Constants.USER_LEFT;
                    x -= speed;

                    break;
                }
                case "RIGHT": {
                    characterImage = Constants.USER_RIGHT;
                    x += speed;
                    break;
                }
            }

        }

        pos = camera.getWorldToScreen(new Vector2D(this.x, this.y));
    }


    public void miniRender(PApplet pApplet) {

        float charX = x;
        float charY = y;
        charX %= MAPSIZE;
        charY %= MAPSIZE;

        if(charX < 0){
            charX += MAPSIZE;
        }

        if(charY < 0){
            charY += MAPSIZE;
        }

        if (me) {
            pApplet.fill(0, 0, 200);
            pApplet.ellipse(charX / 10f, charY / 10f , CHARACTER, CHARACTER);
        }
        else {
            pApplet.fill(0, 0, 0);
            pApplet.ellipse(charX / 10f, charY / 10f , CHARACTER, CHARACTER);
        }

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

    public int getSpeed() {
        return speed;
    }

    public void setSpeed(int speed) {
        this.speed = speed;
    }
}

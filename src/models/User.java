package models;

import processing.core.PConstants;
import utils.Constants;
import dwon.SpriteManager;
import processing.core.PApplet;
import utils.SoundManager;

public class User extends View implements Constants {
    private float x;
    private float y;
    private String name;
    private String direction;
    private int hp;
    private int mana;
    private int stamina;
    private int score;
    private String state;
    private int tick;
    private int attackTick = 0;
    private int characterImage;
    private boolean isAttack;
    private boolean isSpecial;
    private int time;
    private boolean isTime = false;
    private boolean isHit;
    private boolean isTired;
    private boolean me = false;
    private Vector2D pos;
    private int speed;
    private String attackDirection;

    public User(float x, float y, String name, String direction,
                int hp, int mana, int stamina, int score, String state) {
        this.x = x;
        this.y = y;
        this.name = name;
        this.direction = direction;
        this.hp = hp;
        this.mana = mana;
        this.stamina = stamina;
        this.score = score;
        this.state = state;
        this.speed = Constants.PLAYER_SPEED;
        this.characterImage = 10;
        this.attackDirection = PLAYER_DOWN;
    }

    public User(float x, float y, String name, String direction, int hp, int mana,
                int stamina, int score, String state, boolean me) {
        this.x = x;
        this.y = y;
        this.name = name;
        this.direction = direction;
        this.hp = hp;
        this.mana = mana;
        this.stamina = stamina;
        this.score = score;
        this.state = state;
        this.me = true;
        this.pos = new Vector2D(x, y);
        this.speed = Constants.PLAYER_SPEED;
        this.characterImage = 10;
        this.attackDirection = PLAYER_DOWN;
    }


    @Override
    public void render(PApplet pApplet) {
        tick++;
        pApplet.imageMode(PConstants.CORNER);
        pApplet.fill(255);
        pApplet.rect(pos.x - BLOCK_SIZE / 2, pos.y - BLOCK_SIZE / 2 - 20, 50, 15);
        pApplet.fill(255, 0, 0);
        pApplet.rect(pos.x - BLOCK_SIZE / 2, pos.y - BLOCK_SIZE / 2 - 20, hp / 2f, 5);
        pApplet.fill(0, 0, 255);
        pApplet.rect(pos.x - BLOCK_SIZE / 2, pos.y - BLOCK_SIZE / 2 - 15, mana / 2f, 5);
        pApplet.fill(0, 255, 0);

        if (isTired) {
            pApplet.fill(255, 0, 0);
            isTired = false;
        }
        pApplet.rect(pos.x - BLOCK_SIZE / 2, pos.y - BLOCK_SIZE / 2 - 10, (stamina / 10f) / 2f, 5);

        if (state.equals("STOP")) {
            pApplet.image(SpriteManager.getImage(characterImage, 1),
                    pos.x - BLOCK_SIZE / 2, pos.y - BLOCK_SIZE / 2, BLOCK_SIZE, BLOCK_SIZE);
        } else {
            pApplet.image(SpriteManager.getImage(characterImage, tick / 10 % 4),
                    pos.x - BLOCK_SIZE / 2, pos.y - BLOCK_SIZE / 2, BLOCK_SIZE, BLOCK_SIZE);
        }
        pApplet.fill(0);
        pApplet.textSize(10);
        pApplet.text(name, pos.x - 20, pos.y + 40);

        if (isHit) {
            pApplet.fill(255, 0, 0, 200);
            pApplet.rect(pos.x - BLOCK_SIZE / 2, pos.y - BLOCK_SIZE / 2, BLOCK_SIZE, BLOCK_SIZE);
            isHit = false;
        }

        if (state.equals("ATTACK") && !isTime) {
            time = 0;
            isAttack = true;
        }

        if (state.equals("SPECIAL") && !isTime) {
            time = 0;
            isSpecial = true;
        }
        renderAttack(pApplet);
        renderSpecial(pApplet);
    }

    private void renderAttack(PApplet pApplet) {
        if (isAttack && !(state.equals("MOVE") || state.equals("SWIFT"))) {
            time++;
            attackTick++;

            pApplet.imageMode(PConstants.CENTER);
            switch (attackDirection) {
                case PLAYER_DOWN:
                    pApplet.image(SpriteManager.getImage(PUNCH_DOWN, attackTick / 2 % 4),
                            pos.x, pos.y + BLOCK_SIZE / 2, PUNCH_HEIGHT, PUNCH_WIDTH);
                    break;
                case PLAYER_UP:
                    pApplet.image(SpriteManager.getImage(PUNCH_UP, attackTick / 2 % 4),
                            pos.x, pos.y - BLOCK_SIZE / 2, PUNCH_HEIGHT, PUNCH_WIDTH);

                    break;
                case PLAYER_LEFT:
                    pApplet.image(SpriteManager.getImage(PUNCH_LEFT, attackTick / 2 % 4),
                            pos.x - BLOCK_SIZE / 2, pos.y, PUNCH_WIDTH, PUNCH_HEIGHT);
                    break;
                case PLAYER_RIGHT:
                    pApplet.image(SpriteManager.getImage(PUNCH_RIGHT, attackTick / 2 % 4),
                            pos.x + BLOCK_SIZE / 2, pos.y, PUNCH_WIDTH, PUNCH_HEIGHT);
                    break;
            }

            if (attackTick > 15) {
                attackTick = 0;
                isAttack = false;
            }
        }
    }

    private void renderSpecial(PApplet pApplet) {
        if (isSpecial && !state.equals("MOVE")) {
            System.out.println("attack special");
            time++;
            attackTick++;
            int shakeX = (int) (Math.random() * 10 - 5);
            int shakeY = (int) (Math.random() * 10 - 5);
            switch (attackDirection) {
                case PLAYER_DOWN:
                    pApplet.image(SpriteManager.getImage(FIRE_ATTACK_DOWN + attackTick / 5 % 2),
                            pos.x - BLOCK_SIZE / 2 + shakeX, pos.y - BLOCK_SIZE / 2 + shakeY + BLOCK_SIZE, BLOCK_SIZE, BLOCK_SIZE);
                    pApplet.image(SpriteManager.getImage(FIRE_ATTACK_DOWN + attackTick / 5 % 2),
                            pos.x - BLOCK_SIZE / 2 + shakeX, pos.y - BLOCK_SIZE / 2 + shakeY + BLOCK_SIZE * 1.5f, BLOCK_SIZE, BLOCK_SIZE);
                    pApplet.image(SpriteManager.getImage(FIRE_ATTACK_DOWN + attackTick / 5 % 2),
                            pos.x - BLOCK_SIZE / 2 + shakeX, pos.y - BLOCK_SIZE / 2 + shakeY + BLOCK_SIZE * 2, BLOCK_SIZE, BLOCK_SIZE);
                    pApplet.image(SpriteManager.getImage(FIRE_ATTACK_DOWN + attackTick / 5 % 2),
                            pos.x - BLOCK_SIZE / 2 + shakeX, pos.y - BLOCK_SIZE / 2 + shakeY + BLOCK_SIZE * 2.5f, BLOCK_SIZE, BLOCK_SIZE);
                    pApplet.image(SpriteManager.getImage(FIRE_ATTACK_DOWN + attackTick / 5 % 2),
                            pos.x - BLOCK_SIZE / 2 + shakeX, pos.y - BLOCK_SIZE / 2 + shakeY + BLOCK_SIZE * 3, BLOCK_SIZE, BLOCK_SIZE);
                    pApplet.image(SpriteManager.getImage(FIRE_ATTACK_DOWN + attackTick / 5 % 2),
                            pos.x - BLOCK_SIZE / 2 + shakeX, pos.y - BLOCK_SIZE / 2 + shakeY + BLOCK_SIZE * 3.5f, BLOCK_SIZE, BLOCK_SIZE);
                    pApplet.image(SpriteManager.getImage(FIRE_ATTACK_DOWN + attackTick / 5 % 2),
                            pos.x - BLOCK_SIZE / 2 + shakeX, pos.y - BLOCK_SIZE / 2 + shakeY + BLOCK_SIZE * 4f, BLOCK_SIZE, BLOCK_SIZE);
                    break;

                case PLAYER_UP:
                    pApplet.image(SpriteManager.getImage(FIRE_ATTACK_UP + attackTick / 5 % 2),
                            pos.x - BLOCK_SIZE / 2 + shakeX, pos.y - BLOCK_SIZE / 2 + shakeY - BLOCK_SIZE, BLOCK_SIZE, BLOCK_SIZE);
                    pApplet.image(SpriteManager.getImage(FIRE_ATTACK_UP + attackTick / 5 % 2),
                            pos.x - BLOCK_SIZE / 2 + shakeX, pos.y - BLOCK_SIZE / 2 + shakeY - BLOCK_SIZE * 1.5f, BLOCK_SIZE, BLOCK_SIZE);
                    pApplet.image(SpriteManager.getImage(FIRE_ATTACK_UP + attackTick / 5 % 2),
                            pos.x - BLOCK_SIZE / 2 + shakeX, pos.y - BLOCK_SIZE / 2 + shakeY - BLOCK_SIZE * 2, BLOCK_SIZE, BLOCK_SIZE);
                    pApplet.image(SpriteManager.getImage(FIRE_ATTACK_UP + attackTick / 5 % 2),
                            pos.x - BLOCK_SIZE / 2 + shakeX, pos.y - BLOCK_SIZE / 2 + shakeY - BLOCK_SIZE * 2.5f, BLOCK_SIZE, BLOCK_SIZE);
                    pApplet.image(SpriteManager.getImage(FIRE_ATTACK_UP + attackTick / 5 % 2),
                            pos.x - BLOCK_SIZE / 2 + shakeX, pos.y - BLOCK_SIZE / 2 + shakeY - BLOCK_SIZE * 3, BLOCK_SIZE, BLOCK_SIZE);
                    pApplet.image(SpriteManager.getImage(FIRE_ATTACK_UP + attackTick / 5 % 2),
                            pos.x - BLOCK_SIZE / 2 + shakeX, pos.y - BLOCK_SIZE / 2 + shakeY - BLOCK_SIZE * 3.5f, BLOCK_SIZE, BLOCK_SIZE);
                    pApplet.image(SpriteManager.getImage(FIRE_ATTACK_UP + attackTick / 5 % 2),
                            pos.x - BLOCK_SIZE / 2 + shakeX, pos.y - BLOCK_SIZE / 2 + shakeY - BLOCK_SIZE * 4, BLOCK_SIZE, BLOCK_SIZE);
                    break;

                case PLAYER_LEFT:
                    pApplet.image(SpriteManager.getImage(FIRE_ATTACK_LEFT + attackTick / 5 % 2),
                            pos.x - BLOCK_SIZE / 2 + shakeX - BLOCK_SIZE, pos.y - BLOCK_SIZE / 2 + shakeY, BLOCK_SIZE, BLOCK_SIZE);
                    pApplet.image(SpriteManager.getImage(FIRE_ATTACK_LEFT + attackTick / 5 % 2),
                            pos.x - BLOCK_SIZE / 2 + shakeX - BLOCK_SIZE * 1.5f, pos.y - BLOCK_SIZE / 2 + shakeY, BLOCK_SIZE, BLOCK_SIZE);
                    pApplet.image(SpriteManager.getImage(FIRE_ATTACK_LEFT + attackTick / 5 % 2),
                            pos.x - BLOCK_SIZE / 2 + shakeX - BLOCK_SIZE * 2, pos.y - BLOCK_SIZE / 2 + shakeY, BLOCK_SIZE, BLOCK_SIZE);
                    pApplet.image(SpriteManager.getImage(FIRE_ATTACK_LEFT + attackTick / 5 % 2),
                            pos.x - BLOCK_SIZE / 2 + shakeX - BLOCK_SIZE * 2.5f, pos.y - BLOCK_SIZE / 2 + shakeY, BLOCK_SIZE, BLOCK_SIZE);
                    pApplet.image(SpriteManager.getImage(FIRE_ATTACK_LEFT + attackTick / 5 % 2),
                            pos.x - BLOCK_SIZE / 2 + shakeX - BLOCK_SIZE * 3, pos.y - BLOCK_SIZE / 2 + shakeY, BLOCK_SIZE, BLOCK_SIZE);
                    pApplet.image(SpriteManager.getImage(FIRE_ATTACK_LEFT + attackTick / 5 % 2),
                            pos.x - BLOCK_SIZE / 2 + shakeX - BLOCK_SIZE * 3.5f, pos.y - BLOCK_SIZE / 2 + shakeY, BLOCK_SIZE, BLOCK_SIZE);
                    pApplet.image(SpriteManager.getImage(FIRE_ATTACK_LEFT + attackTick / 5 % 2),
                            pos.x - BLOCK_SIZE / 2 + shakeX - BLOCK_SIZE * 4, pos.y - BLOCK_SIZE / 2 + shakeY, BLOCK_SIZE, BLOCK_SIZE);

                    break;

                case PLAYER_RIGHT:
                    pApplet.image(SpriteManager.getImage(FIRE_ATTACK_RIGHT + attackTick / 5 % 2),
                            pos.x - BLOCK_SIZE / 2 + shakeX + BLOCK_SIZE, pos.y - BLOCK_SIZE / 2 + shakeY, BLOCK_SIZE, BLOCK_SIZE);
                    pApplet.image(SpriteManager.getImage(FIRE_ATTACK_RIGHT + attackTick / 5 % 2),
                            pos.x - BLOCK_SIZE / 2 + shakeX + BLOCK_SIZE * 1.5f, pos.y - BLOCK_SIZE / 2 + shakeY, BLOCK_SIZE, BLOCK_SIZE);
                    pApplet.image(SpriteManager.getImage(FIRE_ATTACK_RIGHT + attackTick / 5 % 2),
                            pos.x - BLOCK_SIZE / 2 + shakeX + BLOCK_SIZE * 2, pos.y - BLOCK_SIZE / 2 + shakeY, BLOCK_SIZE, BLOCK_SIZE);
                    pApplet.image(SpriteManager.getImage(FIRE_ATTACK_RIGHT + attackTick / 5 % 2),
                            pos.x - BLOCK_SIZE / 2 + shakeX + BLOCK_SIZE * 2.5f, pos.y - BLOCK_SIZE / 2 + shakeY, BLOCK_SIZE, BLOCK_SIZE);
                    pApplet.image(SpriteManager.getImage(FIRE_ATTACK_RIGHT + attackTick / 5 % 2),
                            pos.x - BLOCK_SIZE / 2 + shakeX + BLOCK_SIZE * 3, pos.y - BLOCK_SIZE / 2 + shakeY, BLOCK_SIZE, BLOCK_SIZE);
                    pApplet.image(SpriteManager.getImage(FIRE_ATTACK_RIGHT + attackTick / 5 % 2),
                            pos.x - BLOCK_SIZE / 2 + shakeX + BLOCK_SIZE * 3.5f, pos.y - BLOCK_SIZE / 2 + shakeY, BLOCK_SIZE, BLOCK_SIZE);
                    pApplet.image(SpriteManager.getImage(FIRE_ATTACK_RIGHT + attackTick / 5 % 2),
                            pos.x - BLOCK_SIZE / 2 + shakeX + BLOCK_SIZE * 4, pos.y - BLOCK_SIZE / 2 + shakeY, BLOCK_SIZE, BLOCK_SIZE);
                    break;
            }

            if (attackTick > 15) {
                attackTick = 0;
                isSpecial = false;
            }
        }
    }

    @Override
    public void onUpdate(Camera camera) {
        int imageNum = (characterImage / 10) * 10;

        if (state.equals("MOVE") || state.equals("SWIFT")) {
            switch (direction) {
                case "UP": {
                    characterImage = imageNum;
                    y -= (speed / 3f);
                    break;
                }
                case "DOWN": {
                    characterImage = imageNum + 1;
                    y += (speed / 3f);
                    break;
                }
                case "LEFT": {
                    characterImage = imageNum + 2;
                    x -= (speed / 3f);
                    break;
                }
                case "RIGHT": {
                    characterImage = imageNum + 3;
                    x += (speed / 3f);
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

        if (charX < 0) {
            charX += MAPSIZE;
        }

        if (charY < 0) {
            charY += MAPSIZE;
        }

        if (me) {
            pApplet.fill(0, 0, 200);
            pApplet.ellipse(charX / 10f, charY / 10f, CHARACTER, CHARACTER);
        } else {
            pApplet.fill(0, 0, 0);
            pApplet.ellipse(charX / 10f, charY / 10f, CHARACTER, CHARACTER);
        }
    }

    public void playAttackSound() {
        SoundManager.play(SOUND_PUNCH, 0);
    }

    public void playFireAttackSound() {
        SoundManager.play(SOUND_FIRE, 0);
    }

    private void playHitSound() {
        int tick = (int) (Math.random() * 3);
        SoundManager.play(SOUND_HIT, tick);
    }

    public void onStateChange(String updateState) {
        if (!state.equals(updateState)) {
            switch (updateState) {
                case "ATTACK":
                    playAttackSound();
                    break;
                case "SPECIAL":
                    playFireAttackSound();
                    break;
            }
        }
    }

    public void onHpChange(int updateHp) {
        if (hp > updateHp) {
            playHitSound();
        }
        if (hp < updateHp) {
            SoundManager.play(SOUND_HP, 0);
        }
    }

    public void onManaChange(int updateMana) {
        if (mana < updateMana) {
            SoundManager.play(SOUND_MANA, 0);
        }
    }

    public int getCharacterImage() {
        return characterImage;
    }

    public void setCharacterImage(int characterImage) {
        this.characterImage = characterImage;
    }

    public void setMe(boolean me) {
        this.me = me;
    }

    public boolean getMe() {
        return me;
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

    public boolean isSpecial() {
        return isSpecial;
    }

    public void setSpecial(boolean special) {
        isSpecial = special;
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

    public void setTired(boolean tired) {
        isTired = tired;
    }

    public void setAttackDirection(String attackDirection) {
        this.attackDirection = attackDirection;
    }
}

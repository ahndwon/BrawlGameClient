package state;

import Models.User;
import Models.View;
import Utils.Constants;
import Utils.Util;
import dwon.SpriteManager;
import processing.core.PApplet;

public class Map extends View {
    private int[] map;
    private int lenX = 0;
    private int lenY = 0;
    private User user;
    float userX;
    float userY;
    private int tick;


    public Map() {

    }

    public Map(int[] map) {
        this.map = map;
    }

    private void checkCenter() {

        for (int i = 0; i < map.length; i++) {
            if (Util.getPosXByIndex(i) == (int) user.getX())
                userX = (Constants.WINDOW_SIZE_X - 200) / 2 - user.getX();
            if (Util.getPosYByIndex(i) == (int) user.getY())
                userY = Constants.WINDOW_SIZE_Y / 2 - user.getY();
        }

    }


    @Override
    public void render(PApplet pApplet) {
        tick++;
//        if(user.getX() >=0 && user.getX()<= 800) {
        for (int i = 0; i < map.length; i++) {
            if (map[i] == 0) {
                pApplet.image(SpriteManager.getImage(Constants.GRASS), Util.getPosXByIndex(i), Util.getPosYByIndex(i), Constants.BLOCK_SIZE, Constants.BLOCK_SIZE);
            } else if (map[i] == 1) {
                pApplet.image(SpriteManager.getImage(Constants.SLOW_TILE),
                        Util.getPosXByIndex(i), Util.getPosYByIndex(i), Constants.BLOCK_SIZE, Constants.BLOCK_SIZE);
            } else {
                pApplet.image(SpriteManager.getImage(Constants.GRASS),
                        Util.getPosXByIndex(i), Util.getPosYByIndex(i), Constants.BLOCK_SIZE, Constants.BLOCK_SIZE);
                pApplet.image(SpriteManager.getImage(Constants.POTION, tick / 10 % 7),
                        Util.getPosXByIndex(i), Util.getPosYByIndex(i), Constants.BLOCK_SIZE, Constants.BLOCK_SIZE);
            }
        }
    }

    @Override
    public void onUpdate() {

    }

    public int getLenX() {
        return lenX;
    }

    public void setLenX(int lenX) {
        this.lenX = lenX;
    }

    public int getLenY() {
        return lenY;
    }

    public void setLenY(int lenY) {
        this.lenY = lenY;
    }

    public int[] getMap() {
        return map;
    }

    public void setMap(int[] map) {
        this.map = map;
    }

    public void setUser(User user) {
        this.user = user;
        checkCenter();
    }

    public float getUserX() {
        return userX;
    }

    public float getUserY() {
        return userY;
    }
}

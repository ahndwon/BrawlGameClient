package state;

import Models.Camera;
import Models.User;
import Models.Vector2D;
import Models.View;
import Utils.Constants;
import Utils.Util;
import dwon.SpriteManager;
import processing.core.PApplet;

public class Map extends View implements Constants {
    private int[] map;
    private int lenX;
    private int lenY;
    private int tick;
    private Vector2D[] pos;
    private Camera camera;
    private boolean isLoad;

    public Map() {

    }

    public Map(int[] map) {
        this.map = map;
        pos = new Vector2D[map.length];
    }

    @Override
    public void render(PApplet pApplet) {
        tick++;

        for (int i = 0; i < map.length; i++) {
            pos[i] = camera.getWorldToScreen(Util.getPosXByIndex(i), Util.getPosYByIndex(i));

            if (map[i] == 0) {
                pApplet.image(SpriteManager.getImage(Constants.GRASS),
                        pos[i].x, pos[i].y,
                        Constants.BLOCK_SIZE, Constants.BLOCK_SIZE);
            } else if (map[i] == 2) {
                pApplet.fill(0, 255, 0);
                pApplet.image(SpriteManager.getImage(Constants.GRASS), pos[i].x,
                        pos[i].y, Constants.BLOCK_SIZE, Constants.BLOCK_SIZE);
                pApplet.image(SpriteManager.getImage(Constants.HEAL_POTION, tick / 3 % 7),
                        pos[i].x, pos[i].y, Constants.BLOCK_SIZE,
                        Constants.BLOCK_SIZE);
            } else if (map[i] == 3) {
                pApplet.image(SpriteManager.getImage(Constants.GRASS), pos[i].x,
                        pos[i].y, Constants.BLOCK_SIZE, Constants.BLOCK_SIZE);
//                pApplet.image(SpriteManager.getImage(Constants.MANA_POTION, tick / 10 % 7),
//                        pos[i].x, pos[i].y, Constants.BLOCK_SIZE,
//                        Constants.BLOCK_SIZE);
                pApplet.image(SpriteManager.getImage(Constants.MANA_POTION, 0),
                        pos[i].x, pos[i].y, Constants.BLOCK_SIZE,
                        Constants.BLOCK_SIZE);
            } else {
                pApplet.fill(0, 0, 255);
                pApplet.image(SpriteManager.getImage(Constants.SLOW_TILE),
                        pos[i].x, pos[i].y, Constants.BLOCK_SIZE, Constants.BLOCK_SIZE);
            }
            pApplet.textSize(10);
            pApplet.fill(0);
        }

    }

    public void minimapRender(PApplet pApplet) {

        pApplet.fill(0);
        pApplet.strokeWeight(3);
        pApplet.rect(0, 0, 160, 160);
        for (int i = 0; i < map.length; i++) {
            if (map[i] == 0) {
                pApplet.image(SpriteManager.getImage(Constants.GRASS),
                        Util.getPosXByIndexForMiniMap(i), Util.getPosYByIndexForMiniMap(i),
                        5, 5);
            } else if (map[i] == 2) {
                pApplet.fill(0, 255, 0);
                pApplet.image(SpriteManager.getImage(Constants.GRASS),
                        Util.getPosXByIndexForMiniMap(i), Util.getPosYByIndexForMiniMap(i),
                        MINI_MAP_BLOCK_SIZE, MINI_MAP_BLOCK_SIZE);
                pApplet.image(SpriteManager.getImage(Constants.HEAL_POTION, tick / 10 % 7),
                        Util.getPosXByIndexForMiniMap(i), Util.getPosYByIndexForMiniMap(i),
                        MINI_MAP_BLOCK_SIZE, MINI_MAP_BLOCK_SIZE);

            } else if (map[i] == 3) {
                pApplet.image(SpriteManager.getImage(Constants.GRASS),
                        Util.getPosXByIndexForMiniMap(i), Util.getPosYByIndexForMiniMap(i),
                        MINI_MAP_BLOCK_SIZE, MINI_MAP_BLOCK_SIZE);
                pApplet.image(SpriteManager.getImage(Constants.MANA_POTION, 0),
                        Util.getPosXByIndexForMiniMap(i), Util.getPosYByIndexForMiniMap(i),
                        MINI_MAP_BLOCK_SIZE, MINI_MAP_BLOCK_SIZE);
            } else {
                pApplet.fill(0, 0, 255);
                pApplet.image(SpriteManager.getImage(Constants.SLOW_TILE),
                        Util.getPosXByIndexForMiniMap(i), Util.getPosYByIndexForMiniMap(i),
                        MINI_MAP_BLOCK_SIZE, MINI_MAP_BLOCK_SIZE);
            }
        }
        pApplet.strokeWeight(0);
    }

    @Override
    public void onUpdate(Camera camera) {
        this.camera = camera;
    }

    public void replaceIndex(int index, int message) {
        map[index] = message;
    }

    public int getLenX() {
        return lenX;
    }

    public boolean isLoad() {
        return isLoad;
    }

    public void setLoad(boolean load) {
        isLoad = load;
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
}

package state;

import Models.Camera;
import Models.User;
import Models.Vector2D;
import Models.View;
import Utils.Constants;
import Utils.Util;
import dwon.SpriteManager;
import processing.core.PApplet;

public class Map extends View {
    private int[] map;
    private int lenX;
    private int lenY;
    private User user;
    int userX;
    int userY;
    private int tick;
    private Vector2D[] pos;
    private float x, y;
    private Camera camera;


    public Map() {

    }




    public Map(int[] map) {
        this.map = map;
        pos = new Vector2D[map.length];
    }

    private void checkCenter() {

        for(int i = 0; i < map.length; i++){
            if(Util.getPosXByIndex(i) == (int) user.getX())
                userX = (int) ((Constants.WINDOW_SIZE_X - 200) / 2 - user.getX());
            if(Util.getPosYByIndex(i) == (int) user.getY())
                userY = (int) (Constants.WINDOW_SIZE_Y / 2 - user.getY());
        }

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
                    pApplet.image(SpriteManager.getImage(Constants.POTION, tick / 10 % 7),
                            pos[i].x, pos[i].y, Constants.BLOCK_SIZE,
                            Constants.BLOCK_SIZE);

                } else {
                    pApplet.fill(0, 0, 255);
                    pApplet.image(SpriteManager.getImage(Constants.SLOW_TILE), pos[i].x,
                            pos[i].y, Constants.BLOCK_SIZE, Constants.BLOCK_SIZE);


                }

                pApplet.textSize(10);
                pApplet.fill(0);
        }
    }


    public void replaceIndex(int index, int message) {
        map[index] = message;
    }
    @Override
    public void onUpdate(Camera camera) {
       this.camera = camera;
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

//    public void setUser(User user) {
//        this.user = user;
////        checkCenter();
//    }

    public float getUserX(){
        return  userX;
    }

    public float getUserY(){
        return userY;
    }

    public void setUserX(int userX) {
        this.userX = userX;
    }

    public void setUserY(int userY) {
        this.userY = userY;
    }
}

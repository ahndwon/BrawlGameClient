package state;

import Models.View;
import Utils.Constants;
import Utils.Util;
import processing.core.PApplet;

public class Map extends View {
    private int[] map;

    public Map() {

    }

    public Map(int[] map) {
        this.map = map;
    }


    @Override
    public void render(PApplet pApplet) {

        for (int i = 0; i < map.length; i++) {
            if (map[i] == 0) {
                pApplet.fill(124, 124, 124);
            } else if (map[i] == 1) {
                pApplet.fill(0, 255, 0);
            } else {
                pApplet.fill(0, 0, 255);
            }
            pApplet.rect(Util.getPosXByIndex(i), Util.getPosYByIndex(i), Constants.BLOCK_SIZE, Constants.BLOCK_SIZE);
        }
    }

    @Override
    public void onUpdate() {

    }


    public int[] getMap() {
        return map;
    }

    public void setMap(int[] map) {
        this.map = map;
    }

}

package state;

import Models.View;
import processing.core.PApplet;

public class Map extends View {
    private int[] map;

    public Map() {

    }

    @Override
    public void render(PApplet pApplet) {

    }

    @Override
    public void onUpdate() {

    }

    public Map(int[] map) {
        this.map = map;
    }


    public int[] getMap() {
        return map;
    }

    public void setMap(int[] map) {
        this.map = map;
    }

}

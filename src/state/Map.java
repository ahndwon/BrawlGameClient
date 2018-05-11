package state;

public class Map {
    private int[] map;
    private float x;
    private float y;

    public Map() {

    }
    public Map(int[] map, float x, float y) {
        this.map = map;
        this.x = x;
        this.y = y;
    }

    public int[] getMap() {
        return map;
    }

    public void setMap(int[] map) {
        this.map = map;
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
}

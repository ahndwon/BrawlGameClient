package Windows;

import Models.Vector2D;

public class Connect {
    private String name;
    private int character;
    private Vector2D position;


    public Connect() {
        position = new Vector2D(0, 0);
    }

    public Connect(String name, int character, Vector2D position) {
        this.name = name;
        this.character = character;
        this.position = position;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getCharacter() {
        return character;
    }

    public void setCharacter(int character) {
        this.character = character;
    }

    public Vector2D getPosition() {
        return position;
    }

    public void setPosition(Vector2D position) {
        this.position = position;
    }
}

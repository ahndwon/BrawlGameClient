package Windows;

import Utils.Util;
import dwon.SpriteManager;
import processing.core.PApplet;

public class Window extends PApplet {
    @Override
    public void settings() {
        size(1600, 1600);
    }

    @Override
    public void setup() {
        loadImage();
    }


    @Override
    public void draw() {
        background(0);

    }

    public void loadImage() {
        SpriteManager.loadSprite(this, Util.USER_DOWN, "./image/image.png", 0, 0, 32, 32, 3);
        SpriteManager.loadSprite(this, Util.USER_LEFT, "./image/image.png", 1, 0, 32, 32, 3);
        SpriteManager.loadSprite(this, Util.USER_RIGHT, "./image/image.png", 2, 0, 32, 32, 3);
        SpriteManager.loadSprite(this, Util.USER_UP, "./image/image.png", 3, 0, 32, 32, 3);
    }
}

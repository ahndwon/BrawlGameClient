package models;

import processing.core.PApplet;
import processing.core.PImage;

public class Button extends View {
    private String text;
    private PImage image;
    private PImage clickedImage;
    private boolean isClicked;

    public Button(float x, float y, float width, float height) {
        this.setPos(new Vector2D(x, y));
        this.setSize(new Vector2D(width, height));
        this.isClicked = false;
    }

    @Override
    public void render(PApplet pApplet) {
        if (!isClicked && image != null) {
            pApplet.image(image, getPos().x, getPos().y, getSize().x, getSize().y);
        } else if (isClicked && clickedImage != null) {
            pApplet.image(clickedImage,getPos().x, getPos().y, getSize().x, getSize().y);
        } else {
            pApplet.rect(getPos().x, getPos().y, getSize().x, getSize().y);
        }
    }

    @Override
    public void onUpdate(Camera camera) {

    }

    public void setText(String text) {
        this.text = text;
    }

    public void setImage(PImage image) {
        this.image = image;
    }

    public void setClickedImage(PImage clickedImage) {
        this.clickedImage = clickedImage;
    }

    public boolean isClicked() {
        return isClicked;
    }

    public void setClicked(boolean clicked) {
        isClicked = clicked;
    }
}

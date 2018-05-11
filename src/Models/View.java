package Models;

import processing.core.PApplet;

public abstract class View implements Cloneable{
    private Vector2D pos;
    private Vector2D size;
    private OnClickListener onClickListener;

    public View() {

    }

    interface OnClickListener {
        void onClick(View view);
    }

    public abstract void render(PApplet pApplet);
    public abstract void onUpdate();

    public void update() {
        if (onClickListener != null)
            onClickListener.onClick(this);
        onUpdate();
    }

    public Vector2D getPos() {
        return pos;
    }

    public void setPos(Vector2D pos) {
        this.pos = pos;
    }

    public int getWidth() {
        return (int) size.x;
    }

    public void setWidth(int width) {
        size.setX(width);
    }

    public int getHeight() {
        return (int) size.y;
    }

    public void setHeight(int height) {
        size.setY(height);
    }

    public Vector2D getSize() {
        return size;
    }

    public void setOnClickListener(OnClickListener listener) {
        onClickListener = listener;
    }

    public void setSize(Vector2D size) {
        this.size = size;
    }

    @Override
    public View clone() {
        try { return (View) super.clone();
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
        }return null;
    }
}

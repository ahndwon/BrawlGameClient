package Windows;

import Models.User;
import Utils.KeyEventManager;
import Utils.Util;
import dwon.SpriteManager;
import processing.core.PApplet;

public class Window extends PApplet {
    private User user = new User(100, 100, "yun", Util.USER_DOWN, 100, Util.USER_STOP);
    private KeyEventManager keyEventManager = new KeyEventManager();
    private Communicator communicator;
    @Override
    public void settings() {
        size(800, 600);
    }

    @Override
    public void setup() {
        communicator = new Communicator("192.168.11.71", 5000);
        communicator.connect();

        communicator.setReceiverListener(new ReceiverListener() {
            @Override
            public void onMapReceive() {

            }
        });

        loadImage();

        keyEventManager.addPressListener(37, new KeyEventManager.PressListener() {
            @Override
            public void onPress(boolean isOnPress, long duration) {
                user.setDirection(Util.USER_LEFT);
                user.setX(user.getX() - 3);
            }
        });

        keyEventManager.addPressListener(39, new KeyEventManager.PressListener() {
            @Override
            public void onPress(boolean isOnPress, long duration) {
                user.setDirection(Util.USER_RIGHT);
                user.setX(user.getX() + 3);
            }
        });

        keyEventManager.addPressListener(38, new KeyEventManager.PressListener() {
            @Override
            public void onPress(boolean isOnPress, long duration) {
                user.setDirection(Util.USER_UP);
                user.setY(user.getY() - 3);
            }
        });

        keyEventManager.addPressListener(40, new KeyEventManager.PressListener() {
            @Override
            public void onPress(boolean isOnPress, long duration) {
                user.setDirection(Util.USER_DOWN);
                user.setY(user.getY() + 3);
            }
        });

        keyEventManager.addPressListener(32, new KeyEventManager.PressListener() {
            @Override
            public void onPress(boolean isOnPress, long duration) {
                user.setDirection(Util.USER_DOWN);
                user.setY(user.getY() + 3);
            }
        });


    }


    @Override
    public void draw() {
        background(0);

        keyEventManager.update();
        user.render(this);

    }

    public void keyPressed() {
        keyEventManager.setPress(keyCode);
    }

    public void keyReleased() {
        keyEventManager.setRelease(keyCode);
    }

    public void loadImage() {
        SpriteManager.loadSprite(this, Util.USER_DOWN, "./image/image.png", 32, 32, new int[]{0, 1, 2, 1});
        SpriteManager.loadSprite(this, Util.USER_LEFT, "./image/image.png", 32, 32, new int[]{12, 13, 14, 13});
        SpriteManager.loadSprite(this, Util.USER_RIGHT, "./image/image.png", 32, 32, new int[]{24, 25, 26, 25});
        SpriteManager.loadSprite(this, Util.USER_UP, "./image/image.png", 32, 32, new int[]{36, 37, 38, 37});
    }
}

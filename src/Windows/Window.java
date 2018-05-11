package Windows;

import Models.User;
import Utils.Constants;
import Utils.KeyEventManager;
import dwon.SpriteManager;
import processing.core.PApplet;
import state.Hit;
import state.Map;
import state.Move;
import state.Update;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


public class Window extends PApplet implements Constants {
    private User user = new User(100, 100, "yun", PLAYER_DOWN, 100, 10, USER_STOP);
    private KeyEventManager keyEventManager = new KeyEventManager();
    private Communicator communicator;
    private Map myMap;
    private List<User> users;

    @Override
    public void settings() {
        size(800, 600);
    }

    @Override
    public void setup() {
        communicator = new Communicator("192.168.11.71", 5000);
        communicator.connect();
        users =  Collections.synchronizedList(new ArrayList<>());
        users.add(user);
        communicator.setReceiverListener(new ReceiverListener() {
            @Override
            public void onMapReceive(Map map) {
                myMap = map;
            }

            @Override
            public void onHitReceive(Hit hit) {
                user.setHit(true);
            }

            @Override
            public void onUpdate(Update update) {
                for (User user : users) {
                    if (user.getName().equals(update.getUser())) {
                        user.setX(update.getX());
                        user.setY(update.getY());
                        user.setDirection(update.getDirection());
                        user.setHp(update.getHp());
                        user.setScore(update.getScore());
                        user.setState(update.getState());
                    } else {
                        users.add(new User(update.getX(), update.getY(), update.getUser(), update.getDirection(), update.getHp(), update.getScore(), update.getState()));
                    }
                }
//                user.setX(update.getX());
//                user.setY(update.getY());
//                user.setHp(update.getHp());
//                user.setScore(update.getScore());
            }
        });

        loadImage();

        keyEventManager.addPressListener(37, new KeyEventManager.PressListener() {
            @Override
            public void onPress(boolean isOnPress, long duration) {
                communicator.moveSend(new Move("LEFT"));
                user.setDirection(PLAYER_LEFT);
                user.setX(user.getX() - 3);

            }
        });

        keyEventManager.addPressListener(39, new KeyEventManager.PressListener() {
            @Override
            public void onPress(boolean isOnPress, long duration) {
                communicator.moveSend(new Move("RIGHT"));
                user.setDirection(PLAYER_RIGHT);
                user.setX(user.getX() + 3);

            }
        });

        keyEventManager.addPressListener(38, new KeyEventManager.PressListener() {
            @Override
            public void onPress(boolean isOnPress, long duration) {
                communicator.moveSend(new Move("UP"));
                user.setDirection(PLAYER_UP);
                user.setY(user.getY() - 3);

            }
        });

        keyEventManager.addPressListener(40, new KeyEventManager.PressListener() {
            @Override
            public void onPress(boolean isOnPress, long duration) {
                communicator.moveSend(new Move("DOWN"));
                user.setDirection(PLAYER_LEFT);
                user.setY(user.getY() + 3);

            }
        });

        keyEventManager.addPressListener(32, new KeyEventManager.PressListener() {
            @Override
            public void onPress(boolean isOnPress, long duration) {
//                communicator.attackSend();
//                user.setDirection(PLAYER_DOWN);
//                user.setY(user.getY() + 3);
            }
        });


        keyEventManager.addReleaseListener(32, new KeyEventManager.ReleaseListener() {
            @Override
            public void onRelease(long duration) {
                communicator.attackSend();
            }
        });

    }

    @Override
    public void draw() {
        background(0);
        myMap.render(this);
        for (User user : users) {
            user.onUpdate();
            user.render(this);
        }
        keyEventManager.update();
    }

    public void keyPressed() {
        keyEventManager.setPress(keyCode);
    }

    public void keyReleased() {
        keyEventManager.setRelease(keyCode);
    }

    public void loadImage() {
        SpriteManager.loadSprite(this, USER_DOWN, "./image/image.png", 32, 32, new int[]{0, 1, 2, 1});
        SpriteManager.loadSprite(this, USER_LEFT, "./image/image.png", 32, 32, new int[]{12, 13, 14, 13});
        SpriteManager.loadSprite(this, USER_RIGHT, "./image/image.png", 32, 32, new int[]{24, 25, 26, 25});
        SpriteManager.loadSprite(this, USER_UP, "./image/image.png", 32, 32, new int[]{36, 37, 38, 37});
        SpriteManager.loadImage(this, HAMMER_UP, "./image/up.png");
        SpriteManager.loadImage(this, HAMMER_DOWN, "./image/down.png");
        SpriteManager.loadImage(this, HAMMER_RIGHT, "./image/right.png");
        SpriteManager.loadImage(this, HAMMER_LEFT, "./image/left.png");

    }
}

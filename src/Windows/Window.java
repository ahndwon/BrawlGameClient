package Windows;

import Models.User;
import Utils.Communicator;
import Utils.CommunicatorListener;
import Utils.Constants;
import Utils.KeyEventManager;
import dwon.SpriteManager;
import processing.core.PApplet;
import state.Hit;
import state.Map;
import state.Move;
import state.Update;

import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;


public class Window extends PApplet implements Constants {
    private User user = new User(100, 100, "yun", PLAYER_DOWN, 100, 10, USER_STOP);
    private KeyEventManager keyEventManager = new KeyEventManager();
    private Communicator communicator;
    private Map myMap;
    private List<String> userNames;
    private ConcurrentHashMap<String, User> userLibrary;
    private boolean isSame;

    @Override
    public void settings() {
        size(800, 600);
    }

    @Override
    public void setup() {
        communicator = new Communicator("192.168.11.71", 5000);
        communicator.connect(user);
        userLibrary = new ConcurrentHashMap<>();
        userLibrary.putIfAbsent(user.getName(), user);
        userNames = new CopyOnWriteArrayList<>(userLibrary.keySet());

        communicator.setOnCommunicatorListener(new CommunicatorListener() {
            @Override
            public void onMapReceive(Map map) {
                myMap = map;
            }

            @Override
            public void onHitReceive(Hit hit) {
                user.setAttack(true);
            }

            @Override
            public void onUpdate(List<Update> updates) {
                for (Update u :
                        updates) {
                    if (!userLibrary.containsKey(u.getUser()))
                        userNames.add(u.getUser());

                    userLibrary.putIfAbsent(u.getUser(), new User(u.getX(), u.getY(),
                            u.getUser(), u.getDirection(), u.getHp(), u.getScore(), u.getState()));

                    if (userLibrary.containsKey(u.getUser())) {
                        User user = userLibrary.get(u.getUser());
                        user.setX(u.getX());
                        user.setY(u.getY());
                        user.setHp(u.getHp());
                        user.setDirection(u.getDirection());
                        user.setScore(u.getScore());
                        user.setState(u.getState());
                    }
                }
                System.out.println("userLibrary: " + userLibrary.keySet());
                System.out.println("userNames: " + userNames.size());
            }
        });

        loadImage();

        keyEventManager.addPressListener(LEFT, (isOnPress, duration) -> {
            communicator.sendMove(new Move("LEFT"));
            user.setDirection(PLAYER_LEFT);
            user.setState(USER_MOVE);
//            user.setX(user.getX() - 3);
        });

        keyEventManager.addPressListener(RIGHT, (isOnPress, duration) -> {
            communicator.sendMove(new Move("RIGHT"));
            user.setDirection(PLAYER_RIGHT);
            user.setState(USER_MOVE);
//            user.setX(user.getX() + 3);
        });

        keyEventManager.addPressListener(UP, (isOnPress, duration) -> {
            communicator.sendMove(new Move("UP"));
            user.setDirection(PLAYER_UP);
            user.setState(USER_MOVE);
//            user.setY(user.getY() - 3);
        });

        keyEventManager.addPressListener(DOWN, (isOnPress, duration) -> {
            communicator.sendMove(new Move("DOWN"));
            user.setDirection(PLAYER_DOWN);
            user.setState(USER_MOVE);
//            user.setY(user.getY() + 3);
        });

        keyEventManager.addReleaseListener(LEFT, duration -> {
            communicator.sendStop();
            user.setState("STOP");
        });

        keyEventManager.addReleaseListener(RIGHT, duration -> {
            communicator.sendStop();
            user.setState("STOP");
        });

        keyEventManager.addReleaseListener(UP, duration -> {
            communicator.sendStop();
            user.setState("STOP");
        });

        keyEventManager.addReleaseListener(DOWN, duration -> {
            communicator.sendStop();
            user.setState("STOP");
        });


        keyEventManager.addPressListener(SHIFT, (isOnPress, duration) -> {
//                communicator.sendAttack();
//                user.setDirection(PLAYER_DOWN);
//                user.setY(user.getY() + 3);
            if (isOnPress) {
                user.setAttack(true);
                communicator.sendAttack();
            }

        });

        keyEventManager.addReleaseListener(SHIFT, duration -> {
            communicator.sendStop();
        });
    }

    @Override
    public void draw() {
        background(0);
        myMap.render(this);
        for (String user : userNames) {
            userLibrary.get(user).onUpdate();
            userLibrary.get(user).render(this);
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
        SpriteManager.loadSprite(this, FIST, "./image/super_dragon_fist_effect.png", 0, 0,
                192, 192, 6);

    }
}

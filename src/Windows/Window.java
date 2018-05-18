package Windows;

import Models.Camera;
import Models.UI;
import Models.User;
import Utils.Communicator;
import Utils.CommunicatorListener;
import Utils.Constants;
import Utils.KeyEventManager;
import dwon.SpriteManager;
import processing.core.PApplet;
import state.*;

import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;


public class Window extends PApplet implements Constants {
    private User user = new User(100, 100, "yun", PLAYER_DOWN, 100, 10, USER_STOP, true);
    private KeyEventManager keyEventManager = new KeyEventManager();
    private Communicator communicator;
    private Map myMap;
    private List<String> userNames;
    private ConcurrentHashMap<String, User> userLibrary;
    private UI ui;
    private int i = 0;
    private Camera camera;
    private char imageNum;

    @Override
    public void settings() {
        size(WINDOW_SIZE_X, WINDOW_SIZE_Y);
    }

    @Override
    public void setup() {
        communicator = new Communicator("localhost", 5000);
        communicator.connect(user);
        userLibrary = new ConcurrentHashMap<>();
        userLibrary.putIfAbsent(user.getName(), user);
        userNames = new CopyOnWriteArrayList<>(userLibrary.keySet());
        ui = new UI(userLibrary, userNames);

        user.setMe(true);
        camera = new Camera();

        communicator.setOnCommunicatorListener(new CommunicatorListener() {
            @Override
            public void onMapReceive(Map map) {
                myMap = map;
//                myMap.setUser(user);
            }


            @Override
            public void onHitReceive(Hit hit) {
                if (userLibrary.containsKey(hit.getTo())) {
                    userLibrary.get(hit.getTo()).setHit(true);
                }
            }

            @Override
            public void onUpdate(List<Update> updates) {
                for (Update u :
                        updates) {
                    if (!userLibrary.containsKey(u.getUser()))
                        userNames.add(u.getUser());

                    userLibrary.putIfAbsent(u.getUser(), new User(u.getX(), u.getY(),
                            u.getUser(), u.getDirection(), u.getHp(), u.getScore(), u.getState()));
                    if (i == 0) {
                        myMap.setUserX((int) (400 - user.getX()));
                        myMap.setUserY((int) (300 - user.getY()));
                        i++;
                    }

                    if (userLibrary.containsKey(u.getUser())) {
                        User user = userLibrary.get(u.getUser());
                        user.setX(u.getX());
                        user.setY(u.getY());
                        user.setHp(u.getHp());
                        user.setDirection(u.getDirection());
                        user.setScore(u.getScore());
                        user.setState(u.getState());
                        user.setSpeed(u.getSpeed());
                        user.setPosX((myMap.getLenX()));
                        user.setPosY((myMap.getLenY()));
                        System.out.println("speed : " + u.getSpeed());

                    }
                }
                System.out.println("userLibrary: " + userLibrary.keySet());
                System.out.println("userNames: " + userNames.size());
            }

            @Override
            public void onKillReceive(Kill kill) {
                if (userLibrary.containsKey(kill.getTo())) {
                    ui.addKiller(kill.getFrom(), kill.getTo());
                }
            }

            @Override
            public void onMapCorrectReceive(int index, int message) {
                myMap.replaceIndex(index, message);
            }
        });


        loadImage();

        keyEventManager.addPressListener(LEFT, (isOnPress, duration) -> {
            communicator.sendMove(new Move("LEFT"));
            user.setDirection(PLAYER_LEFT);
            user.setState(USER_MOVE);
            myMap.setLenX(myMap.getLenX() + PLAYER_SPEED * 2);

        });

        keyEventManager.addPressListener(RIGHT, (isOnPress, duration) -> {
            communicator.sendMove(new Move("RIGHT"));
            user.setDirection(PLAYER_RIGHT);
            user.setState(USER_MOVE);
            myMap.setLenX(myMap.getLenX() - PLAYER_SPEED * 2);

        });

        keyEventManager.addPressListener(UP, (isOnPress, duration) -> {
            communicator.sendMove(new Move("UP"));
            user.setDirection(PLAYER_UP);
            user.setState(USER_MOVE);
            myMap.setLenY(myMap.getLenY() + PLAYER_SPEED * 2);

        });

        keyEventManager.addPressListener(DOWN, (isOnPress, duration) -> {
            communicator.sendMove(new Move("DOWN"));
            user.setDirection(PLAYER_DOWN);
            user.setState(USER_MOVE);
            myMap.setLenY(myMap.getLenY() - PLAYER_SPEED * 2);

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


        keyEventManager.addPressListener(32, (isOnPress, duration) -> {
//                communicator.sendAttack();
//                user.setDirection(PLAYER_DOWN);
//                user.setY(user.getY() + 3);
            if (isOnPress) {
                user.setAttack(true);
                communicator.sendAttack();
            }

        });

        keyEventManager.addReleaseListener(32, duration -> {
            communicator.sendStop();
        });
    }

    @Override
    public void draw() {
        background(255);

        if (myMap.isLoad()) {

            camera.position.x = user.getX() - (WINDOW_SIZE_X - 200) / 2;
            camera.position.y = user.getY() - WINDOW_SIZE_Y / 2;


            myMap.onUpdate(camera);

            myMap.render(this);

            for (String user : userNames) {
                userLibrary.get(user).onUpdate(camera);
                userLibrary.get(user).render(this);
            }

            ui.render(this);
            myMap.minimapRender(this);

            keyEventManager.update();


            for (String user : userNames) {
                userLibrary.get(user).miniRender(this);
            }
        } else {
            fill(255);
            fill(0, 255, 0);
            textSize(40);
            text("please choose your character >> ", 150, 100);
            textSize(20);
            fill(0);
            image(SpriteManager.getImage(CHARACTER_ONE_DOWN, 0), 200, 250, 100, 100);
            text("NUM 1", 220, 380);
            image(SpriteManager.getImage(CHARACTER_TWO_DOWN, 0), 400, 250, 100, 100);
            text("NUM 2", 420, 380);
            image(SpriteManager.getImage(CHARACTER_THREE_DOWN, 0), 600, 250, 100, 100);
            text("NUM 3", 620, 380);

        }
    }

    public void keyPressed() {
        if (keyCode == '1') {
            user.setCharacterImage(Constants.CHARACTER_ONE_UP);
            myMap.setLoad(true);
        } else if (keyCode == '2') {
            user.setCharacterImage(Constants.CHARACTER_TWO_UP);
            myMap.setLoad(true);
        } else if (keyCode == '3') {
            user.setCharacterImage(Constants.CHARACTER_THREE_UP);
            myMap.setLoad(true);
        } else {
            keyEventManager.setPress(keyCode);
        }

    }

    public void keyReleased() {
        keyEventManager.setRelease(keyCode);
    }

    public void loadImage() {
        SpriteManager.loadSprite(this, CHARACTER_ONE_DOWN, "./image/image.png", 32, 32, new int[]{0, 1, 2, 1});
        SpriteManager.loadSprite(this, CHARACTER_ONE_LEFT, "./image/image.png", 32, 32, new int[]{12, 13, 14, 13});
        SpriteManager.loadSprite(this, CHARACTER_ONE_RIGHT, "./image/image.png", 32, 32, new int[]{24, 25, 26, 25});
        SpriteManager.loadSprite(this, CHARACTER_ONE_UP, "./image/image.png", 32, 32, new int[]{36, 37, 38, 37});

        SpriteManager.loadSprite(this, CHARACTER_TWO_DOWN, "./image/image.png", 32, 32, new int[]{3, 4, 5, 3});
        SpriteManager.loadSprite(this, CHARACTER_TWO_LEFT, "./image/image.png", 32, 32, new int[]{15, 16, 17, 16});
        SpriteManager.loadSprite(this, CHARACTER_TWO_RIGHT, "./image/image.png", 32, 32, new int[]{27, 28, 29, 28});
        SpriteManager.loadSprite(this, CHARACTER_TWO_UP, "./image/image.png", 32, 32, new int[]{39, 40, 41, 40});

        SpriteManager.loadSprite(this, CHARACTER_THREE_DOWN, "./image/image.png", 32, 32, new int[]{6, 7, 8, 7});
        SpriteManager.loadSprite(this, CHARACTER_THREE_LEFT, "./image/image.png", 32, 32, new int[]{18, 19, 20, 19});
        SpriteManager.loadSprite(this, CHARACTER_THREE_RIGHT, "./image/image.png", 32, 32, new int[]{30, 31, 32, 31});
        SpriteManager.loadSprite(this, CHARACTER_THREE_UP, "./image/image.png", 32, 32, new int[]{42, 43, 44, 43});

        SpriteManager.loadSprite(this, FIST, "./image/super_dragon_fist_effect.png", 0, 0,
                192, 192, 6);
        SpriteManager.loadImage(this, GRASS, "./image/grass.png");
        SpriteManager.loadImage(this, SLOW_TILE, "./image/tiles.png", 1, 1, 32, 32);
        SpriteManager.loadSprite(this, POTION, "./image/potion.png", 0, 0, 50, 63, 7);

    }
}

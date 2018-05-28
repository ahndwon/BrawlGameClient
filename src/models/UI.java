package models;

import utils.Constants;
import dwon.SpriteManager;
import processing.core.PApplet;
import processing.core.PConstants;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

public class UI implements utils.Constants {
    private ConcurrentHashMap<String, User> users;
    private List<String> userNames;
    private List<Integer> delete = new ArrayList<>();

    private List<String> victim = new ArrayList<>();
    private List<String> killer = new ArrayList<>();
    private int arrow;

    private Button muteButton;

    public UI(ConcurrentHashMap<String, User> userLibrary, List<String> userNames) {
        users = userLibrary;
        this.userNames = userNames;
        muteButton = new Button(0, WINDOW_SIZE_Y - BLOCK_SIZE,
                BLOCK_SIZE, BLOCK_SIZE);
        muteButton.setImage(SpriteManager.getImage(BUTTON_UNMUTE));
        muteButton.setClickedImage(SpriteManager.getImage(BUTTON_MUTE));
    }

    public void checkUserName(float mx, float my) {
        if (userNames.size() > 5) {
            if (checkUp(mx, my) && arrow > -(30 * (userNames.size() - 5))) {
                arrow -= 3;
            } else if (checkDown(mx, my) && arrow <= 0) {
                arrow += 3;
            }
        }
    }

    private boolean checkUp(float mx, float my) {
        return mx > 810 && mx < 990 && my > 370 && my < 400;
    }

    private boolean checkDown(float mx, float my) {
        return mx > 810 && mx < 990 && my > 560 && my < 590;
    }

    public void render(PApplet pApplet, int tick) {
        pApplet.imageMode(PConstants.CORNER);
        pApplet.translate(0, 0);
        pApplet.image(SpriteManager.getImage(Constants.UI),
                800, 360,
                200, 240);

        for (int i = 0; i < userNames.size(); i++) {
            pApplet.fill(0);
            pApplet.textSize(15);
            pApplet.text(users.get(userNames.get(i)).getName() + ":  ",
                    WINDOW_SIZE_X - 170, -arrow + WINDOW_SIZE_Y - (i * 30 + 55));
            pApplet.text(users.get(userNames.get(i)).getScore(),
                    WINDOW_SIZE_X - 100, -arrow + WINDOW_SIZE_Y - (i * 30 + 55));
        }

        pApplet.fill(255);
        pApplet.rect(820, 560, 160, 30);
        pApplet.rect(820, 370, 160, 30);

        pApplet.image(SpriteManager.getImage(Constants.ARROWUP),
                820, 370,
                160, 30);
        pApplet.image(SpriteManager.getImage(Constants.ARROWDOWN),
                820, 560,
                160, 30);

        pApplet.fill(255);
        pApplet.rect(800, 0, 200, 360);

        pApplet.image(SpriteManager.getImage(Constants.UI),
                800, 0,
                200, 360);

        pApplet.fill(255, 0, 0);
        pApplet.text("KILL  LOG", 870, 30);

        if (killer.size() != 0) {
            for (int i = 0; i < killer.size(); i++) {
                if (i < 10) {
                    pApplet.fill(0);
                    pApplet.text(killer.get(i),
                            WINDOW_SIZE_X - 170, (i * 30 + 55));
                    pApplet.fill(255, 0, 0);
                    pApplet.text(" killed  ",
                            WINDOW_SIZE_X - 120, (i * 30 + 55));
                    pApplet.fill(0);
                    pApplet.text(victim.get(i),
                            WINDOW_SIZE_X - 70, (i * 30 + 55));
                }
            }
        }
        if (delete.size() > 0) {
            if (tick - delete.get(0) == 100) {
                killer.remove(0);
                victim.remove(0);
                delete.remove(0);
            }
        }
        muteButton.render(pApplet);

    }


    public void addKiller(String from, String to, int time) {
//        killers.put(from, to);
        killer.add(from);
        victim.add(to);
        delete.add(time);
    }

    public Button getMuteButton() {
        return muteButton;
    }
}

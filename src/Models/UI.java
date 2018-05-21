package Models;

import Utils.Constants;
import Utils.Util;
import dwon.SpriteManager;
import processing.core.PApplet;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

public class UI implements Utils.Constants {
    private ConcurrentHashMap<String, User> users;
    private List<String> userNames;
    //    private ConcurrentHashMap<String, String> killers = new ConcurrentHashMap<>();
    private List<String> killerNames = new ArrayList<>();
    private List<Integer> delete = new ArrayList<>();
    private List<String> victim = new ArrayList<>();
    private List<String> killer = new ArrayList<>();
    private int arrow;


    public UI(ConcurrentHashMap<String, User> userLibrary, List<String> userNames) {
        users = userLibrary;
        this.userNames = userNames;
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
        pApplet.text("KILLLOG", 870, 30);

        if (killer.size() != 0) {
            for (int i = 0; i < killer.size(); i++) {
                if (i < 10) {
                    pApplet.fill(0);
                    pApplet.text(killer.get(i) + "  kill   " + victim.get(i),
                            WINDOW_SIZE_X - 170, (i * 30 + 55));
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
    }

    public void addKiller(String from, String to, int time) {
//        killers.put(from, to);
        killer.add(from);
        victim.add(to);
        killerNames.add(from);
        delete.add(time);
    }


}

package Models;

import processing.core.PApplet;

import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

public class UI implements Utils.Constants{
    private ConcurrentHashMap<String, User> users;
    private List<String> userNames;

    public UI(ConcurrentHashMap<String, User> userLibrary, List<String> userNames) {
        users = userLibrary;
        this.userNames = userNames;
    }

    public void render(PApplet pApplet) {
        pApplet.fill(255);
        pApplet.rect(800, 0, 200, 600);

        for (int i = 0; i < userNames.size(); i++) {
            pApplet.fill(0);
            pApplet.textSize(15);
            pApplet.text(users.get(userNames.get(i)).getName() + ":  ", WINDOW_SIZE_X - 190, WINDOW_SIZE_Y - (i * 30 + 10));
            pApplet.text(users.get(userNames.get(i)).getScore(), WINDOW_SIZE_X - 110, WINDOW_SIZE_Y - (i * 30 + 10));
        }
    }

}

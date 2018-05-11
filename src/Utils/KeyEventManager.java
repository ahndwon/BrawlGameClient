package Utils;

import processing.core.PApplet;

import java.util.ArrayList;
import java.util.HashMap;

public class KeyEventManager {
    private PApplet pApplet;
    private HashMap<Integer, KeyStruct> keyStructHashMap;

    private class KeyStruct {
        ArrayList<OnPressListener> onPressListeners;
        ArrayList<OnReleaseListener> onReleaseListeners;
        boolean isPressed;
        boolean flag;
        long duration;

        KeyStruct() {
            onPressListeners = new ArrayList<>();
            onReleaseListeners = new ArrayList<>();
        }
    }

    public KeyEventManager(PApplet pApplet) {
        this.pApplet = pApplet;
        this.keyStructHashMap = new HashMap<>();
    }


    public interface OnPressListener {
        void onPress(boolean isFirst, float duration);
    }

    public interface OnReleaseListener {
        void onRelease(float duration);
    }


    public void update() {
        for (KeyStruct struct : keyStructHashMap.values()) {
            if (struct.isPressed) {
                for (OnPressListener listener : struct.onPressListeners) {
                    listener.onPress(struct.flag, System.currentTimeMillis() - struct.duration);
                }
                struct.flag = false;
            } else if (!struct.flag) {
                for (OnReleaseListener listener : struct.onReleaseListeners) {
                    listener.onRelease(System.currentTimeMillis() - struct.duration);
                }
                struct.flag = true;
            }
        }
        if (pApplet.keyPressed) {
            setKeyPress(pApplet.keyCode);
        }
        else {
            for (Integer keyCode : keyStructHashMap.keySet()) {
                setKeyRelease(keyCode);
            }
        }
    }

    public void setKeyPress(int key) {
        if (!keyStructHashMap.containsKey(key)) return;
        if (!keyStructHashMap.get(key).flag) return;
        keyStructHashMap.get(key).isPressed = true;
        keyStructHashMap.get(key).flag = true;
        keyStructHashMap.get(key).duration = System.currentTimeMillis();
    }

    public void setKeyRelease(int key) {
        if (!keyStructHashMap.containsKey(key)) return;
        keyStructHashMap.get(key).isPressed = false;
    }

    public void removeOnPressListener(int key, OnPressListener listener) {
        if (keyStructHashMap.containsKey(key)) {
            keyStructHashMap.get(key).onPressListeners.remove(listener);
        }
    }

    public void removeOnReleaseListener(int key, OnReleaseListener listener) {
        if (keyStructHashMap.containsKey(key)) {
            keyStructHashMap.get(key).onReleaseListeners.remove(listener);
        }
    }

    public void addOnPressListener(int key, OnPressListener listener) {
        keyStructHashMap.putIfAbsent(key, new KeyStruct());
        keyStructHashMap.get(key).onPressListeners.add(listener);
    }

    public void addOnReleaseListener(int key, OnReleaseListener listener) {
        keyStructHashMap.putIfAbsent(key, new KeyStruct());
        keyStructHashMap.get(key).onReleaseListeners.add(listener);
    }
}
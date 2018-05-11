package Utils;

import java.util.ArrayList;
import java.util.HashMap;

public class KeyEventManager {
    private HashMap<Integer, KeyStruct> keys = new HashMap<>();



    public class KeyStruct {
        public ArrayList<PressListener> pressListeners;
        public ArrayList<ReleaseListener> releaseListeners;

        public KeyStruct() {
            pressListeners = new ArrayList<>();
            releaseListeners = new ArrayList<>();
        }

        public boolean isPress;
        public long pressedTime;
        public boolean isOnPress;

    }

    public interface PressListener {
        void onPress(boolean isOnPress, long duration);
    }

    public interface ReleaseListener {
        void onRelease(long duration);
    }


    public void update() {// 떼고 있을때 계속해서 릴리즈가 불리는 것을 방지하기 위해서.
        for (Integer key : keys.keySet()) {

            KeyStruct struct = keys.get(key);

            for (PressListener pressListener : struct.pressListeners) {
                if (struct.isPress) {
                    pressListener.onPress(!struct.isOnPress, System.currentTimeMillis() - struct.pressedTime);
                    struct.isOnPress = true;
                }
            }

            for (ReleaseListener releaseListener : struct.releaseListeners) {
                if (struct.isOnPress && !struct.isPress) {
                    releaseListener.onRelease(System.currentTimeMillis() - struct.pressedTime);
                    struct.isOnPress = false;
                }
            }


//            for (Listener listener : struct.listeners) {
//                if (struct.isPress) {
//                    listener.onPress(!pressedFirst, System.currentTimeMillis() - struct.pressedTime);
//                    struct.isOnPress = true;
//                } else if (struct.isOnPress) {
//                    listener.onRelease(System.currentTimeMillis() - struct.pressedTime);
//                    struct.isOnPress = false;
//                }
//            }
        }

    }

    public void setPress(int key) {
        if (!keys.containsKey(key)) return;
        if (keys.get(key).isPress) return;
        keys.get(key).isPress = true;
        keys.get(key).pressedTime = System.currentTimeMillis();

    }

    public void setRelease(int key) {
        if (!keys.containsKey(key)) return;
        keys.get(key).isPress = false;
    }

    public void addPressListener(int key, PressListener listener) {
        keys.putIfAbsent(key, new KeyStruct());
        keys.get(key).pressListeners.add(listener);
    }

    public void addPressListener(char key, PressListener listener) {
        keys.putIfAbsent((int) key, new KeyStruct());
        keys.get(key).pressListeners.add(listener);
    }

    public void addReleaseListener(int key, ReleaseListener listener) {
        keys.putIfAbsent(key, new KeyStruct());
        keys.get(key).releaseListeners.add(listener);
    }

    public void addReleaseListener(char key, ReleaseListener listener) {
        keys.putIfAbsent((int) key, new KeyStruct());
        keys.get(key).releaseListeners.add(listener);
    }


}

package Utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

public class KeyEventManager {
    private HashMap<Integer, KeyStruct> keys = new HashMap<>();
    private List<Integer> preKeys = new ArrayList<>();

    private class KeyStruct {
        private ArrayList<PressListener> pressListeners;
        private ArrayList<ReleaseListener> releaseListeners;

        private KeyStruct() {
            pressListeners = new ArrayList<>();
            releaseListeners = new ArrayList<>();
        }

        private boolean isPress;
        private long pressedTime;
        private boolean isOnPress;

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
//                    if (preKeys.size() > 0) {
//                        keys.get(preKeys.get(0)).isPress = true;
//                        keys.get(key).pressedTime = System.currentTimeMillis();
//                    }
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
//        for (Integer keyset : keys.keySet()) {
//
//            KeyStruct struct = keys.get(keyset);
//            if (struct.isPress)
//                preKeys.add(key);
//        }

//        if (preKeys.size() < 1) {
            keys.get(key).isPress = true;
            keys.get(key).pressedTime = System.currentTimeMillis();
//        }
    }


    public void setRelease(int key) {
        if (!keys.containsKey(key)) return;
        System.out.println(key);
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

package Windows;

import state.Hit;
import state.Map;
import state.Update;

public interface ReceiverListener {
    void onMapReceive(Map map);
    void onHitReceive(Hit hit);
    void onUpdate(Update update);
}

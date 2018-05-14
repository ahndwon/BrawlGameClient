package Utils;

import state.Hit;
import state.Map;
import state.Update;

import java.util.List;

public interface CommunicatorListener {
    void onMapReceive(Map map);
    void onHitReceive(Hit hit);
    void onUpdate(List<Update> updates);
}

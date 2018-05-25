package utils;

import com.google.gson.JsonObject;
import states.Hit;
import states.Kill;
import states.Map;
import states.Update;

import java.util.List;

public interface CommunicatorListener {
    void onMapReceive(Map map);
    void onHitReceive(Hit hit);
    void onUpdate(List<Update> updates);
    void onKillReceive(Kill kill);
    void onMapCorrectReceive(int index, int message);
    void onRejectReceive(JsonObject jsonObject);
}

package Utils;

import com.google.gson.JsonObject;
import state.Hit;
import state.Kill;
import state.Map;
import state.Update;

import java.net.Socket;
import java.util.List;

public interface CommunicatorListener {
    void onMapReceive(Map map);
    void onHitReceive(Hit hit);
    void onUpdate(List<Update> updates);

    void onKillReceive(Kill kill);

    void onMapCorrectReceive(int index, int message);

    void onRejectReceive(JsonObject jsonObject);
}

package Windows;

import com.google.gson.JsonObject;

public interface CommunicatorListener {
    void addCharacter(JsonObject message);
    void removeCharacter(JsonObject message);
}

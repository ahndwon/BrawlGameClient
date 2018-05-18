package typeAdapter;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;
import state.Update;

import java.io.IOException;

public class UpdateTypeAdapter extends TypeAdapter<Update> {
    @Override
    public void write(JsonWriter jsonWriter, Update update) throws IOException {

    }

    @Override
    public Update read(JsonReader reader) throws IOException {
        Gson gson = new GsonBuilder().create();
        reader.beginObject();
        Update update = new Update();

        while (reader.hasNext()) {
            switch (reader.nextName()) {
                case "user" :
                    update.setUser(reader.nextString());
                    break;
                case "x" :
                    update.setX((float) reader.nextDouble());
                    break;
                case "y":
                    update.setY((float) reader.nextDouble());
                    break;
                case "hp":
                    update.setHp(reader.nextInt());
                    break;
                case "direction":
                    update.setDirection(reader.nextString());
                    break;
                case "score" :
                    update.setScore(reader.nextInt());
                    break;
                case "state":
                    update.setState(reader.nextString());
                    break;
                case "speed" :
                    update.setSpeed(reader.nextInt());
                    break;
            }
        }
        reader.endObject();


        return update;
    }
}

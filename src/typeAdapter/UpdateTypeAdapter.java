package typeAdapter;

import Windows.Update;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;

import java.io.IOException;

public class UpdateTypeAdapter extends TypeAdapter<Update> {
    @Override
    public void write(JsonWriter jsonWriter, Update update) throws IOException {

    }

    @Override
    public Update read(JsonReader reader) throws IOException {
        Gson gson = new GsonBuilder().create();
        JsonArray jsonArray = gson.fromJson(reader, JsonArray.class);
        for (int i = 0; i < jsonArray.size(); i++) {

        }
        reader.beginObject();

        Update update = new Update();

        while (reader.hasNext()) {
            switch (reader.nextName()) {
                case "users" :
                    reader.beginArray();
                    reader.beginObject();
                    break;
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

            }
        }

        return update;
    }
}

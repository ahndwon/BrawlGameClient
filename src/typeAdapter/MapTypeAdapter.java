package typeAdapter;

import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;
import state.Map;

import java.io.IOException;

public class MapTypeAdapter extends TypeAdapter<Map> {
    private Map map;
    @Override
    public void write(JsonWriter jsonWriter, Map map) throws IOException {

    }

    @Override
    public Map read(JsonReader reader) throws IOException {
        reader.beginObject();


        while (reader.hasNext()) {
            switch (reader.nextName()) {
                case "body" :
                    reader.beginObject();
                    break;
                case "map" :
                    reader.beginArray();
                    int[] mapArray = new int[1024];
                    for (int i = 0; i < 1024; i++) {
                        mapArray[i] = reader.nextInt();
                    }
                    map = new Map(mapArray);
                    break;

            }
        }
        reader.endArray();
        reader.endObject();
        return map;
    }
}

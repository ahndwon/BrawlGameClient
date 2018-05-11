package Windows;

import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;

import java.io.IOException;

public class ItemConsumeTypeAdapter extends TypeAdapter<ItemConsume> {
    @Override
    public void write(JsonWriter jsonWriter, ItemConsume itemConsume) throws IOException {

    }

    @Override
    public ItemConsume read(JsonReader reader) throws IOException {
        reader.beginObject();
        ItemConsume itemConsume = new ItemConsume();

        while (reader.hasNext()) {
            switch (reader.nextName()) {
                case "body":
                    reader.beginObject();
                    break;
                case "user":
                    itemConsume.setUserName(reader.nextString());
                    break;
                case "index":
                    itemConsume.setIndex(reader.nextInt());
                    break;
            }

        }

        reader.endObject();
        reader.endObject();
        return itemConsume;
    }
}


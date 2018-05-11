package typeAdapter;

import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;
import state.ItemCreate;

import java.io.IOException;

public class ItemCreateTypeAdapter extends TypeAdapter<ItemCreate> {
    @Override
    public void write(JsonWriter jsonWriter, ItemCreate itemCreate) throws IOException {

    }

    @Override
    public ItemCreate read(JsonReader reader) throws IOException {
        reader.beginObject();

        ItemCreate itemCreate = new ItemCreate();

        while (reader.hasNext()) {
            switch (reader.nextName()) {
                case "body" :
                    reader.beginObject();
                    break;
                case "index" :
                    itemCreate.setIndex(reader.nextInt());
                    break;

            }
        }

        reader.endObject();
        reader.endObject();
        return itemCreate;
    }
}

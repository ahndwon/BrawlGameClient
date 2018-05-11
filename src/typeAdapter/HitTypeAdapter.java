package typeAdapter;


import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;
import state.Hit;

import java.io.IOException;

public class HitTypeAdapter extends TypeAdapter<Hit> {
    @Override
    public void write(JsonWriter writer, Hit hit) throws IOException {
        writer.beginObject();
        writer.name("type").value(hit.getClass().getSimpleName());
        writer.name("body").beginObject();
        writer.name("from").value(hit.getFrom());
        writer.name("to").value(hit.getTo());
        writer.name("damage").value(hit.getDamage());
        writer.endObject();
        writer.endObject();
    }

    @Override
    public Hit read(JsonReader reader) throws IOException {
        reader.beginObject();

        Hit h = new Hit();

        while (reader.hasNext()) {
            switch (reader.nextName()) {

                case "body":
                    reader.beginObject();
                    break;
                case "from":
                    h.setFrom(reader.nextString());
                    break;
                case "to":
                    h.setTo(reader.nextString());
                    break;
                case "damage":
                    h.setDamage(reader.nextInt());
                    break;

            }
        }
        reader.endObject();
        reader.endObject();
        return h;
    }
}

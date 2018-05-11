package typeAdapter;

import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;
import state.Kill;

import java.io.IOException;

public class KillTypeAdapter extends TypeAdapter<Kill> {

    @Override
    public void write(JsonWriter writer, Kill kill) throws IOException {
        writer.beginObject();
        writer.name("type").value(kill.getClass().getSimpleName());
        writer.name("body").beginObject();
        writer.name("from").value(kill.getFrom());
        writer.name("to").value(kill.getTo());
        writer.endObject();
        writer.endObject();
    }

    @Override
    public Kill read(JsonReader reader) throws IOException {
        reader.beginObject();

        Kill k = new Kill();

        while (reader.hasNext()) {
            switch (reader.nextName()) {

                case "body":
                    reader.beginObject();
                    break;
                case "from":
                    k.setFrom(reader.nextString());
                    break;
                case "to":
                    k.setTo(reader.nextString());
                    break;
            }
        }
        reader.endObject();
        reader.endObject();
        return k;
    }
}

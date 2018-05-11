package Windows;

import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;

import java.io.IOException;

public class JoinTypeAdapter extends TypeAdapter<Join> {

    @Override
    public void write(JsonWriter writer, Join join) throws IOException {
        writer.beginObject();
        writer.name("type").value(join.getClass().getSimpleName());
        writer.name("body").beginObject();
        writer.name("user").value(join.getUser());
        writer.endObject();
        writer.endObject();
    }

    @Override
    public Join read(JsonReader jsonReader) throws IOException {
        return null;
    }
}

package Utils;

import Models.User;
import com.google.gson.*;
import state.Hit;
import state.Map;
import state.Move;
import state.Update;
import typeAdapter.HitTypeAdapter;
import typeAdapter.MapTypeAdapter;
import typeAdapter.UpdateTypeAdapter;

import java.io.IOException;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Communicator {
    private String host;
    private int port;
    private Socket socket;
    private JsonObject message;
    private CommunicatorListener listener;

    public Communicator(String host, int port) {
        this.host = host;
        this.port = port;
    }

    public void connect(User user) {
        socket = new Socket();
        InetSocketAddress endPoint = new InetSocketAddress(host, port);
        try {
            socket.connect(endPoint);
            JsonObject jsonObject = new JsonObject();
            jsonObject.addProperty("type", "Join");
            JsonObject body = new JsonObject();
            body.addProperty("user", user.getName());
            jsonObject.add("body", body);
            send(jsonObject);
        } catch (IOException e) {
            e.printStackTrace();
        }

        new Thread(() -> {
            int len;
            byte[] lengthBuf = new byte[2];
            byte[] buf = new byte[3000];
            ByteBuffer byteBuffer = ByteBuffer.allocate(2);

            try {
                while ((len = socket.getInputStream().read(lengthBuf, 0, 2)) != -1) {
                    byteBuffer.put(lengthBuf[0]);
                    byteBuffer.put(lengthBuf[1]);
                    byteBuffer.flip();
                    Short length = byteBuffer.getShort();
                    byteBuffer.clear();
                    System.out.println(length);

                    len = socket.getInputStream().read(buf, 0, length);
                    String str = new String(buf, 0, len);
                    JsonParser jsonParser = new JsonParser();
                    JsonObject jsonObject = (JsonObject) jsonParser.parse(str);
                    System.out.println(jsonObject);

                    String type = jsonObject.get("type").getAsString();

                    Gson gson;

                    switch (type) {

                        case "Map":
                            gson = new GsonBuilder().registerTypeAdapter(Map.class, new MapTypeAdapter()).create();
                            Map map = gson.fromJson(jsonObject.get("body").toString(), Map.class);
                            listener.onMapReceive(map);
                            break;

                        case "HIT":
                            gson = new GsonBuilder().registerTypeAdapter(Hit.class, new HitTypeAdapter()).create();
                            Hit hit = gson.fromJson(jsonObject.get("body").toString(), Hit.class);
                            listener.onHitReceive(hit);
                            break;

                        case "KILL":
                            break;

                        case "Update":
                            gson = new GsonBuilder().registerTypeAdapter(Update.class, new UpdateTypeAdapter()).create();
                            JsonArray userArray = gson.fromJson(jsonObject.get("users"), JsonArray.class);

                            List<Update> updates = new ArrayList<>();
                            for (int i = 0; i < userArray.size(); i++) {
                                updates.add(gson.fromJson(userArray.get(i).toString(), Update.class));
                            }
                            listener.onUpdate(updates);
                            break;
                    }

                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }).start();
    }


    public void setOnCommunicatorListener(CommunicatorListener listener) {
        this.listener = listener;
    }

    public JsonObject getMessage() {
        return message;
    }

    public OutputStream getOutputStream() throws IOException {
        return socket.getOutputStream();
    }

    public void send(JsonObject message) {
        ByteBuffer byteBuffer = ByteBuffer.allocate(2);
        byteBuffer.putShort((short) message.toString().getBytes().length);
        try {
            byteBuffer.flip();
            System.out.println(Arrays.toString(byteBuffer.array()));
            getOutputStream().write(byteBuffer.array());
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            getOutputStream().write(message.toString().getBytes());
            System.out.println(message.toString());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void sendMove(Move move) {

        String direction = move.getDirection();
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("type", "Move");
        JsonObject body = new JsonObject();
        body.addProperty("direction", direction);
        jsonObject.add("body", body);

        send(jsonObject);

    }

    public void sendAttack() {
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("type", "Attack");
        send(jsonObject);
    }

    public void sendStop() {
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("type", "Stop");
        send(jsonObject);
    }

}
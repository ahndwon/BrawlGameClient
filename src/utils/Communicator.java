package utils;

import models.User;
import com.google.gson.*;
import states.*;
import typeAdapter.HitTypeAdapter;
import typeAdapter.KillTypeAdapter;
import typeAdapter.MapTypeAdapter;
import typeAdapter.UpdateTypeAdapter;

import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.nio.ByteBuffer;
import java.util.ArrayList;
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
            sendJoin(user);
        } catch (IOException e) {
            e.printStackTrace();
        }

        new Thread(() -> {
            try (InputStream is = socket.getInputStream();
                 DataInputStream dis = new DataInputStream(is)) {
                while (true) {
                    int len = 0;
                    int left = dis.readUnsignedShort();
                    byte[] buf = new byte[8192];
                    StringBuilder result = new StringBuilder();

                    while (left > 0 && (len = dis.read(buf, 0, Math.min(left, buf.length))) > 0){
                        result.append(new String(buf, 0, len));
                        left -= len;
                    }
                    if (len == -1) {
                        break;
                    }
                    String str = result.toString();
                    Gson gson = new Gson();
                    JsonObject jsonObject = gson.fromJson(str, JsonObject.class);
                    String type = jsonObject.get("type").getAsString();

                    switch (type) {
                        case "Map":
                            gson = new GsonBuilder().registerTypeAdapter(Map.class, new MapTypeAdapter()).create();
                            Map map = gson.fromJson(jsonObject.get("body").toString(), Map.class);
                            listener.onMapReceive(map);
                            break;

                        case "Hit":
                            gson = new GsonBuilder().registerTypeAdapter(Hit.class, new HitTypeAdapter()).create();
                            Hit hit = gson.fromJson(jsonObject.get("body").toString(), Hit.class);
                            listener.onHitReceive(hit);
                            break;

                        case "Kill":
                            gson = new GsonBuilder().registerTypeAdapter(Kill.class, new KillTypeAdapter()).create();
                            Kill kill = gson.fromJson(jsonObject.get("body").toString(), Kill.class);
                            listener.onKillReceive(kill);
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

                        case "Correct":
                            int index = jsonObject.get("index").getAsInt();
                            int message = jsonObject.get("message").getAsInt();
                            listener.onMapCorrectReceive(index, message);
                            break;
                        case "Reject":
                            listener.onRejectReceive(jsonObject);
                            break;
                    }
                }
                disconnect();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }).start();
    }

    public void disconnect() {
        try {
            socket.getInputStream().close();
            socket.getOutputStream().close();
            socket.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
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
//            System.out.println(Arrays.toString(byteBuffer.array()));
            getOutputStream().write(byteBuffer.array());
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            getOutputStream().write(message.toString().getBytes());
//            System.out.println(message.toString());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void sendJoin(User user) {
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("type", "Join");
        JsonObject body = new JsonObject();
        body.addProperty("user", user.getName());
        jsonObject.add("body", body);
        send(jsonObject);
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

    public void sendCharacterImageNum(Image image) {
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("type", "Character");
        JsonObject body = new JsonObject();
        body.addProperty("num", image.getCharacterImage());
        jsonObject.add("body", body);
        send(jsonObject);
    }

    public void sendSpecial() {
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("type", "Special");
        send(jsonObject);
    }

    public void sendSwift() {
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("type", "SWIFT");
        send(jsonObject);
    }
}
package Windows;

import com.google.gson.*;
import state.Map;
import typeAdapter.MapTypeAdapter;

import java.io.IOException;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.nio.ByteBuffer;

public class Communicator {
    private String host;
    private int port;
    private Socket socket;
    private JsonObject message;
    private CommunicatorListener communicatorListener;
    private ReceiverListener receiver;

    public Communicator(String host, int port) {
        this.host = host;
        this.port = port;
    }

    public void setReceiverListener(ReceiverListener listener) {
        this.receiver = listener;
    }

    public void connect() {
        socket = new Socket();
        InetSocketAddress endPoint = new InetSocketAddress(host, port);
        try {
            socket.connect(endPoint);
            JsonObject jsonObject = new JsonObject();
            jsonObject.addProperty("type", "Join");
            JsonObject body = new JsonObject();
            body.addProperty("user", "yun");
            jsonObject.add("body", body);
            send(jsonObject);
        } catch (IOException e) {
            e.printStackTrace();
        }

        new Thread(new Runnable() {
            @Override
            public void run() {
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
                        Gson gson = new GsonBuilder().create();
                        JsonParser jsonParser = new JsonParser();
                        JsonObject jsonObject = (JsonObject) jsonParser.parse(str);
                        System.out.println(jsonObject);


                        if ((jsonObject.get("type").getAsString().equals("Map"))) {
                            Gson gson1 = new GsonBuilder().registerTypeAdapter(Map.class, new MapTypeAdapter()).create();
                            Map map = gson1.fromJson(jsonObject.get("body").toString(), Map.class);
                            receiver.onMapReceive();
                        }


//                        String str = new String(buf, 0, len);
//                        Gson gson = new GsonBuilder().create();
//                        JsonObject jsonObject = gson.fromJson(str, JsonObject.class);
//                        System.out.println(jsonObject);
//
//                        if (jsonObject.get("type").getAsString().equals("connect"))
//                            communicatorListener.addCharacter(jsonObject);
//                        else if(jsonObject.get("type").getAsString().equals("accept")){
//                            JsonArray array = (JsonArray) jsonObject.get("state");
//                            for (int i = 0; i < array.size(); i++) {
//                                JsonObject state = new JsonObject();
//                                state.add("state", array.get(i));
//                                System.out.println(state);
//                                communicatorListener.addCharacter(state);
//                            }
//                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }


    public void setOnCommunicatorListener(CommunicatorListener listener) {
        this.communicatorListener = listener;
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
            getOutputStream().write(byteBuffer.array());
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            getOutputStream().write(message.toString().getBytes());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
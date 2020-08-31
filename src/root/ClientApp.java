package root;

import root.GUI.ChatWindow;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.*;

public class ClientApp {

    private static String name;

    private static ArrayList<ChatWindow.Client> onlineList = new ArrayList<>();
    private static ArrayList<ChatWindow.Client> allClients = new ArrayList<>();


    public static void main(String[] args) throws IOException {
        Socket socket = new Socket("localhost", Server.PORT);
        System.out.println("Connected to server: " + socket);
        DataInputStream in = new DataInputStream(socket.getInputStream());
        DataOutputStream out = new DataOutputStream(socket.getOutputStream());

        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    while (true) {
                        String message = in.readUTF();
                        if (message.startsWith("/authok")) {
                            System.out.println("Authorized");
                            break;
                        }
                    }

                    while (true) {
                        String message = in.readUTF();
                        if (message.startsWith("/name")) {
                            name = message.split("\\s")[1];
                            System.out.println("Имя получено " + name);
                            break;
                        }
                    }

                    while (true) {
                        String message = in.readUTF();
                        if (message.startsWith("/logs")) {
                            String[] logs = message.split("%");
                            for (int i = 1; i < logs.length; i += 2) {
                                allClients.add(new ChatWindow.Client(logs[i], logs[i + 1]));
                            }
                            System.out.println("Логи получены " + allClients.toString());
                            break;
                        }
                    }

                    while (true) {
                        String message = in.readUTF();
                        if (message.startsWith("/online")) {
                            String[] clientsOn = message.split("%");
                            for (int i = 1; i < clientsOn.length; i += 2) {
                                if (!clientsOn[i].equals(name)) {
                                    onlineList.add(new ChatWindow.Client(clientsOn[i], clientsOn[i + 1]));
                                }
                            }
                            System.out.println("Онлайн список получен " + onlineList.toString());
                            break;
                        }
                    }
                    ChatWindow chatWindow = new ChatWindow(allClients, onlineList, name, in, out);

                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }
}
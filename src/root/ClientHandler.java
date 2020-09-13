package root;

import root.GUI.AuthWindow;
import root.GUI.ChatWindow;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.Objects;

public class ClientHandler {
    private AuthService.Record record;
    private Server server;
    private Socket socket;
    private DataInputStream in;
    private DataOutputStream out;

    public DataOutputStream getOut() {
        return out;
    }

    public ClientHandler(Server server, Socket socket) {
        try {
            this.server = server;
            this.socket = socket;
            in = new DataInputStream(socket.getInputStream());
            out = new DataOutputStream(socket.getOutputStream());

            new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        doAuth();
                        readMessage();
                    } catch (IOException e) {
                        e.printStackTrace();
                    } finally {
                        closeConnection();
                    }
                }
            })
                    .start();

        } catch (IOException e) {
            throw new RuntimeException("Client handler was not created");
        }
    }

    public AuthService.Record getRecord() {
        return record;
    }

    public void setRecord(AuthService.Record record) {
        this.record = record;
    }

    public void doAuth() throws IOException {
        AuthWindow authorization = new AuthWindow(server, this);
        while (authorization.isActive()) ;
//        record = authorization.getPossibleRecord();
        server.subscribe(this);
        server.broadcastMessage(String.format("[Сервер]: Клиент %s подключился", record.getName()));
        server.broadcastMessage(String.format("/userOnline %s><%s", record.getName(),record.getAvatar()));
        out.writeUTF("/authok");
        out.writeUTF("/name " + record.getName());
        out.writeUTF("/logs%" + getLogs());
        out.writeUTF("/online%" + server.getClientsOnline());
    }

    public void sendMessage(String message) {
        try {
            out.writeUTF(message);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private String getLogs() {
        StringBuilder logs = new StringBuilder();
        for (AuthService.Record r : server.getAuthService().getRecords()) {
            if (logs.length() == 0) {
                logs.append(r.getName()).append("%").append(r.getAvatar());
            } else {
                logs.append("%").append(r.getName()).append("%").append(r.getAvatar());
            }
        }
        return logs.toString();
    }

    public void readMessage() throws IOException {
        while (true) {
            String message = in.readUTF();
            if (message.contains("/end")) return;
            String taker = getAdressee(message);
            message = formMessage(message);
            if (taker.equals("Общий чат")) {
                server.broadcastMessage(message, this);
            } else {
                server.unicastMessage(message, taker);
            }
        }
    }

    public String getAdressee(String message) {
        StringBuilder txt = new StringBuilder(message);
        txt.setLength(txt.indexOf("/addr"));
        txt.deleteCharAt(0);
        return txt.toString().trim();
    }

    public String formMessage(String message) {
        StringBuilder txt = new StringBuilder(message);
        txt.delete(0, txt.indexOf("/addr") + 5);
        return txt.toString();
    }

    public void closeConnection() {
        server.unsubscribe(this);
        server.broadcastMessage(String.format("[Сервер]: Клиент  %s  отключился", record.getName()), this);
        try {
            in.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            out.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        server.broadcastMessage(String.format("/userOffline %s", record.getName()));
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ClientHandler that = (ClientHandler) o;
        return record.equals(that.record) &&
                server.equals(that.server) &&
                socket.equals(that.socket) &&
                in.equals(that.in) &&
                out.equals(that.out);
    }

    @Override
    public int hashCode() {
        return Objects.hash(record, server, socket, in, out);
    }
}

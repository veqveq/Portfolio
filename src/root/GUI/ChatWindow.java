package root.GUI;

import root.MessageHistoryHandler;

import javax.swing.*;
import java.awt.*;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.util.*;

public class ChatWindow extends JFrame {

    private JLabel chatTitle = new JLabel();
    JTextArea chatHistory = new JTextArea();
    JTextField message = new JTextField();
    private Client chatClient = new Client("Общий чат", "im");
    private Client currentClient = chatClient;

    private ArrayList<Client> onlineList;
    private ArrayList<Client> allClients;

    private String name;
    private UsersList userList;
    private MessageModule messageModule;

    public ChatWindow(ArrayList<Client> allClients, ArrayList<Client> onlineList, String name, DataInputStream in, DataOutputStream out) {
        this.name = name;
        this.allClients = allClients;
        this.onlineList = onlineList;

        setTitle(String.format("Чат. Пользователь: %s", name));
        setBounds(150, 150, 800, 800);
        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);

        JPanel chatWindow = new JPanel();
        JScrollPane scrollHistory = new JScrollPane(chatHistory);
        chatHistory.setEnabled(false);
        chatWindow.setLayout(new BorderLayout());

        this.userList = new UsersList(this);
        this.messageModule = new MessageModule(this, in, out, chatHistory, userList);

        JPanel messagePanel = new JPanel();
        messagePanel.setLayout(new BorderLayout());
        JButton send = new JButton("Send");

        send.addActionListener(new ButtonListener(message, chatHistory, name, this));
        message.addActionListener(new ButtonListener(message, chatHistory, name, this));

        chatTitle.setFont(new Font("1", Font.BOLD, 25));
        chatTitle.setText("Общий чат");
        chatTitle.setHorizontalAlignment(SwingConstants.CENTER);

        messagePanel.add(message, BorderLayout.CENTER);
        messagePanel.add(send, BorderLayout.EAST);

        chatWindow.add(chatTitle, BorderLayout.NORTH);
        chatWindow.add(scrollHistory, BorderLayout.CENTER);
        chatWindow.add(messagePanel, BorderLayout.SOUTH);

        getContentPane().add(chatWindow, BorderLayout.CENTER);
        getContentPane().add(userList, BorderLayout.WEST);

        setVisible(true);
    }

    public MessageModule getMessageModule() {
        return messageModule;
    }

    public ArrayList<Client> getOnlineList() {
        return onlineList;
    }

    public ArrayList<Client> getAllClients() {
        return allClients;
    }

    public String getMessage() {
        return message.getText();
    }

    public String getChatTitle() {
        return chatTitle.getText();
    }

    public Client getCurrentClient() {
        return currentClient;
    }

    @Override
    public String getName() {
        return name;
    }

    protected void setChatTitle(String title) {
        chatTitle.setText(title);
        setCurrentClient(title);
        chatHistory.setText(MessageHistoryHandler.readMessage(name,currentClient));
        userList.cleanMessageCounter(title);
    }

    private void setCurrentClient(String name) {
        for (Client c : allClients) {
            if (c.getName().equals(name)) {
                currentClient = c;
            }
        }
    }

    @Override
    public void dispose() {
        messageModule.sendMessage("/end");
        super.dispose();
    }

    public static class Client {

        private String name;
        private String avatar;

        public Client(String name, String avatar) {
            this.name = name;
            this.avatar = avatar;
        }

        public String getName() {
            return name;
        }

        public String getAvatar() {
            return avatar;
        }

    }
}
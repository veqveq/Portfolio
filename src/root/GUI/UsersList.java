package root.GUI;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class UsersList extends JPanel {

    private JPanel usersListPane;
    private ChatWindow chatWindow;
    private JComboBox<String> changeRoom;
    private ArrayList<UserButton> userArrList = new ArrayList<>();

    public UsersList(ChatWindow chatWindow) {
        this.chatWindow = chatWindow;
        this.usersListPane = new JPanel();

        setLayout(new BorderLayout());
        JLabel userListTitle = new JLabel(String.format("%-10s", "Пользователи:"));
        userListTitle.setFont(new Font("", Font.BOLD, 25));
        userListTitle.setHorizontalAlignment(SwingConstants.CENTER);
        add(userListTitle, BorderLayout.NORTH);

        usersListPane.setLayout(new GridLayout(15, 1));
        add(usersListPane, BorderLayout.CENTER);

        changeRoom = new JComboBox<>();
        fillComboBox();
        add(changeRoom, BorderLayout.SOUTH);
        changeRoom.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                chatWindow.setChatTitle(changeRoom.getSelectedItem().toString());
            }
        });
        removeUserList();
    }

    public JComboBox<String> getChangeRoom() {
        return changeRoom;
    }

    private void fillComboBox(){
        if (changeRoom.getItemCount() != 0){
            changeRoom.removeAllItems();
        }
        changeRoom.addItem("Общий чат");
        for (ChatWindow.Client r : chatWindow.getAllClients()) {
            if (!chatWindow.getName().equals(r.getName())) {
                changeRoom.addItem(r.getName());
            }
        }
    }

    public void setUser(String name, String avatar) {
        UserButton newUserButton = new UserButton(name, avatar, chatWindow);
        usersListPane.add(newUserButton);
        userArrList.add(newUserButton);
        chatWindow.revalidate();
    }

    public void setTitle(String title) {
        JLabel titleList = new JLabel(title);
        titleList.setAlignmentX(JLabel.LEFT);
        titleList.setFont(new Font("1", Font.BOLD, 16));
        usersListPane.add(titleList);
    }

    public void removeUserList() {
        usersListPane.removeAll();
        setTitle("Онлайн: ");

        for (ChatWindow.Client r : chatWindow.getOnlineList()) {
            if (!chatWindow.getName().equals(r.getName())) {
                setUser(r.getName(), r.getAvatar());
            }
        }

        setTitle("Оффлайн: ");

        for (ChatWindow.Client r : chatWindow.getAllClients()) {
            if (checkUserOffline(r.getName()) && !chatWindow.getName().equals(r.getName())) {
                setUser(r.getName(), r.getAvatar());
            }
        }
    }

    private boolean checkUserOffline(String name) {
        for (ChatWindow.Client user : chatWindow.getOnlineList()) {
            if (user.getName().equals(name)) return false;
        }
        return true;
    }

    protected void addMessageCounter(String name) {
        for (UserButton ub : userArrList) {
            if (ub.getUserName().equals(name)) {
                ub.addMissingMessage();
            }
        }
    }

    protected void cleanMessageCounter(String name) {
        for (UserButton ub : userArrList) {
            if (ub.getUserName().equals(name)) {
                ub.delMissingMessage();
            }
        }
    }
}
package root.GUI;

import root.AuthService;
import root.Server;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.*;

public class RegWindow extends JFrame {

    private Server server;
    private JTextField name = new JTextField();
    private JTextField login = new JTextField();
    private JPasswordField password = new JPasswordField();
    private JPasswordField confirmPassword = new JPasswordField();
    private JLabel message = new JLabel(" ");
    private AuthWindow authWindow;
    private String avatarPath = "src\\root\\GUI\\avatars\\pic1.png";
    private JLabel avatarImage = new JLabel();
    private Color avatarIsActivate = new Color(255, 255, 255);
    private Color avatarIsDeactivate = new Color(147, 186, 203);
    private RegWindow thisWindow;

    protected void setAvatarIsActive() {
        avatarImage.setBorder(BorderFactory.createLineBorder(avatarIsActivate, 4));
    }

    protected void setAvatarIsDeactive() {
        avatarImage.setBorder(BorderFactory.createLineBorder(avatarIsDeactivate, 4));
    }

    public RegWindow(Server server, AuthWindow authWindow) {
        thisWindow = this;
        this.server = server;
        this.authWindow = authWindow;
        avatarImage.setHorizontalAlignment(JLabel.CENTER);
        avatarImage.setIcon(new ImageIcon(avatarPath));
        setAvatarIsDeactive();
        avatarImage.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                setAvatarIsActive();
                ChangeAvatarWindow changeAvatar = new ChangeAvatarWindow(thisWindow);
                while (changeAvatar.isActive()) {
                }
            }

            @Override
            public void mousePressed(MouseEvent e) {

            }

            @Override
            public void mouseReleased(MouseEvent e) {

            }

            @Override
            public void mouseEntered(MouseEvent e) {

            }

            @Override
            public void mouseExited(MouseEvent e) {

            }
        });

        avatarImage.setPreferredSize(new Dimension(50, 100));
        message.setHorizontalAlignment(SwingConstants.CENTER);
        authWindow.setVisible(false);
        setTitle("Регистрация нового пользователя");
        setBounds(350, 450, 500, 170);
        setResizable(false);
        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);

        setLayout(new BorderLayout());
        JPanel text = new JPanel(new GridLayout(4, 1));
        JPanel fields = new JPanel(new GridLayout(4, 1));

        name.addActionListener(new textFieldListener("name"));
        login.addActionListener(new textFieldListener("login"));
        password.addActionListener(new textFieldListener("pass"));
        confirmPassword.addActionListener(new textFieldListener("confPass"));

        text.add(new JLabel("Ник"));
        text.add(new JLabel("Логин"));
        text.add(new JLabel("Пароль"));
        text.add(new JLabel("Повторите пароль"));

        fields.add(name);
        fields.add(login);
        fields.add(password);
        fields.add(confirmPassword);

        JButton enter = new JButton("Зарегистрироваться");
        enter.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                addNewClient();
            }
        });

        add(message, BorderLayout.NORTH);
        add(text, BorderLayout.WEST);
        add(fields, BorderLayout.CENTER);
        add(enter, BorderLayout.SOUTH);
        add(avatarImage, BorderLayout.EAST);
        message.setForeground(Color.RED);
        message.setAlignmentX(Component.CENTER_ALIGNMENT);

        setVisible(true);
    }

    private void addNewClient() {
        Set<AuthService.Record> clientsData = server.getAuthService().getRecords();
        for (AuthService.Record r : clientsData) {
            if (name.getText().isBlank() || login.getText().isBlank() || password.getPassword().length == 0 || confirmPassword.getPassword().length == 0) {
                setMessage(String.format("Заполните все поля!", name.getText()));
                return;
            }
            if (r.getLogin().equals(login.getText())) {
                setMessage("Учётная запись уже зарегистрирована! Пожалуйста, авторизуйтесь!");
                login.setText(null);
                password.setText(null);
                confirmPassword.setText(null);
                return;
            }
        }
        if (!comparePass(password.getPassword(), confirmPassword.getPassword())) {
            setMessage(String.format("Пароли не совпадают!", name.getText()));
            confirmPassword.setText(null);
            return;
        }
        server.getAuthService().setRecord(name.getText(), login.getText(), password.getText(),avatarPath);
        server.broadcastMessage(String.format("/userReg %s><%s", name.getText(), avatarPath));
        server.broadcastMessage(String.format("[Сервер]: Клиент %s зарегистрировался", name.getText()));
        authWindow.setLogin(login.getText());
        authWindow.setMessage("Учётная запись создана!");
        dispose();
    }

    private void setMessage(String message) {
        Color color;
        if (message.contains("успешно")) {
            color = new Color(29, 111, 3);

        } else {
            color = new Color(189, 9, 9);
        }
        this.message.setForeground(color);
        this.message.setText(message);
    }


    private class textFieldListener implements ActionListener {
        private String name;

        public textFieldListener(String name) {
            this.name = name;
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            switch (name) {
                case "name":
                    login.grabFocus();
                    break;
                case "login":
                    password.grabFocus();
                    break;
                case "pass":
                    confirmPassword.grabFocus();
                    break;
                default:
                    addNewClient();
                    break;
            }
        }
    }

    public void setAvatarImage(String avatarPath) {
        this.avatarPath = avatarPath;
        this.avatarImage.setIcon(new ImageIcon(this.avatarPath));
    }

    private boolean comparePass(char[] pass1, char[] pass2) {
        if (pass1.length == pass2.length) {
            for (int i = 0; i < pass1.length; i++) {
                if (pass1[i] != pass2[i]) return false;
            }
        } else return false;
        return true;
    }

    private boolean comparePass(String pass, char[] pass2) {
        char[] pass1 = pass.toCharArray();
        if (pass1.length == pass2.length) {
            for (int i = 0; i < pass1.length; i++) {
                if (pass1[i] != pass2[i]) return false;
            }
        } else return false;
        return true;
    }

    @Override
    public void dispose() {
        authWindow.setVisible(true);
        super.dispose();
    }
}
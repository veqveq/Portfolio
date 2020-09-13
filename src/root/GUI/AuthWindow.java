package root.GUI;

import root.AuthService;
import root.ClientHandler;
import root.Server;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class AuthWindow extends JFrame {
    private JTextField login = new JTextField();
    private JPasswordField password = new JPasswordField();
    private JLabel message = new JLabel(" ");
    private Server server;
    private AuthService.Record possibleRecord;
    private boolean connected;
    private ClientHandler ch;

    public AuthWindow(Server server, ClientHandler ch) {
        this.server = server;
        this.ch = ch;
        setTitle("Авторизация");
        setBounds(350, 450, 350, 170);
        setResizable(false);
        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        setLayout(new BoxLayout(getContentPane(), BoxLayout.Y_AXIS));
        add(new JLabel("Логин"));
        login.addActionListener(new textFieldListener("login"));
        password.addActionListener(new textFieldListener("password"));
        add(login);
        add(new JLabel("Пароль"));
        add(password);
        JPanel buttons = new JPanel();
        buttons.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets.right = 20;
        JButton reg = new JButton("Регистрация");
        JButton enter = new JButton("Войти");
        reg.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                registration();
            }
        });
        enter.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                authorization();
            }
        });
        buttons.add(reg, gbc);
        buttons.add(enter);
        add(buttons);
        message.setForeground(Color.RED);
        message.setAlignmentX(Component.CENTER_ALIGNMENT);
        add(message);
        setVisible(true);
        while (!connected) Thread.onSpinWait();
//        loading();
        dispose();
    }

    protected void setMessage(String message) {
        Color color;
        if (message.contains("Авторизация") || message.contains("создана")) {
            color = new Color(29, 111, 3);

        } else {
            color = new Color(189, 9, 9);
        }
        this.message.setForeground(color);
        this.message.setText(message);
    }

    public void setLogin(String login) {
        this.login.setText(login);
    }

    private void authorization() {
        possibleRecord = server.getAuthService().findRecord(login.getText(), password.getText());
        if (possibleRecord != null) {
            if (server.isOccupied(possibleRecord)) {
                setMessage("Вы уже в чате!");
            } else {
                setMessage(String.format("Авторизация выполнена! Привет, %s", possibleRecord.getName()));
                ch.setRecord(possibleRecord);
                connected = true;
            }
        } else {
            setMessage("Неверно указаны логин/пароль!");
            password.setText(null);
        }
    }

    private void registration() {
        new RegWindow(server, this);
        password.grabFocus();
    }

    public AuthService.Record getPossibleRecord() {
        return possibleRecord;
    }

    private class textFieldListener implements ActionListener {
        private String name;

        public textFieldListener(String name) {
            this.name = name;
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            if (name.equals("login")) {
                password.grabFocus();
            } else {
                authorization();
            }
        }
    }

    private void sleeping(int millis) {
        try {
            Thread.currentThread().join(millis);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private void loading() {
        JLabel loading = new JLabel("Загрузка");
        loading.setForeground(new Color(10, 121, 33));
        loading.setHorizontalAlignment(SwingConstants.CENTER);
        add(loading);
        StringBuilder txt = new StringBuilder();
        for (int i = 0; i < 3; i++) {
            sleeping(1000);
            txt.append(loading.getText()).
                    append(" . ");
            loading.setText(txt.toString());
            txt.setLength(0);
        }
    }
}
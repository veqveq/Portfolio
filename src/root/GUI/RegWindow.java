package root.GUI;

import root.AuthService;
import root.Server;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Set;

public class RegWindow extends JFrame {

    private Server server;
    private JTextField name = new JTextField();
    private JTextField login = new JTextField();
    private JPasswordField password = new JPasswordField();
    private JPasswordField confirmPassword = new JPasswordField();
    private JLabel message = new JLabel(" ");
    private AuthWindow authWindow;
    private String avatar = "avatars/pic1.png";


    public RegWindow(Server server, AuthWindow authWindow) {
        this.server = server;
        this.authWindow=authWindow;
        message.setHorizontalAlignment(SwingConstants.CENTER);
        authWindow.setVisible(false);
        setTitle("Регистрация нового пользователя");
        setBounds(350, 450, 500, 170);
        setResizable(false);
        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);

        setLayout(new BorderLayout());
        JPanel text = new JPanel(new GridLayout(4,1));
        JPanel fields = new JPanel(new GridLayout(4,1));

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
        add(text,BorderLayout.WEST);
        add(fields,BorderLayout.CENTER);
        add(enter, BorderLayout.SOUTH);
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
            if (r.getName().equals(name.getText())) {
                setMessage(String.format("Пользователь с ником %s существует! Измените ник", name.getText()));
                name.setText(null);
                return;
            }
            if (r.getLogin().equals(login.getText()) && comparePass(r.getPassword(),password.getPassword())) {
                setMessage("Учётная запись уже зарегистрирована! Пожалуйста, авторизуйтесь!");
                login.setText(null);
                password.setText(null);
                confirmPassword.setText(null);
                return;
            }
        }
        if (!comparePass(password.getPassword(),confirmPassword.getPassword())) {
            setMessage(String.format("Пароли не совпадают!", name.getText()));
            confirmPassword.setText(null);
            return;
        }
        server.getAuthService().setRecord(name.getText(), login.getText(), password.getText());
        server.broadcastMessage(String.format("/userReg %s><%s",name.getText(),avatar));
        server.broadcastMessage(String.format("[Сервер]: Клиент %s зарегистрировался",name.getText()));
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

    private boolean comparePass(char[]pass1,char[]pass2){
        if (pass1.length == pass2.length){
            for (int i = 0; i < pass1.length; i++) {
                if (pass1[i]!=pass2[i]) return false;
            }
        }else return false;
        return true;
    }

    private boolean comparePass(String pass,char[]pass2){
        char[]pass1 = pass.toCharArray();
        if (pass1.length == pass2.length){
            for (int i = 0; i < pass1.length; i++) {
                if (pass1[i]!=pass2[i]) return false;
            }
        }else return false;
        return true;
    }

    @Override
    public void dispose() {
        authWindow.setVisible(true);
        super.dispose();
    }
}
package root.GUI;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ButtonListener implements ActionListener {

    private StringBuilder txt = new StringBuilder();
    private String name;
    private JTextField message;
    private JTextArea history;
    private ChatWindow cw;

    public ButtonListener(JTextField message, JTextArea history, String name, ChatWindow cw) {
        this.message = message;
        this.history = history;
        this.name = name;
        this.cw = cw;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (!message.getText().equals("")) {
            txt.append(history.getText());
            if (!history.getText().isBlank()) txt.append("\n");
            txt.append(String.format("[%s]: %s", name, message.getText()));
            history.setText(txt.toString());
            cw.getMessageModule().sendMessage();
            message.setText("");
            txt.setLength(0);
        }
    }
}

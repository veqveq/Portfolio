package root.GUI;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class UserButton extends JLabel {

    private JLabel userName = new JLabel();
    private JLabel messageCounter = new JLabel();

    public UserButton(String name, String avatar, ChatWindow chatWindow) {
        setBounds(0, 0, 100, 100);
        setBackground(new Color(255, 255, 255, 0));
        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();

        userName.setIcon(new ImageIcon(ChatWindow.class.getResource(avatar)));
        userName.setText(name);
        gbc.anchor = GridBagConstraints.WEST;
        gbc.weightx = 1;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        add(userName,gbc);
        gbc.fill = GridBagConstraints.NONE;
        gbc.weighty = 0;
        gbc.anchor = GridBagConstraints.CENTER;
        add(messageCounter,gbc);

        addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
//                if (isEnabled()) {
                chatWindow.setChatTitle(name);
//                }
            }

            @Override
            public void mousePressed(MouseEvent e) {

            }

            @Override
            public void mouseReleased(MouseEvent e) {

            }

            @Override
            public void mouseEntered(MouseEvent e) {
//                if (isEnabled()) {
                setOpaque(true);
                userName.setFont(new Font("Calibri", Font.BOLD, 16));
                userName.setForeground(new Color(0x4A39AA));
                userName.setBackground(new Color(255, 255, 255, 255));
                setBackground(new Color(255, 255, 255, 255));
//                }
            }

            @Override
            public void mouseExited(MouseEvent e) {
//                if (isEnabled()) {
                userName.setFont(new Font("Calibri", Font.BOLD, 14));
                userName.setForeground(new Color(0, 0, 0));
                userName.setBackground(new Color(255, 255, 255, 0));
                setBackground(new Color(255, 255, 255, 0));
                setOpaque(false);
//                }
            }
        });
    }

    public void addMissingMessage() {
        if (messageCounter.getText().isBlank()) {
            messageCounter.setText("1");
        } else {
            messageCounter.setText(String.valueOf(Integer.parseInt(messageCounter.getText()) + 1));
        }
    }

    public void delMissingMessage() {
        messageCounter.setText("");
    }

    public String getUserName() {
        return userName.getText();
    }
}
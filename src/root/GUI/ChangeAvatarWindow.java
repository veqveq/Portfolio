package root.GUI;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;

public class ChangeAvatarWindow extends JFrame {

    private RegWindow regWindow;


    public ChangeAvatarWindow(RegWindow regWindow) {
        this.regWindow = regWindow;
        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        setBounds(0, 0, 180, 180);
        setResizable(false);
        setLayout(new GridLayout(3, 3));
        Path path = Paths.get("src\\root\\GUI\\avatars");
        File dir = new File(String.valueOf(path));
        for (File file : dir.listFiles()) {
            add(new AvatarButton(file.getPath()));
        }
        setVisible(true);
    }

    public ChangeAvatarWindow(MouseListener mouseListener) {
    }

    private class AvatarButton extends JLabel {
        public AvatarButton(String avatar) {
            setIcon(new ImageIcon(avatar));
            setHorizontalAlignment(JLabel.CENTER);
            addMouseListener(new MouseListener() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    regWindow.setAvatarImage(avatar);
                    regWindow.setAvatarIsDeactive();
                    dispose();
                }

                @Override
                public void mousePressed(MouseEvent e) {

                }

                @Override
                public void mouseReleased(MouseEvent e) {

                }

                @Override
                public void mouseEntered(MouseEvent e) {
                    setBorder(BorderFactory.createEtchedBorder());
                }

                @Override
                public void mouseExited(MouseEvent e) {
                    setBorder(BorderFactory.createEmptyBorder());
                }
            });
        }
    }
}

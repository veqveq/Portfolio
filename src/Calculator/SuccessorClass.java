package Calculator;

import javax.swing.*;

public abstract class SuccessorClass {

    JLabel input;
    JLabel history;

    public SuccessorClass() {
        this.input = MyWindow.input;
        this.history = MyWindow.history;
    }
}

package Calculator;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ButtonListener implements ActionListener {

    @Override
    public void actionPerformed(ActionEvent e) {        //Метод приема действия при нажатии кнопки
        new Action(e.getActionCommand()).action();      //Создать объект класса действие, аргумент - текст на нажатой кнопке. После создания выполняется метод класса
    }
}
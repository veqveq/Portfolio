package Calculator;

import javax.swing.*;

public class ListenMemory extends Listen {                  //Класс слушатель кнопок памяти

    private final Utilities util;
    private JLabel memoryIndicate;

    public ListenMemory(char key, Utilities util) {       //Конструктор класса слушатель кнопок памяти
        super(key);
        this.util = util;
        this.memoryIndicate = MyWindow.memoryIndicate;

    }

    //Метод слушания действий для кнопок работы с памятью
    protected void listen() {
        switch (key) {                                                                                //Проверка множественного условия
            case 'R':                                                                                           //Если 2 символ на кнопке R
                if (!memoryIndicate.getText().equals("")) {                                                    //Если в памяти есть число
                    input.setText(util.formatString(MyWindow.getMemory()));                                 //Записать в поле ввод отформатированное значение переменной память
                }
                break;
            case 'C':                                                                                           //Если 2 символ на кнопке С
                MyWindow.setMemory("0");                                                                          //Очистить память
                memoryIndicate.setText("");                                                          //Скрыть индикатор памяти
                break;
            default:
                char earlySgn = key;                                                                  //Записать в переменную 2 символ с названия кнопки (+/-)
                MyWindow.setMemory(util.calculation(earlySgn, MyWindow.getMemory(), input.getText()));        //Добавить/вычесть в памяти значение из поля ввод
                memoryIndicate.setText("M");                                                           //Включить индикатор памяти
                MyWindow.setRewrite(true);                                                                       //Разрешить перезапись
        }
    }
}
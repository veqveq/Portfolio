package Calculator;

public class ListenNumbs extends Listen {                               //Класс слушатель кнопок с цифрами

    private final StringBuilder text = new StringBuilder();             //Объявление поля с конструктором строк

    public ListenNumbs(char key) {                                    //Конструктор класса слушатель кнопок с цифрами
        super(key);
    }

    @Override
    protected void listen() {
        if (input.getText().equals("0")) {                         //Если в поле ввод стоит только ноль
            MyWindow.setRewrite(true);                                      //Включить перезапись
        }

        if (MyWindow.isRewrite()) {                                         //Если разрешена перезапись
            input.setText(String.valueOf(key));                                //Записать в поле ввод цифру нажатой кнопки
            MyWindow.setRewrite(false);                                 //Запретить перезапись
        } else {                                                            //Иначе
            text.append(input.getText())                           //Считать в конструктор строк значение из поля ввод
                    .append(key);                                           //Добавить к нему цифру нажатой кнопки
            input.setText(text.toString());                        //Записать строку из конструктора в поле ввод
            text.setLength(0);                                              //Очистить конструктор строк
        }
    }
}
package Calculator;

public class ListenSigns extends Listen {                               //Класс слушатель кнопок с символами

    private final StringBuilder text = new StringBuilder();             //Объявление поля с конструктором строк
    private final Utilities util;                                       //Объявление поля с утилитами

    public ListenSigns(char key, Utilities util) {                    //Конструктор класса слушатель символов
        super(key);
        this.util = util;
    }

    //Метод слушания действий для символов
    protected void listen() {
        switch (key) {                                              //Проверка множественного условия
            case '.':                                               //Если нажата кнопка с точкой
                if (MyWindow.isRewrite()) {                             //Если включена перезапись
                    text.append("0").                               //Вписать в конструктор 0
                            append(key);                            //Добавить в конце точку
                    input.setText(text.toString());        //Вписать в поле ввод содержимое конструктора
                    text.setLength(0);                              //Очистить конструктор
                    MyWindow.setRewrite(false);                       //Выключить перезапись
                }
                if (!input.getText().contains(".")) {               //Проверить, есть ли точки в строке ввод
                    text.append(input.getText())                    //Если нет, считать в конструктор строк поле ввод
                            .append(key);                                    //Добавить точку
                    input.setText(text.toString());                 //Записать в поле ввод значение конструктора строк
                    text.setLength(0);                                       //Очистить конструктор
                }
                break;

            case 'C':                                                        //Если нажата кнопка "С"
                input.setText("0");                                 //Стереть информацию из поля ввод
                history.setText("0");                               //Стереть информацию из поля история
                MyWindow.setAnswer("0");                                       //Стереть информацию из переменной ответ
                break;

            case '\u2906':                                                           //Если нажата кнопка <- (стереть)
                if (input.getText().length() > 1) {                         //Если в поле ввод больше одного символа
                    text.append(input.getText()).                           //Считать в конструктор поле ввод
                            deleteCharAt(input.getText().length() - 1);     //Удалить последний символ
                    input.setText(text.toString());                         //Записать в поле ввод значение конструктора
                    text.setLength(0);                                               //Очистить конструктор
                } else {                                                             //Если в поле ввод один и меньше символов
                    input.setText("0");                                     //Записать в поле ввод ноль
                    MyWindow.setRewrite(true);                                        //Разрешить перезапись
                }
                break;

            case '%':
                if (!history.getText().equals("0") &&
                        history.getText().charAt(history.getText().length() - 1) == ' ' &&
                        history.getText().charAt(history.getText().length() - 2) != '=') {
                    char earlySgn = history.getText().charAt(history.getText().length() - 2);
                    input.setText(util.formatString(util.calculation(key, MyWindow.getAnswer(), input.getText())));
                    text.append(history.getText()).
                            append(input.getText());
                    history.setText(text.toString());
                    text.setLength(0);
                    MyWindow.setAnswer(util.calculation(earlySgn, MyWindow.getAnswer(), input.getText()));
                    MyWindow.setRewrite(true);
                    break;
                }

            case 'ₓ':
                if (!history.getText().equals("0") &&
                        history.getText().charAt(history.getText().length() - 1) == ' ' &&
                        history.getText().charAt(history.getText().length() - 2) != '=') {
                    char earlySgn = history.getText().charAt(history.getText().length() - 2);
                    MyWindow.setAnswer(util.calculation(earlySgn, MyWindow.getAnswer(), util.formatString(util.calculation(key, input.getText()))));
                    text.append(history.getText());
                    if (Float.parseFloat(input.getText()) > 0) {
                        text.append("1 / ").
                                append(input.getText());
                    } else {
                        text.append("1 / (").
                                append(input.getText()).
                                append(")");
                    }
                    history.setText(text.toString());
                    input.setText(MyWindow.getAnswer());
                    MyWindow.setRewrite(true);
                    text.setLength(0);
                    break;
                }
                if (input.getText().equals("0")) {
                    input.setText("Ошибка!");
                    history.setText("0");
                    MyWindow.setAnswer("0");
                    MyWindow.setRewrite(true);
                    break;
                }
                MyWindow.setAnswer(util.formatString(util.calculation(key, input.getText())));
                if (Float.parseFloat(input.getText()) > 0) {
                    text.append("1 / ").
                            append(input.getText());
                } else {
                    text.append("1 / (").
                            append(input.getText()).
                            append(")");
                }
                history.setText(text.toString());
                input.setText(MyWindow.getAnswer());
                MyWindow.setRewrite(true);
                text.setLength(0);
                break;

            case '±':
                if (!input.getText().equals("0")) {
                    if (input.getText().charAt(0) != '-') {
                        text.append("-").
                                append(input.getText());
                    } else {
                        text.append(input.getText()).
                                deleteCharAt(0);
                    }
                    input.setText(text.toString());
                    text.setLength(0);
                }
                break;

            case '√':
                if (!history.getText().equals("0") &&
                        history.getText().charAt(history.getText().length() - 1) == ' ' &&
                        history.getText().charAt(history.getText().length() - 2) != '=') {
                    char earlySgn = history.getText().charAt(history.getText().length() - 2);
                    MyWindow.setAnswer(util.calculation(earlySgn, MyWindow.getAnswer(), util.formatString(util.calculation(key, input.getText()))));
                    text.append(history.getText()).
                            append(key).
                            append(input.getText());
                    history.setText(text.toString());
                    input.setText(MyWindow.getAnswer());
                    MyWindow.setRewrite(true);
                    text.setLength(0);
                    break;
                }
                if (Float.parseFloat(input.getText()) < 0) {
                    history.setText("0");
                    input.setText("Ошибка!");
                    MyWindow.setAnswer("0");
                    MyWindow.setRewrite(true);
                    break;
                }
                MyWindow.setAnswer(util.formatString(util.calculation(key, input.getText())));
                text.append(key).
                        append(input.getText());
                history.setText(text.toString());
                input.setText(MyWindow.getAnswer());
                MyWindow.setRewrite(true);
                text.setLength(0);
                break;

            case '∛':
                if (!history.getText().equals("0") &&
                        history.getText().charAt(history.getText().length() - 1) == ' ' &&
                        history.getText().charAt(history.getText().length() - 2) != '=') {
                    text.append(history.getText()).
                            append(key).
                            append(MyWindow.input.getText());
                    char earlySgn = history.getText().charAt(history.getText().length() - 2);
                    MyWindow.setAnswer(util.calculation(earlySgn, MyWindow.getAnswer(), util.formatString(util.calculation(key, input.getText()))));
                    history.setText(text.toString());
                    input.setText(MyWindow.getAnswer());
                    MyWindow.setRewrite(true);
                    text.setLength(0);
                    break;
                }
                MyWindow.setAnswer(util.formatString(util.calculation(key, input.getText())));
                text.append(key).append(input.getText());
                history.setText(text.toString());
                input.setText(MyWindow.getAnswer());
                MyWindow.setRewrite(true);
                text.setLength(0);
                break;

            case '²':
                if (!history.getText().equals("0") &&
                        history.getText().charAt(history.getText().length() - 1) == ' ' &&
                        history.getText().charAt(history.getText().length() - 2) != '=') {
                    text.append(history.getText()).
                            append(input.getText()).
                            append("²");
                    char earlySgn = history.getText().charAt(history.getText().length() - 2);
                    MyWindow.setAnswer(util.calculation(earlySgn, MyWindow.getAnswer(), util.formatString(util.calculation(key, input.getText()))));
                    history.setText(text.toString());
                    input.setText(MyWindow.getAnswer());
                    MyWindow.setRewrite(true);
                    text.setLength(0);
                    break;
                }
                MyWindow.setAnswer(util.formatString(util.calculation(key, input.getText())));
                text.append(input.getText()).append("²");
                history.setText(text.toString());
                input.setText(MyWindow.getAnswer());
                MyWindow.setRewrite(true);
                text.setLength(0);
                break;

            case '=':                                                                                         //Если нажата кнопка =
                if (history.getText().equals("0") && input.getText().equals("0"))           //Если поля история и ввод пустые, то ничего не произойдет
                    break;

                if (!MyWindow.getAnswer().equals("0") &&
                        !history.getText().equals("0") &&
                        !history.getText().contains("=") &&
                        !util.checkSign(String.valueOf(history.getText().charAt(history.getText().length() - 1)))) {
                    text.append(history.getText()).
                            append(" ").
                            append(key).
                            append(" ");
                    history.setText(text.toString());
                    input.setText(MyWindow.getAnswer());
                    break;
                }


                if (!history.getText().equals(input.getText()) &&                                                     //Если содержимое истории не совпадает с содержимым ввода
                        !util.checkSign(String.valueOf(history.getText().charAt(history.getText().length() - 1)))) {  //И в конце поля история не символ
                    history.setText(util.formatString(input.getText()));                                              //Записать в историю содержимое поля ввод
                    MyWindow.setAnswer(input.getText());                                                                    //Записать в переменную ответ содержимое поля ввод
                    MyWindow.setRewrite(true);                                                                                            //Разрешить перезапись
                    break;
                }

                if (history.getText().contains("=")) {
                    if (input.getText().equals("0") ||
                            input.getText().equals(util.formatString(String.valueOf(MyWindow.getAnswer())))) {
                        history.setText(util.formatString(String.valueOf(MyWindow.getAnswer())));                           //Записать в поле история информацию из поля ответ
                    } else {
                        history.setText(util.formatString(String.valueOf(input.getText())));                  //Записать в поле история информацию из поля ответ
                        MyWindow.setAnswer(input.getText());
                    }
                    input.setText("0");                                                                            //Записать в поле ввод ноль
                    MyWindow.setRewrite(true);                                                                                //Разрешить перезапись
                    break;
                }

                if (history.getText().equals("0") && !input.getText().equals("0")) {         //Если в поле история записан ноль, а поле ввод не пустое
                    history.setText(util.formatString(input.getText()));                     //Добавить в поле история отформатированное значение поля ввод
                    MyWindow.setAnswer(input.getText());                                              //Добавить в переменную значение из поля ввод
                    MyWindow.setRewrite(true);                                                                 //Разрешить перезапись
                    break;
                }

                if (util.checkSign(String.valueOf(history.getText().charAt(history.getText().length() - 2)))) {   //Если в поле история на последнем месте записан символ и поле ввод не пустое
                    char earlySgn = history.getText().charAt(history.getText().length() - 2);                     //Записать последний знак в переменную
                    if (input.getText().charAt(0) == '.' && !util.errorDivZero(earlySgn)) {                                //Если в поле ввод на первом месте стоит точка
                        text.append(history.getText()).append("0");                                                        //Считать поле истоия в конструктор и добавить в конце ноль
                        history.setText(text.toString());                                                                  //Записать значение конструктора в поле история
                        text.setLength(0);                                                                                          //Очистить конструкт
                    }
                    if (!util.errorDivZero(earlySgn)) {                                                                                 //Если в выражении не происходит деления на ноль
                    MyWindow.setAnswer(util.calculation(earlySgn, MyWindow.getAnswer(), input.getText())); //Вычислить результат последнего действия и записать результат в поле ответ
                    text.append(MyWindow.history.getText()).                                                                        //Считать в конструктор поле история
                            append(util.formatString(input.getText())).                                                    //Добавить к нему содержимое поля ввод
                            append(" = ");                                                                                          //Добавить знак равно
                    history.setText(text.toString());                                                                      //Записать в поле история значение из конструктора
                    input.setText(util.formatString(String.valueOf(MyWindow.getAnswer())));                                //Записать в поле ввод отформатированное значение из поля ответ
                    text.setLength(0);                                                                                              //Очистить конструктор
                    MyWindow.setRewrite(true);                                                                                      //Разрешить перезапись
                    }
                    break;
                }

            default:                                                             //Если нажата кнопка с арифметическим знаком
                util.inputMathSign(key);                               //Выполнить метод ввод арифметического знака. Аргумент - текст на нажатой кнопке
                MyWindow.setRewrite(true);                                         //Разрешить перезапись
                break;
        }
    }
}
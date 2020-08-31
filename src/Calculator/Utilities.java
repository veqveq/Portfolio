package Calculator;

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;

public class Utilities extends SuccessorClass {

    private final StringBuilder text = new StringBuilder();                   //Объявление поля с конструктором строк
    private final StringBuilder formatString = new StringBuilder();           //Объявление поля с конструктором строк для форматирующего метода



    //Метод ввод арифметического знака
    public void inputMathSign(char sgn) {
        if (!errorDivZero(sgn)) {                                                                          //Если в выражении не происходит деления на ноль
            if (history.getText().equals("0") && !input.getText().equals("0")) {         //Если в поле история записан ноль а поле ввод не пустое
                text.append(formatString(input.getText())).                                       //Считать отформатированное поле ввод в конструктор
                        append(" ").
                        append(sgn).                                                                       //Добавить символ арифметического действия (аргумент метода)
                        append(" ");
                history.setText(text.toString());                                                 //Записать в поле история содержимое конструктора
                MyWindow.setAnswer(input.getText());                                              //Записать в переменную ответ значение поля ввод
                text.setLength(0);                                                                         //Очистить конструктор
            } else {                                                                                       //Иначе, если в поле история не ноль
                if (history.getText().charAt(history.getText().length() - 1) != ' ') {   //Если последний символ в поле история не пробел
                    text.append(history.getText()).                                               //Считать в конструктор поле история
                            append(" ").
                            append(sgn).                                                                   //Добавить символ арифметического действия
                            append(" ");
                    history.setText(text.toString());                                                      //Записать в поле история содержимое конструктора
                    text.setLength(0);                                                                              //Очитстить конструктор
                } else if (history.getText().indexOf('=') != -1) {                                         //Если в поле история встречен знак равно
                    if (input.getText().equals("0") ||                                                     //Если в поле ввод стоит ноль
                            input.getText().equals(formatString(String.valueOf(MyWindow.getAnswer())))) {       //Либо в поле ввод записан ответ
                        text.append(formatString(String.valueOf(MyWindow.getAnswer()))).                                 //Считать поле ответ в конструктор
                                append(" ").
                                append(sgn).                                                                        //Добавить символ арифметического действия
                                append(" ");
                        history.setText(text.toString());                                                  //Записать в поле история содержимое конструктора
                    } else {                                                                                        //Иначе
                        text.append(formatString(String.valueOf(input.getText()))).                        //Записать в конструктор содержимое поля ввод
                                append(" ").
                                append(sgn).                                                                        //Добавить знак арифметического действия
                                append(" ");
                        history.setText(text.toString());                                                  //Записать в поле история содержимое конструктора
                        MyWindow.setAnswer(input.getText());                                                //Записать в переменную ответ содержимое поля ввод
                        input.setText("0");                                                                //Записать в поле ввод ноль
                        MyWindow.setRewrite(true);                                                                    //Разрешить перезапись
                    }
                    text.setLength(0);                                                                              //Очистить конструктор

                } else if (checkSign(String.valueOf(history.getText().charAt(history.getText().length() - 2))) &&   //Если предпоследний знак в поле история является символом
                        history.getText().charAt(history.getText().length() - 2) != sgn &&                          //И этот символ не равен символу на нажатой кнопке
                        (input.getText().equals("0") || input.getText().equals(MyWindow.getAnswer()))) {  //И в поле ввод стоит либо ноль, либо ответ
                    text.append(MyWindow.history.getText()).                                                                          //Считать в конструктор поле история
                            delete(history.getText().length() - 3, history.getText().length()).                     //Удалить последние 3 символа
                            append(" ").
                            append(sgn).                                                                                              //Добавить текущий символ арифметического действия
                            append(" ");
                    history.setText(text.toString());                                                                        //Записать в поле история содержимое конструктора
                    text.setLength(0);                                                                                                //Очистить конструктор
                } else {                                                                                                              //Если не выполнено ни одно предыдущее условие
                    char earlySgn = history.getText().charAt(history.getText().length() - 2);                       //Считать в переменную последний символ в строке
                    if (input.getText().charAt(0) == '.' && !errorDivZero(earlySgn)) {                                       //Если в поле история первый символ точка и нет деления на ноль
                        text.append(history.getText()).append("0");                                                          //Считать в конструктор содержимое истории и добавить ноль
                        history.setText(text.toString());                                                                    //Записать в историю содержимое конструктора
                        text.setLength(0);                                                                                            //Очистить конструктор
                    }
                    MyWindow.setAnswer(calculation(earlySgn, MyWindow.getAnswer(), input.getText()));                               //Выполнить вычисления и записать результат в переменную ответ
                    text.append(history.getText()).                                                                          //Считать в конструктор поле история
                            append(formatString(input.getText())).                                                           //Добавить отформатированное содержимое поля ввод
                            append(" ").
                            append(sgn).                                                                                              //Добавить знак арифметического действия
                            append(" ");
                    history.setText(text.toString());                                                                        //Записать в историю содержимое конструктора
                    input.setText(formatString(String.valueOf(MyWindow.getAnswer())));                                            //Записать в поле ввод содержимое поля ответ
                    text.setLength(0);                                                                                                //Очистить конструктор
                }
            }
        }
    }

    //Метод, проверяющий - является ли символ числом
    public boolean checkSign(String sign) {                     //Аргумент метода - символ
        if (sign.equals(".")) return false;
        for (int i = 0; i <= 9; i++) {                          //Цикл проходит все однозначные числа
            if (sign.equals(String.valueOf(i))) return false;   //Если символ равен однозначному числу - вернуть false
        }
        return true;                                            //Иначе вернуть true
    }

    //Метод выполнения арифметического действия
    public String calculation(char sgn, String val1, String val2) {         //Аргументы - символ действия, поля ответ и ввод
        BigDecimal bigVal1 = new BigDecimal(val1);                          //Инициализировать большое число равное первому числу
        BigDecimal bigVal2 = new BigDecimal(val2);                          //Инициализировать большое число равное второму числу
        BigDecimal result = new BigDecimal(0);                          //Инициализировать большое число для хранения результата
        switch (sgn) {                                                      //Проверка множественного условия
            case '+':                                                       //Если переданный символ - плюс
                result = bigVal1.add(bigVal2);                              //Добавить второе число к первому
                break;
            case '-':                                                               //Если переданный символ - минус
                result = bigVal1.subtract(bigVal2);                                 //Отнять от первого числа второе
                break;
            case 'x':                                                               //Если переданный символ - умножить
                result = bigVal1.multiply(bigVal2);                                 //Умножить первое число на второе
                break;
            case '\u00F7':                                                          //Если переданный символ - разделить
                result = bigVal1.divide(bigVal2, 11, RoundingMode.HALF_UP);     //Разделить первое число на второе c округлением до 10 разрядов
                break;
            case '%':
                result = bigVal2.multiply(bigVal1.divide(new BigDecimal(100), 11, RoundingMode.HALF_UP));
                break;
        }
        return result.toString();                                           //Вернуть ответ
    }

    public String calculation(char sgn, String val1) {         //Аргументы - символ действия, поля ответ и ввод
        BigDecimal bigVal1 = new BigDecimal(val1);                          //Инициализировать большое число равное первому числу
        BigDecimal result = new BigDecimal(0);                          //Инициализировать большое число для хранения результата
        switch (sgn) {                                                      //Проверка множественного условия
            case '√':
                result = countRoot(bigVal1, new BigDecimal(2));
                break;
            case '∛':
                result = countRoot(bigVal1, new BigDecimal(3));
                break;
            case '²':
                result = bigVal1.pow(2, new MathContext(11, RoundingMode.DOWN));
                break;
            case 'ₓ':
                result = new BigDecimal(1).divide(bigVal1, new MathContext(11));
                break;

        }
        return result.toString();                                           //Вернуть ответ
    }

    private BigDecimal countRoot(BigDecimal val1, BigDecimal val2) {
        BigDecimal numb = new BigDecimal(1);
        double res = Math.pow(Math.abs(val1.doubleValue()), 1 / val2.doubleValue());
        MathContext mc = new MathContext(11, RoundingMode.HALF_UP);
        BigDecimal result = new BigDecimal(res, new MathContext(1, RoundingMode.DOWN));
        for (int i = 0; i < 50; i++) {
            result = (((val2.subtract(numb)).multiply(result)).add(val1.divide(result.pow((val2.subtract(numb)).intValue()), mc))).multiply(numb.divide(val2, mc));
        }
        return result.round(mc);
    }


    //Метод для форматирования строки - подавления хвостовых нулей
    public String formatString(String str) {                                //Аргумент метода - произвольная строка, поле для записи отформатированного значения
        char sign;                                                          //Объявление переменной для хранения символа
        if (str.length() > 1 &&                                             //Если длина строки больше одного символа
                (str.contains("."))) {                                      //И есть дробная часть
            do {                                                            //Старт цикла с постусловием
                sign = str.charAt(str.length() - 1);                        //Запись последнего символа строки в переменную
                if (sign == '0') {                                          //Если в переменной записан ноль
                    formatString.append(str).                               //Записать значение строки в конструктор
                            setLength(str.length() - 1);                    //Уменьшить длину строки на единицу
                    str = formatString.toString();                          //Записать значение конструктора в переменную со строкой
                }
            } while (sign == '0');                                           //Цикл выполняется пока в переменной записан ноль
            if (sign == '.') {                                               //Проверка условия. Если в переменной записана точка (подавлены все нули дробной части)
                formatString.append(str).                                    //Записать значение строки в конструктор
                        setLength(str.length() - 1);                         //Уменьшить длину строки на единицу
                str = formatString.toString();                               //Записать значение конструктора в переменную со строкой
            }
            formatString.setLength(0);                                       //Очистить конструктор
        }
        return str;                                                          //Вернуть строковое значение переменной
    }

    //Метод проверки деления на ноль
    public boolean errorDivZero(char sgn) {
        if (sgn == '\u00F7' &&                                              //Если введен знак разделить
                input.getText().equals("0") &&                     //И в поле ввод стоит ноль
                !history.getText().equals("0")) {                  //А поле история не пустое
            history.setText("0");                                  //Записать в поле история ноль
            input.setText("Ошибка! Деление на ноль!");             //В поле ввод вывести сообщение об ошибке
            MyWindow.setRewrite(true);                                      //Включить перезапись
            return true;                                                    //Вернуть true
        }
        return false;
    }
}
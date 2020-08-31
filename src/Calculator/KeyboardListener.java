package Calculator;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class KeyboardListener extends KeyAdapter {

    @Override
    public void keyReleased(KeyEvent e) {           //Переопределение метода приема нажатой кнопки
        String keyName = formString(e);             //Записать в переменную отформатированное имя нажатой кнопки
        if (keyName.length() != 0) {                //Если имя кнопки не нулевое
            new Action(keyName).action();           //Создать объект класса действие, аргумент - текст на нажатой кнопке. После создания выполняется метод класса
        }
    }

    //Метод форматирования константы нажатой кнопки
    private String formString(KeyEvent e) {
        String key = e.getKeyText(e.getKeyCode());              //Записать название константы нажатой кнопки в переменную
        if (key.length() > 1 && key.contains("NumPad")) {       //Если нажата кнопка на NumPad клавиатуре - удалить из названия константы слово NumPad
            key = String.valueOf(key.charAt(key.length() - 1));
        }

        if (new Utilities().checkSign(key)) {    //Если нажатая кнопка является символом
            switch (key) {                       //Проверить множественное условие
                case "Enter":                    //Перевод названия констант в формат, который в последствии поймут слушатели
                case "Equals":
                    key = "=";
                    break;
                case "/":
                case "Slash":
                    key = "÷";
                    break;
                case "Minus":
                    key = "-";
                    break;
                case "*":
                    key = "x";
                    break;
                case "Backspace":
                    key = "⤆";
                    break;
                case "Delete":
                    key = "C";
                    break;
                case "-":
                case ".":
                case "+":
                    break;
                default:                         //Если нажат какой либо другой символ
                    key = null;                  //Имя константы становится пустым
                    break;
            }
        }
        return key;                              //Вернуть отредактированное значение имени константы
    }
}
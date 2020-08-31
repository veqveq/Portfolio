package Calculator;

public class Action{

    private final Utilities util;         //Объявление переменной класса утилиты
    private final String  key;            //Объявление переменной для хранения символа действия

    public Action(String key) {           //Конструктор класса действие
        this.key = key;
        this.util = new Utilities();
    }

    void action(){
            Listen listenButton;                                        //Создать объект класса слушатель
            if (!util.checkSign(key) && !key.equals(".")) {             //Если кнопка не является символом
                listenButton = new ListenNumbs(key.charAt(key.length()-1));                    //Создать класс слушатель цифр - наследника слушателя
            } else if (key.charAt(0) == 'M') {                          //Иначе если первый символ на нажатой кнопке M
                listenButton = new ListenMemory(key.charAt(key.length()-1), util);             //Создать класс слушатель памяти - наследника слушателя
            } else {                                                    //Иначе
                listenButton = new ListenSigns(key.charAt(key.length()-1), util);              //Создать класс слушатель символов - наследника слушателя
            }
            listenButton.listen();                                      //Выполнить метод класса слушатель
        }
}
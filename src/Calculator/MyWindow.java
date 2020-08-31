package Calculator;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyListener;

public class MyWindow extends JFrame {

    //Задание служебных переменных
    private static String answer = "0";                               //Создание переменной для хранения результата вычислений
    private static String memory = "0";                               //Создание переменной для хранения памяти калькулятора
    private static boolean rewrite;                                   //Создание переменной отвечающей за перезапись поля ввод

    static JLabel history = new JLabel("0", SwingConstants.RIGHT);       //Создание поля для вывода записанного выражения
    static JLabel input = new JLabel("0", SwingConstants.RIGHT);         //Создание поля для ввода текщего числа и вывода результата
    static JLabel memoryIndicate = new JLabel("");                       //Создание поля для вывода индикатора присутствия данных в памяти калькулятора

    public MyWindow() {

        String[][] signs = {
                {"MR", "M+", "M-", "MC"},
                {"%", "∛", "C", "⤆"},
                {"1/ₓ", "√", "x²", "÷"},
                {"7", "8", "9", "x"},
                {"4", "5", "6", "-"},
                {"1", "2", "3", "+"},
                {"±", "0", ".", "="},
        };

        //Инициализация окна программы
        setTitle("Калькулятор");                                      //Название
        setBounds(100, 100, 400, 500);              //Размеры
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);      //Действие при нажатии кнопки закрыть
        setMinimumSize(new Dimension(350, 450));          //Задание минимального размера окна

        //Инициализация панелей с контейнерами
        JPanel inpPrint = new JPanel();                                 //Панель ввода-вывода
        JPanel memInpPrint = new JPanel();                              //Панель ввода-вывода + поле с индикатором памяти
        JPanel keyboard = new JPanel();                                 //Панель цифровой клавиатуры

        //Задание стилей разметки
        setLayout(new GridBagLayout());                      //Задание стиля разметки страницы
        keyboard.setLayout(new GridLayout(signs.length, signs[0].length));             //Задание стиля разметки панели с клавиатурой
        inpPrint.setLayout(new GridLayout(2, 1));             //Задание стиля разметки панели ввода-вывода
        memInpPrint.setLayout(new GridBagLayout());                     //Задание стиля разметки панели ввода-вывода-памяти

        //Настройка графического интерфейса
        memoryIndicate.setVerticalAlignment(SwingConstants.BOTTOM);       //Настройка выравнивания индикатора памяти калькулятора
        history.setFont(new Font("", Font.PLAIN, 30));          //Настройка шрифта поля вывода
        input.setFont(new Font("", Font.BOLD, 80));             //Настройка шрифта поля ввода
        memoryIndicate.setFont(new Font("", Font.BOLD, 50));    //Настройка шрифта поля ввода
        inpPrint.add(history);                                            //Добавления поля вывода в панель
        inpPrint.add(input);                                              //Добавление поля ввода в панель
        GridBagConstraints gbc = new GridBagConstraints();                //Создание стиля разметки с гибкой сеткой компонентов
        gbc.weighty = 1f;                                                 //Высота контейнера индикатора памяти не фиксирована
        gbc.weightx = 0f;                                                 //Ширина контейнера индикатора памяти фиксирована
        gbc.fill = GridBagConstraints.BOTH;
        memInpPrint.add(memoryIndicate, gbc);                              //Добавление индикатора памяти в панель ввод-вывод-память
        gbc.weightx = 1f;                                                  //Ширина панели ввода-вывода не фиксирована
        memInpPrint.add(inpPrint, gbc);                                    //Добавление панели ввода-вывода в панель ввод-вывод-память

        ButtonListener buttonListener = new ButtonListener();              //Создание слушателя действий для кнопок
        KeyListener keyboardListener = new KeyboardListener();             //Создание слушателя нажатия кнопок на клавиатуре
        addKeyListener(keyboardListener);                                  //Добавление слушателя нажатий кнопок форме
        setFocusable(true);                                                //Установка фокусировки на форму по умолчанию

        JButton[][] keys = new JButton[signs.length][signs[0].length];
        for (int i = 0; i < signs.length; i++) {
            for (int j = 0; j < signs[i].length; j++) {
                keys[i][j] = new JButton(String.valueOf(signs[i][j]));
                keys[i][j].setFont(new Font("", Font.PLAIN, 18));
                if (i > 0 && (i < 3 || j == 3)) {
                    keys[i][j].setBackground(new Color(198, 193, 193));
                } else if (i == 0) {
                    keys[i][j].setBackground(new Color(147, 146, 146));
                } else {
                    keys[i][j].setBackground(new Color(222, 222, 222));
                }
                if (i ==signs.length-1 && j == signs[0].length-1){
                    keys[i][j].setBackground(new Color(163, 203, 246));
                }
                keys[i][j].addActionListener(buttonListener);
                keys[i][j].addKeyListener(keyboardListener);
                keyboard.add(keys[i][j]);
            }
        }

        GridBagConstraints cont = new GridBagConstraints();                //Создание стиля разметки с гибкой сеткой компонентов
        cont.weighty = 0f;                                                 //Высота контейнера индикатора памяти не фиксирована
        cont.weightx = 1f;                                                 //Ширина контейнера индикатора памяти не фиксирована
        cont.gridy = 0;
        cont.gridx = 0;
        cont.fill = GridBagConstraints.BOTH;
        add(memInpPrint, cont);                              //Добавление индикатора памяти в панель ввод-вывод-память
        cont.gridy += 1;
        cont.weighty = 1;                                                  //Ширина панели ввода-вывода не фиксирована
        add(keyboard, cont);                                    //Добавление панели ввода-вывода в панель ввод-вывод-память


        setVisible(true);                                                           //Включение видимости
    }

    //Создание сеттеров для изменяемых полей
    public static void setAnswer(String answer) {
        MyWindow.answer = answer;
    }

    public static void setMemory(String memory) {
        MyWindow.memory = memory;
    }

    public static void setRewrite(boolean rewrite) {
        MyWindow.rewrite = rewrite;
    }

    //Создание геттеров для изменяемых полей
    public static String getAnswer() {
        return answer;
    }

    public static String getMemory() {
        return memory;
    }

    public static boolean isRewrite() {
        return rewrite;
    }
}
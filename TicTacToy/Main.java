package ru.geekbrains.catch_the_drop;

import java.util.Random;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        TicTacToy();                                                        //Запуск игры;
    }

    //Метод ввода числа с клавиатуры
    static int inputValue(){
        Scanner sc = new Scanner(System.in);
        return sc.nextInt();
    }

    //Метод поиска случайного числа
    static int randomValue(int MaxValue){
        Random rnd = new Random();
        return rnd.nextInt(MaxValue);
    }

    //Игра крестики-нолики
    static void TicTacToy() {                                               //Метод запускающий игру
        char[] signs = {'O', 'V', '|', 'P', 'E', 'W', 'T', 'S', '%', 'H'};  //Инициализация массива с символами для игроков ИИ
        char[] aiSigns = {'O'};
        int mapSize = 3;
        int signsToWin = 3;

        System.out.println("Привет, хомо");
        System.out.println("Это игра крестики-нолики");
        System.out.println("************************");
        System.out.println();
//        int startChange;
        while (startScreen() == 2){
            System.out.println(String.format("Размер поля: %d x %d | Количество ботов: %d | Длина ряда для победы: %d",mapSize,mapSize,aiSigns.length,signsToWin));
//            startChange = startScreen();
            aiSigns = changeLotPlayers(signs);
            mapSize = changeMapSize();
            signsToWin = changeWinLineLength(mapSize);      
        }
        game(mapSize,signsToWin,aiSigns);
    }
    
    //Метод выбора количества игроков
    static char[] changeLotPlayers(char [] signs){
        System.out.println("Выбери количество ботов от 1 до 10");
        int LotPlayers = inputValue();
        while (!correctInput(LotPlayers,10)){
            System.out.println("Количество ботов введено некорректно. Введи количество игроков заново");
            LotPlayers = inputValue();
        }
        char [] aiPlayers = new char[LotPlayers];
        for (int i = 0; i < LotPlayers; i++) {
            aiPlayers[i] = signs[i];
        }
        return aiPlayers;
    }

    //Метод выбора длины победной серии
    static int changeWinLineLength(int size){
        System.out.println(String.format("Cколько фишек в ряду должно быть для победы? (не более размера поля = %d)",size));
        int winLine = inputValue();
        while (!correctInput(winLine,size)){
            System.out.println("Длина серии введена некорректно! Повтори ввод.");
            winLine = inputValue();
        }
        return winLine;
    }

    //Метод выбора размера поля
    static int changeMapSize(){
        System.out.println("Выбери размер поля. Минимальный размер 3х3");
        System.out.println("Введи длину стороны");
        int mapSize = inputValue();
        while(mapSize<3){
            System.out.println("Размер введен некорректно! Минимальный размер поля 3х3");
            System.out.println("Введи размер поля заново!");
            mapSize = inputValue();
        }
        return mapSize;
    }

    //Метод вывода стартового экрана
    static int startScreen(){
        int userChange;
        do {
            System.out.println("1. Начать игру");
            System.out.println("2. Изменить настройки");
            userChange = inputValue();
        }while (!correctInput(userChange,2));
        return userChange;
    }

    //Метод реализации игрового цикла
        static void game(int mapSize, int signsToWin, char[] aiSigns){
            char emptySign = '-';                                                           //Символ пустой клетки поля
            char userSign = 'X';                                                            //Символ пользователя
            char currentSign;                                                               //Объявление переменной для хранения символа ходящего игрока
            char[][] map = new char[mapSize][mapSize];                                      //Объявление массива с игровым полем
            do
            {                                                                               //Глобальный игровой цикл. Отвечает за перезапуск игры
                initMap(map, emptySign);                                                    //Вызов метода инициализирующего игровое поле
                currentSign = userSign;                                                     //Первый ход в раунде отдается человеку
                do
                {                                                                           //Внутренний цикл. Отвечает за выполнение одного хода
                    if (currentSign == userSign)
                        printMap(map);                                                      //Если ход человека - то выполни метод для печати поля
                    newTurn(map, userSign, emptySign, currentSign, aiSigns, signsToWin);    //Выполни метод нового хода
                    if (checkWin(map, currentSign, mapSize, signsToWin)) {                  //Просканируй поле на предмет выигрышной комбинации
                        printMap(map);                                                      //Если выигрыш есть - напечатай поле
                        identificationWinner(currentSign, userSign, aiSigns);               //Выполни метод по идентификации победителя
                        break;                                                              //И выйди из внутреннего цикла во внешний. Раунд закончен
                    }
                    currentSign = rotateSign(currentSign, userSign, aiSigns);               //Если победы нет - передай ход другому игроку
                } while (!checkFullMap(map, emptySign));                                    //Внутренний цикл выполняется пока на поле есть свободные ячейки
            } while (restart());                                                            //Внешний цикл выполняется пока пользователь согласен сыграть еще
        }



    //Метод для инициализации чистого поля
    static void initMap(char[][] map, char emptySign) {
        for (int i = 0; i < map.length; i++) {              //Цикл прохода по строкам поля
            for (int j = 0; j < map[i].length; j++) {       //Цикл прохода по столбцам поля
                map[i][j] = emptySign;                      //Заполни массив символами пустой клетки
            }
        }
    }

    //Метод для отрисовки поля
    static void printMap(char[][] map) {
        System.out.println("Загрузка...");
        System.out.print(" ");
        for (int i = 1; i <= map.length; i++) {         //Цикл для вывода шапки игрового поля (Номера столбцов)
            System.out.printf("%2d", i);
        }
        System.out.println();
        for (int i = 0; i < map.length; i++) {          //Цикл для прохода по строкам поля
            System.out.print(i + 1);                    //Вывод номера строки поля
            for (int j = 0; j < map[i].length; j++) {   //Цикл прохода по столбцам поля
                System.out.printf("%2s", map[i][j]);    //Вывод содержимого ячейки поля
            }
            System.out.println();
        }
    }

    //Метод совершения хода
    static void newTurn(char[][] map, char userSign, char emptySign, char currentSign, char[] aiSigns, int signsToWin) {
        if (currentSign == userSign) {                                                                                  //Если текущий ход делает человек
            userTurn(map, userSign, emptySign, currentSign);                                                            //То выполни метод "ход Юзера"
        } else {                                                                                                        //Если нет
            compTurn(currentSign, map, map.length, emptySign, userSign, aiSigns, signsToWin);                           //То выполни метод "ход компьютера"
        }
    }

    //Метод для передачи хода другому игроку
    static char rotateSign(char currentSign, char userSign, char[] aiSigns) {
        int currentAi = 0;                                      //Переменная для хранения номера ИИ, сделавшего ход
        for (int i = 0; i < aiSigns.length; i++) {              //Цикл для определения порядкового номера ИИ, сделавшего ход
            if (currentSign == aiSigns[i]) currentAi = i + 1;
        }
        if (currentAi == aiSigns.length) {                      //Если номер ходившегося ИИ последний в массиве, передай ход человеку
            return userSign;
        } else {                                                //Иначе передай ход следующему ИИ
            return aiSigns[currentAi];                          //Индекс следующего ИИ в массиве соответствует порядковому номеру предыдущего ходившего
        }
    }

    //Метод для вывода надписи об исходе раунда
    static void identificationWinner(char currentSign, char userSign, char[] aiSigns) {
        if (currentSign == userSign) {                          //Если ходивший - человек - хвалим, поздравляем
            System.out.println("Поздравляю! Ты победил!");
        } else {                                                //Если ходивший - ИИ
            for (int i = 0; i < aiSigns.length; i++) {          //Циклом вычисляем его номер
                if (aiSigns[i] == currentSign) {                //Выводим ругательную надпись
                    System.out.println(String.format("Ты проиграл! Победил ИИ-%s [%s]", i + 1, currentSign));
                }
            }
        }
    }

    //Метод для проверки заполнения поля. Если выполнился - наступает ничья
    static boolean checkFullMap(char[][] map, char emptySign) {
        for (int i = 0; i < map.length; i++) {                  //Проходим циклами все ячейки массива
            for (int j = 0; j < map[i].length; j++) {
                if (map[i][j] == emptySign)
                    return false;       //Как только встречается пустая - цикл не выполняется. Можно играть дальше
            }
        }
        System.out.println("Поле заполнено! Ничья");
        return true;
    }

    //Метод проверки победы на поле
    static boolean checkWin(char[][] map, char currentSign, int size, int signsToWin) {
        for (int i = 0; i < size; i++) {                    //Циклы прохода по ячейкам
            for (int j = 0; j < size; j++) {
                int line = 0;                               //Переменная для хранения длины комбинации символов в строке
                int column = 0;                             //--//--//--//-- в столбце
                int diag1 = 0;                              //--//--//--//-- по диагонали слева направо
                int diag2 = 0;                              //--//--//--//-- по диагонали справа налево
                if (map[i][j] == currentSign) {             //Если символ в массиве равен символу игрока, чья победа проверяется
                    for (int k = 1; k < signsToWin; k++) {          //Запускаем цикл, считающий количество одинаковых знаков на интервале, равном победному
//                        Проверка следующего символа по горизонтали
                        if ((j + k) < size) {                       //Проверка, попадает ли следующий в интервале элемент в границы массива
                            if (map[i][j] == map[i][j + k]) line++; //Сравниваем следующий элемент с символом игрока. Если равно - увеличиваем счётчик
                        }
//                        Проверка следующего символа по вертикали
                        if ((i + k) < size) {
                            if (map[i][j] == map[i + k][j]) column++;
                        }
//                        Проверка следующего символа по диагонали слева направо
                        if ((i + k) < size && (j + k) < size) {
                            if (map[i][j] == map[i + k][j + k]) diag1++;
                        }
//                        Проверка следующего символа по диагонали справа налево
                        if ((i - k) >= 0 && (j + k) < size) {
                            if (map[i][j] == map[i - k][j + k]) diag2++;
                        }
                    }
                }
//                Проверка счётчиков. Если хотя бы один счётчик равен победной комбинации минус один (исходный элемент с которым шло сравнение) то это победа
                if (line == signsToWin - 1 || column == signsToWin - 1 || diag1 == signsToWin - 1 || diag2 == signsToWin - 1)
                    return true;
            }
        }
        return false;
    }

    //Метод для выяснения желания сыграть еще раз
    static boolean restart() {
        System.out.println("Повторить игру? 1 - да / 0 - нет"); //Пояснительная надпись
        return inputValue() == 1;                               //Возвращает true если пользователь ввел 1
    }

    //Метод хода игрока
    static void userTurn(char[][] map, char userSign, char emptySign, char currentSign) {
        int x;                                                                              //Объявление переменной X
        int y;                                                                              //Объявление переменной Y
        do
        {                                                                                //Цикл с постусловием для корректного ввода переменной юзером
            y = inputCoordinate('x', map.length) - 1;                                       //Выполнить метод ввода координаты X. Записать в ячейку значение с декрементом 1 для перехода к индексу массива
            x = inputCoordinate('y', map.length) - 1;                                       //Выполнить метод ввода координаты Y. Записать в ячейку значение с декрементом 1 для перехода к индексу массива
        } while (!occupateCoordinate(map, x, y, emptySign, userSign, currentSign));         //Цикл работает до тех пор, пока пользователь не укажет свободную ячейку на поле (проверяется методом)
        map[x][y] = userSign;                                                               //Записать фишку человека в массив по заданным пользователем координатам
        System.out.println(String.format("Ты поставил фишку в [%d|%d]", y + 1, x + 1));     //Вывести справочную надпись с указанием введенных пользователем координат
    }

    //Метод ввода координат игроком через консоль
    static int inputCoordinate(char coordName, int size) {
        System.out.println(String.format("Введите координату %s от 1 до %s", coordName, size)); //Вывод приглашающей к вводу координаты надписи
        int coord = inputValue();                                                               //Выполнить метод ввода числа с клавиатуры
        while (!correctInput(coord, size)) {                                                    //Цикл с предусловием. Работает до тех пор, пока пользователь вводит координаты вне границ поля (проверяется методом)
            System.out.println(String.format("Координата %s  введена не корректно. Введите %s от 1 до %s", coordName, coordName, size));    //Вывод ругательной надписи о выходе координаты за границы массива
            coord = inputValue();                                                               //Перезапись введенной координаты пользователем
        }
        return coord;                                                                           //Возвращает проверенное на попадание в массив значение координаты
    }

    //Проверка попадания введенных координат в игровое поле
    static boolean correctInput(int coord, int size) {
        return coord <= size && coord > 0;                     //Возвращает True если координата меньше/равна размеру строки(столбца) и больше нуля
    }

    //Метод для проверки свободности ячейки, в которую игрок намеревается поставить свою фишку
    static boolean occupateCoordinate(char[][] map, int x, int y, char emptySign, char userSign, char currentSign) {
        if (map[x][y] == emptySign) {                                                                                   //Если ячейка с пользовательскими координатами занята символом пустой ячейки
            return true;                                                                                                //Вернуть True
        } else {                                                                                                        //Иначе
            if (currentSign == userSign) {                                                                              //Проверка текущего символа. Если это символ человека
                System.out.println(String.format("Координата [%d|%d] занята. Выбери другую ячейку", y + 1, x + 1));     //Вывести ругательную строку
            }
            return false;                                                                                               //И для человека, и для ИИ вернуть False
        }
    }

    //Метод хода ИИ
    static void compTurn(char currentSign, char[][] map, int size, char emptySign, char userSign, char[] aiSigns, int signsToWin) {
        int x;                                                                                  //Объявление переменной X
        int y;                                                                                  //Объявление переменной Y
        do
        {                                                                                    //Цикл с постусловием для координат ячейки
            x = randomValue(size);                                                              //Генерация координаты X
            y = randomValue(size);                                                              //Генерация координаты Y
        } while (!occupateCoordinate(map, x, y, emptySign, userSign, currentSign));             //Цикл выполняется пока не найдется свободная ячейка
        if (!blockTurnEnemy(map, aiSigns, userSign, signsToWin, currentSign, emptySign)) {      //Если метод блокировки ходов ничего не нашел
            writeAiCoordinates(map, x, y, currentSign);                                         //Заполни случайную ячейку, найденную выше
        }
    }

    //Метод записи найденной ИИ ячейки в массив
    static void writeAiCoordinates(char[][] map, int x, int y, char currentSign) {
        map[x][y] = currentSign;                                                                                //Запись символа ИИ в массив по сгенерированным координатам
        System.out.println(String.format("Игрок [%s] поставил фишку в [%d|%d]", currentSign, y + 1, x + 1));    //Вывод справочной надписи с ходом ИИ
    }

    /*Алгоритм работы ИИ
     *Алгоритм работы:
     * На 1 этапе ищется предвыигрышная комбинация у текущего ИИ. Если такая найдена - поставить фишку
     * На 2 этапе проверяются и блокируются все комбинации, выстроенные на поле игроком, в которых до победы осталось поставить одну фишку
     * На 3 этапе проверяются и блокируются все предпобедные комбинации других ИИ
     * На 4 этапе циклически проверяются все комбинации игрока, содержащие не менее 2 символов в комбинации. Данная проверка выполняется с вероятностью 50%
     * На 5 этапе циклически проверяются все комбинации всех ИИ, содержащие не менее 2 символов в комбинации. Данная проверка выполняется с вероятностью 50%
     * Если не один из этапов не выполнен, считается что блокировать нечего и фишка ставится случайным образом
     * */

    static boolean blockTurnEnemy(char[][] map, char[] aiSigns, char userSign, int signsToWin, char currentSign, char emptySign) {

//1 этап

        if (findBlockTurn(map, currentSign, signsToWin, emptySign, currentSign, userSign)) return true;                         //Поиск предвыигрышной комбинации у текущего ИИ

//2 этап

        if (findBlockTurn(map, userSign, signsToWin, emptySign, currentSign, userSign)) return true;                            //Если метод поиска блокирующего хода нашел комбинацию человека - вернуть True

//3 этап

        for (int i = 0; i < aiSigns.length; i++) {                                                                              //Цикл для перебора всех ИИ
            if (currentSign != aiSigns[i] && (findBlockTurn(map, aiSigns[i], signsToWin, emptySign, currentSign, userSign)))    //Если символ игрока в раунде не равен символу в цикле и найден блокирующий ход - вернуть True
                return true;
        }
//4 этап

        if (randomValue(2) == 0) {                                                                                      //Если рандомное значение равно 0 - выполнить поиск сокращенной комбинации для блокировки
            for (int i = signsToWin - 1; i >= 2; i--) {                                                                          //Цикл для реализации сокращения длины блокируемой комбинации до диапазона[2:длина выигрышной комбинации -2]
                if (findBlockTurn(map, userSign, i, emptySign, currentSign, userSign)) return true;                              //Если блокирующий ход для сокращенной комбинации найден - вернуть True;
            }
        }

//5 этап
        if (randomValue(2) == 0) {                                                                                             //Если рандомное значение равно 0 - выполнить поиск сокращенной комбинации для блокировки
            for (int i = 0; i < aiSigns.length; i++) {                                                                                  //Цикл для перебора всех ИИ
                for (int j = signsToWin - 1; j >= 2; j--) {                                                                             //Цикл для реализации сокращения длины блокируемой комбинации
                    if (currentSign != aiSigns[i] && (findBlockTurn(map, aiSigns[i], j, emptySign, currentSign, userSign))) return true;//Если символ игрока в раунде не равен символу в цикле и найден блокирующий ход - вернуть True
                }
            }
        }
        return false;                                                                                                                   //Если ни одной блокируемой комбинации не найдено - вернуть false
    }

    //Метод для поиска линии по которой возможна блокировка хода соперника;
    static boolean findBlockTurn(char[][] map, char taskSign, int taskCombinationLength, char emptySign, char currentSign, char userSign) {
        if (blockLine(map, taskSign, taskCombinationLength, emptySign, currentSign, userSign)) {          //Выполнить поиск блокирующего хода по линии
            return true;                                                                                //Если найден - вернуть True
        } else if (blockColumn(map, taskSign, taskCombinationLength, emptySign, currentSign, userSign)) {  //Выполнить поиск блокирующего хода по столбцу
            return true;                                                                                //Если найден - вернуть True
        } else if (blockdiag1(map, taskSign, taskCombinationLength, emptySign, currentSign, userSign)) {   //Выполнить поиск блокирующего хода по диагонали слева направо
            return true;                                                                                //Если найден - вернуть True
        } else if (blockdiag2(map, taskSign, taskCombinationLength, emptySign, currentSign, userSign)) {   //Выполнить поиск блокирующего хода по диагонали справа налево
            return true;                                                                                //Если найден - вернуть True
        } else
            return false;                                                                               //Если не найден - вернуть False
    }

    //Метод для поиска комбинаций соперника по горизонтали
    public static boolean blockLine(char[][] map, char taskSign, int taskCombinationLength, char emptySign, char currentSign, char userSign) {
        for (int i = 0; i < map.length; i++) {                                                                                          //Цикл для прохода по строкам ячейка
            for (int j = 0; j < map.length; j++) {                                                                                      //Цикл для прохода по столбцам ячейки
                int di = i;                                                                                                             //Записать текущий номер строки стартового элемента проверямой комбинации
                int dj = j;                                                                                                             //Записать текущий номер столбца стартового элемента проверямой комбинации
                int sum = 0;                                                                                                            //Инициализировать счетчик суммы элементов в проверяемой комбинации
                for (int k = 0; k < taskCombinationLength; k++) {                                       //Циклом пробежать по элементам массива на длину проверямой комбинации
                    if (j + k < map.length) {                                                           //Если проверямый индекс столбца не выходит за границу массива
                        if (map[i][j + k] == emptySign) {                                               //Сравнить следующий элемент в строке с текущим . Если встречена пустая ячейка
                            di = i;                                                                     //Перезаписать текущий номер строки стартового элемента на номер строки пустой ячейки
                            dj = j + k;                                                                 //Перезаписать текущий номер столбца стартового элемента на номер строки пустой ячейки
                        }
                        if (map[i][j + k] == taskSign) {                                                 //Если проверяемая ячейка является целевой для проверямой комбинации и входит в нее
                            sum++;                                                                      //Увеличить счетчик суммы элементов в комбинации
                        } else if (map[i][j + k] != emptySign) {                                          //Если значение не целевое и не пустое - оно вражеское. Следовательно комбинация уже заблокирована и проверять ее дальше при
                            break;                                                                      //текущем стартовом элементе нет смысла. Выход из вложенного цикла пробежки по элементам левее стартового
                        }
                    }
                    if (sum == taskCombinationLength - 1 && occupateCoordinate(map, di, dj, emptySign, userSign, currentSign)) {    //Если счётчик длины комбинации равен целевому значению минус 1 и ячейка в массиве свобода
                        writeAiCoordinates(map, di, dj, currentSign);                                                               //Записать фишку ИИ в текущие координаты стартовой либо найденой пустой ячейки в массив поля
                        return true;                                                                                                //И вернуть значение True
                    }
                }                                                                                                                   //Закрытие цикла пробежки на длину комбинации от стартового элоемента
            }                                                                                                                       //Смена номера столбца стартовой ячейки
        }                                                                                                                           //Смена номера строки стартовой ячейки
        return false;
    }

    //Метод для поиска комбинаций соперника по вертикали
    public static boolean blockColumn(char[][] map, char taskSign, int taskCombinationLength, char emptySign, char currentSign, char userSign) {
        for (int i = 0; i < map.length; i++) {
            for (int j = 0; j < map.length; j++) {
                int di = i;
                int dj = j;
                int sum = 0;
                for (int k = 0; k < taskCombinationLength; k++) {
                    if (i + k < map.length) {
                        if (map[i + k][j] == emptySign) {
                            di = i + k;
                            dj = j;
                        }
                        if (map[i + k][j] == taskSign) {
                            sum++;
                        } else if (map[i + k][j] != emptySign) {
                            break;
                        }
                    }
                    if (sum == taskCombinationLength - 1 && occupateCoordinate(map, di, dj, emptySign, userSign, currentSign)) {
                        writeAiCoordinates(map, di, dj, currentSign);
                        return true;
                    }
                }
            }
        }
        return false;
    }

    //Метод для поиска комбинаций соперника по диагонали слева направо
    public static boolean blockdiag1(char[][] map, char taskSign, int taskCombinationLength, char emptySign, char currentSign, char userSign) {
        for (int i = 0; i < map.length; i++) {
            for (int j = 0; j < map.length; j++) {
                int di = i;
                int dj = j;
                int sum = 0;
                for (int k = 0; k < taskCombinationLength; k++) {
                    if (i + k < map.length && j + k < map.length) {
                        if (map[i + k][j + k] == emptySign) {
                            di = i + k;
                            dj = j + k;
                        }
                        if (map[i + k][j + k] == taskSign) {
                            sum++;
                        } else if (map[i + k][j + k] != emptySign) {
                            break;
                        }
                    }
                    if (sum == taskCombinationLength - 1 && occupateCoordinate(map, di, dj, emptySign, userSign, currentSign)) {
                        writeAiCoordinates(map, di, dj, currentSign);
                        return true;
                    }
                }
            }
        }
        return false;
    }

    //Метод для поиска комбинаций соперника по диагонали справа налево
    public static boolean blockdiag2(char[][] map, char taskSign, int taskCombinationLength, char emptySign, char currentSign, char userSign) {
        for (int i = 0; i < map.length; i++) {
            for (int j = 0; j < map.length; j++) {
                int di = i;
                int dj = j;
                int sum = 0;
                for (int k = 0; k < taskCombinationLength; k++) {
                    if (i - k >= 0 && j + k < map.length) {
                        if (map[i - k][j + k] == emptySign) {
                            di = i - k;
                            dj = j + k;
                        }
                        if (map[i - k][j + k] == taskSign) {
                            sum++;
                        } else if (map[i - k][j + k] != emptySign) {
                            break;
                        }
                    }
                    if (sum == taskCombinationLength - 1 && occupateCoordinate(map, di, dj, emptySign, userSign, currentSign)) {
                        writeAiCoordinates(map, di, dj, currentSign);
                        return true;
                    }
                }
            }
        }
        return false;
    }
}

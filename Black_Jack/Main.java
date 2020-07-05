package Black_Jack;

import java.util.Scanner;

public class Main {

    public static PackCards pack1 = new PackCards();
    private static final Human player1 = new Human();
    private static final AI ii1 = new AI();

    public static void main(String[] args) {
        blackJack();
    }

    private static void blackJack() {
        pack1.initPackOfCards();
        do {
            player1.setSumValue(0);
            ii1.setSumValue(0);
            player1.turn();
            if (player1.getSumValue() > 21) {
                System.out.printf("**********\n" +
                        "У вас %d \n" +
                        "Перебор\n", player1.getSumValue());
                player1.lose();
            } else {
                System.out.println("**********\n" +
                        "Ходит компьютер");
                ii1.turn();
                if (ii1.getSumValue() > 21) {
                    System.out.printf("********** \n" +
                            "У компьютера %d. \n" +
                            "Перебор \n", ii1.getSumValue());
                    player1.win();
                } else {
                    checkWinner(player1.getSumValue(), ii1.getSumValue());
                }
            }
            if (player1.getMoney() == 0) {
                System.out.println("Вы всё проиграли! До свиданья!");
                break;
            }
        } while (resetGame());
    }

    //Метод определения победителя
    private static void checkWinner(int sumUser, int sumAI) {
        System.out.printf("**********\n" +
                "У вас %d, у компьютера %d. \n", sumUser, sumAI);
        if (sumUser > sumAI) {
            player1.win();
        } else {
            player1.lose();
        }
    }

    //Метод спрашивающий желания повторить раунд
    private static boolean resetGame() {
        Scanner sc = new Scanner(System.in);
        int task;
        System.out.println("**********\n" +
                "Повторить игру? (1-да | 0 - нет)");
        do {
            task = sc.nextByte();
        } while (!correctInput(task, 0, 1));
        return (task == 1);
    }

    //Метод проверки корректного ввода
    public static boolean correctInput(double value, double from, double until) {
        if (value >= from && value <= until) return true;
        System.out.println("Введено некорректное значение");
        return false;
    }
}
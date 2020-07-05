package Black_Jack;

import java.util.Scanner;

import static Black_Jack.Main.correctInput;
import static Black_Jack.Main.pack1;

public class Human extends Player{

    private final Scanner sc = new Scanner(System.in);

    //Метод хода игрока
    @Override
    public void turn() {
        bank.printBank();
        rate = doRate();
        do {
            System.out.println("**********\n" +
                    "Ваш ход. ");
            if (pack1.countRestCardsOfPack() == 0) {
                pack1.initPackOfCards();
            }
            System.out.print("Вам достается карта ");
            sumValue += pack1.turn();
            System.out.println();
            System.out.printf("Сумма ваших очков: %d \n", sumValue);
            if (sumValue >= 21) break;
        } while (stopOrContin());
    }

    //Метод для совершения ставки
    private double doRate() {
        System.out.println("**********\n" +
                "Введите вашу ставку");
        double rate;
        do {
            rate = sc.nextDouble();
        } while (!bank.checkRate(rate));
        return rate;
    }

    //Метод спрашивающий желания взять еще
    private boolean stopOrContin() {
        int task;
        System.out.println("**********\n" +
                "Еще или хватит? (1-еще | 0-стоп)");
        do {
            task = sc.nextByte();
        } while (!correctInput(task, 0, 1));
        return(task == 1);
    }

    //Метод для поражения
    public void lose() {
        System.out.println("Вы проиграли");
        bank.setMoney(bank.getMoney() - rate);
        bank.printBank();
    }

    //Метод для победы
    public void win() {
        System.out.println("Вы выиграли");
        bank.setMoney(bank.getMoney() + rate);
        bank.printBank();
    }
}

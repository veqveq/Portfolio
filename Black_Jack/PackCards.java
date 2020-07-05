package Black_Jack;

import java.util.Arrays;
import java.util.Random;

public class PackCards {

    private final String[] cardSuit = {"2", "3", "4", "5", "6", "7", "8", "9", "10", "В", "Д", "К", "Т"};
    private final int[] cardScore = {2, 3, 4, 5, 6, 7, 8, 9, 10, 2, 3, 4, 11};
    private int[] packOfCards = new int[cardSuit.length];

    //Метод инициализации колоды
    public void initPackOfCards() {
        packOfCards = new int[cardSuit.length];
        System.out.println("**********");
        System.out.println("Перетасовка колоды...");
        Arrays.fill(packOfCards,4);
    }

    //Метод подсчета остатка карт в колоде
    public int countRestCardsOfPack() {
        int restCards = 0;
        for (int i = 0; i < cardSuit.length; i++) {
            restCards += packOfCards[i];
        }
        return restCards;
    }

    //Метод проверки есть ли текущая карта в колоде
    public boolean checkPackOfCards(int card) {
        return(packOfCards[card] != 0);
    }

    //Метод вынимания карты из колоды
    public int turn() {
        Random rnd = new Random();
        int card;
        do {
            card = rnd.nextInt(cardSuit.length);
        } while (!checkPackOfCards(card));
        System.out.print(cardSuit[card] + " ");
        packOfCards[card]--;
        return cardScore[card];
    }

    //Геттер возвращающий длину колоды
    public int getPackLength() {
        return cardSuit.length;
    }

    //Геттер возвращающий количество очков для карты
    public int getCardScore(int card) {
        return cardScore[card];
    }

    //Геттер возвращающий количество карт в колоде
    public int getCardValue(int card) {
        return packOfCards[card];
    }
}

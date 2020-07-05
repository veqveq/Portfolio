package Black_Jack;

import java.util.Random;

import static Black_Jack.Main.pack1;

public class AI extends Player {
    private final Random rnd = new Random();

    //Метод хода компьютера
    @Override
    public void turn() {
        do {
            if (pack1.countRestCardsOfPack() == 0) {
                pack1.initPackOfCards();
            }
            sumValue += pack1.turn();
        } while (stopOrContin(sumValue) && sumValue <= 21);
        System.out.println();
    }

    //Искусственный интеллект компьютера
    private boolean stopOrContin(int sumAI) {
        int restValue = 21 - sumAI;
        double goodCard = 0;
        double restCards = pack1.countRestCardsOfPack();
        for (int i = 0; i < pack1.getPackLength(); i++) {
            if (pack1.getCardScore(i) <= restValue) {
                goodCard += pack1.getCardValue(i);
            }
        }
        double chance = goodCard / restCards;

        if (chance == 0) {
            return false;
        } else {
            for (double i = 10; i > 0; i--) {
                if (chance >= (i / 10) && randomizer((int) i)) {
                    return true;
                }
            }
        }
        return false;
    }

    //Рандомайзер
    private boolean randomizer(int chance) {
        int randomize = rnd.nextInt(10);
        return (randomize <= chance);
    }
}

package Black_Jack;

public abstract class Player {

    protected int sumValue;
    protected double rate = 0;
    protected Bank bank = new Bank();

    //Сеттер для изменения суммы очков
    public void setSumValue(int sumUser) {
        this.sumValue = sumUser;
    }

    //Геттер для возврата суммы очков
    public int getSumValue() {
        return sumValue;
    }


    //Геттер для возврата остатка денег
    public double getMoney() {
        return bank.getMoney();
    }

    public abstract void turn();
}
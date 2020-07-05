package Black_Jack;

public class Bank {
    private double money = 1000;

    //Показать остаток денег
    public void printBank() {
        System.out.printf("Остаток ваших средств: %.2f$\n", money);
    }

    //Геттер для возврата оставшейся суммы
    public double getMoney() {
        return money;
    }

    //Изменить остаток денег
    public void setMoney(double money) {
        this.money = money;
    }

    //Проверка ставки
    public boolean checkRate(double rate) {
        if (rate > money) {
            System.out.println("Недостаточно средств для ставки! \n" +
                    "Введите ставку заново");
            return false;
        }
        if (rate < 0) {
            System.out.println("Ставка не может быть отрицательной!");
        }
        return true;
    }
}

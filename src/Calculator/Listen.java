package Calculator;

public abstract class Listen extends SuccessorClass {          //Абстрактный класс слушателя

    char key;               //Объявление поля для хранения названия нажатой кнопки

    protected Listen(char key) {         //Конструктор абстрактного класса слушатель
        this.key = key;
    }

    protected abstract void listen();  //Объявление абстрактного метода Слушать
}
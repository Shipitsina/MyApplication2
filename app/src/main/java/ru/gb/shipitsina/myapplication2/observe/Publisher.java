package ru.gb.shipitsina.myapplication2.observe;

import java.util.ArrayList;
import java.util.List;

import ru.gb.shipitsina.myapplication2.data.CardData;

// Обработчик подписок
public class Publisher {

    private List<Observer> observers;   // Все обозреватели

    public Publisher() {
        observers = new ArrayList<>();
    }

    // Подписать
    public void subscribe(Observer observer) {
        observers.add(observer);
    }

    // Отписать
    public void unsubscribe(Observer observer) {
        observers.remove(observer);
    }

    // Разослать событие
    public void notify(CardData cardData) {
        for (Observer observer : observers) {
            observer.updateCardData(cardData);
            unsubscribe(observer);
        }
    }
}
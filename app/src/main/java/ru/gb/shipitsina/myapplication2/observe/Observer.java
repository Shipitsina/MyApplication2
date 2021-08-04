package ru.gb.shipitsina.myapplication2.observe;

import ru.gb.shipitsina.myapplication2.data.CardData;

// Наблюдатель, вызывается updateText, когда надо отправить событие по изменению текста
public interface Observer {
    void updateCardData(CardData cardData);
}


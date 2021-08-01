package ru.gb.shipitsina.myapplication2.data;

public interface CardsSource {
    CardData getCardData(int position);
    int size();
}


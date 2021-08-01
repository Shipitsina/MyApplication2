package ru.gb.shipitsina.myapplication2.data;

public class CardData {
    private String title;       // заголовок
    private String description; // описание
    private int picture;        // изображение

    public CardData(String title, String description, int picture){
        this.title = title;
        this.description=description;
        this.picture=picture;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public int getPicture() {
        return picture;
    }

}

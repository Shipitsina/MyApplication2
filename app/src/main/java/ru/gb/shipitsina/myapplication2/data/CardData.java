package ru.gb.shipitsina.myapplication2.data;

import ru.gb.shipitsina.myapplication2.Note;

public class CardData {
    private String title;       // заголовок
    private String description; // описание
    private int picture;        // изображение
    int index;
    private Note[] notes = {new Note("Title1","Content1"),
            new Note("Title2","Content2"),
            new Note("Title3","Content3"),
            new Note("Title4","Content4"),
            new Note("Title5","Content5"),
            new Note("Title6","Content6")};

    public CardData(int index, int picture) {
        this.index = index;
        this.picture=picture;
    }

    public CardData(String title, String description, int picture){
        this.title = title;
        this.description=description;
        this.picture=picture;
    }

    public String getTitle() {
        return notes[index].getTitle();
    }

    public String getDescription() {
        return notes[index].getContent();
    }

    public int getPicture() {
        return picture;
    }

}

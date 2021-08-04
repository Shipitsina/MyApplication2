package ru.gb.shipitsina.myapplication2.data;

import android.os.Parcel;
import android.os.Parcelable;

import ru.gb.shipitsina.myapplication2.Note;

public class CardData implements Parcelable {
    private String title;       // заголовок
    private String description; // описание
    private int picture;        // изображение

    public CardData(String title, String description, int picture) {
        this.title = title;
        this.description=description;
        this.picture=picture;
    }
    protected CardData(Parcel in) {
        title = in.readString();
        description = in.readString();
        picture = in.readInt();
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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(title);
        dest.writeString(description);
        dest.writeInt(picture);
    }
    public static final Creator<CardData> CREATOR = new Creator<CardData>() {
        @Override
        public CardData createFromParcel(Parcel in) {
            return new CardData(in);
        }

        @Override
        public CardData[] newArray(int size) {
            return new CardData[size];
        }
    };
}

package ru.gb.shipitsina.myapplication2.ui;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.CheckBox;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.recyclerview.widget.RecyclerView;

import ru.gb.shipitsina.myapplication2.R;
import ru.gb.shipitsina.myapplication2.data.CardData;
import ru.gb.shipitsina.myapplication2.data.CardsSource;

public class NotesAdapter extends RecyclerView.Adapter <NotesAdapter.ViewHolder>{

    private final static String TAG = "SocialNetworkAdapter";
    private CardsSource dataSource;
    private OnItemClickListener itemClickListener;

        // Передаём в конструктор источник данных
        // В нашем случае это массив, но может быть и запрос к БД
        public NotesAdapter(CardsSource dataSource) {
            this.dataSource = dataSource;
        }

        // Создать новый элемент пользовательского интерфейса
        // Запускается менеджером
        @NonNull
        @Override
        public NotesAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
            // Создаём новый элемент пользовательского интерфейса
            // Через Inflater
            View v = LayoutInflater.from(viewGroup.getContext())
                    .inflate(R.layout.item, viewGroup, false);
            Log.d(TAG, "onCreateViewHolder");
            // Здесь можно установить всякие параметры
            return new ViewHolder(v);
        }

        // Заменить данные в пользовательском интерфейсе
        // Вызывается менеджером

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
            // Получить элемент из источника данных (БД, интернет...)
            // Вынести на экран, используя ViewHolder
            holder.setData(dataSource.getCardData(position));
            Log.d(TAG, "onBindViewHolder");
        }

        // Вернуть размер данных, вызывается менеджером
        @Override
        public int getItemCount() {
            return dataSource.size();
        }
    // Сеттер слушателя нажатий
    public void SetOnItemClickListener(OnItemClickListener itemClickListener){
        this.itemClickListener = itemClickListener;
    }

    // Интерфейс для обработки нажатий, как в ListView
    public interface OnItemClickListener {
        void onItemClick(View view , int position);
    }

// Этот класс хранит связь между данными и элементами View
// Сложные данные могут потребовать несколько View на один пункт списка
    public class ViewHolder extends RecyclerView.ViewHolder {

    private TextView title;
    private TextView description;
    private AppCompatImageView image;
    private CheckBox like;


    public ViewHolder(@NonNull View itemView) {
        super(itemView);
        title = itemView.findViewById(R.id.title);
        description = itemView.findViewById(R.id.description);
        image = itemView.findViewById(R.id.imageView);
        like = itemView.findViewById(R.id.like);

        // Обработчик нажатий на этом ViewHolder
        image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (itemClickListener != null) {
                    itemClickListener.onItemClick(v, getAdapterPosition());
                }
            }
        });
    }

    public void setData(CardData cardData) {
        title.setText(cardData.getTitle());
        description.setText(cardData.getDescription());
        like.setChecked(cardData.isLike());
        image.setImageResource(cardData.getPicture());
    }
}
}

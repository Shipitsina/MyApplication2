package ru.gb.shipitsina.myapplication2.ui;
import android.os.Build;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.CheckBox;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import java.text.SimpleDateFormat;

import ru.gb.shipitsina.myapplication2.R;
import ru.gb.shipitsina.myapplication2.data.CardData;
import ru.gb.shipitsina.myapplication2.data.CardsSource;

public class NotesAdapter extends RecyclerView.Adapter <NotesAdapter.ViewHolder>{

    private final static String TAG = "NotesAdapter";
    private CardsSource dataSource;
    private final Fragment fragment;
    private OnItemClickListener itemClickListener;
    private int menuPosition;


        // Передаём в конструктор источник данных
        // В нашем случае это массив, но может быть и запрос к БД
        public NotesAdapter(Fragment fragment) {
            this.fragment = fragment;
        }
    public void setDataSource(CardsSource dataSource){
        this.dataSource = dataSource;
        notifyDataSetChanged();
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
    public void onBindViewHolder(@NonNull NotesAdapter.ViewHolder holder, int position) {
            // Получить элемент из источника данных (БД, интернет...)
            // Вынести на экран, используя ViewHolder
            holder.setData(dataSource.getCardData(position));
            Log.d(TAG, "onBindViewHolder");
        }
    public int getMenuPosition() {
        return menuPosition;
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

    public class ViewHolder extends RecyclerView.ViewHolder {

        private TextView title;
        private TextView description;
        private AppCompatImageView image;
        private TextView date;

        public ViewHolder(@NonNull final View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.title);
            description = itemView.findViewById(R.id.description);
            image = itemView.findViewById(R.id.imageView);
            date = itemView.findViewById(R.id.date);

            registerContextMenu(itemView);

            // Обработчик нажатий на картинке
            image.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (itemClickListener != null) {
                        itemClickListener.onItemClick(v, getAdapterPosition());
                    }
                }
            });

            // Обработчик нажатий на картинке
            image.setOnLongClickListener(new View.OnLongClickListener() {
                @RequiresApi(api = Build.VERSION_CODES.N)
                @Override
                public boolean onLongClick(View v) {
                    menuPosition = getLayoutPosition();
                    itemView.showContextMenu(10, 10);
                    return true;
                }
            });
        }

        private void registerContextMenu(@NonNull View itemView) {
            if (fragment != null){
                itemView.setOnLongClickListener(new View.OnLongClickListener() {
                    @Override
                    public boolean onLongClick(View v) {
                        menuPosition = getLayoutPosition();
                        return false;
                    }
                });
                fragment.registerForContextMenu(itemView);
            }
        }

        public void setData(CardData cardData){
            title.setText(cardData.getTitle());
            description.setText(cardData.getDescription());
            image.setImageResource(cardData.getPicture());
            date.setText(new SimpleDateFormat("dd-MM-yy").format(cardData.getDate()));
        }
    }

}

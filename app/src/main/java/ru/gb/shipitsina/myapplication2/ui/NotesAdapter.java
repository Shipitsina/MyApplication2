package ru.gb.shipitsina.myapplication2.ui;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import ru.gb.shipitsina.myapplication2.R;

public class NotesAdapter extends RecyclerView.Adapter <NotesAdapter.ViewHolder>{

    private String[] dataSource;

        // Передаём в конструктор источник данных
        // В нашем случае это массив, но может быть и запрос к БД
        public NotesAdapter(String[] dataSource) {
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
            // Здесь можно установить всякие параметры
            return new ViewHolder(v);
        }

        // Заменить данные в пользовательском интерфейсе
        // Вызывается менеджером

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
            // Получить элемент из источника данных (БД, интернет...)
            // Вынести на экран, используя ViewHolder
            holder.getTextView().setText(dataSource[position]);
        }

        // Вернуть размер данных, вызывается менеджером
        @Override
        public int getItemCount() {
            return dataSource.length;
        }

// Этот класс хранит связь между данными и элементами View
// Сложные данные могут потребовать несколько View на один пункт списка
    public class ViewHolder extends RecyclerView.ViewHolder {

        private TextView textView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            textView = (TextView) itemView;
        }

        public TextView getTextView() {
            return textView;
        }
    }
}

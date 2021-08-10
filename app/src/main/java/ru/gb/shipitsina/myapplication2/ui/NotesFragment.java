package ru.gb.shipitsina.myapplication2.ui;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import ru.gb.shipitsina.myapplication2.MainActivity;
import ru.gb.shipitsina.myapplication2.Navigation;
import ru.gb.shipitsina.myapplication2.Note;
import ru.gb.shipitsina.myapplication2.R;
import ru.gb.shipitsina.myapplication2.data.CardData;
import ru.gb.shipitsina.myapplication2.data.CardsSource;
import ru.gb.shipitsina.myapplication2.data.CardsSourceFirebaseImpl;
import ru.gb.shipitsina.myapplication2.data.CardsSourceImpl;
import ru.gb.shipitsina.myapplication2.data.CardsSourceResponse;
import ru.gb.shipitsina.myapplication2.observe.Observer;
import ru.gb.shipitsina.myapplication2.observe.Publisher;

public class NotesFragment extends Fragment {
    private CardsSource data;
    private NotesAdapter adapter;
    private RecyclerView recyclerView;
    private Publisher publisher;
    private Navigation navigation;
    private boolean moveToFirstPosition;

    public static NotesFragment newInstance() {
        return new NotesFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_notes, container, false);
        initView(view);
        setHasOptionsMenu(true);
        data = new CardsSourceFirebaseImpl().init(new CardsSourceResponse() {
            @Override
            public void initialized(CardsSource cardsData) {
                adapter.notifyDataSetChanged();
            }
        });
        adapter.setDataSource(data);
        return view;
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        MainActivity activity = (MainActivity) context;
        navigation = activity.getNavigation();
        publisher = activity.getPublisher();
    }

    @Override
    public void onDetach() {
        navigation = null;
        publisher = null;
        super.onDetach();
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.cards_menu, menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        return onItemSelected(item.getItemId()) || super.onOptionsItemSelected(item);
    }

    private void initView(View view) {
        recyclerView = view.findViewById(R.id.recycler_view_lines);
        initRecyclerView();
    }

    private void initRecyclerView() {

        // Эта установка служит для повышения производительности системы
        recyclerView.setHasFixedSize(true);

        // Будем работать со встроенным менеджером
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);

        // Установим адаптер
        adapter = new NotesAdapter(this);
        recyclerView.setAdapter(adapter);

        // Добавим разделитель карточек
        DividerItemDecoration itemDecoration = new DividerItemDecoration(getContext(), LinearLayoutManager.VERTICAL);
        itemDecoration.setDrawable(getResources().getDrawable(R.drawable.separator, null));
        recyclerView.addItemDecoration(itemDecoration);
        if (moveToFirstPosition && data.size() > 0) {
            recyclerView.scrollToPosition(0);
            moveToFirstPosition = false;
        }
        // Установим слушателя
        adapter.SetOnItemClickListener(new NotesAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                navigation.addFragment(ChosenNoteFragment.newInstance(data.getCardData(position)), true);
            }
        });
    }

    @Override
    public void onCreateContextMenu(@NonNull ContextMenu menu, @NonNull View v, @Nullable ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        MenuInflater inflater = requireActivity().getMenuInflater();
        inflater.inflate(R.menu.card_menu, menu);
    }

    @Override
    public boolean onContextItemSelected(@NonNull MenuItem item) {
        return onItemSelected(item.getItemId()) || super.onContextItemSelected(item);
    }

    private boolean onItemSelected(int menuItemId) {
        switch (menuItemId) {
            case R.id.action_add:
                navigation.addFragment(ChosenNoteFragment.newInstance(), true);
                publisher.subscribe(new Observer() {
                    @Override
                    public void updateCardData(CardData cardData) {
                        data.addCardData(cardData);
                        adapter.notifyItemInserted(data.size() - 1);
                        // это сигнал, чтобы вызванный метод onCreateView
                        // перепрыгнул на начало списка
                        moveToFirstPosition = true;
                    }
                });
                return true;
            case R.id.action_update:
                final int updatePosition = adapter.getMenuPosition();
                navigation.addFragment(ChosenNoteFragment.newInstance(data.getCardData(updatePosition)), true);
                publisher.subscribe(new Observer() {
                    @Override
                    public void updateCardData(CardData cardData) {
                        data.updateCardData(updatePosition, cardData);
                        adapter.notifyItemChanged(updatePosition);
                    }
                });
                return true;
            case R.id.action_delete:
                AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                builder.setTitle("Внимание!")
                        .setMessage("Вы уверены, что хотите удалить эту заметку?")
                        .setIcon(R.mipmap.ic_launcher_round)
                        .setCancelable(true)
                        .setPositiveButton("Да",
                                new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        int deletePosition = adapter.getMenuPosition();
                                        data.deleteCardData(deletePosition);
                                        adapter.notifyItemRemoved(deletePosition);
                                    }
                                })
                        .setNegativeButton("Нет",
                                new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                    }
                                })
                        .show();
                return true;
            case R.id.action_clear:
                AlertDialog.Builder builder2 = new AlertDialog.Builder(getContext());
                builder2.setTitle("Внимание!")
                        .setMessage("Вы уверены, что хотите удалить ВСЕ заметки?")
                        .setIcon(R.mipmap.ic_launcher_round)
                        .setCancelable(true)
                        .setPositiveButton("Да",
                                new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        data.clearCardData();
                                        adapter.notifyDataSetChanged();
                                    }
                                })
                        .setNegativeButton("Нет",
                                new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                    }
                                })
                        .show();
                return true;
        }
        return false;
    }
}
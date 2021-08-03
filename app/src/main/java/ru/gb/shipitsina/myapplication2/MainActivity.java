package ru.gb.shipitsina.myapplication2;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.material.navigation.NavigationView;

import ru.gb.shipitsina.myapplication2.data.CardsSource;
import ru.gb.shipitsina.myapplication2.data.CardsSourceImpl;
import ru.gb.shipitsina.myapplication2.ui.ChosenNoteFragment;
import ru.gb.shipitsina.myapplication2.ui.NotesAdapter;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        addFragment(NotesFragment.newInstance());
    }

    private void initView() {
        Toolbar toolbar = initToolbar();
        initDrawer(toolbar);
        initButtonMain();/*
        initButtonFavorite();
        initButtonSettings();
        initButtonBack();*/
    }


    private Toolbar initToolbar() {
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        return toolbar;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        // Обработка выбора пункта меню приложения (активити)
        int id = item.getItemId();
        if (navigateFragment(id)) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void initDrawer(Toolbar toolbar) {
        final DrawerLayout drawer = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar,
                R.string.navigation_drawer_open,
                R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        // Обработка навигационного меню
        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(new NavigationView
                .OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int id = item.getItemId();
                if (navigateFragment(id)){
                    drawer.closeDrawer(GravityCompat.START);
                    return true;
                }
                return false;
            }
        });
    }

    private void initButtonMain() {
        Button buttonMain = findViewById(R.id.buttonMain);
        buttonMain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addFragment(new MainFragment());
            }
        });
    }

    private void addFragment(Fragment fragment) {

        //Получить менеджер фрагментов
        FragmentManager fragmentManager = getSupportFragmentManager();

        // Открыть транзакцию
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

        // Добавить фрагмент
        fragmentTransaction.replace(R.id.fragment_container, fragment);
        fragmentTransaction.addToBackStack(null);
        // Закрыть транзакцию
        fragmentTransaction.commit();
    }

    private boolean navigateFragment(int id) {
        switch (id) {
            case R.id.action_settings:
                addFragment(new SettingsFragment());
                return true;
            case R.id.action_main:
                addFragment(new MainFragment());
                return true;
            case R.id.action_favorite:
                addFragment(new FavoriteFragment());
                return true;
        }
        return false;
    }

    public static class NotesFragment extends Fragment {

        public static final String CURRENT_NOTE = "CurrentNote";
        private Note currentNote;
        private boolean isLandscape;

        public static NotesFragment newInstance() {
            return new NotesFragment();
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            // Inflate the layout for this fragment
            View view = inflater.inflate(R.layout.fragment_notes, container, false);
            RecyclerView recyclerView = view.findViewById(R.id.recycler_view_lines);
            // Получим источник данных для списка
            CardsSource data = new CardsSourceImpl(getResources()).init();
            initRecyclerView(recyclerView, data);
            return view;
        }

        private void initRecyclerView(RecyclerView recyclerView, CardsSource data){

            // Эта установка служит для повышения производительности системы
            recyclerView.setHasFixedSize(true);

            // Будем работать со встроенным менеджером
            LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
            recyclerView.setLayoutManager(layoutManager);

            // Установим адаптер
            final NotesAdapter adapter = new NotesAdapter(data);
            recyclerView.setAdapter(adapter);

            // Добавим разделитель карточек
            DividerItemDecoration itemDecoration = new DividerItemDecoration(getContext(),  LinearLayoutManager.VERTICAL);
            itemDecoration.setDrawable(getResources().getDrawable(R.drawable.separator, null));
            recyclerView.addItemDecoration(itemDecoration);

            // Установим слушателя
            adapter.SetOnItemClickListener(new NotesAdapter.OnItemClickListener() {
                @Override
                public void onItemClick(View view, int position) {
                    Toast.makeText(getContext(), String.format("Позиция - %d", position),
                            Toast.LENGTH_SHORT).show();
                    showPortCoatOfArms(position);
                }
            });
        }
        private void showPortCoatOfArms(int currentNote) {
            //Получить менеджер фрагментов
            FragmentManager fragmentManager = getParentFragmentManager();

            // Открыть транзакцию
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

            // Добавить фрагмент
            fragmentTransaction.replace(R.id.fragment_container, ChosenNoteFragment.newInstance(currentNote));
            fragmentTransaction.addToBackStack(null);
            // Закрыть транзакцию
            fragmentTransaction.commit();
        }
    }
}


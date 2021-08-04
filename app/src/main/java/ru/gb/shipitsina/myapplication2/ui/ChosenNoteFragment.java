package ru.gb.shipitsina.myapplication2.ui;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import android.text.format.DateUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.material.textfield.TextInputEditText;

import java.util.Calendar;
import java.util.Date;

import ru.gb.shipitsina.myapplication2.MainActivity;
import ru.gb.shipitsina.myapplication2.R;
import ru.gb.shipitsina.myapplication2.data.CardData;
import ru.gb.shipitsina.myapplication2.data.CardsSource;
import ru.gb.shipitsina.myapplication2.data.CardsSourceImpl;
import ru.gb.shipitsina.myapplication2.observe.Publisher;

public class ChosenNoteFragment extends Fragment {
    public static final String ARG_PARAM_DATA = "Param_CardData";
    private CardData cardData;
    private TextInputEditText title;
    private TextInputEditText content;
    private Publisher publisher;
    public ChosenNoteFragment() {
    }

    public static ChosenNoteFragment newInstance(CardData cardData) {
        ChosenNoteFragment fragment = new ChosenNoteFragment();
        Bundle args = new Bundle();
        args.putParcelable(ARG_PARAM_DATA, cardData);
        fragment.setArguments(args);
        return fragment;
    }
    public static ChosenNoteFragment newInstance(){
        ChosenNoteFragment fragment = new ChosenNoteFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            cardData = getArguments().getParcelable(ARG_PARAM_DATA);
        }

    }
    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        MainActivity activity = (MainActivity)context;
        publisher = activity.getPublisher();
    }

    @Override
    public void onDetach() {
        publisher = null;
        super.onDetach();
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_chosen_note, container, false);
        initView(view);
        if (cardData != null) {
            populateView();
        }
        return view;
    }

    @Override
    public void onStop() {
        super.onStop();
        cardData = collectCardData();
    }
    // Здесь передадим данные в паблишер
    @Override
    public void onDestroy() {
        super.onDestroy();
        publisher.notify(cardData);
    }
    private CardData collectCardData(){
        String title = this.title.getText().toString();
        String description = this.content.getText().toString();
        int picture;
        if (cardData != null){
            picture = cardData.getPicture();
        } else {
            picture = R.drawable.blue;
        }
        return new CardData(title, description, picture);
    }
    private void initView(View view) {
        title = view.findViewById(R.id.inputTitle);
        content = view.findViewById(R.id.inputDescription);
    }

    private void populateView(){
        title.setText(cardData.getTitle());
        content.setText(cardData.getDescription());
    }
}
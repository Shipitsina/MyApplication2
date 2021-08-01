package ru.gb.shipitsina.myapplication2;
import android.os.Bundle;
import android.text.format.DateUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import java.util.Calendar;

public class FavoriteFragment extends Fragment {

    public static final String ARG_PARAM_INDEX = "index";

    private int index;
    TextView currentDateTime;
    Calendar dateAndTime= Calendar.getInstance();
    public FavoriteFragment() {
    }

    public static ChosenNoteFragment newInstance(int index) {
        ChosenNoteFragment fragment = new ChosenNoteFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_PARAM_INDEX, index);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            index = getArguments().getInt(ARG_PARAM_INDEX);
        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_chosen_note, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        currentDateTime=(TextView) view.findViewById(R.id.currentDateTime);
        initNote(view);
        setInitialDateTime();
    }

    private void initNote(View view) {
        TextView tvTitle = view.findViewById(R.id.title);
        tvTitle.setText(NotesFragment.notes[index].getTitle());
        TextView tvContent = view.findViewById(R.id.content);
        tvContent.setText(NotesFragment.notes[index].getContent());
    }

    // установка начальных даты и времени
    private void setInitialDateTime() {

        currentDateTime.setText(DateUtils.formatDateTime(getContext(),
                dateAndTime.getTimeInMillis(),
                DateUtils.FORMAT_SHOW_DATE | DateUtils.FORMAT_SHOW_YEAR
                        | DateUtils.FORMAT_SHOW_TIME));
    }
}

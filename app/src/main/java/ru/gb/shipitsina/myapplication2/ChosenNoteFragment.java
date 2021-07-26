package ru.gb.shipitsina.myapplication2;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class ChosenNoteFragment extends Fragment {

    public static final String ARG_PARAM_INDEX = "index";

    private int index;

    public ChosenNoteFragment() {
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

        initNote(view);
    }

    private void initNote(View view) {

    }
}
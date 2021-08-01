package ru.gb.shipitsina.myapplication2;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class NotesFragment extends Fragment {
    private static final int DEFAULT_INDEX = 0;
    public static Note [] notes = {new Note("Title1","Contest1"),
            new Note("Title2","Contest2"),
            new Note("Title3","Contest3")};

    private boolean isLand = false;
    public NotesFragment() {
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setReenterTransition(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_notes, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        isLand = getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE;


        if (isLand){
            showNoteLand(DEFAULT_INDEX);
        }
        
        initList(view);
    }

    private void initList(View view) {
        LinearLayout linearLayout = (LinearLayout) view;

        for (int i = 0; i<notes.length; i++){

        TextView textView = new TextView(linearLayout.getContext());
        textView.setText(notes[i].getTitle());
        textView.setTextSize(30);

        final int finalIndex = i;

        textView.setOnClickListener(v -> {
            showNote(finalIndex);
        });

        linearLayout.addView(textView);}
    }

    private void showNote(int index) {
        if (isLand){
            showNoteLand(index);
        } else {
            showNotePort(index);
        }
    }

    private void showNoteLand(int index) {
        Fragment fragment = ChosenNoteFragment.newInstance(index);

        requireActivity().getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.note_content, fragment)
                .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                .commit();
    }

    private void showNotePort(int index) {
        Intent intent = new Intent();
        intent.setClass(getActivity(), NoteActivity.class);
        intent.putExtra(ChosenNoteFragment.ARG_PARAM_INDEX,index);
        startActivity(intent);
    }
}
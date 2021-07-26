package ru.gb.shipitsina.myapplication2;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

public class NotesFragment extends Fragment {


    public NotesFragment() {
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
        
        initList(view);
    }

    private void initList(View view) {
        LinearLayout linearLayout = (LinearLayout) view;

        TextView textView = new TextView(linearLayout.getContext());
        textView.setText("test");
        textView.setTextSize(30);

        final int finalIndex = 1;

        textView.setOnClickListener(v -> {
            showNote(finalIndex);
        });

        linearLayout.addView(textView);
    }

    private void showNote(int index) {
        Intent intent = new Intent();
        intent.setClass(getActivity(), NoteActivity.class);
        intent.putExtra(ChosenNoteFragment.ARG_PARAM_INDEX,index);
        startActivity(intent);
    }
}
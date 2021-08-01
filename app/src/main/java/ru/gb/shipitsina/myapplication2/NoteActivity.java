package ru.gb.shipitsina.myapplication2;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.content.res.Configuration;
import android.os.Bundle;

import java.util.Calendar;

public class NoteActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note);

        if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE){
            finish();
            return;
        }

        if (savedInstanceState == null){
            Fragment fragment = new Fragment();
            fragment.setArguments(getIntent().getExtras());
        }
    }
}
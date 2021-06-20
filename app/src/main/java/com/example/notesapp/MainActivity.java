package com.example.notesapp;

import android.content.res.Configuration;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import java.util.ArrayList;
import java.util.Date;

public class MainActivity extends AppCompatActivity {
    protected static boolean isLandscape;
    protected static ArrayList<Notes> notesArrayList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        isLandscape = getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE;
        notesArrayList = initArrayNotes();

        iniFrame();
    }

    private ArrayList<Notes> initArrayNotes() {
        ArrayList<Notes> arrayList = new ArrayList<>();
        arrayList.add(new Notes("note1", "BlaBlaBlaBlaBlaBla BlaBlaBlaBla", new Date().getTime()));
        arrayList.add(new Notes("note2", "BlaBlaBlaBla", new Date().getTime()));

        return arrayList;
    }

    private void iniFrame() {
        if (!isLandscape) {
            Bundle bundle = new Bundle();
            bundle.putParcelableArrayList(Notes.NOTE_KEY, notesArrayList);
            NotesFragment fragment = new NotesFragment();
            fragment.setArguments(bundle);

            includeFragment(new NotesFragment(), R.id.frame_container);

        } else {
            includeFragment(new NotesFragment(), R.id.frame_container);
            includeFragment(new NotesDescriptionFragment(), R.id.frame_container_land);
        }
    }

    private void includeFragment(Fragment fragment, int frameId) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

        fragmentTransaction.add(frameId, fragment);

        fragmentTransaction.commit();
    }
}
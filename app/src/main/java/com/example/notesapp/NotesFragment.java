package com.example.notesapp;

import android.os.Bundle;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import java.util.ArrayList;


public class NotesFragment extends Fragment {
    public NotesFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_notes, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {

        insertNotes((LinearLayout) view);

        super.onViewCreated(view, savedInstanceState);
    }

    private void insertNotes(LinearLayout view) {
        Bundle args = getArguments();
        if (args != null) {
            ArrayList<Notes> notesArrayList = getArguments().getParcelableArrayList(Notes.NOTE_KEY);

            for (int i = 0; i < notesArrayList.size(); i++) {
                TextView textView = new TextView(getContext());
                textView.setTextSize(30);
                textView.setText(notesArrayList.get(i).getNote());
                view.addView(textView);
                int finalI = i;
                textView.setOnClickListener(v -> {
                    setNoteDisc(notesArrayList.get(finalI));
                });
            }
        }
    }

    private void setNoteDisc(Notes notes) {
        FragmentManager fragmentManager = getParentFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

        Bundle bundle = new Bundle();
        NotesDescriptionFragment fragment = new NotesDescriptionFragment();
        bundle.putParcelable(Notes.NOTE_KEY, notes);
        fragment.setArguments(bundle);

        if (!MainActivity.isLandscape) {
            fragmentTransaction.replace(R.id.frame_container, fragment);
            fragmentTransaction.addToBackStack(null);
        } else {
            fragmentTransaction.replace(R.id.frame_container_land, fragment);
        }

        fragmentTransaction.commit();
    }
}
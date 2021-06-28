package com.example.notesapp.ui.fragments;

import android.os.Bundle;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.example.notesapp.data.Notes;
import com.example.notesapp.R;

import java.util.ArrayList;

public class NotesDescriptionFragment extends Fragment {
    protected static ArrayList<Notes> notesArrayList = new ArrayList<>();

    public NotesDescriptionFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_notes_description, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {

        insertNotes((LinearLayout) view);

        super.onViewCreated(view, savedInstanceState);
    }

    private void insertNotes(LinearLayout view) {
        Bundle args = getArguments();
        if (args != null) {
            Notes notes = getArguments().getParcelable(Notes.NOTE_KEY);

            addNewTextView(notes.getNote(), view);
            addNewTextView(notes.getNoteDescription(), view);
            addNewTextView(notes.getNoteDate(), view);
        }
    }

    private void addNewTextView(String note, LinearLayout view) {
        TextView textView = new TextView(getContext());
        textView.setTextSize(30);
        textView.setText(note);
        view.addView(textView);
    }
}
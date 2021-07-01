package com.example.notesapp.ui.fragments;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.widget.EditText;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.example.notesapp.R;
import com.example.notesapp.data.Notes;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class UpdateNote extends Fragment {
    private static EditText editTextHeading;
    private static EditText editTextNote;
    private static EditText editTextDate;
    private static Notes notes;

    public UpdateNote() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_update_note, container, false);
        Bundle args = getArguments();
        if (args != null) {
            notes = getArguments().getParcelable(Notes.NOTE_KEY);
            initElem(view, notes);
        }
        return view;
    }

    private void initElem(View view, Notes note) {
        editTextHeading = view.findViewById(R.id.editTextHeading);
        editTextNote = view.findViewById(R.id.editTextNote);
        editTextDate = view.findViewById(R.id.editTextDate);

        editTextHeading.setText(note.getNote());
        editTextNote.setText(note.getNoteDescription());
        editTextDate.setText(note.getNoteDate());
    }

    @Override
    public void onDestroyView() {
        @SuppressLint("SimpleDateFormat") SimpleDateFormat f = new SimpleDateFormat("dd.MM.yyyy");

        notes.setNote(editTextHeading.getText().toString());
        notes.setNoteDescription(editTextNote.getText().toString());
        try {
            Date dateNote = f.parse(editTextDate.getText().toString());
            notes.setNoteDate(dateNote.getTime());
        } catch (ParseException e) {
            e.printStackTrace();
        }
        super.onDestroyView();
    }
}
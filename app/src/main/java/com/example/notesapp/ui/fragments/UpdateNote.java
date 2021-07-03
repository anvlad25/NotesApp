package com.example.notesapp.ui.fragments;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.widget.EditText;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.notesapp.R;
import com.example.notesapp.data.NoteMapping;
import com.example.notesapp.data.Notes;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class UpdateNote extends Fragment {
    private EditText editTextHeading;
    private EditText editTextNote;
    private EditText editTextDate;
    private static Notes notes;

    private final FirebaseFirestore firebaseFirestore = FirebaseFirestore.getInstance();
    private final CollectionReference collection = firebaseFirestore.collection(NoteMapping.COLLECTION_PATH);

    public UpdateNote() {
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

        try {
            Date dateNote = f.parse(editTextDate.getText().toString());
            Notes updateNote = new Notes(editTextHeading.getText().toString(), editTextNote.getText().toString(), dateNote.getTime());
            collection.document(notes.getId()).set(NoteMapping.toDocument(updateNote));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        super.onDestroyView();
    }
}
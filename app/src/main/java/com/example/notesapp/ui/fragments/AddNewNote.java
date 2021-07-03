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
import java.util.ArrayList;
import java.util.Date;

public class AddNewNote extends Fragment {
    private EditText editTextHeading;
    private EditText editTextNote;
    private EditText editTextDate;

    private final FirebaseFirestore firebaseFirestore = FirebaseFirestore.getInstance();
    private final CollectionReference collection = firebaseFirestore.collection(NoteMapping.COLLECTION_PATH);

    public AddNewNote() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_add_new_note, container, false);
        initElem(view);
        return view;
    }

    private void initElem(View view) {
        editTextHeading = view.findViewById(R.id.editTextHeading);
        editTextNote = view.findViewById(R.id.editTextNote);
        editTextDate = view.findViewById(R.id.editTextDate);
    }

    @Override
    public void onDestroyView() {
        @SuppressLint("SimpleDateFormat") SimpleDateFormat f = new SimpleDateFormat("dd.MM.yyyy");

        try {
            Date dateNote = f.parse(editTextDate.getText().toString());
            Notes note = new Notes(editTextHeading.getText().toString(), editTextNote.getText().toString(), dateNote.getTime());
            collection.add(NoteMapping.toDocument(note))
                    .addOnSuccessListener(documentReference -> note.setId(documentReference.getId()));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        super.onDestroyView();
    }
}
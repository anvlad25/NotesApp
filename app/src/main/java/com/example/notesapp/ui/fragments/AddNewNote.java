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
import java.util.ArrayList;
import java.util.Date;

public class AddNewNote extends Fragment {
    private static EditText editTextHeading;
    private static EditText editTextNote;
    private static EditText editTextDate;
    private static ArrayList<Notes> notesArrayList;

    public AddNewNote() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_add_new_note, container, false);
        Bundle args = getArguments();
        if (args != null) {
            notesArrayList = getArguments().getParcelableArrayList(Notes.NOTE_KEY);
            initElem(view, notesArrayList);
        }
        return view;
    }

    private void initElem(View view, ArrayList<Notes> notesArrayList) {
        editTextHeading = view.findViewById(R.id.editTextHeading);
        editTextNote = view.findViewById(R.id.editTextNote);
        editTextDate = view.findViewById(R.id.editTextDate);
    }

    @Override
    public void onDestroyView() {
        @SuppressLint("SimpleDateFormat") SimpleDateFormat f = new SimpleDateFormat("dd.MM.yyyy");

        try {
            Date dateNote = f.parse(editTextDate.getText().toString());
            notesArrayList.add(new Notes(editTextHeading.getText().toString(), editTextNote.getText().toString(), dateNote.getTime()));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        super.onDestroyView();
    }
}
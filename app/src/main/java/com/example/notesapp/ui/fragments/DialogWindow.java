package com.example.notesapp.ui.fragments;

import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.view.*;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.example.notesapp.R;

public class DialogWindow extends DialogFragment {

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        View view = requireActivity().getLayoutInflater().inflate(R.layout.dialog_window, null);
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext())
                .setView(view)
                .setPositiveButton(R.string.done, (d, i) -> {
                    ((NotesFragment) requireParentFragment()).deleteNote();
                    dismiss();
                })
                .setNegativeButton(R.string.cancel, (d, i) -> dismiss());

        return builder.create();
    }
}

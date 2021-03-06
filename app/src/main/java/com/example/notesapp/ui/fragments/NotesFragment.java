package com.example.notesapp.ui.fragments;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.*;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.notesapp.MainActivity;
import com.example.notesapp.data.NoteMapping;
import com.example.notesapp.data.Notes;
import com.example.notesapp.R;
import com.example.notesapp.ui.GridItemDecoration;
import com.example.notesapp.ui.NotesAdapter;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.ArrayList;
import java.util.Map;


public class NotesFragment extends Fragment {
    private static final int NUMBEROFCOLUMNS = 3;
    private static final int SPACING = 20;
    private static final String DIALOG_TAG = "dialogTag";
    private static ArrayList<Notes> notesArrayList = new ArrayList<>();
    private NotesAdapter adapter;
    private int position;

    private final FirebaseFirestore firebaseFirestore = FirebaseFirestore.getInstance();
    private final CollectionReference collection = firebaseFirestore.collection(NoteMapping.COLLECTION_PATH);

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_notes, container, false);
        RecyclerView recyclerView = view.findViewById(R.id.recyclerViewNotes);

        notesArrayList = initArrayNotes();
        initRecyclerView(recyclerView, notesArrayList);

        return view;
    }

    private ArrayList<Notes> initArrayNotes() {
        ArrayList<Notes> arrayList = new ArrayList<>();

        collection.get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        for (QueryDocumentSnapshot document : task.getResult()) {
                            Map<String, Object> doc = document.getData();
                            String id = document.getId();
                            Notes notes = NoteMapping.toNotes(id, doc);
                            arrayList.add(notes);
                        }
                        adapter.notifyDataSetChanged();
                    }
                });
        return arrayList;
    }

    private void initRecyclerView(RecyclerView recyclerView, ArrayList<Notes> data) {
        recyclerView.setHasFixedSize(true);

        int numberOfColumns = NUMBEROFCOLUMNS;
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getContext(), numberOfColumns);
        gridLayoutManager.getPaddingLeft();
        recyclerView.setLayoutManager(gridLayoutManager);

        adapter = new NotesAdapter(data, this);
        recyclerView.addItemDecoration(new GridItemDecoration(numberOfColumns, SPACING));
        adapter.setOnItemClickListener((view1, position) -> setNoteDisc(new NotesDescriptionFragment(), notesArrayList.get(position)));

        recyclerView.setAdapter(adapter);
    }

    private void setNoteDisc(Fragment fragment, Notes notes) {
        FragmentManager fragmentManager = getParentFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

        Bundle bundle = new Bundle();
        bundle.putParcelable(Notes.NOTE_KEY, notes);
        fragment.setArguments(bundle);

        fragmentTransaction.replace(R.id.frame_container, fragment);
        fragmentTransaction.addToBackStack(null);

        fragmentTransaction.commit();
    }

    @Override
    public void onCreateContextMenu(@NonNull ContextMenu menu, @NonNull View v, @Nullable ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        MenuInflater inflater = requireActivity().getMenuInflater();
        inflater.inflate(R.menu.card_menu, menu);
    }

    @Override
    public boolean onContextItemSelected(@NonNull MenuItem item) {
        position = adapter.getElemPosition();
        switch (item.getItemId()) {
            case R.id.action_update:
                setNoteDisc(new UpdateNote(), notesArrayList.get(position));
                return true;
            case R.id.action_delete:
                DialogFragment dialogFragment = new DialogWindow();
                dialogFragment.show(getChildFragmentManager(), DIALOG_TAG);
                return true;
        }
        return super.onContextItemSelected(item);
    }

    public void deleteNote() {
        collection.document(notesArrayList.get(position).getId()).delete();
        notesArrayList.remove(position);
        adapter.notifyItemRemoved(position);
    }
}
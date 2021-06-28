package com.example.notesapp.ui.fragments;

import android.os.Bundle;
import android.view.*;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.notesapp.data.Notes;
import com.example.notesapp.R;
import com.example.notesapp.ui.GridItemDecoration;
import com.example.notesapp.ui.NotesAdapter;

import java.util.ArrayList;


public class NotesFragment extends Fragment {
    private static final int NUMBEROFCOLUMNS = 3;
    private static final int SPACING = 20;
    private static ArrayList<Notes> notesArrayList = new ArrayList<>();
    private NotesAdapter adapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_notes, container, false);
        RecyclerView recyclerView = view.findViewById(R.id.recyclerViewNotes);
        Bundle args = getArguments();
        if (args != null) {
            notesArrayList = getArguments().getParcelableArrayList(Notes.NOTE_KEY);
            initRecyclerView(recyclerView, notesArrayList);
        }
        return view;
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
        int position = adapter.getElemPosition();
        switch (item.getItemId()) {
            case R.id.action_update:
                setNoteDisc(new UpdateNote(), notesArrayList.get(position));
                return true;
            case R.id.action_delete:
                notesArrayList.remove(position);
                adapter.notifyItemRemoved(position);
                return true;
        }
        return super.onContextItemSelected(item);
    }

}
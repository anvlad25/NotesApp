package com.example.notesapp.ui;

import android.os.Bundle;
import android.view.Display;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.notesapp.Notes;
import com.example.notesapp.NotesDescriptionFragment;
import com.example.notesapp.R;

import java.util.ArrayList;


public class NotesFragment extends Fragment {
    private static final int NUMBEROFCOLUMNS = 3;
    private static final int SPACING = 20;
    private static ArrayList<Notes> notesArrayList = new ArrayList<>();

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

        NotesAdapter adapter = new NotesAdapter(data);
        recyclerView.addItemDecoration(new GridItemDecoration(numberOfColumns, SPACING));
        adapter.setOnItemClickListener((view1, position) -> setNoteDisc(notesArrayList.get(position)));

        recyclerView.setAdapter(adapter);
    }

    private void setNoteDisc(Notes notes) {
        FragmentManager fragmentManager = getParentFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

        Bundle bundle = new Bundle();
        NotesDescriptionFragment fragment = new NotesDescriptionFragment();
        bundle.putParcelable(Notes.NOTE_KEY, notes);
        fragment.setArguments(bundle);

        fragmentTransaction.replace(R.id.frame_container, fragment);
        fragmentTransaction.addToBackStack(null);

        fragmentTransaction.commit();
    }
}



/*

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
                textView.setOnClickListener(v -> setNoteDisc(notesArrayList.get(finalI)));
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

        fragmentTransaction.replace(R.id.frame_container, fragment);
        fragmentTransaction.addToBackStack(null);

        fragmentTransaction.commit();
    }

 */
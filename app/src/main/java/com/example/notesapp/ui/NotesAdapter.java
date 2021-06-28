package com.example.notesapp.ui;

import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;
import com.example.notesapp.data.Notes;
import com.example.notesapp.R;
import org.jetbrains.annotations.NotNull;

import java.text.SimpleDateFormat;
import java.util.ArrayList;

public class NotesAdapter extends RecyclerView.Adapter<NotesAdapter.ViewHolder> {
    private ArrayList<Notes> dataSource;
    private OnItemClickListener itemClickListener;
    private final Fragment fragment;
    private int elemPosition;

    public NotesAdapter(ArrayList<Notes> dataSource, Fragment fragment) {
        this.dataSource = dataSource;
        this.fragment = fragment;
    }

    @NonNull
    @Override
    public NotesAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item, viewGroup, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull NotesAdapter.ViewHolder viewHolder, int i) {
        viewHolder.getTextView().setText(dataSource.get(i).getNote());
    }

    @Override
    public int getItemCount() {
        return dataSource.size();
    }

    public interface OnItemClickListener {
        void onItemClick(View view, int position);
    }

    public void setOnItemClickListener(OnItemClickListener itemClickListener) {
        this.itemClickListener = itemClickListener;
    }

    public int getElemPosition() {
        System.out.println("position " + elemPosition);
        return elemPosition;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView textView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            textView = (TextView) itemView;

            registerContextMenu(itemView);
            textView.setOnClickListener(v -> {
                if (itemClickListener != null) {
                    elemPosition = getLayoutPosition();
                    itemClickListener.onItemClick(v, getAdapterPosition());
                }
            });
        }

        private void registerContextMenu(@NotNull View itemView) {
            if (fragment != null) {
                itemView.setOnLongClickListener(v -> {
                    elemPosition = getLayoutPosition();
                    return false;
                });
                fragment.registerForContextMenu(itemView);
            }
        }

        public TextView getTextView() {
            return textView;
        }
    }
}

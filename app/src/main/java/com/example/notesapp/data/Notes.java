package com.example.notesapp.data;

import android.annotation.SuppressLint;
import android.os.Parcel;
import android.os.Parcelable;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Notes implements Parcelable {
    public static final String NOTE_KEY = "NOTE_KEY";

    private String id;
    private String note;
    private String noteDescription;
    private String noteDate;

    public Notes(String note, String noteDescription, long noteDate) {
        this.note = note;
        this.noteDescription = noteDescription;

        Date date = new Date(noteDate);
        @SuppressLint("SimpleDateFormat") SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy");
        this.noteDate = dateFormat.format(date);
    }

    protected Notes(Parcel in) {
        note = in.readString();
        noteDescription = in.readString();
        noteDate = in.readString();
    }

    public static final Creator<Notes> CREATOR = new Creator<Notes>() {
        @Override
        public Notes createFromParcel(Parcel in) {
            return new Notes(in);
        }

        @Override
        public Notes[] newArray(int size) {
            return new Notes[size];
        }
    };

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public String getNoteDescription() {
        return noteDescription;
    }

    public void setNoteDescription(String noteDescription) {
        this.noteDescription = noteDescription;
    }

    public String getNoteDate() {
        return noteDate;
    }

    public void setNoteDate(long noteDate) {
        Date date = new Date(noteDate);
        @SuppressLint("SimpleDateFormat") SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy");
        this.noteDate = dateFormat.format(date);
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(note);
        dest.writeString(noteDescription);
        dest.writeString(noteDate);
    }
}

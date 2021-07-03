package com.example.notesapp.data;

import android.annotation.SuppressLint;



import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public abstract class NoteMapping {
    public static final String COLLECTION_PATH = "notes";

    public static class Fields {
        public final static String NOTE = "note";
        public final static String NOTE_DESCRIPTION = "noteDescription";
        public final static String NOTE_DATE = "noteDate";
    }

    public static Map<String, Object> toDocument(Notes notes) {
        Map<String, Object> answer = new HashMap<>();
        answer.put(Fields.NOTE, notes.getNote());
        answer.put(Fields.NOTE_DESCRIPTION, notes.getNoteDescription());
        answer.put(Fields.NOTE_DATE, notes.getNoteDate());
        return answer;
    }

    public static Notes toNotes(String id, Map<String, Object> doc) {
        @SuppressLint("SimpleDateFormat") SimpleDateFormat f = new SimpleDateFormat("dd.MM.yyyy");
        String noteDateStr = (String) doc.get(Fields.NOTE_DATE);
        Date dateNote = new Date();

        if (noteDateStr == null) {
            noteDateStr = "01.01.1900";
        }

        try {
            dateNote = f.parse(noteDateStr);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        Notes answer = new Notes((String) doc.get(Fields.NOTE), (String) doc.get(Fields.NOTE_DESCRIPTION), dateNote.getTime());
        answer.setId(id);
        return answer;
    }
}

package com.example.journal.main;

import com.example.journal.models.Note;

import java.util.List;

public interface MainViewInterface {

    void onNotesLoaded(List<Note> notes);

    void onNoteAdded();

    void onDataNotAvailable();

}

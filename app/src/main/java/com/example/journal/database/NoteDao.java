package com.example.journal.database;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.journal.models.Note;

import java.util.List;

import io.reactivex.Maybe;

@Dao
public interface NoteDao {

    @Query("SELECT * FROM notes")
    Maybe<List<Note>> getAll();

    @Insert
    void insertAll(Note... notes);

}

package com.example.journal;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.example.journal.adapters.NotesAdapter;
import com.example.journal.database.LocalCacheManager;
import com.example.journal.models.Note;
import com.example.journal.ui.AddNote;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class Notepad extends AppCompatActivity {

    @BindView(R.id.rvNotes)
    RecyclerView rvNotes;

    RecyclerView.Adapter adapter;
    List<Note> notesList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notepad);

        ButterKnife.bind(this);

        initViews();
        loadNotes();

    }
    private void initViews() {

        rvNotes.setLayoutManager(new LinearLayoutManager(this));
    }

    private void loadNotes(){

        //Call Method to get Notes
        LocalCacheManager.getInstance(this).getNotes(this);


    }

    @OnClick(R.id.fabAddNote)
    public void addNote(){
        Intent i = new Intent(Notepad.this,AddNote.class);
        startActivity(i);
    }


    public void onNotesLoaded(List<Note> notes) {
        notesList = notes;

        if(notesList.size() == 0){
            onDataNotAvailable();
        }else {
            adapter = new NotesAdapter(this, notes);
            rvNotes.setAdapter(adapter);
        }
    }


    public void onNoteAdded() {
        Toast.makeText(this,"Note Added",Toast.LENGTH_SHORT).show();
    }


    public void onDataNotAvailable() {
        Toast.makeText(this,"No Notes Yet",Toast.LENGTH_SHORT).show();
    }

    }


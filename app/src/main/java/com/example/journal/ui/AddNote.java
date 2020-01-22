package com.example.journal.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Toast;

import com.example.journal.Notepad;
import com.example.journal.R;
import com.example.journal.database.LocalCacheManager;
import com.example.journal.ui.add_note.AddNoteViewInterface;

import butterknife.BindView;
import butterknife.ButterKnife;

public class AddNote extends AppCompatActivity implements AddNoteViewInterface {

    @BindView(R.id.etTitle)
    EditText etTitle;

    @BindView(R.id.etNote)
    EditText etNote;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_note);
        ButterKnife.bind(this);
    }


    private void saveNote(){

        String title = etTitle.getText().toString();
        String note_text = etNote.getText().toString();

        if(title.equals("") || note_text.equals("")){
            showToast("Please fill all the fields before saving");
        }else{
            //Call Method to add note

            LocalCacheManager.getInstance(this).addNotes(this, title,note_text);
        }

    }


    private void showToast(String msg){
        Toast.makeText(this,msg,Toast.LENGTH_SHORT);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_add_note,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();
        if(id == R.id.action_save){
            saveNote();
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onNoteAdded() {
        Toast.makeText(this,"Note Added",Toast.LENGTH_SHORT).show();

        Intent i = new Intent(AddNote.this, Notepad.class);
        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(i);
    }

    @Override
    public void onDataNotAvailable() {
        Toast.makeText(this,"Could not add note",Toast.LENGTH_SHORT).show();
    }

}


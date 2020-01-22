package com.example.journal.database;

import android.content.Context;

import com.example.journal.Notepad;
import com.example.journal.main.MainViewInterface;
import com.example.journal.models.Note;
import com.example.journal.ui.AddNote;
import com.example.journal.ui.add_note.AddNoteViewInterface;

import java.util.List;

import io.reactivex.Completable;
import io.reactivex.CompletableObserver;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Action;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

public class LocalCacheManager {

    private Context context;
    private static LocalCacheManager _instance;
    private AppDatabase db;

    public static LocalCacheManager getInstance(Context context) {
        if (_instance == null) {
            _instance = new LocalCacheManager(context);
        }
        return _instance;
    }
    public LocalCacheManager(Context context) {
        this.context = context;
        db = AppDatabase.getAppDatabase(context);
    }
    public void getNotes(final Notepad mainViewInterface) {
        db.noteDao().getAll().subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new Consumer<List<Note>>() {
            @Override
            public void accept(List<Note> notes) throws Exception {
                mainViewInterface.onNotesLoaded(notes);
            }
        });
    }

    public void addNotes(final AddNoteViewInterface addNoteViewInterface, final String title, final String note_text) {
        Completable.fromAction(new Action() {
            @Override
            public void run() throws Exception {
                Note note = new Note(title, note_text);
                db.noteDao().insertAll(note);
            }
        }).observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io()).subscribe(new CompletableObserver() {
            @Override
            public void onSubscribe(Disposable d) {
            }

            @Override
            public void onComplete() {
                addNoteViewInterface.onNoteAdded();
            }

            @Override
            public void onError(Throwable e) {
                addNoteViewInterface.onDataNotAvailable();
            }
        });
    }


}


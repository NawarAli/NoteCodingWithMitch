package com.letsmakethatapp.note.async;

import android.os.AsyncTask;

import com.letsmakethatapp.note.Persistence.NoteDao;
import com.letsmakethatapp.note.models.Note;

public class InsertAsyncTask extends AsyncTask<Note, Void, Void> {
    private NoteDao mNoteDao;
    public InsertAsyncTask(NoteDao dao) {
        mNoteDao = dao;
    }

    @Override
    protected Void doInBackground(Note... notes) {
        mNoteDao.insertNotes(notes);
        return null;
    }
}

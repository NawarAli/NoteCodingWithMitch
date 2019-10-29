package com.letsmakethatapp.note;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;

import com.letsmakethatapp.note.adapters.NotesRecyclerAdapter;
import com.letsmakethatapp.note.models.Note;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    // Tag
    private static final String TAG = "MainActivity";
    // Ui component
    private RecyclerView mRecyclerView;
    // variable
    private ArrayList<Note> mNotes = new ArrayList<>();
    private NotesRecyclerAdapter mNotesRecyclerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mRecyclerView = findViewById(R.id.recyclerView);
        initRecyclerView();
        insertFakeNotes();
    }
    private void insertFakeNotes(){
        for (int i =0; i < 20; i++){
            Note note = new Note();
            note.setTitle("title #"+i);
            note.setContent("content #"+i);
            note.setTimestamp("Jan 2019");
            mNotes.add(note);
        }
        mNotesRecyclerAdapter.notifyDataSetChanged();
    }
    private void initRecyclerView(){
        LinearLayoutManager linearLayoutManager = new
                LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(linearLayoutManager);
        mNotesRecyclerAdapter = new NotesRecyclerAdapter(mNotes);
        mRecyclerView.setAdapter(mNotesRecyclerAdapter);
    }
}

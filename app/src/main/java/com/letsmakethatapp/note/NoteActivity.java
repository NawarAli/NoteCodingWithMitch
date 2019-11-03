package com.letsmakethatapp.note;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.os.Bundle;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.letsmakethatapp.note.models.Note;

public class NoteActivity extends AppCompatActivity
        implements View.OnTouchListener,
        GestureDetector.OnGestureListener,
        GestureDetector.OnDoubleTapListener,
        View.OnClickListener {
    //const
    private static final int EDIT_MODE_ENABLED = 1;
    private static final int EDIT_MODE_DISABLED = 0;

    //Ui component
    private LinedEditText mLinedEditText;
    private EditText mEditTitle;
    private TextView mViewTitle;
    private RelativeLayout mBackArrowContainer, mCheckContainer;
    private ImageButton mCheck, mBackArrow;

    //vars
    private boolean mIsNewNote;
    private Note mNote;
    private int mMode;
    private GestureDetector mGestureDetector;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note);
        intiViews();
        mIsNewNote = getIncomingIntent();
        setListener();
        if (mIsNewNote) {
            //this is new note go to edit mode
            setNewNoteProperties();
            enableEditMode();

        } else {
            setNoteProperties();
            disableContentInteraction();
        }
    }

    @SuppressLint("ClickableViewAccessibility")
    private void setListener() {
        mLinedEditText.setOnTouchListener(this);
        mViewTitle.setOnClickListener(this);
        mCheck.setOnClickListener(this);
        mBackArrow.setOnClickListener(this);
        mGestureDetector = new GestureDetector(this, this);
    }

    private void intiViews() {
        mLinedEditText = findViewById(R.id.note_text);
        mEditTitle = findViewById(R.id.note_text_edit);
        mViewTitle = findViewById(R.id.note_text_title);
        mCheckContainer = findViewById(R.id.check_container);
        mBackArrowContainer = findViewById(R.id.back_arrow_container);
        mCheck = findViewById(R.id.toolbar_check);
        mBackArrow = findViewById(R.id.toolbar_back_arrow);
    }

    private boolean getIncomingIntent() {
        if (getIntent().hasExtra("selected_note")) {
            mNote = getIntent().getParcelableExtra("selected_note");
            mMode = EDIT_MODE_DISABLED;
            return false;
        }
        mMode = EDIT_MODE_ENABLED;
        return true;
    }

    @SuppressLint("SetTextI18n")
    private void setNewNoteProperties() {
        mViewTitle.setText("Note Title");
        mEditTitle.setText("Note Title");
    }

    private void setNoteProperties() {
        mViewTitle.setText(mNote.getTitle());
        mEditTitle.setText(mNote.getTitle());
        mLinedEditText.setText(mNote.getContent());
    }

    private void enableEditMode() {

        mBackArrowContainer.setVisibility(View.GONE);
        mCheckContainer.setVisibility(View.VISIBLE);
        mViewTitle.setVisibility(View.GONE);
        mEditTitle.setVisibility(View.VISIBLE);
        mMode = EDIT_MODE_ENABLED;
        enableContentInteraction();
    }

    private void disableEditMode() {
        mBackArrowContainer.setVisibility(View.VISIBLE);
        mCheckContainer.setVisibility(View.GONE);
        mViewTitle.setVisibility(View.VISIBLE);
        mEditTitle.setVisibility(View.GONE);
        mMode = EDIT_MODE_DISABLED;
        disableContentInteraction();
    }

    private void enableContentInteraction() {
        mLinedEditText.setKeyListener(new EditText(this).getKeyListener());
        mLinedEditText.setFocusable(true);
        mLinedEditText.setFocusableInTouchMode(true);
        mLinedEditText.setCursorVisible(true);
        mLinedEditText.requestFocus();
    }

    private void disableContentInteraction() {
        mLinedEditText.setKeyListener(null);
        mLinedEditText.setFocusable(false);
        mLinedEditText.setFocusableInTouchMode(false);
        mLinedEditText.setCursorVisible(false);
        mLinedEditText.clearFocus();
    }


    private void showSoftKeyboard() {
        InputMethodManager imm = (InputMethodManager)
                this.getSystemService(Activity.INPUT_METHOD_SERVICE);
        View view = this.getCurrentFocus();
        if (view == null) {
            view = new View(this);
        }
        assert imm != null;
        imm.showSoftInput(view, 0);
    }

    private void hideSoftKeyboard() {
        InputMethodManager imm = (InputMethodManager)
                this.getSystemService(Activity.INPUT_METHOD_SERVICE);
        View view = this.getCurrentFocus();
        if (view == null) {
            view = new View(this);
        }
        assert imm != null;
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.toolbar_check: {
                hideSoftKeyboard();
                disableEditMode();
                mMode = EDIT_MODE_DISABLED;
                break;
            }
            case R.id.note_text_title: {
                enableEditMode();
                showSoftKeyboard();
                mEditTitle.requestFocus();
                mEditTitle.setSelection(mEditTitle.getText().length());
                mMode = EDIT_MODE_ENABLED;
                break;
            }
            case R.id.toolbar_back_arrow: {
                onBackPressed();
                break;
            }
        }
    }

    @Override
    public void onBackPressed() {
        if (mMode == EDIT_MODE_ENABLED) {
            onClick(mCheck);
            mMode = EDIT_MODE_DISABLED;
        } else
            super.onBackPressed();
    }


    @SuppressLint("ClickableViewAccessibility")
    @Override
    public boolean onTouch(View v, MotionEvent event) {
        return mGestureDetector.onTouchEvent(event);
    }

    @Override
    public boolean onDoubleTap(MotionEvent e) {
        enableEditMode();
        return false;
    }

    //EXTRA
    @Override
    public boolean onDoubleTapEvent(MotionEvent e) {
        return false;
    }

    @Override
    public void onShowPress(MotionEvent e) {

    }

    @Override
    public boolean onSingleTapUp(MotionEvent e) {
        return false;
    }

    @Override
    public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
        return false;
    }

    @Override
    public void onLongPress(MotionEvent e) {

    }

    @Override
    public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
        return false;
    }

    @Override
    public boolean onSingleTapConfirmed(MotionEvent e) {

        return false;
    }

    @Override
    public boolean onDown(MotionEvent e) {
        return false;
    }

}

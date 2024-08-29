package com.example.keepnotes;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.Timestamp;
import com.google.firebase.firestore.DocumentReference;

public class Details_saveNotes extends AppCompatActivity {
    EditText titleText, contentText;
    CardView saveBtn;
    TextView pgtitl;
    String Title, contenT, DOCID;
    boolean isedit = false;
    ImageView delete;

    @SuppressLint("WrongViewCast")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details_save_notes);

        titleText = findViewById(R.id.edit1);
        contentText = findViewById(R.id.edit2);
        saveBtn = findViewById(R.id.btn);
        pgtitl = findViewById(R.id.txtview);
        delete = findViewById(R.id.deleteIcon);

        Title = getIntent().getStringExtra("title");
        contenT = getIntent().getStringExtra("content");
        DOCID = getIntent().getStringExtra("docID");

        if (DOCID != null && !DOCID.isEmpty()) {
            isedit = true;
        }

        titleText.setText(Title);
        contentText.setText(contenT);
        if(isedit){
            pgtitl.setText("Edit Your note");
            delete.setVisibility(View.VISIBLE);

        }

        saveBtn.setOnClickListener((v) -> savenote());
        delete.setOnClickListener((v) -> deleteitfromfireBse());
    }

    void savenote() {
        String noteTitl = titleText.getText().toString();
        String noteContnt = contentText.getText().toString();

        if (noteTitl.isEmpty()) {
            titleText.setError("Title is required");
            return;
        }

        details save = new details();
        save.setTitl(noteTitl);
        save.setContnt(noteContnt);
        save.setTime(Timestamp.now());

        saveNotetofirebas(save);
    }


    void saveNotetofirebas(details save) {
        DocumentReference documentReference;
        if(isedit){
            documentReference = Udetails.getCollectionReferenceForNotes().document(DOCID);

        }else {
            documentReference = Udetails.getCollectionReferenceForNotes().document();

        }
        documentReference.set(save).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()) {
                    // Note is added
                    Udetails.showToast(Details_saveNotes.this,  "Added");
                    Intent intent = new Intent(Details_saveNotes.this, AddNote_AllScreen.class);
                    startActivity(intent);
                    finish();
                } else {
                    // Failed while adding note
                    Udetails.showToast(Details_saveNotes.this, "Failed while adding note");
                }
            }
        });
    }
    void deleteitfromfireBse(){
        DocumentReference documentReference;
        documentReference = Udetails.getCollectionReferenceForNotes().document(DOCID);
        documentReference.delete().addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()) {
                    Udetails.showToast( Details_saveNotes.this,  "Note deleted");
                    Intent intent = new Intent(Details_saveNotes.this, AddNote_AllScreen.class);
                    startActivity(intent);
                    finish();
                } else {
                    Udetails.showToast( Details_saveNotes.this,  "Failed while deleting note");
                }
            }
        });
    }
}

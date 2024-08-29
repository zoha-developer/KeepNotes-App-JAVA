package com.example.keepnotes;

import android.annotation.SuppressLint;
import android.content.ClipData;
import android.content.Intent;
import android.os.Bundle;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.SearchView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.GoogleAuthProvider;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

public class AddNote_AllScreen extends AppCompatActivity {
    CardView addNote;
    RecyclerView Recyclr;
    holdrAdapt holdr;
    ImageView menu;
    LinearLayout promptLayout;
    TextView allNotesTex;



    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_note_all_screen);

        addNote = findViewById(R.id.btn_add);
        Recyclr = findViewById(R.id.recycler_view);
        menu = findViewById(R.id.menu_icon);
        promptLayout = findViewById(R.id.prompt_layout);
        allNotesTex = findViewById(R.id.txtview);

        addNote.setOnClickListener(v -> startActivity(new Intent(this, Details_saveNotes.class)));
        menu.setOnClickListener((v) ->shomenu() );
        setRecyclr();
        checkForNotes();
    }
    private void shomenu() {
        PopupMenu popup = new PopupMenu(this, menu);
        popup.getMenu().add("Sign In");
        popup.show();
        popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                if (item.getTitle().equals("Sign In")) {
                    startActivity(new Intent(AddNote_AllScreen.this,siginIn.class));
                    finish();
                    return true;
                }
                return false;
            }
        });
    }

    void setRecyclr() {
        Query query = Udetails.getCollectionReferenceForNotes()
                .orderBy("time", Query.Direction.DESCENDING);  // Ensure "time" matches your field name

        FirestoreRecyclerOptions<details> options = new FirestoreRecyclerOptions.Builder<details>()
                .setQuery(query, details.class)
                .build();
        Recyclr.setLayoutManager(new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL));

        holdr = new holdrAdapt(options, this);
        Recyclr.setAdapter(holdr);
        holdr.notifyDataSetChanged();

    }
    @Override
    protected void onStart() {
        super.onStart();
       {
            holdr.startListening();
        }
    }
    @Override
    protected void onStop() {
        super.onStop();
        {
            holdr.stopListening();
        }
    }
    @Override
    protected void onResume() {
        super.onResume();
        holdr.notifyDataSetChanged();
    }

    private void checkForNotes() {
        FirebaseFirestore db = FirebaseFirestore.getInstance();

        // Fetch notes
        db.collection("notes")  // Replace "notes" with your actual collection name
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        boolean hasNotes = task.getResult() != null && !task.getResult().isEmpty();
                        if (hasNotes) {
                            // Notes exist, hide the prompt and show "All Notes"
                            allNotesTex.setVisibility(View.VISIBLE);
                            promptLayout.setVisibility(View.GONE);
                        } else {
                            // No notes, show the prompt and hide "All Notes"
                            promptLayout.setVisibility(View.VISIBLE);
                            allNotesTex.setVisibility(View.GONE);
                        }
                    } else {
                        // Handle error if needed
                        Toast.makeText(AddNote_AllScreen.this, "Error loading notes", Toast.LENGTH_SHORT).show();
                        promptLayout.setVisibility(View.VISIBLE);
                        allNotesTex.setVisibility(View.GONE);
                    }
                });
    }


}

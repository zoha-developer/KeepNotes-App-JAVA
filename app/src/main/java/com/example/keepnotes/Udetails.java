package com.example.keepnotes;

import android.content.Context;
import android.widget.Toast;

import com.google.firebase.Timestamp;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.text.SimpleDateFormat;

public class Udetails {

    //Its a Utility class

    static void showToast(Context context, String message){
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show();

    }

    static CollectionReference getCollectionReferenceForNotes() {
        return FirebaseFirestore.getInstance().collection("notes");
    }

    static String timestampToString (Timestamp time) {
        return new SimpleDateFormat(  "MM/dd/yyyy").format(time.toDate());
    }



}

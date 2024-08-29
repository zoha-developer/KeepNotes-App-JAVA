package com.example.keepnotes;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;

import java.util.List;

//this is NoteAdapter class


public class holdrAdapt extends FirestoreRecyclerAdapter<details, holdrAdapt.Noteviewholdr> {

    private Context context;



    public holdrAdapt(@NonNull FirestoreRecyclerOptions<details> options, Context context) {
        super(options);
        this.context = context;


    }

    @Override
    protected void onBindViewHolder(@NonNull Noteviewholdr holder, int position, @NonNull details save) {

        int randomColor = getRandomColor();
        holder.cardView.setCardBackgroundColor(randomColor);
        holder.notetitle.setText(save.Titl);
        holder.notecontent.setText(save.Contnt);
        holder.notetime.setText(Udetails.timestampToString(save.time));

        holder.itemView.setOnClickListener((v) -> {
            Intent intnt = new Intent(context, Details_saveNotes.class);
            intnt.putExtra("title", save.Titl);
            intnt.putExtra("content", save.Contnt);
            String DOCID = getSnapshots().getSnapshot(position).getId();
            intnt.putExtra("docID", DOCID);
            context.startActivity(intnt);

        });
    }
    @NonNull
    @Override
    public Noteviewholdr onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_recylr_view, parent, false);
        return new Noteviewholdr(view);
    }
    private int getRandomColor() {
        int red = (int) (Math.random() * 106) + 150;  // Values between 150 and 255
        int green = (int) (Math.random() * 106) + 150;  // Values between 150 and 255
        int blue = (int) (Math.random() * 106) + 150;  // Values between 150 and 255

        int color = Color.rgb(red, green, blue);
        Log.d("Color", "Generated Color: #" + Integer.toHexString(color));
        return color;
    }

    class Noteviewholdr extends RecyclerView.ViewHolder {
        TextView notetitle, notecontent, notetime;
        CardView cardView;

        public Noteviewholdr(@NonNull View itemView) {
            super(itemView);
            notetitle = itemView.findViewById(R.id.nottitle);
            notecontent = itemView.findViewById(R.id.notcontent);
            notetime = itemView.findViewById(R.id.note_time);
            cardView = itemView.findViewById(R.id.noteview);
        }
    }
}

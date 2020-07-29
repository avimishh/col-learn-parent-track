package com.example.coll_tab.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.coll_tab.R;
import com.example.coll_tab.models.Note;

import java.util.ArrayList;
import java.util.Locale;

//import android.support.annotation.NonNull;
//import android.support.v7.widget.RecyclerView;

public class CardAdapterNote extends RecyclerView.Adapter<CardAdapterNote.NoteHolder> {

    private Context context;
    private ArrayList<Note> notes;

    public CardAdapterNote(Context context, ArrayList<Note> notes) {
        this.context = context;
        this.notes = notes;
    }

    @NonNull
    @Override
    public NoteHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_card_note, parent, false);
        return new NoteHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull NoteHolder holder, int position) {
        Note note = notes.get(position);
        holder.setDetails(note);
    }

    @Override
    public int getItemCount() {
        return notes.size();
    }

    class NoteHolder extends RecyclerView.ViewHolder {

        private TextView[] txt = new TextView[3];// = {txtDate, txtNumQuests, txtPlus, txtMinus, txtMulti, txtScore};
        private ImageView img;

        NoteHolder(View itemView) {
            super(itemView);
            txt[0] = itemView.findViewById(R.id.txtDate2);
            txt[1] = itemView.findViewById(R.id.txtTeacher);
            txt[2] = itemView.findViewById(R.id.txtMessage);
            img = itemView.findViewById(R.id.imgTeacher);
        }

        void setDetails(Note note) {
            txt[0].setText(getDate(note.date));
            txt[1].setText(String.format(Locale.US, "המורה: %s %s", note.teacher.firstName, note.teacher.lastName));
            txt[2].setText(note.message);
            img.setImageResource(R.drawable.teacher);


//            String[] opHeb = {"חיבור", "חיסור", "כפל  "};
//            txt[0].setText(getDate(sheet.date));
//            for (int i = 0; i < 3; i++) {
//                Question quest = sheet.questions[i];
//                txt[i+1].setText(String.format(Locale.US, " %s : %d מתוך %d", opHeb[i], quest.correct, quest.asked  ));
//            }
//            int score = (int)calculateScore(sheet.questions);
//            txt[4].setText(String.format(Locale.US, " ציון:   %d%%", score  ));
//            if(score>=60) {
//                img.setImageResource(R.drawable.checkmark);
//            }
//            else {
//                img.setImageResource(R.drawable.notcheckmark_foreground);
//            }
//            Question quest = sheet.questions[0];
//            txtPlus.setText(String.format(Locale.US, " חיבור : %d מתוך %d", quest.correct, quest.asked  ));
//            quest = sheet.questions[1];
//            txtMinus.setText(String.format(Locale.US, " חיסור : %d מתוך %d", quest.operator, quest.correct, quest.asked  ));
//            quest = sheet.questions[2];
//            txtMulti.setText(String.format(Locale.US, " כפל : %d מתוך %d", quest.operator, quest.correct, quest.asked  ));
        }

        String getDate(String date) {
            return date.substring(8, 10) + "/" + date.substring(5, 7) + "/" + date.substring(0, 4);
        }
    }
}
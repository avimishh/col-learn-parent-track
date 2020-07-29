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
import com.example.coll_tab.models.Question;
import com.example.coll_tab.models.Sheet;

import java.util.ArrayList;
import java.util.Locale;

//import android.support.annotation.NonNull;
//import android.support.v7.widget.RecyclerView;

public class CardAdapter extends RecyclerView.Adapter<CardAdapter.SheetHolder> {

    private Context context;
    private ArrayList<Sheet> sheets;

    public CardAdapter(Context context, ArrayList<Sheet> sheets) {
        this.context = context;
        this.sheets = sheets;
    }

    @NonNull
    @Override
    public SheetHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_card, parent, false);
        return new SheetHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SheetHolder holder, int position) {
        Sheet sheet = sheets.get(position);
        holder.setDetails(sheet);
    }

    @Override
    public int getItemCount() {
        return sheets.size();
    }

    class SheetHolder extends RecyclerView.ViewHolder {

        private TextView[] txt = new TextView[5];// = {txtDate, txtNumQuests, txtPlus, txtMinus, txtMulti, txtScore};
        private ImageView img;
        SheetHolder(View itemView) {
            super(itemView);
            txt[0] = itemView.findViewById(R.id.txtDate);
//            txtNumQuests = itemView.findViewById(R.id.txtNumQuests);
            txt[1] = itemView.findViewById(R.id.txtPlus);
            txt[2] = itemView.findViewById(R.id.txtMinus);
            txt[3] = itemView.findViewById(R.id.txtMulti);
            txt[4] = itemView.findViewById(R.id.txtScore);
            img = itemView.findViewById(R.id.imgScore);
        }

        void setDetails(Sheet sheet) {
            String[] opHeb = {"חיבור", "חיסור", "כפל  "};
            txt[0].setText(getDate(sheet.date));
            for (int i = 0; i < 3; i++) {
                Question quest = sheet.questions[i];
                txt[i+1].setText(String.format(Locale.US, " %s : %d מתוך %d", opHeb[i], quest.correct, quest.asked  ));
            }
            int score = (int)calculateScore(sheet.questions);
            txt[4].setText(String.format(Locale.US, " ציון:   %d%%", score  ));
            if(score>=60) {
                img.setImageResource(R.drawable.checkmark);
            }
            else {
                img.setImageResource(R.drawable.ncheckmark_foreground);
            }
//            Question quest = sheet.questions[0];
//            txtPlus.setText(String.format(Locale.US, " חיבור : %d מתוך %d", quest.correct, quest.asked  ));
//            quest = sheet.questions[1];
//            txtMinus.setText(String.format(Locale.US, " חיסור : %d מתוך %d", quest.operator, quest.correct, quest.asked  ));
//            quest = sheet.questions[2];
//            txtMulti.setText(String.format(Locale.US, " כפל : %d מתוך %d", quest.operator, quest.correct, quest.asked  ));
        }

        float calculateScore(Question[] quests){
            int nAsked = 0, nCorrect = 0;
            for (int i = 0; i < 3; i++) {
                nAsked += quests[i].asked;
                nCorrect += quests[i].correct;
            }
            return (float)nCorrect*100/(float)nAsked;
        }

        String getDate(String date){
            return date.substring(8,10) + "/" + date.substring(5,7) + "/" + date.substring(0,4);
        }
    }
}
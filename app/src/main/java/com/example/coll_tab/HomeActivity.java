package com.example.coll_tab;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.coll_tab.models.Child;
import com.example.coll_tab.models.Note;
import com.example.coll_tab.models.Parent;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class HomeActivity extends AppCompatActivity {
    SharedPreferences sp;
    SharedPreferences.Editor sedt;

    String[] childrenNames;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        sp = getSharedPreferences("Coll_tab", 0);
        sedt = sp.edit();
        // button by id
        Button btn_update = (Button) findViewById(R.id.button3);
        Button btn_note = (Button) findViewById(R.id.button4);
        Button btn_statical = (Button) findViewById(R.id.button5);
        Button btn_child_details = (Button) findViewById(R.id.button6);

        // 1. button update and Intent to UpdateDetailsActivity
        btn_update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent myIntent_update = new Intent(HomeActivity.this, UpdateDetailsActivity.class);
                HomeActivity.this.startActivity(myIntent_update);
            }
        });

        // 2. button note and Intent to NotesActivity
        btn_note.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setDialog(NotesActivity.class);
//                Intent myIntent_note = new Intent(HomeActivity.this, NotesActivity.class);
//                HomeActivity.this.startActivity(myIntent_note);
            }
        });

        // 3. button statical and Intent to PerformanceChildActivity
        btn_statical.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setDialog(PerformanceChildActivity.class);
//                Intent myIntent_statical = new Intent(HomeActivity.this, PerformanceChildActivity.class);
//                HomeActivity.this.startActivity(myIntent_statical);
            }
        });

        // 4. button child details and Intent to ChildDetailsActivity
        btn_child_details.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setDialog(ChildDetailsActivity.class);
//                Intent myIntent_child_details = new Intent(HomeActivity.this, ChildDetailsActivity.class);
//                HomeActivity.this.startActivity(myIntent_child_details);
            }
        });


    }

    @Override
    protected void onResume() {
        super.onResume();
        setHomeName();
    }

    void setHomeName() {
        TextView name = (TextView) findViewById(R.id.textView2);
        String parentString = sp.getString("_parent", null);
        ObjectMapper mapper = new ObjectMapper();
        try {
            Parent parent = mapper.readValue(parentString, Parent.class);
            name.setText(parent.firstName);
            childrenNames = new String[parent.childrenRef.length];
            for (int i = 0; i < parent.childrenRef.length; i++) {
                Child child = parent.childrenRef[i];
                childrenNames[i] = child.firstName + " " + child.lastName;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    void setDialog(final Class nextAct) {
        AlertDialog.Builder builder = new AlertDialog.Builder(HomeActivity.this);
        builder.setTitle("בחר ילד:");
        builder.setItems(childrenNames,new DialogInterface.OnClickListener() {
            @Override
            public void onClick (DialogInterface dialog, int which){
                System.out.println(which);
                sedt.putInt("child_index", which);
                sedt.commit();
                Intent myIntent = new Intent(HomeActivity.this, nextAct);
                HomeActivity.this.startActivity(myIntent);
            }
        });
        builder.show();
    }


}

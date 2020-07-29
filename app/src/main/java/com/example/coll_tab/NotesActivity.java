package com.example.coll_tab;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Toast;

import com.example.coll_tab.adapter.CardAdapterNote;
import com.example.coll_tab.models.Note;
import com.example.coll_tab.models.Parent;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

public class NotesActivity extends AppCompatActivity implements VolleyCallback {
    SharedPreferences sp;

    private CardAdapterNote adapter;
    private ArrayList<Note> noteArrayList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notes);

        sp = getSharedPreferences("Coll_tab", 0);

        initView();
        API.childNotesRequest(NotesActivity.this, getChildId());
    }

    private void initView() {
        RecyclerView recyclerView = findViewById(R.id.recyclerView3);
        recyclerView.setLayoutManager(new LinearLayoutManager(NotesActivity.this));
        recyclerView.setHasFixedSize(true);
        noteArrayList = new ArrayList<>();
        adapter = new CardAdapterNote(NotesActivity.this, noteArrayList);
        recyclerView.setAdapter(adapter);
//        recyclerView.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.VERTICAL));
    }

    private void createListData(Note[] notes) {
        noteArrayList.addAll(Arrays.asList(notes));
        adapter.notifyDataSetChanged();
    }

    String getChildId(){
        try {
            ObjectMapper mapper = new ObjectMapper();
            String parentString = sp.getString("_parent", null);
            Parent parent = mapper.readValue(parentString, Parent.class);
            int child_index = sp.getInt("child_index",0);
            return parent.childrenRef[child_index].id;
        }catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public void onSuccess(String result) {
        ObjectMapper mapper = new ObjectMapper();
        try {
            Note[] notes = mapper.readValue(result, Note[].class);
            createListData(notes);
        }catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onError(String err) {
        Toast.makeText(NotesActivity.this, "התקשורת לשרת נכשלה!", Toast.LENGTH_SHORT).show();
    }
}

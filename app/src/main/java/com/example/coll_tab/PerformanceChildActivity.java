package com.example.coll_tab;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.coll_tab.adapter.CardAdapter;
import com.example.coll_tab.models.MathStats;
import com.example.coll_tab.models.Parent;
import com.example.coll_tab.models.Sheet;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

public class PerformanceChildActivity extends AppCompatActivity implements VolleyCallback {
    SharedPreferences sp;

    private CardAdapter adapter;
    private ArrayList<Sheet> sheetArrayList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_performance_child);

        sp = getSharedPreferences("Coll_tab", 0);

        initView();
        API.childPerformanceRequest(PerformanceChildActivity.this, getChildId());

//        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
//        getSupportActionBar().setTitle("Recycler View with Card View");
    }

    private void initView() {
        RecyclerView recyclerView = findViewById(R.id.recyclerView2);
        recyclerView.setLayoutManager(new LinearLayoutManager(PerformanceChildActivity.this));
        recyclerView.setHasFixedSize(true);
        sheetArrayList = new ArrayList<>();
        adapter = new CardAdapter(PerformanceChildActivity.this, sheetArrayList);
        recyclerView.setAdapter(adapter);
//        recyclerView.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.VERTICAL));
//        createListData();
    }

    private void createListData(Sheet[] sheets) {
        sheetArrayList.addAll(Arrays.asList(sheets));
        adapter.notifyDataSetChanged();
    }

//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        switch (item.getItemId()) {
//            case android.R.id.home:
//                finish();
//                break;
//        }
//        return super.onOptionsItemSelected(item);
//    }

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
            MathStats[] stats = mapper.readValue(result, MathStats[].class);
            if(stats.length == 0){
                Toast.makeText(PerformanceChildActivity.this, "לא קיימות סטטיסטיקות!", Toast.LENGTH_SHORT).show();
                return;
            }
            createListData(stats[0].sheets);
        }catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onError(String err) {
        Toast.makeText(PerformanceChildActivity.this, "התקשורת לשרת נכשלה!", Toast.LENGTH_SHORT).show();
    }
}

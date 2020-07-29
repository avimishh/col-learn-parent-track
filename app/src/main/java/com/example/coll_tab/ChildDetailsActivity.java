package com.example.coll_tab;

import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import com.example.coll_tab.models.Child;
import com.example.coll_tab.models.Parent;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.util.Calendar;

public class ChildDetailsActivity extends AppCompatActivity implements VolleyCallback {
    TextView text_f_name;
    TextView text_l_name;
    TextView text_id;
    TextView text_b_date;
    TextView text_age;
    TextView text_address;
    TextView text_phone;
//    TextView text_level;
    TextView text_pass;
    SharedPreferences sp;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_child_details);

        sp = getSharedPreferences("Coll_tab", 0);

        // TextView by id
        text_f_name = (TextView) findViewById(R.id.textView13);
        text_l_name = (TextView) findViewById(R.id.textView14);
        text_id = (TextView) findViewById(R.id.textView15);
        text_b_date = (TextView) findViewById(R.id.textView16);
        text_age = (TextView) findViewById(R.id.textView17);
        text_address = (TextView) findViewById(R.id.textView18);
        text_phone = (TextView) findViewById(R.id.textView19);
//        text_level = (TextView) findViewById(R.id.textView20);
        text_pass = (TextView) findViewById(R.id.textView21);

        try {
            ObjectMapper mapper = new ObjectMapper();
            String parentString = sp.getString("_parent", null);
            Parent parent = mapper.readValue(parentString, Parent.class);
            int child_index = sp.getInt("child_index", 0);
            String child_id = parent.childrenRef[child_index].id;
            API.childRequest(this, child_id);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void onSuccess(String result) {
        ObjectMapper mapper = new ObjectMapper();
        try {
            Child child = mapper.readValue(result, Child.class);

            text_f_name.setText(child.firstName);
            text_l_name.setText(child.lastName);
            text_id.setText(child.id);
            text_b_date.setText(getDate(child.birth));
            text_address.setText(child.address);
            text_phone.setText(child.phone);
            text_age.setText(calculateAge(child.birth));
            text_pass.setText(child.gamesPassword);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onError(String err) {
        Toast.makeText(ChildDetailsActivity.this, "התקשורת לשרת נכשלה!", Toast.LENGTH_SHORT).show();
    }

    String getDate(String date) {
        return date.substring(8, 10) + "/" + date.substring(5, 7) + "/" + date.substring(0, 4);
    }

    String calculateAge(String date) {
        int year = Integer.parseInt(date.substring(0, 4));
        int month = Integer.parseInt(date.substring(5, 7));
        int day = Integer.parseInt(date.substring(8, 10));
        Calendar lCal = Calendar.getInstance();
        int lYear = lCal.get(Calendar.YEAR);
        int lMonth = lCal.get(Calendar.MONTH) + 1;
        int lDay = lCal.get(Calendar.DATE);
        int age = lYear - year;
        if ((month < lMonth) || ((month == lMonth) && (day < lDay))) {
            --age;
        }
        return "" + age;
    }
}



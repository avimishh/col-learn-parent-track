package com.example.coll_tab;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.basgeekball.awesomevalidation.AwesomeValidation;
import com.basgeekball.awesomevalidation.ValidationStyle;
import com.basgeekball.awesomevalidation.utility.RegexTemplate;
import com.example.coll_tab.models.Parent;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;

public class UpdateDetailsActivity extends AppCompatActivity implements VolleyCallback {
    SharedPreferences sp;
    SharedPreferences.Editor sedt;
    // Initialize Variable
    EditText edit_f_name_1, edit_l_name_1, edit_id_1, edit_phone_1, edit_pass_1;
    Button btn_update_all;

    AwesomeValidation awesomeValidation;

    String parent_id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_details);

        sp = getSharedPreferences("Coll_tab", 0);
        sedt = sp.edit();

        // Assign Variable
        edit_f_name_1 = (EditText) findViewById(R.id.editText8);
        edit_l_name_1 = (EditText) findViewById(R.id.editText9);
        edit_id_1 = (EditText) findViewById(R.id.editText10);
        edit_phone_1 = (EditText) findViewById(R.id.editText11);
        edit_pass_1 = (EditText) findViewById(R.id.editText12);

        // button Assign Variable
        btn_update_all = (Button) findViewById(R.id.button7);

        // Initialize Validation Style
        awesomeValidation = new AwesomeValidation(ValidationStyle.BASIC);

        // Add Validation for First Name
        awesomeValidation.addValidation(this, R.id.editText8, RegexTemplate.NOT_EMPTY, R.string.invalid_first_name);
        // For Last Name
        awesomeValidation.addValidation(this, R.id.editText9, RegexTemplate.NOT_EMPTY, R.string.invalid_last_name);
        // For Id
        awesomeValidation.addValidation(this, R.id.editText10, "[0-9]{9}", R.string.invalid_id);
        // For Phone
        awesomeValidation.addValidation(this, R.id.editText11, "05[0-9]{8}", R.string.invalid_phone);
        // For Password
        awesomeValidation.addValidation(this, R.id.editText12, ".{5,}", R.string.invalid_password);

        btn_update_all.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // Check Validation
                if (awesomeValidation.validate()) {
                    String[] parentDetails = {edit_f_name_1.getText().toString(), edit_l_name_1.getText().toString(),
                            edit_id_1.getText().toString(), edit_phone_1.getText().toString(), edit_pass_1.getText().toString()};
                    String userJWT = sp.getString("JWT", null);
                    API.updateParentRequest(UpdateDetailsActivity.this, parent_id, userJWT, parentDetails);
                } else {
                    Toast.makeText(UpdateDetailsActivity.this, "הפרטים לעדכון אינם תקינים!", Toast.LENGTH_SHORT).show();
                }

            }
        });

        String parentString = sp.getString("_parent", null);
        System.out.println(parentString);
        ObjectMapper mapper = new ObjectMapper();
        try {
            Parent parent = mapper.readValue(parentString, Parent.class);
            parent_id = parent.id;
            edit_f_name_1.setText(parent.firstName);
            edit_l_name_1.setText(parent.lastName);
            edit_id_1.setText(parent.id);
            edit_phone_1.setText(parent.phone);
//            edit_pass_1.setText(parent.password);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onSuccess(String result) {
        sedt.putString("_parent", result);
        sedt.commit();
        ObjectMapper mapper = new ObjectMapper();
        try {
            Parent parent = mapper.readValue(result, Parent.class);
            edit_f_name_1.setText(parent.firstName);
            edit_l_name_1.setText(parent.lastName);
            edit_id_1.setText(parent.id);
            edit_phone_1.setText(parent.phone);
//            edit_pass_1.setText(parent.password);
        } catch (Exception ex) {

        }

//        Intent myIntent_update_all = new Intent(UpdateDetailsActivity.this, HomeActivity.class);
        // On Success
        Toast.makeText(UpdateDetailsActivity.this, "העדכון הצליח!", Toast.LENGTH_SHORT).show();
//        UpdateDetailsActivity.this.startActivity(myIntent_update_all);
        finish();
    }

    @Override
    public void onError(String err) {
        Toast.makeText(UpdateDetailsActivity.this, "העדכון נכשל!", Toast.LENGTH_SHORT).show();
    }
}

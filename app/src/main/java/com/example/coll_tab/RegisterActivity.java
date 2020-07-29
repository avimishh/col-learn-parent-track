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
import com.example.coll_tab.models.User;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.json.JSONObject;

public class RegisterActivity extends AppCompatActivity implements VolleyCallback{

    // Initialize Variable
    EditText edit_f_name, edit_l_name, edit_id, edit_phone, edit_pass;
    Button btn_reg_home_screen;

    AwesomeValidation awesomeValidation;
    SharedPreferences sp;
    SharedPreferences.Editor sedt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        sp = getSharedPreferences("Coll_tab", 0);
        sedt = sp.edit();

        // Assign Variable
        edit_f_name = (EditText)findViewById (R.id.editText3);
        edit_l_name = (EditText)findViewById (R.id.editText4);
        edit_id = (EditText)findViewById (R.id.editText5);
        edit_phone = (EditText)findViewById (R.id.editText6);
        edit_pass = (EditText)findViewById (R.id.editText7);

        // button Assign Variable
        btn_reg_home_screen = (Button) findViewById(R.id.button2);

        // Initialize Validation Style
        awesomeValidation = new AwesomeValidation(ValidationStyle.BASIC);

        // Add Validation for First Name
        awesomeValidation.addValidation(this, R.id.editText3, RegexTemplate.NOT_EMPTY, R.string.invalid_first_name);
        // For Last Name
        awesomeValidation.addValidation(this, R.id.editText4, RegexTemplate.NOT_EMPTY, R.string.invalid_last_name);
        // For Id
        awesomeValidation.addValidation(this, R.id.editText5, "[0-9]{9}", R.string.invalid_id);
        // For Phone
        awesomeValidation.addValidation(this, R.id.editText6, "05[0-9]{8}", R.string.invalid_phone);
        // For Password
        awesomeValidation.addValidation(this, R.id.editText7, ".{4,}", R.string.invalid_password);

        // button register and Intent to HomeActivity
        btn_reg_home_screen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Check Validation
                if(awesomeValidation.validate()){
                    // string
                    String str_f_name = edit_f_name.getText().toString();
                    String str_l_name = edit_l_name.getText().toString();
                    String str_id = edit_id.getText().toString();
                    String str_phone = edit_phone.getText().toString();
                    String str_pass = edit_pass.getText().toString();
                    String[] details = {str_id, str_pass, str_f_name, str_l_name, str_phone};
                    // request
                    API.registerRequest(RegisterActivity.this, details);
                }
                else{
                    Toast.makeText(RegisterActivity.this, "פרטי הרשמה אינם תקינים!", Toast.LENGTH_SHORT).show();
                }

            }
        });

    }

    @Override
    public void onSuccess(String result) {
        ObjectMapper mapper = new ObjectMapper();
        try {
            JSONObject resultJSON = new JSONObject(result);
            JSONObject headers = resultJSON.getJSONObject("headers");
//            System.out.println(headers.get("X-Auth-Token"));
            User user = mapper.readValue(result, User.class);
//            String userJsonString = mapper.writeValueAsString(user);
            String parentJsonString = mapper.writeValueAsString(user._parent);
//            System.out.println(parentJsonString);
            sedt.putString("JWT", headers.get("X-Auth-Token").toString());
            sedt.putString("_parent", parentJsonString);
            sedt.commit();
        } catch (Exception e) {
            e.printStackTrace();
        }
        // On Success
        Toast.makeText(RegisterActivity.this,"ההרשמה הצליחה!", Toast.LENGTH_SHORT).show();
        Intent myIntent_reg_home_screen = new Intent(RegisterActivity.this, HomeActivity.class);
        RegisterActivity.this.startActivity(myIntent_reg_home_screen);
        finish();
    }

    @Override
    public void onError(String err) {
        Toast.makeText(RegisterActivity.this,"ההרשמה נכשלה!", Toast.LENGTH_SHORT).show();
    }
}

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

public class LoginActivity extends AppCompatActivity implements VolleyCallback{

    // Initialize Variable
    EditText edit_id, edit_pass;
    Button btn_login, btn_reg;

    AwesomeValidation awesomeValidation;
    SharedPreferences sp;
    SharedPreferences.Editor sedt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        sp = getSharedPreferences("Coll_tab", 0);
        sedt = sp.edit();
        // init sp
        sedt.putString("_parent", null);
        sedt.commit();

        // Assign Variable
        edit_id = (EditText)findViewById (R.id.editText);
        edit_pass = (EditText)findViewById (R.id.editText2);
        // Save to sp for future login
        edit_id.setText(sp.getString("userId",null));
        edit_pass.setText(sp.getString("userPass",null));
        // button Assign Variable
        btn_login = (Button) findViewById(R.id.button);
        btn_reg = (Button) findViewById(R.id.button1);

        // Initialize Validation Style
        awesomeValidation = new AwesomeValidation(ValidationStyle.BASIC);

        // Add Validation for id
        awesomeValidation.addValidation(this, R.id.editText, "[0-9]{9}", R.string.invalid_id);
        // For Password
        awesomeValidation.addValidation(this, R.id.editText2, ".{4,}", R.string.invalid_password);

        // button login and Intent to HomeActivity
        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent myIntent_login = new Intent(LoginActivity.this, HomeActivity.class);

                // Check Validation
                if(awesomeValidation.validate()){
                    // string
                    String str_id = edit_id.getText().toString();
                    String str_pass = edit_pass.getText().toString();
                    sedt.putString("userId", str_id);
                    sedt.putString("userPass", str_pass);
                    sedt.commit();
                    // request
                    API.loginRequest(LoginActivity.this, str_id, str_pass);
                }
                else{
                    Toast.makeText(LoginActivity.this, "פרטי התחברות אינם תקינים!", Toast.LENGTH_SHORT).show();
                }

            }
        });

        // button register and Intent to RegisterActivity
        btn_reg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent myIntent_reg = new Intent(LoginActivity.this, RegisterActivity.class);
                LoginActivity.this.startActivity(myIntent_reg);

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
        Toast.makeText(LoginActivity.this, "ההתחברות הצליחה!", Toast.LENGTH_SHORT).show();
        Intent myIntent_login = new Intent(LoginActivity.this, HomeActivity.class);
        LoginActivity.this.startActivity(myIntent_login);
        finish();
    }

    @Override
    public void onError(String err) {
        Toast.makeText(LoginActivity.this, "ההתחברות נכשלה!", Toast.LENGTH_SHORT).show();
    }

}

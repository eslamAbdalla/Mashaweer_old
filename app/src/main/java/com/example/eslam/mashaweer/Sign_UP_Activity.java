package com.example.eslam.mashaweer;

import android.annotation.SuppressLint;
import android.app.DownloadManager;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.ColorSpace;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Method;

public class Sign_UP_Activity extends AppCompatActivity {


    EditText First_Name , Last_Name,LogInName,Password,RePassword;
    RadioGroup Radio_G_UserType ;
    RadioButton Radio_B_UserType ;
    Button Sign_Up;
    String SignUp_URL ="http://Mashaweer.somee.com/api/users" ;

    int User_Type = 2 ;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign__up_);


        First_Name = (EditText)findViewById(R.id.SignUpFirstName);
        Last_Name  = (EditText)findViewById(R.id.SignUpLastName);
        LogInName  = (EditText)findViewById(R.id.SignUpLogInName);
        Password   = (EditText)findViewById(R.id.SignUpPassword);
        RePassword = (EditText)findViewById(R.id.SignUpRePassword);

        Sign_Up = (Button)findViewById(R.id.btn_sign_In);


        final JSONObject User_Data = new JSONObject();


        Radio_G_UserType = (RadioGroup) findViewById(R.id.radio_User_Type);

        Radio_G_UserType.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener()
        {
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch(checkedId){
                    case R.id.radio_owner:
                        User_Type = 1;
                        break;
                    case R.id.radio_renter:
                        User_Type = 2;
                        break;

                }
            }
        });




        Sign_Up.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {



                String Entered_FirstName;
                String Entered_LastName;
                String Entered_LogInName;
                String Entered_Password;
                String Entered_RePassword;
                int Entered_UserType;


                Entered_FirstName = First_Name.getText().toString();
                Entered_LastName = Last_Name.getText().toString();
                Entered_LogInName = LogInName.getText().toString();
                Entered_Password = Password.getText().toString();
                Entered_RePassword = RePassword.getText().toString();
                Entered_UserType = User_Type;

                try {
                    User_Data.put("First_Name", Entered_FirstName);
                    User_Data.put("Last_Name", Entered_LastName);
                    User_Data.put("LogIN_Name", Entered_LogInName);
                    User_Data.put("Password", Entered_Password);
                    User_Data.put("UserTypeKey", Entered_UserType);
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                if (!Entered_Password.equals(Entered_RePassword)) {
                    RePassword.setText("");
                    RePassword.setHint("Password Not Match");
                    RePassword.setHintTextColor(Color.parseColor("#FF890222"));
                } else {
                    Sign_Up.setClickable(false);


                    JsonObjectRequest AddNewUserRequest = new JsonObjectRequest(Request.Method.POST, SignUp_URL, User_Data
                            , new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject response) {
                            Toast.makeText(Sign_UP_Activity.this, "Done", Toast.LENGTH_LONG).show();


                            startActivity(new Intent(Sign_UP_Activity.this,Profile.class));
                        }
                    }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {

                        }




                    });

                    MySingleton.getInstance(Sign_UP_Activity.this).addToRequestQueue(AddNewUserRequest);


                }

            }
        });





    }
}

package com.example.eslam.mashaweer;

import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONException;
import org.json.JSONObject;

public class Sign_UP_Activity extends AppCompatActivity {


    EditText First_Name , Last_Name,LogInName,Password,RePassword;
    RadioGroup Radio_G_UserType ;
    RadioButton Radio_B_UserType ;
    Button Sign_Up;
    String SignUp_URL ="http://Mashaweer.somee.com/api/users" ;
    ProgressBar progressBar ;


    int User_Type = 2 ;
    public static int Entered_UserType ;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign__up_);


        progressBar = (ProgressBar)findViewById(R.id.login_progress);

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

                progressBar.setVisibility(View.VISIBLE);


              //  String Entered_FirstName;
                //String Entered_LastName;
              //  final String Entered_LogInName;
              //  final String Entered_Password;
              //  final String Entered_RePassword;
             //   Entered_UserType;


                Profile_Owner.Entered_FirstName = First_Name.getText().toString();
                Profile_Owner.Entered_LastName = Last_Name.getText().toString();
                Profile_Owner.Entered_LogInName = LogInName.getText().toString();
                Profile_Owner.Entered_Password = Password.getText().toString();
                Profile_Owner.Entered_RePassword = RePassword.getText().toString();
                Entered_UserType = User_Type;


                if (Profile_Owner.Entered_LogInName.contains(" ")){
                    LogInName.setText("");
                    LogInName.setHint("User Name Contain Spaces");
                    LogInName.setHintTextColor(Color.parseColor("#FF890222"));
                    progressBar.setVisibility(View.GONE);
                }
                else {

                try {
                    User_Data.put("First_Name", Profile_Owner.Entered_FirstName);
                    User_Data.put("Last_Name", Profile_Owner.Entered_LastName);
                    User_Data.put("LogIN_Name", Profile_Owner.Entered_LogInName);
                    User_Data.put("Password", Profile_Owner.Entered_Password);
                    User_Data.put("UserTypeKey", Entered_UserType);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
    String CheckUserName_URL ="http://Mashaweer.somee.com/api/CheckLogInName/?userName="+ Profile_Owner.Entered_LogInName+"" ;



                JsonObjectRequest CheckUserName = new JsonObjectRequest(Request.Method.GET, CheckUserName_URL, null
                        , new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {

                        int State = 0;
                        try {
                            State = response.getInt("state");
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }


                        if (State == 2) {
                            LogInName.setText("");
                            LogInName.setHint("User Already Exist");
                            LogInName.setHintTextColor(Color.parseColor("#FF890222"));
                            progressBar.setVisibility(View.GONE);
                        } else if (State == 1) {


                            if (!Profile_Owner.Entered_Password.equals(Profile_Owner.Entered_RePassword)) {
                                RePassword.setText("");
                                RePassword.setHint("Password Not Match");
                                RePassword.setHintTextColor(Color.parseColor("#FF890222"));
                                progressBar.setVisibility(View.GONE);
                            } else {
                                Sign_Up.setClickable(false);


                                JsonObjectRequest AddNewUserRequest = new JsonObjectRequest(Request.Method.POST, SignUp_URL, User_Data
                                        , new Response.Listener<JSONObject>() {
                                    @Override
                                    public void onResponse(JSONObject response) {
                                        Toast.makeText(Sign_UP_Activity.this, "Sign Up Successfuly", Toast.LENGTH_LONG).show();
                                        progressBar.setVisibility(View.GONE);

                                        if (User_Type == 1) {
                                            startActivity(new Intent(Sign_UP_Activity.this, Profile_Owner.class));
                                        }
                                        else if (User_Type == 2) {
                                            startActivity(new Intent(Sign_UP_Activity.this, Profile_Renter.class));
                                        }
                                    }
                                }, new Response.ErrorListener() {
                                    @Override
                                    public void onErrorResponse(VolleyError error) {

                                        error.printStackTrace();
                                        error.getMessage();


                                    }


                                });


                                MySingleton.getInstance(Sign_UP_Activity.this).addToRequestQueue(AddNewUserRequest);

                            }
                        }


                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        progressBar.setVisibility(View.GONE);
                        error.printStackTrace();
                        error.getMessage();
                        Toast.makeText(Sign_UP_Activity.this, "Connection Error", Toast.LENGTH_LONG).show();


                    }


                 });
                MySingleton.getInstance(Sign_UP_Activity.this).addToRequestQueue(CheckUserName);



            }}
        });


    }
}

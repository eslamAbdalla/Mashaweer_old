package com.example.eslam.mashaweer;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONException;
import org.json.JSONObject;

public class LogIN extends AppCompatActivity {

    EditText ET_UserName,ET_Password ;
    Button Bt_LogIn,Bt_Sign_Up ;

//      String Entered_User_Name;
//      String Entered_Password;

    public static String firstName ,lastName,UserKey,User_Type;

    public  static int User_Type_Key;

//    String Url_Login ;

    int LogInStatus ;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_in);


        ET_UserName = (EditText)findViewById(R.id.logInName);
        ET_Password = (EditText)findViewById(R.id.logInPassword);

        Bt_LogIn = (Button)findViewById(R.id.btn_Log_In);
        Bt_Sign_Up = (Button)findViewById(R.id.btn_Sign_Up);

        Bt_Sign_Up.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LogIN.this,Sign_UP_Activity.class));
            }
        });


        Bt_LogIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                String     Entered_User_Name = ET_UserName.getText().toString();
                String Entered_Password = ET_Password.getText().toString();

                String      Url_Login = "http://Mashaweer.somee.com/api/Login/"+Entered_User_Name+"?password="+Entered_Password+"";

                if (Entered_User_Name.isEmpty()&& Entered_Password.isEmpty()){
                    Toast.makeText(LogIN.this,"Please Enter User Name & Password",Toast.LENGTH_LONG).show();
                    LogInStatus = 0;


                }



                JsonObjectRequest LogInDataRequest = new JsonObjectRequest(Request.Method.GET, Url_Login
                        , null, new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {

                        try {
                            LogInStatus = response.getInt("state");

                            JSONObject result = response.getJSONObject("result");
                            UserKey = result.getString("userId");
                            firstName = result.getString("firstName");
                            lastName = result.getString("lastName");
                            User_Type = result.getString("userType");
                            User_Type_Key = result.getInt("userTypeKey");



                        } catch (JSONException e) {
                            e.printStackTrace();
                        }


                        // 1 = not Found ================== 2 = done

                        if (LogInStatus == 2 ) {
                            Toast.makeText(LogIN.this, "Welcom" + " " + firstName + " " + lastName, Toast.LENGTH_LONG).show();

                            if (User_Type_Key == 1) {
                                startActivity(new Intent(LogIN.this, Profile_Owner.class));
                                Profile_Owner.Entered_FirstName = firstName;
                                Profile_Owner.Entered_LastName = lastName;
                            }else if (User_Type_Key == 2) {
                                startActivity(new Intent(LogIN.this, Profile_Renter.class));
                                Profile_Renter.Entered_FirstName = firstName;
                                Profile_Renter.Entered_LastName = lastName;
                            }
                            LogInStatus = 0;
                        } else if (LogInStatus == 1) {
                            Toast.makeText(LogIN.this, "Please Enter Valid User Name & Password", Toast.LENGTH_LONG).show();
                            LogInStatus = 0;
                        }


                    }

                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                });

                LogInDataRequest.setRetryPolicy(new DefaultRetryPolicy(
                        500,
                        DefaultRetryPolicy.DEFAULT_TIMEOUT_MS,
                        DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));


                MySingleton.getInstance(LogIN.this).addToRequestQueue(LogInDataRequest);





            }
        });

    }
}

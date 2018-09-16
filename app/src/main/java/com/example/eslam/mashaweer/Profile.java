package com.example.eslam.mashaweer;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class Profile extends AppCompatActivity {


    TextView DisplayName ;
   public static String
           Entered_FirstName
           ,Entered_LastName
           ,Entered_LogInName
           ,Entered_Password
           ,Entered_RePassword;
   public static int Entered_UserType ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        DisplayName = (TextView)findViewById(R.id.displayname);
        DisplayName.setText(Entered_FirstName+" "+Entered_LastName);



    }
}

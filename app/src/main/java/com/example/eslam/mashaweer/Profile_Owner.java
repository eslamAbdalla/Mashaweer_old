package com.example.eslam.mashaweer;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class Profile_Owner extends AppCompatActivity {


    TextView DisplayName,UserType ;
   public static String
           Entered_FirstName
           ,Entered_LastName
           ,Entered_LogInName
           ,Entered_Password
           ,Entered_RePassword;
   // public static int Entered_UserType ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_owner);

        DisplayName = (TextView)findViewById(R.id.displayname);
        UserType = (TextView)findViewById(R.id.user_Type_O);

        DisplayName.setText(Entered_FirstName+" "+Entered_LastName);
        UserType.setText(LogIN.User_Type);



    }
}

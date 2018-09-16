package com.example.eslam.mashaweer;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class Profile_Renter extends AppCompatActivity {


    TextView DisplayName,UserType ;
    public static String
            Entered_FirstName
            ,Entered_LastName
            ,Entered_LogInName
            ,Entered_Password
            ,Entered_RePassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile__renter);

        DisplayName = (TextView)findViewById(R.id.displayname);
        UserType = (TextView)findViewById(R.id.user_Type_R);

        DisplayName.setText(Entered_FirstName+" "+Entered_LastName);
        UserType.setText(LogIN.User_Type);


    }
}

package com.example.theplayschool;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;

import com.facebook.AccessToken;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity {
    AccessToken accessToken;
    private boolean isLoggedIn;
    FirebaseUser firebaseUser;
    SharedPreferences sharedPreferences;
    int introstatus;

    private static int SPLASH_TIME_OUT = 2000;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTitle("Welcome Aboard!");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //get introstatus from sharedpreferences
        sharedPreferences=getSharedPreferences("MyPref",MODE_PRIVATE);
        introstatus=sharedPreferences.getInt("introstatus",0);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                //check for any logged in user and start the MainActivity class directly
                if(firebaseUser!=null)//||isLoggedIn)
                {
                    startActivity(new Intent(MainActivity.this, Student_Dashboard.class));
                }
                //else start the SignInActivity class for user to login
                else
                {

                    //if the intro has been displayed once,go direct to signinactivity
                    if (introstatus != 0 && introstatus == 3) {
                        startActivity(new Intent(MainActivity.this, LoginActivity.class));
                    } else {
                        //go to introactivity
                        startActivity(new Intent(MainActivity.this, IntroActivity.class));
                    }


                }
            }
        },SPLASH_TIME_OUT);//open the new Activity after a delay of 3s
    }
    @Override
    protected void onStart() {
        super.onStart();

        //check for firebase email login user
        firebaseUser= FirebaseAuth.getInstance().getCurrentUser();


        //accessToken = AccessToken.getCurrentAccessToken();
        //isLoggedIn = accessToken != null && !accessToken.isExpired();
    }


}


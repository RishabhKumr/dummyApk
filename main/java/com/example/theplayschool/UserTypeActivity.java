package com.example.theplayschool;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import java.io.FileInputStream;

public class UserTypeActivity extends AppCompatActivity {
    private ImageButton student_btn;
    private TextView user_name;
    private String file = "UserName";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_type);


        //Username read
        try {
            FileInputStream fin = openFileInput(file);
            int c;
            String temp = " ";
            while((c = fin.read())!= -1) {
                temp = temp + Character.toString((char) c);
            }
            user_name.setText(temp);
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
    }
}

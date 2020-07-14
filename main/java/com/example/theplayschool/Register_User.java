package com.example.theplayschool;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class Register_User extends AppCompatActivity {
    EditText name,email,password,phone;
    Button registerBtn;
    TextView loginBtn;
    FirebaseAuth fAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register__user);
        setTitle("Register Here!");
        name = findViewById(R.id.name);
        email = findViewById(R.id.email);
        password = findViewById(R.id.password);
        phone = findViewById(R.id.phone);
        registerBtn = findViewById(R.id.registerbtn);
        loginBtn = findViewById(R.id.login);

        fAuth = FirebaseAuth.getInstance();

        registerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String Email = email.getText().toString().trim();
                String Password = password.getText().toString().trim();
                String Name = name.getText().toString();
                String Phone = phone.getText().toString().trim();

                if(TextUtils.isEmpty(Email))
                {
                    email.setError("Please enter your Email!");
                    return;
                }
                if (TextUtils.isEmpty(Name))
                {
                    name.setError("Please enter your Name!");
                    return;
                }
                if(TextUtils.isEmpty(Phone))
                {
                    phone.setError("Please enter your Phone!");
                    return;
                }
                if(TextUtils.isEmpty(Password))
                {
                    password.setError("Please enter your Password!");
                    return;
                }

                fAuth.createUserWithEmailAndPassword(Email,Password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful())
                        {
                            Toast.makeText(Register_User.this, "Done, Thanks!", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(getApplicationContext(),LoginActivity.class));
                            name.getText().clear();
                            phone.getText().clear();
                            email.getText().clear();
                            password.getText().clear();
                        }
                        else
                        {
                            Toast.makeText(Register_User.this, "Error!" + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }

        });
        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(),LoginActivity.class));
            }
        });
    }
}

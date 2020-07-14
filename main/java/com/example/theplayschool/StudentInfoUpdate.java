package com.example.theplayschool;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class StudentInfoUpdate extends AppCompatActivity {

    EditText student_name;
    EditText student_school;
    Button submit_btn;
    ProgressBar loader;
    Spinner grade_list;
    Spinner board_list;
    DatabaseReference databaseStudent;
    private static int SPLASH_TIME_OUT = 2000;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_info_update);
        setTitle("Info");
        databaseStudent = FirebaseDatabase.getInstance().getReference("Student");
        student_name = (EditText) findViewById(R.id.stuname);
        student_school = (EditText) findViewById(R.id.stuschool);
        submit_btn =  (Button) findViewById(R.id.submit);
        grade_list = (Spinner) findViewById(R.id.grade);
        board_list = (Spinner) findViewById(R.id.board);
        loader = (ProgressBar) findViewById(R.id.progressBar);
        loader.setVisibility(View.GONE);
        submit_btn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view)
            {
                loader.setVisibility(View.VISIBLE);
                addStudent();

                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        Intent LoginIntent = new Intent(StudentInfoUpdate.this,Student_Dashboard.class);
                        startActivity(LoginIntent);
                        finish();;
                    }
                },SPLASH_TIME_OUT);

            }
        });
    }
    private void addStudent()
    {
        String name = student_name.getText().toString().trim();
        String school = student_school.getText().toString().trim();
        String grade = grade_list.getSelectedItem().toString();
        String board = board_list.getSelectedItem().toString();
        if(!TextUtils.isEmpty(name))
        {
            String id = databaseStudent.push().getKey();
            Student student = new Student(id,name,school,grade,board);
            databaseStudent.child(id).setValue(student);
            Toast.makeText(this,"Done, Thanks!",Toast.LENGTH_LONG).show();
        }
        else
        {
            Toast.makeText(this,"Failed to register!",Toast.LENGTH_SHORT).show();
        }
    }

}

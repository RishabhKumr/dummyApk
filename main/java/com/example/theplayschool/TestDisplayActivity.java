package com.example.theplayschool;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.List;
import java.util.Optional;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.Converter.*;
import retrofit2.converter.gson.GsonConverterFactory;

public class TestDisplayActivity extends AppCompatActivity {

    private TextView QuestionNo;
    private TextView Question;
    private RadioButton Option1;
    private RadioButton Option2;
    private RadioButton Option3;
    private RadioButton Option4;
    private Button privious;
    private Button next;
    int count = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_display);
        QuestionNo = (TextView) findViewById(R.id.QuestionNumber);
        Question = (TextView) findViewById(R.id.Question);
        Option1 = (RadioButton) findViewById(R.id.option1);
        Option2 = (RadioButton) findViewById(R.id.option2);
        Option3 = (RadioButton) findViewById(R.id.option3);
        Option4 = (RadioButton) findViewById(R.id.option4);
        privious = (Button) findViewById(R.id.privious);
        next  =     (Button)     findViewById(R.id.next);
        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                count++;
                getData(count);
            }
        });
    }
    public void getData(int count)
    {
        Retrofit retrofit = new Retrofit.Builder().baseUrl("https://jsonplaceholder.typicode.com/").addConverterFactory(GsonConverterFactory.create()).build();
        JsonPlaceHolderApi jsonPlaceHolderApi = retrofit.create(JsonPlaceHolderApi.class);
        Call<List<Post>> call = jsonPlaceHolderApi.getPosts();
        call.enqueue(new Callback<List<Post>>() {
            @Override
            public void onResponse(Call<List<Post>> call, Response<List<Post>> response) {
                if (!response.isSuccessful()) {
                    QuestionNo.setText("Code: " + response.code());
                    return;
                }
                List<Post> posts = response.body();
                for (Post post: posts) {
                        String Question_txt = "";
                        String Option1_txt = "";
                        String Option2_txt = "";
                        String Option3_txt = "";
                        Question_txt += "ID: " + post.getId() + "\n";
                        Option1_txt += "User ID: " + post.getUserId() + "\n";
                        Option2_txt += "Title: " + post.getTitle() + "\n";
                        Option3_txt += "Text: " + post.getText() + "\n\n";
                        QuestionNo.append(Question_txt);
                        Option1.append(Option1_txt);
                        Option2.append(Option2_txt);
                        Option3.append(Option3_txt);
                }
            }

            @Override
            public void onFailure(Call<List<Post>> call, Throwable t) {
                Question.setText(t.getMessage());
            }
        });
    }
}
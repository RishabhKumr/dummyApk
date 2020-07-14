package com.example.theplayschool;

import com.google.gson.annotations.SerializedName;

public class Test {
    private int QuestionNumber;
    private String Question;
    private String option1;
    private String option2;
    private String option3;
    private String option4;
    private String Answer;
    @SerializedName("body")
    private String text;
    public int getQuestionNumber() {
        return QuestionNumber;
    }

    public String getQuestion() {
        return Question;
    }

    public String getOption1() {
        return option1;
    }

    public String getOption2() {
        return option2;
    }

    public String getOption3() {
        return option3;
    }

    public String getOption4() {
        return option4;
    }

    public String getAnswer() {
        return Answer;
    }

    public String getText() {
        return text;
    }


}

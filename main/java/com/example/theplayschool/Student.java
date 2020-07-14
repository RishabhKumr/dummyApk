package com.example.theplayschool;

public class Student {
    String ID;
    String name;
    String school;
    String grade;
    String board;
    public Student()
    {

    }
    public Student(String ID,String name,String school,String grade,String board)
    {
        this.ID = ID;
        this.name = name;
        this.school = school;
        this.grade = grade;
        this.board = board;
    }

    public String getID() {
        return ID;
    }

    public String getName() {
        return name;
    }

    public String getSchool() {
        return school;
    }

    public String getGrade() {
        return grade;
    }

    public String getBoard() {
        return board;
    }
}

package com.example.shybal.liverpoolquiz.Model;

import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.util.Log;
import android.widget.Toast;

import com.example.shybal.liverpoolquiz.Database.QuestionsDatabaseHelper;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;

public class Questions {

    public Questions() {
    }

    public ArrayList<String> getChoice1() {
        return choice1;
    }

    public ArrayList<String> getChoice2() {
        return choice2;
    }

    public ArrayList<String> getChoice3() {
        return choice3;
    }

    public ArrayList<String> getChoice4() {
        return choice4;
    }

    public ArrayList<String> getAnswers() {
        return answers;
    }

    public ArrayList<String> getQuestions() {

        return questions;
    }

    private Cursor c = null;
    private ArrayList<String> choice1 = null;
    private ArrayList<String> choice2 = null;
    private ArrayList<String> choice3 = null;
    private ArrayList<String> choice4 = null;
    private ArrayList<String> questions = null;
    private ArrayList<String> answers = null;


    public void setDataToQuestions(Context context, String player) {
        questions = new ArrayList<>();
        answers = new ArrayList<>();
        choice1 = new ArrayList<>();
        choice2 = new ArrayList<>();
        choice3 = new ArrayList<>();
        choice4 = new ArrayList<>();
        int nextnum = (int) Math.random() * (14-0) + 0;

        QuestionsDatabaseHelper myDbHelper = new QuestionsDatabaseHelper(context);
        try {
            myDbHelper.createDataBase();
        } catch (IOException ioe) {
            throw new Error("Unable to create database");
        }
        try {
            myDbHelper.openDataBase();
        } catch (SQLException sqle) {
            throw sqle;
        }

        //Toast.makeText(context, "Successfully Imported", Toast.LENGTH_SHORT).show();

        c = myDbHelper.query(player, null, null, null, null, null, null);
        if (c.moveToFirst()) {
            do {

                questions.add(c.getString(1));
                answers.add(c.getString(2));
                choice1.add(c.getString(3));
                choice2.add(c.getString(4));
                choice3.add(c.getString(5));
                choice4.add(c.getString(6));
            } while (c.moveToNext());
        }
                c.close();
        myDbHelper.close();
        //Toast.makeText(context, "Successfully Imported" + c.getCount(), Toast.LENGTH_SHORT).show();


    }


}

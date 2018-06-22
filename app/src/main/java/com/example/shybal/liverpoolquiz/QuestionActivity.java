package com.example.shybal.liverpoolquiz;

import android.animation.ArgbEvaluator;
import android.animation.ObjectAnimator;
import android.content.Intent;
import android.graphics.Color;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.shybal.liverpoolquiz.Model.Questions;
import com.example.shybal.liverpoolquiz.Model.Scores;

import java.util.Collections;
import java.util.Random;

public class QuestionActivity extends AppCompatActivity {
    TextView timerText, livesText, scoreTextView, questionText;
    String answer, playerName;
    Button choice1,choice2,choice3,choice4;
    int score, livesRemaining, questionCount;
    ImageView playerImage;
    Handler handler;
    ObjectAnimator anim;
    Questions questions;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_question);

        //makes the action bar transparent for android version 21+
        removeActionBar();

        //fetch the player selected by the user
        playerName = getIntent().getStringExtra("player");

        //initializes all layout resources
        initialiseResources(playerName);

        //fetches data from the database to the model
        loadDatabase(playerName);

        score = 0;




        //Button 1 Click event
        choice1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                disableButtons();
                if (choice1.getText().equals(answer)) {
                    manageBlinkEffect(true,choice1);

                }
                else {
                    manageBlinkEffect(false,choice1);
                }

            }
        });

        //Button 2 Click event
        choice2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                disableButtons();

                if (choice2.getText().equals(answer)) {
                    manageBlinkEffect(true,choice2);

                }
                else {
                    manageBlinkEffect(false,choice2);



                }

            }
        });

        //Button 3 Click event
        choice3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                disableButtons();

                if (choice3.getText().equals(answer)) {
                    manageBlinkEffect(true,choice3);

                }
                else {
                    manageBlinkEffect(false,choice3);



                }

            }
        });

        //Button 4 Click event
        choice4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                disableButtons();

                if (choice4.getText().equals(answer)) {
                    manageBlinkEffect(true,choice4);

                }
                else {
                    manageBlinkEffect(false,choice4);

                }


            }
        });
    }


    private void initialiseResources(String playerName) {
        handler = new Handler();
        playerImage = (ImageView) findViewById(R.id.player_image);
        if (playerName.equals("messi")) {
            playerImage.setImageDrawable(getDrawable(R.drawable.messicartoon));
        }
        else if (playerName.equals("salah")) {
            playerImage.setImageDrawable(getDrawable(R.drawable.salahcartoon));
        }
        else {
            playerImage.setImageDrawable(getDrawable(R.drawable.roncartoon));
        }

        choice1 = (Button) findViewById(R.id.choice1);
        choice2 = (Button) findViewById(R.id.choice2);
        choice3 = (Button) findViewById(R.id.choice3);
        choice4 = (Button) findViewById(R.id.choice4);
        questionText = (TextView) findViewById(R.id.question_text);
        timerText = (TextView) findViewById(R.id.timer_text_view);
        scoreTextView = (TextView) findViewById(R.id.score_text_view);
        livesText = (TextView) findViewById(R.id.lives_count_text_view);
        questionCount = 0;
        livesRemaining = 3;
    }
    private void loadDatabase(String player) {
        questions = new Questions();
        questions.setDataToQuestions(QuestionActivity.this,player);
        long seed = System.nanoTime();
        Collections.shuffle(questions.getQuestions(), new Random(seed));
        Collections.shuffle(questions.getAnswers(), new Random(seed));
        Collections.shuffle(questions.getChoice1(),new Random(seed));
        Collections.shuffle(questions.getChoice2(),new Random(seed));
        Collections.shuffle(questions.getChoice3(),new Random(seed));
        Collections.shuffle(questions.getChoice4(),new Random(seed));
        updateQuestion(questionCount);

    }
    private void updateQuestion(int num) {
        if (num > 14) {
            gameOver();
        }
        else {

            //set the question and answers
            questionText.setText(questions.getQuestions().get(num));
            questionText.setMovementMethod(new ScrollingMovementMethod());
            choice1.setText(questions.getChoice1().get(num));
            choice2.setText(questions.getChoice2().get(num));
            choice3.setText(questions.getChoice3().get(num));
            choice4.setText(questions.getChoice4().get(num));
            answer = questions.getAnswers().get(num);

        }

    }

    private void manageBlinkEffect(boolean isWriteAnswer,Button button) {
        if (isWriteAnswer) {
            anim = ObjectAnimator.ofInt(button, "backgroundColor", R.color.buttonColor, Color.GREEN,
                    R.color.buttonColor);
            anim.setDuration(250);
            anim.setEvaluator(new ArgbEvaluator());
            anim.setRepeatCount(3);
            anim.start();
            final Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    // Do something after 5s = 5000ms
                    questionCount++;
                    updateScore();
                    updateQuestion(questionCount);
                    enableButtons();
                }
            }, 1000);
        }
        else {
            anim = ObjectAnimator.ofInt(button, "backgroundColor", R.color.buttonColor, Color.RED,
                    R.color.buttonColor);
            anim.setDuration(250);
            anim.setEvaluator(new ArgbEvaluator());
            anim.setRepeatCount(2);
            anim.start();
            final Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    // Do something after 5s = 5000ms
                    questionCount++;
                    updateLives();
                    updateQuestion(questionCount);
                    enableButtons();
                }
            }, 1000);
        }

    }
    private void disableButtons() {
        choice1.setEnabled(false);
        choice2.setEnabled(false);
        choice3.setEnabled(false);
        choice4.setEnabled(false);
    }
    private void enableButtons() {
        choice1.setEnabled(true);
        choice2.setEnabled(true);
        choice3.setEnabled(true);
        choice4.setEnabled(true);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        long seed = System.nanoTime();
        Collections.shuffle(questions.getQuestions(), new Random(seed));
        Collections.shuffle(questions.getAnswers(), new Random(seed));
        Collections.shuffle(questions.getChoice1(),new Random(seed));
        Collections.shuffle(questions.getChoice2(),new Random(seed));
        Collections.shuffle(questions.getChoice3(),new Random(seed));
        Collections.shuffle(questions.getChoice4(),new Random(seed));
    }


    private void updateScore() {
        Scores.scores++;
        scoreTextView.setText(Integer.toString(score));
    }
    private void updateLives() {
        if (livesRemaining == 0) {
            gameOver();
        } else {
            livesRemaining--;
            livesText.setText(Integer.toString(livesRemaining));
        }


    }

    private void gameOver() {
        Intent openResults = new Intent(QuestionActivity.this,ScoreActivity.class);
        startActivity(openResults);
        QuestionActivity.this.finish();
    }

    private void removeActionBar() {
        Window w = getWindow();
        w.setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
        getWindow().getDecorView().setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                        | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
    }


}

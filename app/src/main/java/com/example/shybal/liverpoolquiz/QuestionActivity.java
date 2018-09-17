package com.example.shybal.liverpoolquiz;

import android.animation.ArgbEvaluator;
import android.animation.ObjectAnimator;
import android.content.Intent;
import android.graphics.Color;
import android.os.CountDownTimer;
import android.os.Handler;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.shybal.liverpoolquiz.Model.Question;
import com.example.shybal.liverpoolquiz.Model.QuestionsList;
import com.example.shybal.liverpoolquiz.Model.UserScore;
import com.example.shybal.liverpoolquiz.others.URLClass;
import com.jaeger.library.StatusBarUtil;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Collections;
import java.util.Random;

public class QuestionActivity extends MainBaseActivity {
    TextView timerText, livesText, scoreTextView, questionText;
    String answer, playerName;
    Button choice1btn,choice2btn,choice3btn,choice4btn;
    int livesRemaining, usersQuestionNo;
    RequestQueue requestQueue;
    ImageView playerImage;
    Handler handler;
    private static String TAG = "QuestionActivity";
    ObjectAnimator anim;
    Timer myTimer;
    QuestionsList questions;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        StatusBarUtil.setTransparent(QuestionActivity.this);

        setContentView(R.layout.activity_question);

        //makes the action bar transparent for android version 21+
        //removeActionBar();

        //fetch the player selected by the user
        playerName = getIntent().getStringExtra("player");

        //initializes all layout resources
        initialiseResources(playerName);

        //fetches data from the database to the model
        //loadDatabase(playerName);
        UserScore.currentScore = 0;
        requestQueue = Volley.newRequestQueue(QuestionActivity.this);
        getQuestions();


        //Button 1 Click event
        choice1btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                disableButtons();
                if (choice1btn.getText().equals(answer)) {
                    manageBlinkEffect(true,choice1btn);

                }
                else {
                    manageBlinkEffect(false,choice1btn);
                }

            }
        });

        //Button 2 Click event
        choice2btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                disableButtons();

                if (choice2btn.getText().equals(answer)) {
                    manageBlinkEffect(true,choice2btn);

                }
                else {
                    manageBlinkEffect(false,choice2btn);



                }

            }
        });

        //Button 3 Click event
        choice3btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                disableButtons();

                if (choice3btn.getText().equals(answer)) {
                    manageBlinkEffect(true,choice3btn);

                }
                else {
                    manageBlinkEffect(false,choice3btn);



                }

            }
        });

        //Button 4 Click event
        choice4btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                disableButtons();

                if (choice4btn.getText().equals(answer)) {
                    manageBlinkEffect(true,choice4btn);

                }
                else {
                    manageBlinkEffect(false,choice4btn);

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

        choice1btn = (Button) findViewById(R.id.choice1);
        choice2btn = (Button) findViewById(R.id.choice2);
        choice3btn = (Button) findViewById(R.id.choice3);
        choice4btn = (Button) findViewById(R.id.choice4);
        questionText = (TextView) findViewById(R.id.question_text);
        timerText = (TextView) findViewById(R.id.timer_text_view);
        scoreTextView = (TextView) findViewById(R.id.score_text_view);
        livesText = (TextView) findViewById(R.id.lives_count_text_view);
        usersQuestionNo = 0;
        livesRemaining = 3;

        myTimer = new Timer(30000,1000);
    }
/*    private void loadDatabase(String player) {
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

    }*/
    private void updateQuestion(int num) {
        if (num < usersQuestionNo) {
            gameOver();
            myTimer.cancel();
        }
        else {

            //set the question and answers
            questionText.setText(questions.getQuestion(usersQuestionNo).getQuestion());
            questionText.setMovementMethod(new ScrollingMovementMethod());
            choice1btn.setText(questions.getQuestion(usersQuestionNo).getChoice1());
            choice2btn.setText(questions.getQuestion(usersQuestionNo).getChoice2());
            choice3btn.setText(questions.getQuestion(usersQuestionNo).getChoice3());
            choice4btn.setText(questions.getQuestion(usersQuestionNo).getChoice4());
            answer = questions.getQuestion(usersQuestionNo).getAnswer();
            myTimer.start();
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
                    usersQuestionNo++;
                    updateScore();
                    updateQuestion(usersQuestionNo);
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
                    usersQuestionNo++;
                    updateLives();
                    updateQuestion(usersQuestionNo);
                    enableButtons();
                }
            }, 1000);
        }

    }
    private void disableButtons() {
        choice1btn.setEnabled(false);
        choice2btn.setEnabled(false);
        choice3btn.setEnabled(false);
        choice4btn.setEnabled(false);
    }
    private void enableButtons() {
        choice1btn.setEnabled(true);
        choice2btn.setEnabled(true);
        choice3btn.setEnabled(true);
        choice4btn.setEnabled(true);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        long seed = System.nanoTime();
        Collections.shuffle(questions.getQuestions(), new Random(seed));
    }


    private void updateScore() {
        UserScore.currentScore++;
        scoreTextView.setText(Integer.toString(UserScore.currentScore));
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


    public void getQuestions() {
        JSONObject jo = new JSONObject();
        try{
            //set parameters
            jo.put("quiz_name","football_quiz");
            jo.put("category",playerName);
            Log.d(TAG,jo.toString());
            BaseActivity.startSpinwheel(false,false);
            JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, URLClass.GET_QUESTIONS, jo,
                    new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject response) {
                            BaseActivity.stopSpinWheel();
                            Log.d(TAG, response.toString());
                            boolean isSucess;
                            try {
                                Log.d("Sucess","response recieved sucessfully");
                                isSucess = response.getBoolean("success");
                                if (isSucess) {
                                    Toast.makeText(BaseActivity,"" + "isSucess : True",Toast.LENGTH_LONG).show();
                                    JSONArray questionsListJSON = response.getJSONArray("questions_list");
                                    fillQuestions(questionsListJSON);
                                }else {
                                    Toast.makeText(BaseActivity,"" + "isSucess : False",Toast.LENGTH_LONG).show();
                                }
                            }
                            catch (JSONException e) {
                                e.printStackTrace();
                            }

                        }
                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            //handle error
                            BaseActivity.stopSpinWheel();
                            Log.d(TAG, error.toString());
                        }
                    }
            );
            jsonObjectRequest.setRetryPolicy(new DefaultRetryPolicy(
                    DefaultRetryPolicy.DEFAULT_TIMEOUT_MS * 20,
                    2,
                    2));
            QuestionActivity.this.requestQueue.add(jsonObjectRequest);
        }
        catch (JSONException e){
            e.printStackTrace();
        }

    }

    private void fillQuestions(JSONArray questionsListJSON) {
         questions = new QuestionsList();
        questions.setQuestionCount(questionsListJSON.length());

        for (int i = 0; i < questions.getQuestionCount(); i++) {
            JSONObject jo = new JSONObject();
            try {
                jo = questionsListJSON.getJSONObject(i);
                Question question = new Question();
                question.setQuestion(jo.getString("question"));
                question.setQuestionID(jo.getInt("_id"));
                question.setChoice1(jo.getString("choice1"));
                question.setChoice2(jo.getString("choice2"));
                question.setChoice3(jo.getString("choice3"));
                question.setChoice4(jo.getString("choice4"));
                question.setAnswer(jo.getString("answer"));
                questions.addQuestion(question);
            }
            catch (JSONException e) {
                e.printStackTrace();
            }
        }

        long seed = System.nanoTime();
        Collections.shuffle(questions.getQuestions(), new Random(seed));
        updateQuestion(questions.getQuestionCount());

    }

    public class Timer extends CountDownTimer {
        public Timer(long millisInFuture, long countDownInterval) {
            super(millisInFuture, countDownInterval);
        }

        @Override
        public void onTick(long l) {
            timerText.setText((l / 1000) + "");
        }

        @Override
        public void onFinish() {
            gameOver();
        }

    }
}

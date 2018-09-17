package com.example.shybal.liverpoolquiz;

import android.animation.ValueAnimator;
import android.content.Intent;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;

import com.example.shybal.liverpoolquiz.Model.UserScore;
import com.jaeger.library.StatusBarUtil;


public class ScoreActivity extends AppCompatActivity {
    TextView scoreTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        StatusBarUtil.setTransparent(ScoreActivity.this);
        setContentView(R.layout.activity_score);
        scoreTextView = (TextView) findViewById(R.id.score_number_text);
        scoreTextView.setText(Integer.toString(UserScore.currentScore));
        RunAnimation();
    }

    public void displayMenu(View view) {
        startActivity(new Intent(ScoreActivity.this,HomeActivity.class));
    }


    private void RunAnimation()
    {
        Animation a = AnimationUtils.loadAnimation(this, R.anim.score_animation);
        a.reset();
        scoreTextView.clearAnimation();
        scoreTextView.startAnimation(a);

    }
}

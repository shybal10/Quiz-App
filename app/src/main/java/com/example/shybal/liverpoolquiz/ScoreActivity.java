package com.example.shybal.liverpoolquiz;

import android.animation.ValueAnimator;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;

import com.example.shybal.liverpoolquiz.Model.Scores;

public class ScoreActivity extends AppCompatActivity {
    TextView scoreTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            Window w = getWindow();
            w.setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
        }
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_score);
        getWindow().getDecorView().setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                        | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
        scoreTextView = (TextView) findViewById(R.id.score_number_text);
        scoreTextView.setText(Integer.toString(Scores.scores));
        //Animation animation = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.score_animation);
        //scoreTextView.startAnimation(animation);
        //setScoreAnimator();
    }

    public void displayMenu(View view) {
    }
    private void setScoreAnimator() {
        scoreTextView.setText(Integer.toString(Scores.scores));
        final ValueAnimator animator = ValueAnimator.ofFloat(42,12);
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                float animatedValue = (float) animation.getAnimatedValue();
                scoreTextView.setTextSize(animatedValue);
            }
        });
    }
}

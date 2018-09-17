package com.example.shybal.liverpoolquiz;

import android.content.DialogInterface;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Build;
import android.provider.Settings;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.shybal.liverpoolquiz.musicService.BackgroundMusic;
import com.jaeger.library.StatusBarUtil;

public class HomeActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    Spinner playerNamesSpinner;
    String playerName;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        StatusBarUtil.setTransparent(HomeActivity.this);
        setContentView(R.layout.activity_home);
       //set up the spinners
        playerNamesSpinner = (Spinner) findViewById(R.id.select_player_spinner);
        playerNamesSpinner.setOnItemSelectedListener(this);

        // Create an ArrayAdapter using the string array player_names and a default spinner layout
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,R.array.Select_Player,R.layout.spinner_item);

        // Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(R.layout.other_list_items);
        // Apply the adapter to the spinner
        playerNamesSpinner.setAdapter(adapter);
    }

    public void displayQuestions(View view) {
        if (playerName.equals("choose your hero..")) {
            Toast.makeText(this, "select a player!", Toast.LENGTH_SHORT).show();
        } else {
            Intent openQuestionsPage = new Intent(HomeActivity.this,QuestionActivity.class);
            openQuestionsPage.putExtra("player",playerName);
            startActivity(openQuestionsPage);
        }

    }


    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        playerName = (String) parent.getItemAtPosition(position);
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    public void openSpinner(View view) {
        playerNamesSpinner.performClick();
    }

    @Override
    public void onBackPressed() {
/*        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Are you sure you want to exit?")
                .setCancelable(false)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        HomeActivity.this.finish();
                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });
        AlertDialog alert = builder.create();
        alert.show();*/
    }
}

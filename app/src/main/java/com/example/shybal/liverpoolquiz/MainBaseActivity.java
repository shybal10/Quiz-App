package com.example.shybal.liverpoolquiz;

import android.app.ActionBar;
import android.app.Dialog;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Handler;
import android.os.SystemClock;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ProgressBar;
import org.json.JSONObject;


public class MainBaseActivity extends AppCompatActivity {
    protected static MainBaseActivity BaseActivity;
   // public MainBaseFragment BaseFragment;
    private static final String TAG = "MainBaseActivity";
    private Dialog spinWheelDialog;
    Handler spinWheelTimer = new Handler(); // Handler to post a runnable that

    public static final int SPINWHEEL_LIFE_TIME = 700;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.e(TAG, "On create");
        BaseActivity = this;

    }

    public void startSpinwheel(boolean setDefaultLifetime, boolean isCancelable) {
        // Log.d(TAG, "startSpinwheel"+getCurrentActivity().getClass() );
        // If already showing no need to create.
        if (spinWheelDialog != null && spinWheelDialog.isShowing())
            return;

        spinWheelDialog = new Dialog(BaseActivity, R.style.wait_spinner_style);
        ProgressBar progressBar = new ProgressBar(BaseActivity);
        ActionBar.LayoutParams layoutParams = new ActionBar.LayoutParams(ActionBar.LayoutParams.WRAP_CONTENT, ActionBar.LayoutParams.WRAP_CONTENT);
        spinWheelDialog.addContentView(progressBar, layoutParams);
        spinWheelDialog.setCancelable(isCancelable);

        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                spinWheelDialog.show();
            }
        });

        // start timer for SPINWHEEL_LIFE_TIME
        spinWheelTimer.removeCallbacks(dismissSpinner);
        if (setDefaultLifetime) // If requested for default dismiss time.
            spinWheelTimer.postAtTime(dismissSpinner, SystemClock.uptimeMillis() + SPINWHEEL_LIFE_TIME);

        spinWheelDialog.setCanceledOnTouchOutside(false);
    }

    public void stopSpinWheel() {
        // Log.d(TAG, "stopSpinWheel"+getCurrentActivity().getClass());
        if (spinWheelDialog != null)
            try {
                spinWheelDialog.dismiss();
            } catch (IllegalArgumentException e) {
                Log.e(TAG, "Parent is died while tryingto dismiss spin wheel dialog ");
                e.printStackTrace();
            }
        spinWheelDialog = null;
    }

    Runnable dismissSpinner = new Runnable() {
        @Override
        public void run() {
            stopSpinWheel();
        }
    };

    public boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager = (ConnectivityManager) BaseActivity
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager
                .getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }

    public static MainBaseActivity getMainBaseActivity() {
        return BaseActivity;
    }

}

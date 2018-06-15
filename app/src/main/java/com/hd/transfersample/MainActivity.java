package com.hd.transfersample;

import android.annotation.SuppressLint;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.text.format.DateUtils;
import android.widget.TextView;

import com.hd.transfer.TransferGif;

public class MainActivity extends AppCompatActivity {

    private final static String inputParentPath = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_MOVIES) + "/screen_capture/";

    private final static String videoName = "screen_capture_20180605-09-45-54.mp4";
//    private final static String videoName = "screen_capture_20180615-09-22-02.mp4";

    private final static String paletteName = "palette.jpeg";

    private String gifName;

    private TextView tvTransfer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tvTransfer = findViewById(R.id.tvTransfer);
        gifName = videoName.split("\\.")[0] + ".gif";
    }

    @Override
    protected void onResume() {
        super.onResume();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                startTransfer(1);
            }
        }, 3000);
    }

    @SuppressLint("SetTextI18n")
    private void startTransfer(final int mode) {
        final long startTime = System.currentTimeMillis();
        tvTransfer.setText("start transfer :" + startTime);
        new Thread(new Runnable() {
            @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
            @Override
            public void run() {
                boolean state;
                if (mode == 0) {
                    state = TransferGif.transfer(inputParentPath + videoName, inputParentPath + gifName, inputParentPath + paletteName);
                } else {
                    state = TransferGif.transfer(inputParentPath + videoName, inputParentPath + gifName);
                }
                reportState(state, startTime);
            }
        }).start();
    }

    private void reportState(final boolean state, long startTime) {
        final long endTime = System.currentTimeMillis();
        final long time = endTime - startTime;
        runOnUiThread(new Runnable() {
            @SuppressLint("SetTextI18n")
            @Override
            public void run() {
                tvTransfer.setText("end transfer :" + time + "===" + DateUtils.formatElapsedTime(time) + "==" + state);
            }
        });
    }

}

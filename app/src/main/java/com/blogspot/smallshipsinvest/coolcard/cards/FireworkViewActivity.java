package com.blogspot.smallshipsinvest.coolcard.cards;

import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.WindowManager;
import android.widget.RelativeLayout;

import com.blogspot.smallshipsinvest.coolcard.Data;
import com.blogspot.smallshipsinvest.coolcard.R;
import com.blogspot.smallshipsinvest.coolcard.helpers.Studio;
import com.blogspot.smallshipsinvest.coolcard.helpers.XY;

public class FireworkViewActivity extends AppCompatActivity {


    private static final int X_NUM_OF_FIREWORKS = 3;
    private static final int NUM_OF_BALLS = 9;
    private static final double FIREWORK_RADIUS = 0.3d;
    private static final float BALL_SIZE = 0.01f;
    private static final long FIREWORK_DURATION = 1_000;
    private static final long FIREWORK_MAX_DELAY = 1_000;
    private static final float[] FIREWORK_INFLATOR_PATH = {1, 5, 10, 9, 8, 7, 6, 4, 2, 1, 1};

    private static final float TEXT_INFLATOR = 20f;
    private static final long TEXT_DURATION = 5_000;
    private static final float TEXT_SPACING_MULTIPLIER = 1f;

    private static final long START_DELAY = 1_000;

    private MediaPlayer mp;
    private Handler h;
    private Runnable r;
    Studio.Firework[] firework;
    Studio.Script greeting;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);

        mp = MediaPlayer.create(this, Data.card.musicID());
        setVolumeControlStream(AudioManager.STREAM_MUSIC);

        RelativeLayout screen = (RelativeLayout) findViewById(R.id.screen);

        DisplayMetrics dm = getResources().getDisplayMetrics();
        double screenRatio = 1d * dm.heightPixels / dm.widthPixels;

        int[] imageIDs = {R.drawable.ball1, R.drawable.ball2, R.drawable.ball3, R.drawable.ball4, R.drawable
                .ball5, R.drawable.ball6};

        XY[] burstPattern = XY.chessPattern(X_NUM_OF_FIREWORKS, screenRatio);
        firework = new Studio.Firework[burstPattern.length];

        for (int i = 0; i < burstPattern.length; i++) {
            XY[] fireworkTargets = XY.fireworkPattern(burstPattern[i], NUM_OF_BALLS, FIREWORK_RADIUS,
                    screenRatio);
            firework[i] = new Studio.Firework(screen, burstPattern[i], fireworkTargets);
            firework[i].setImage(imageIDs);
            firework[i].setSize(BALL_SIZE);
            firework[i].setDuration(FIREWORK_DURATION);
            firework[i].setDelay(0, FIREWORK_MAX_DELAY, false);
            firework[i].setInflatorPath(FIREWORK_INFLATOR_PATH);
        }

        greeting = new Studio.Script(screen);
        greeting.setTextSize(Data.card.fontSize());
        greeting.setTextColor(R.color.cardRed, 0f);
        greeting.setText(Data.card.greeting);
        greeting.setInflator(TEXT_INFLATOR);
        greeting.setDuration(TEXT_DURATION);
        greeting.setFont(Studio.Script.ROBOTO_BOLD_ITALIC_FONT);
        greeting.setLineSpacing(TEXT_SPACING_MULTIPLIER);
        greeting.squeeze();

        r = new Runnable() {
            @Override
            public void run() {
                for (Studio.Firework fw : firework) {
                    fw.start();
                }
                greeting.reinflate();
                mp.start();
            }
        };

        h = new Handler();
        h.postDelayed(r, START_DELAY);

    }

    @Override
    protected void onStop() {
        super.onStop();
        mp.release();
        h.removeCallbacks(r);
        finish();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        mp.release();
        h.removeCallbacks(r);
        for (Studio.Firework fw : firework) {
            fw.end();
        }
    }

}

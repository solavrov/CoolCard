package com.blogspot.smallshipsinvest.coolcard.cards;

import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.view.WindowManager;
import android.widget.RelativeLayout;

import com.blogspot.smallshipsinvest.coolcard.Data;
import com.blogspot.smallshipsinvest.coolcard.R;
import com.blogspot.smallshipsinvest.coolcard.helpers.Studio;
import com.blogspot.smallshipsinvest.coolcard.helpers.XY;


public class SpaceViewActivity extends AppCompatActivity {

    private static final int X_NUM_OF_SKY_STARS = 6;
    private static final double SKY_STAR_SHIFT = 0.1d;
    private static final float STAR_SKY_RELATIVE_SIZE = 0.042f;
    private static final long STAR_BLINK_DURATION = 1_000;
    private static final long STAR_BLINK_DELAY = 10_000;
    private static final float SKY_STAR_INFLATOR = 4f;

    private static final int NUM_OF_CLOSE_FLYING_STARS = 5;
    private static final float FLYING_CLOSE_STAR_SIZE = 0.042f;
    private static final long CLOSE_FLYING_STAR_DURATION = 5_000;
    private static final long CLOSE_FLYING_STAR_MIN_DELAY = 0;
    private static final long CLOSE_FLYING_STAR_MAX_DELAY = 10_000;
    private static final float CLOSE_STAR_INFLATOR = 15f;

    private static final int NUM_OF_FAR_FLYING_STARS = 5;
    private static final float FLYING_FAR_STAR_SIZE = 0.021f;
    private static final long FAR_FLYING_STAR_DURATION = 10_000;
    private static final long FAR_FLYING_STAR_MIN_DELAY = 0;
    private static final long FAR_FLYING_STAR_MAX_DELAY = 10_000;
    private static final float FAR_STAR_INFLATOR = 15f;

    private static final float TEXT_INFLATOR = 10f;
    private static final long TEXT_DURATION = 10_000;
    private static final float TEXT_SPACING_MULTIPLIER = 1f;

    private static final long START_DELAY = 1_000;

    private MediaPlayer mp;
    private Handler h;
    private Runnable r;
    private Studio.Sky sky;
    private Studio.Firework flyingClose;
    private Studio.Firework flyingFar;
    private Studio.Script greeting;

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

        int[] imageIDs = {R.drawable.star, R.drawable.star2};

        XY[] skyPattern = XY.chessPattern(X_NUM_OF_SKY_STARS, screenRatio);
        skyPattern = XY.shiftRandomly(skyPattern, SKY_STAR_SHIFT, screenRatio);

        sky = new Studio.Sky(screen, skyPattern);
        sky.setImage(imageIDs);
        sky.setDuration(STAR_BLINK_DURATION);
        sky.setDelay(0, STAR_BLINK_DELAY, true);
        sky.setInflator(SKY_STAR_INFLATOR);
        sky.setSize(STAR_SKY_RELATIVE_SIZE);

        double farRadius = 1.5 * FLYING_FAR_STAR_SIZE * FAR_STAR_INFLATOR + 0.5 * Math.sqrt(1 + Math.pow
                (screenRatio,2));
        XY[] farTargets = XY.fireworkPattern(new XY(), NUM_OF_FAR_FLYING_STARS, farRadius, screenRatio);
        flyingFar = new Studio.Firework(screen, new XY(), farTargets);
        flyingFar.setImage(imageIDs);
        flyingFar.setSize(FLYING_FAR_STAR_SIZE);
        flyingFar.setDuration(FAR_FLYING_STAR_DURATION);
        flyingFar.setDelay(FAR_FLYING_STAR_MIN_DELAY, FAR_FLYING_STAR_MAX_DELAY, true);
        float[] farInflatorPath = {1 , FAR_STAR_INFLATOR};
        flyingFar.setInflatorPath(farInflatorPath);

        double closeRadius = 1.5 * FLYING_CLOSE_STAR_SIZE * CLOSE_STAR_INFLATOR + 0.5 * Math.sqrt(1 + Math.pow
                (screenRatio,2));
        XY[] closeTargets = XY.fireworkPattern(new XY(), NUM_OF_CLOSE_FLYING_STARS, closeRadius, screenRatio);
        flyingClose = new Studio.Firework(screen, new XY(), closeTargets);
        flyingClose.setImage(imageIDs);
        flyingClose.setSize(FLYING_CLOSE_STAR_SIZE);
        flyingClose.setDuration(CLOSE_FLYING_STAR_DURATION);
        flyingClose.setDelay(CLOSE_FLYING_STAR_MIN_DELAY, CLOSE_FLYING_STAR_MAX_DELAY, true);
        float[] closeInflatorPath = {1 , CLOSE_STAR_INFLATOR};
        flyingClose.setInflatorPath(closeInflatorPath);

        greeting = new Studio.Script(screen);
        greeting.setTextSize(Data.card.fontSize());
        greeting.setTextColor(R.color.cardLightBlue, 0f);
        greeting.setText(Data.card.greeting);
        greeting.setInflator(TEXT_INFLATOR);
        greeting.setDuration(TEXT_DURATION);
        greeting.setFont(Studio.Script.ROBOTO_BOLD_ITALIC_FONT);
        greeting.setLineSpacing(TEXT_SPACING_MULTIPLIER);
        greeting.squeeze();


        r = new Runnable() {
            @Override
            public void run() {
                sky.flashSize();
                flyingClose.start();
                flyingFar.start();
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
        sky.end();
        flyingClose.end();
        flyingFar.end();
    }

}


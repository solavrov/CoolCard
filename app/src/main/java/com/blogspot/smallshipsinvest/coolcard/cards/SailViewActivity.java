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

public class SailViewActivity extends AppCompatActivity {

    private static final int SCREEN_PADDING = -10_000;

    private static final float SUN_DIVIDER_FOR_LOW_SCREEN_RATIO = 1.10f;
    private static final float SHIP_DIVIDER_FOR_LOW_SCREEN_RATIO = 1.25f;
    private static final double SCREEN_RATIO_FLOOR = 1.5d;

    private static final long BACKGROUND_DURATION = 20_000;

    //    sun
    private static final XY SUN_START_POINT = new XY(0.5d, 1.4d);
    private static final float SUN_RELATIVE_SIZE = 1f;
    private static final long SUNRISE_DURATION = 20_000;

    //    greeting
    private static final long GREETING_DURATION = 3_000;
    private static final float GREETING_INFLATOR = 10f;
    private static final long GREETING_DELAY = 20_000;

    //    ship
    private static final XY SHIP_POSITION_POINT = new XY(0.5d, 0.83d);
    private static final float SHIP_RELATIVE_SIZE = 0.35f;
    private static final long SHIP_SWING_DURATION = 1_200;
    private static final float SHIP_SWING_ANGLE = 15f;

    //    waves
    private static final XY WAVE_LEFT_UPPER_CORNER_POINT = new XY(0d, 0.8d);
    private static final XY WAVE_RELATIVE_DIMENSIONS = new XY(1.2d, 0.36d);
    private static final double WAVE_RELATIVE_SHIFT = 0.2d;
    private static final long WAVE_SHIFT_DURATION = 3_000;
    private static final XY WAVE2_LEFT_UPPER_CORNER_POINT = new XY(0d, 0.9d);
    private static final XY WAVE2_RELATIVE_DIMENSIONS = new XY(1.2d, 0.36d);
    private static final double WAVE2_RELATIVE_SHIFT = 0.2d;
    private static final long WAVE2_SHIFT_DURATION = 1_500;

    private static final long START_DELAY = 1_000;


    private MediaPlayer mp;
    private Handler h;
    private Runnable r;
    private Studio.Actor ship;
    private Studio.Actor wave;
    private Studio.Actor wave2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);

        mp = MediaPlayer.create(this, Data.card.musicID());
        setVolumeControlStream(AudioManager.STREAM_MUSIC);

        RelativeLayout screen = (RelativeLayout) findViewById(R.id.screen);
        assert screen != null;
        screen.setPadding(SCREEN_PADDING, SCREEN_PADDING, SCREEN_PADDING, SCREEN_PADDING);

        DisplayMetrics dm = getResources().getDisplayMetrics();
        double screenRatio = 1d * dm.heightPixels / dm.widthPixels;

        float sunDivider = 1f;
        float shipDivider = 1f;
        if (screenRatio < SCREEN_RATIO_FLOOR) {
            sunDivider = SUN_DIVIDER_FOR_LOW_SCREEN_RATIO;
            shipDivider = SHIP_DIVIDER_FOR_LOW_SCREEN_RATIO;
        }

        Studio.animateBackground(screen, R.color.colorBlack, R.color.colorSky, BACKGROUND_DURATION);

        Studio.Actor sun = new Studio.Actor(screen, SUN_START_POINT);
        sun.setSize(SUN_RELATIVE_SIZE / sunDivider);
        sun.setImage(R.drawable.sun);
        sun.setDuration(SUNRISE_DURATION);
        XY sunTarget = new XY();
        sunTarget.y /= (screenRatio * sunDivider);
        sun.setTarget(sunTarget);
        sun.fly();

        Studio.Script greeting = new Studio.Script(screen, 0f, (float) (1 - 1 / (screenRatio * sunDivider)));
        greeting.setTextColor(R.color.colorDarkWater, 0f);
        greeting.setText(Data.card.greeting);
        greeting.setTextSize(Data.card.fontSize() / sunDivider);
        greeting.setDuration(GREETING_DURATION);
        greeting.setInflator(GREETING_INFLATOR);
        greeting.setFont(Studio.Script.ROBOTO_BOLD_ITALIC_FONT);
        greeting.setDelay(GREETING_DELAY);
        greeting.inflate();

        ship = new Studio.Actor(screen, SHIP_POSITION_POINT);
        ship.setSize(SHIP_RELATIVE_SIZE / shipDivider, 2 * SHIP_RELATIVE_SIZE / shipDivider);
        ship.setImage(R.drawable.ship_motion);
        ship.setDuration(SHIP_SWING_DURATION);

        XY waveCenter = new XY(WAVE_LEFT_UPPER_CORNER_POINT.x + WAVE_RELATIVE_DIMENSIONS.x / 2,
                WAVE_LEFT_UPPER_CORNER_POINT.y + WAVE_RELATIVE_DIMENSIONS.y / 2 / screenRatio);
        wave = new Studio.Actor(screen, waveCenter);
        wave.setTarget(new XY(waveCenter.x - WAVE_RELATIVE_SHIFT, waveCenter.y));
        wave.setSize((float) WAVE_RELATIVE_DIMENSIONS.x, (float) WAVE_RELATIVE_DIMENSIONS.y);
        wave.setImage(R.drawable.wave);
        wave.setDuration(WAVE_SHIFT_DURATION);

        XY waveCenter2 = new XY(WAVE2_LEFT_UPPER_CORNER_POINT.x + WAVE2_RELATIVE_DIMENSIONS.x / 2,
                WAVE2_LEFT_UPPER_CORNER_POINT.y + WAVE2_RELATIVE_DIMENSIONS.y / 2 / screenRatio);
        wave2 = new Studio.Actor(screen, waveCenter2);
        wave2.setTarget(new XY(waveCenter2.x - WAVE2_RELATIVE_SHIFT, waveCenter2.y));
        wave2.setSize((float) WAVE2_RELATIVE_DIMENSIONS.x, (float) WAVE2_RELATIVE_DIMENSIONS.y);
        wave2.setImage(R.drawable.wave2);
        wave2.setDuration(WAVE2_SHIFT_DURATION);

        r = new Runnable() {
            @Override
            public void run() {
                ship.swing(SHIP_SWING_ANGLE);
                wave.flyLoop(false);
                wave2.flyLoop(false);
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
        ship.end();
        wave.end();
        wave2.end();
    }

}

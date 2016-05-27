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

public class LoveViewActivity extends AppCompatActivity {

    private static final float FLOWER_RELATIVE_SIZE = 0.05f;
    private static final float FLOWER_INFLATOR = 5f;
    private static final int FLOWER_INFLATING_TIME = 15_000;
    private static final int X_NUM_OF_FLOWERS = 13;
    private static final double RELATIVE_CUT_DIAMETER = 0.75d;
    private static final double FLOWER_RANDOM_SHIFT = 0.02d;

    private static final float HEART_RELATIVE_SIZE = 0.2f;
    private static final int HEART_INFLATING_TIME = 10_000;

    private static final float TEXT_SPACING_MULTIPLIER = 1f;

    private static final long START_DELAY = 1_000;

    private MediaPlayer mp;
    private Handler h;
    private Runnable r;
    private Studio.Sky garden;
    private Studio.Actor heart;
    private Studio.Script greeting;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);

        mp = MediaPlayer.create(this, Data.card.musicID());
        setVolumeControlStream(AudioManager.STREAM_MUSIC);

        DisplayMetrics dm = getResources().getDisplayMetrics();
        double screenRatio = 1d * dm.heightPixels / dm.widthPixels;

        RelativeLayout screen = (RelativeLayout) findViewById(R.id.screen);


//        garden
        XY[] pattern = XY.outCircleChessPattern(X_NUM_OF_FLOWERS, RELATIVE_CUT_DIAMETER, screenRatio);
        pattern = XY.shiftRandomly(pattern, FLOWER_RANDOM_SHIFT, screenRatio);
        garden = new Studio.Sky(screen, pattern);
        garden.setDuration(FLOWER_INFLATING_TIME);
        garden.setInflator(FLOWER_INFLATOR);
        garden.setSize(FLOWER_RELATIVE_SIZE);
        int flowerIDs[] = {R.drawable.white_rose, R.drawable.pink_rose};
        garden.setImage(flowerIDs);


//        heart
        XY point = new XY();
        heart = new Studio.Actor(screen, point);
        heart.setImage(R.drawable.heart);
        heart.setSize(HEART_RELATIVE_SIZE);
        heart.setInflator(1 / HEART_RELATIVE_SIZE);
        heart.setDuration(HEART_INFLATING_TIME);


//        greeting
        greeting = new Studio.Script(screen);
        greeting.setDuration(HEART_INFLATING_TIME);
        greeting.setTextSize(Data.card.fontSize());
        greeting.setInflator(1 / HEART_RELATIVE_SIZE);
        greeting.setTextColor(R.color.cardRed, 0f);
        greeting.setText(Data.card.greeting);
        greeting.setFont(Studio.Script.ROBOTO_BOLD_ITALIC_FONT);
        greeting.setLineSpacing(TEXT_SPACING_MULTIPLIER);


        r = new Runnable() {
            @Override
            public void run() {
                garden.inflate();
                heart.inflate();
                greeting.inflate();
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
    }

}

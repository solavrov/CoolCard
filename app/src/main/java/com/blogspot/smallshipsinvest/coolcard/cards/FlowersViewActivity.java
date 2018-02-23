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

public class FlowersViewActivity extends AppCompatActivity {

    private static final long BACKGROUND_DURATION = 10_000;

    private static final float FLOWER_RELATIVE_SIZE = 0.02f;
    private static final float FLOWER_INFLATOR = 7f;
    private static final int FLOWER_INFLATING_TIME = 10_000;
    private static final int X_NUM_OF_FLOWERS = 19;
    private static final double RELATIVE_CUT_DIAMETER = 0.85d;
    private static final double FLOWER_RANDOM_SHIFT = 0.01d;

    private static final float TEXT_SPACING_MULTIPLIER = 1f;
    private static final long GREETING_INFLATING_TIME = 10_000;
    private static final float GREETING_INFLATOR = 7f;

    private static final long START_DELAY = 1_000;

    private MediaPlayer mp;
    private Handler h;
    private Runnable r;
    private Studio.Sky garden;
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
        Studio.animateBackground(screen, R.color.black, R.color.cardLeafGreen, BACKGROUND_DURATION);


//        garden
        XY[] pattern = XY.outCircleChessPattern(X_NUM_OF_FLOWERS, RELATIVE_CUT_DIAMETER, screenRatio);
        pattern = XY.shiftRandomly(pattern, FLOWER_RANDOM_SHIFT, screenRatio);
        garden = new Studio.Sky(screen, pattern);
        garden.setDuration(FLOWER_INFLATING_TIME);
        garden.setInflator(FLOWER_INFLATOR);
        garden.setSize(FLOWER_RELATIVE_SIZE);
        int flowerIDs[] = {R.drawable.f1, R.drawable.f2, R.drawable.f3, R.drawable.f4, R.drawable.f5, R
                .drawable.f6, R.drawable.f7, R.drawable.f8};
        garden.setImage(flowerIDs);


//        greeting
        greeting = new Studio.Script(screen);
        greeting.setDuration(GREETING_INFLATING_TIME);
        greeting.setTextSize(Data.card.fontSize());
        greeting.setInflator(GREETING_INFLATOR);
        greeting.setTextColor(R.color.white, 0f);
        greeting.setText(Data.card.greeting);
        greeting.setFont(Studio.Script.ROBOTO_BOLD_ITALIC_FONT);
        greeting.setLineSpacing(TEXT_SPACING_MULTIPLIER);
        greeting.squeeze();


        r = new Runnable() {
            @Override
            public void run() {
                garden.inflate();
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
    }

}

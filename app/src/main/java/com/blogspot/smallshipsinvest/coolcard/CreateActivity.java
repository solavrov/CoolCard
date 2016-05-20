package com.blogspot.smallshipsinvest.coolcard;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.blogspot.smallshipsinvest.coolcard.cards.FireworkViewActivity;
import com.blogspot.smallshipsinvest.coolcard.cards.FlowersViewActivity;
import com.blogspot.smallshipsinvest.coolcard.cards.LoveViewActivity;
import com.blogspot.smallshipsinvest.coolcard.cards.SailViewActivity;
import com.blogspot.smallshipsinvest.coolcard.cards.SpaceViewActivity;
import com.blogspot.smallshipsinvest.coolcard.helpers.CodeHelper;
import com.blogspot.smallshipsinvest.coolcard.helpers.Navigator;
import com.blogspot.smallshipsinvest.coolcard.helpers.ViewHelper;
import com.firebase.client.Firebase;

public class CreateActivity extends AppCompatActivity {

    private static TextView codeView;
    private static TextView greetingView;
    private static Firebase cloud;
    private static Spinner fontSizeSpinner;
    private static Spinner musicSpinner;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create);

        codeView = (TextView) findViewById(R.id.code);
        greetingView = (TextView) findViewById(R.id.greetingView);
        cloud = new Firebase(Data.FIREBASE);

        setIcon((ImageView) findViewById(R.id.icon));

        codeView.setText(Data.cardCreate.code);

        greetingView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                Navigator.run(v.getContext(), EditActivity.class, Navigator.KEEP, Navigator.START);
                return true;
            }
        });

        ViewHelper.setSpinnerFromResource(this, R.id.fontSizeSpinner, R.array.font_sizes, R.layout.spinner, R
                .layout.spinner_dropdown);
        fontSizeSpinner = (Spinner) findViewById(R.id.fontSizeSpinner);
        assert fontSizeSpinner != null;
        fontSizeSpinner.setSelection(Data.cardCreate.fontSize);
        fontSizeSpinner.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                InputMethodManager imm = (InputMethodManager) getApplicationContext().getSystemService
                        (Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(greetingView.getWindowToken(), 0);
                return false;
            }
        });

        ViewHelper.setSpinnerFromResource(this, R.id.musicSpinner, R.array.music, R.layout.spinner, R
                .layout.spinner_dropdown);
        musicSpinner = (Spinner) findViewById(R.id.musicSpinner);
        musicSpinner.setSelection(Data.cardCreate.musicMap.get(Data.cardCreate.template));
        musicSpinner.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                InputMethodManager imm = (InputMethodManager) getApplicationContext().getSystemService
                        (Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(greetingView.getWindowToken(), 0);
                return false;
            }
        });

    }

    @Override
    protected void onResume() {
        super.onResume();
        greetingView.setText(Data.cardCreate.greeting);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Data.cardCreate.fontSize = fontSizeSpinner.getSelectedItemPosition();
        Data.cardCreate.music = Card.musicFromSpinner(musicSpinner.getSelectedItemPosition());
        Data.cardCreate.musicMap.put(Data.cardCreate.template, musicSpinner.getSelectedItemPosition());
    }

    private void setIcon(ImageView v) {
        switch (Data.cardCreate.template) {
            case Card.SPACE_TEMPLATE:
                v.setImageResource(R.drawable.space_icon);
                break;
            case Card.LOVE_TEMPLATE:
                v.setImageResource(R.drawable.love_icon);
                break;
            case Card.SAIL_TEMPLATE:
                v.setImageResource(R.drawable.sail_icon);
                break;
            case Card.FLOWERS_TEMPLATE:
                v.setImageResource(R.drawable.flowers_icon);
                break;
            case Card.FIREWORK_TEMPLATE:
                v.setImageResource(R.drawable.firework_icon);
                break;
        }
    }

    public void generateCode(View view) {
        Data.cardCreate.code = CodeHelper.generateCode(new CodeHelper.Alphabet(), CodeHelper.CODE_PATTERN);
        codeView.setText(Data.cardCreate.code);
    }

    public void runSend(View view) {

        Data.cardCreate.fontSize = fontSizeSpinner.getSelectedItemPosition();
        Data.cardCreate.music = Card.musicFromSpinner(musicSpinner.getSelectedItemPosition());
        Data.cardCreate.greeting = greetingView.getText().toString();

        Data.card = Data.cardCreate.copy();

        if (!Data.card.greeting.equals("")) {

            cloud.child(Card.CARDS).child(Data.card.code).child(Card.TEMPLATE).setValue(Data.card.template);
            cloud.child(Card.CARDS).child(Data.card.code).child(Card.FONT_SIZE).setValue(Data.card.fontSize);
            cloud.child(Card.CARDS).child(Data.card.code).child(Card.MUSIC).setValue(Data.card.music);
            cloud.child(Card.CARDS).child(Data.card.code).child(Card.GREETING).setValue(Data.card.greeting);

            String message = getResources().getString(R.string.received) + Data.card.code +
                    "\n\n" + getResources().getString(R.string.download) + Data.APP_LINK;

            CodeHelper.shareText(this, message, true);

            Data.cardCreate.code = CodeHelper.generateCode(new CodeHelper.Alphabet(), CodeHelper.CODE_PATTERN);

            finish();

        } else {
            Toast.makeText(this, R.string.forgot_greeting, Toast.LENGTH_LONG).show();
        }

    }

    public void runViewFromCreate(View view) {

        Data.cardCreate.fontSize = fontSizeSpinner.getSelectedItemPosition();
        Data.cardCreate.music = Card.musicFromSpinner(musicSpinner.getSelectedItemPosition());
        Data.cardCreate.greeting = greetingView.getText().toString();

        Data.card = Data.cardCreate.copy();

//        template switcher
        switch (Data.card.template) {
            case Card.LOVE_TEMPLATE:
                Navigator.run(view.getContext(), LoveViewActivity.class, Navigator.KEEP, Navigator
                        .START);
                break;
            case Card.SPACE_TEMPLATE:
                Navigator.run(view.getContext(), SpaceViewActivity.class, Navigator.KEEP, Navigator
                        .START);
                break;
            case Card.SAIL_TEMPLATE:
                Navigator.run(view.getContext(), SailViewActivity.class, Navigator.KEEP, Navigator
                        .START);
                break;
            case Card.FLOWERS_TEMPLATE:
                Navigator.run(view.getContext(), FlowersViewActivity.class, Navigator.KEEP, Navigator
                        .START);
                break;
            case Card.FIREWORK_TEMPLATE:
                Navigator.run(view.getContext(), FireworkViewActivity.class, Navigator.KEEP, Navigator
                        .START);
                break;
        }

    }

}

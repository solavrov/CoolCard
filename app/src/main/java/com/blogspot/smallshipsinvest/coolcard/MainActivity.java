package com.blogspot.smallshipsinvest.coolcard;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.TextView;

import com.blogspot.smallshipsinvest.coolcard.cards.FireworkViewActivity;
import com.blogspot.smallshipsinvest.coolcard.cards.FlowersViewActivity;
import com.blogspot.smallshipsinvest.coolcard.cards.LoveViewActivity;
import com.blogspot.smallshipsinvest.coolcard.cards.SailViewActivity;
import com.blogspot.smallshipsinvest.coolcard.cards.SpaceViewActivity;
import com.blogspot.smallshipsinvest.coolcard.helpers.CodeHelper;
import com.blogspot.smallshipsinvest.coolcard.helpers.Navigator;
import com.blogspot.smallshipsinvest.coolcard.helpers.ViewHelper;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;

public class MainActivity extends AppCompatActivity {


    public final static long SHORT_FADING_TIME = 2_500;
    public final static long LONG_FADING_TIME = 6_000;

    private static TextView messageView;
    private static EditText codeView;
    private static Firebase cloud;

    private static boolean notConnecting = true;
    private static boolean readyToView = true;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setVersion();
        TextView versionView = (TextView) findViewById(R.id.version);
        assert versionView != null;
        versionView.setText("v" + Data.appVersionName);

        Firebase.setAndroidContext(this);

        cloud = new Firebase(Data.FIREBASE);
        messageView = (TextView) findViewById(R.id.message);
        codeView = (EditText) findViewById(R.id.code);
        codeView.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                boolean handled = false;
                if (actionId == EditorInfo.IME_ACTION_DONE) {
                    runViewFromMain(codeView);
                    handled = true;
                }
                return handled;
            }
        });

        if (Data.cardCreate.code.equals("")) {
            Data.cardCreate.code = CodeHelper.generateCode(new CodeHelper.Alphabet(), CodeHelper.CODE_PATTERN);
        }

//        Data.cardCreate.code = "card1";

    }

    @Override
    protected void onResume() {
        super.onResume();
        if (notConnecting) messageView.setText("");
        readyToView = true;
    }

    @Override
    protected void onPause() {
        super.onPause();
        readyToView = false;
    }

    private void setVersion() {
        PackageInfo pInfo;
        try {
            pInfo = getPackageManager().getPackageInfo(getPackageName(), 0);
            Data.appVersionName = pInfo.versionName;
            Data.appVersionCode = pInfo.versionCode;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void runViewFromMain(View view) {

        if (notConnecting) {

            notConnecting = false;

            String enteredCode = codeView.getText().toString();

            if (!enteredCode.equals("")) {

                final Context context = view.getContext();

//                messageView.setText(R.string.wait);
                ViewHelper.sendFadingMessage(messageView, R.string.wait, 0, 1f);

                cloud.child(Card.CARDS).child(enteredCode)
                        .addListenerForSingleValueEvent(new ValueEventListener() {

                            @Override
                            public void onDataChange(DataSnapshot dataSnapshot) {

                                if (readyToView) {

                                    if (dataSnapshot.getValue() != null) {

                                        messageView.setText("");

                                        Data.card.template = (String) dataSnapshot.child(Card.TEMPLATE)
                                                .getValue();
                                        Data.card.fontSize = (int) (long) dataSnapshot.child(Card.FONT_SIZE)
                                                .getValue();
                                        Data.card.music = (String) dataSnapshot.child(Card.MUSIC).getValue();
                                        Data.card.greeting = (String) dataSnapshot.child(Card.GREETING)
                                                .getValue();

//                                template switcher
                                        switch (Data.card.template) {

                                            case Card.SPACE_TEMPLATE:
                                                Navigator.run(context, SpaceViewActivity.class, Navigator
                                                        .KEEP, Navigator.START);
                                                break;

                                            case Card.LOVE_TEMPLATE:
                                                Navigator.run(context, LoveViewActivity.class, Navigator.KEEP,
                                                        Navigator.START);
                                                break;

                                            case Card.SAIL_TEMPLATE:
                                                Navigator.run(context, SailViewActivity.class, Navigator.KEEP,
                                                        Navigator.START);
                                                break;

                                            case Card.FLOWERS_TEMPLATE:
                                                Navigator.run(context, FlowersViewActivity.class, Navigator
                                                        .KEEP, Navigator.START);
                                                break;

                                            case Card.FIREWORK_TEMPLATE:
                                                Navigator.run(context, FireworkViewActivity.class, Navigator
                                                        .KEEP, Navigator.START);
                                                break;

                                            default:
//                                                messageView.setText(R.string.update);
                                                ViewHelper.sendFadingMessage(messageView,
                                                        R.string.update,
                                                        LONG_FADING_TIME, 0f);

                                        }

                                    } else {
//                                        messageView.setText(R.string.incorrect);
                                        ViewHelper.sendFadingMessage(messageView, R.string.incorrect,
                                                SHORT_FADING_TIME, 0f);
                                    }


                                }

                                notConnecting = true;

                            }

                            @Override
                            public void onCancelled(FirebaseError firebaseError) {
                            }

                        });

            } else {
//                messageView.setText(R.string.incorrect);
                ViewHelper.sendFadingMessage(messageView, R.string.enter_code_message, SHORT_FADING_TIME, 0f);
                notConnecting = true;
            }

        }

    }

    public void runTemplatesFromMain(View view) {
        Navigator.run(view.getContext(), TemplatesActivity.class, Navigator.KEEP, Navigator.START);
    }

}



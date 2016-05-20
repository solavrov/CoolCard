package com.blogspot.smallshipsinvest.coolcard;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.EditText;

public class EditActivity extends AppCompatActivity {

    private static EditText greetingEdit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);
        greetingEdit = (EditText) findViewById(R.id.greetingEdit);
        assert greetingEdit != null;
        greetingEdit.setText(Data.cardCreate.greeting);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Data.cardCreate.greeting = greetingEdit.getText().toString();
    }

}

package com.blogspot.smallshipsinvest.coolcard;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.blogspot.smallshipsinvest.coolcard.helpers.ListAdapter;
import com.blogspot.smallshipsinvest.coolcard.helpers.Navigator;

import java.util.ArrayList;

public class TemplatesActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_templates);

        ListView listView = (ListView) findViewById(R.id.tempList);

        ArrayList<ListAdapter.Item> list = new ArrayList<>();
        list.add(new ListAdapter.Item(R.drawable.firework_icon, getResources().getString(R.string.firework)));
        list.add(new ListAdapter.Item(R.drawable.love_icon, getResources().getString(R.string.love)));
        list.add(new ListAdapter.Item(R.drawable.sail_icon, getResources().getString(R.string.sail)));
        list.add(new ListAdapter.Item(R.drawable.space_icon, getResources().getString(R.string.space)));
        list.add(new ListAdapter.Item(R.drawable.flowers_icon, getResources().getString(R.string.flowers)));

        ListAdapter adapter = new ListAdapter(this, R.layout.listview_item, R.id.itemImage, R.id
                .itemText, list);

        assert listView != null;
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                runCreate(position);
            }
        });

    }

    private void runCreate(int position) {

        switch (position) {
            case 0:
                Data.cardCreate.template = Card.FIREWORK_TEMPLATE;
                break;
            case 1:
                Data.cardCreate.template = Card.LOVE_TEMPLATE;
                break;
            case 2:
                Data.cardCreate.template = Card.SAIL_TEMPLATE;
                break;
            case 3:
                Data.cardCreate.template = Card.SPACE_TEMPLATE;
                break;
            case 4:
                Data.cardCreate.template = Card.FLOWERS_TEMPLATE;
                break;
        }

        Navigator.run(this, CreateActivity.class, Navigator.KEEP, Navigator.START);

    }


}

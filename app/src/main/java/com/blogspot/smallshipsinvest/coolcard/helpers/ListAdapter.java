package com.blogspot.smallshipsinvest.coolcard.helpers;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class ListAdapter extends ArrayAdapter<ListAdapter.Item> {

    private Context context;
    private int layoutID;
    private int imageViewID;
    private int textViewID;
    private ArrayList<Item> list;

    public ListAdapter(Context context, int layoutID, int imageViewID, int textViewID,
                       ArrayList<ListAdapter.Item> list) {
        super(context, layoutID, list);
        this.context = context;
        this.layoutID = layoutID;
        this.imageViewID = imageViewID;
        this.textViewID = textViewID;
        this.list = list;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Activity
                    .LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(layoutID, parent, false);
        }

        Item item = list.get(position);

        ImageView iv = (ImageView) convertView.findViewById(imageViewID);
        iv.setImageResource(item.imageID);

        TextView tv = (TextView) convertView.findViewById(textViewID);
        tv.setText(item.text);

        return convertView;

    }

    public static class Item {

        public int imageID;
        public String text;

        public Item(int imageID, String text) {
            this.imageID = imageID;
            this.text = text;
        }

    }

}

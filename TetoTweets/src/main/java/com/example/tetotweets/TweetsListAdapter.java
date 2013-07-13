package com.example.tetotweets;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

import twitter4j.Status;

/**
 * Created by dacosta on 7/13/13.
 */
public class TweetsListAdapter extends ArrayAdapter<Status> {
    public TweetsListAdapter(Context context, List<Status> objects) {
        super(context, R.layout.tweet_list_item, objects);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.tweet_list_item, null);
        }

        TextView usernameTextView = (TextView) convertView.findViewById(android.R.id.text1);
        usernameTextView.setText(getItem(position).getUser().getName());

        TextView contextTextView = (TextView) convertView.findViewById(android.R.id.text2);
        contextTextView.setText(getItem(position).getText());

        return convertView;
    }
}

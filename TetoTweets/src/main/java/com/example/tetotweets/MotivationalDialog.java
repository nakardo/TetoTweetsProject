package com.example.tetotweets;

import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;

/**
 * Created by dacosta on 7/13/13.
 */
public class MotivationalDialog extends DialogFragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.motivational_dialog_view, container);
        getDialog().getWindow().requestFeature(Window.FEATURE_NO_TITLE);

        return view;
    }
}

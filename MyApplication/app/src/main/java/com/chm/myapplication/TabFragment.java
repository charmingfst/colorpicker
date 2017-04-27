package com.chm.myapplication;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * Created by ason on 2017/1/18.
 */
public class TabFragment extends Fragment {
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        Bundle bundle = getArguments();
        String text = bundle.getString("id");
        TextView textView = new TextView(getActivity());
        textView.setText(text);
        textView.setTextSize(30);



        return textView;
    }
}

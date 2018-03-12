package com.example.kolip.swift;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Gallery;
import android.widget.ImageView;

/**
 * Created by kolip on 21-02-2018.
 */

public class Imageadapter extends BaseAdapter {
    private Context mContext;

    // array of integers for images IDs
    private Integer[] mImageIds = { R.drawable.f1,
            R.drawable.f4, R.drawable.f6};

    // constructor
    public Imageadapter(Context c) {
        mContext = c;
    }

    @Override
    public int getCount() {
        return mImageIds.length;
    }

    @Override
    public Object getItem(int i) {
        return i;
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ImageView imageView = new ImageView(mContext);

        imageView.setImageResource(mImageIds[i]);
        imageView.setLayoutParams(new Gallery.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT));
        return imageView;
    }
}

package com.example.kolip.swift;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.List;

/**
 * Created by kolip on 21-02-2018.
 */

public class flightcusadapter extends BaseAdapter {
    Context context;

   private List<flightlist.myobjec> list1;

    private static LayoutInflater inflater = null;

    public flightcusadapter(Context context, List<flightlist.myobjec> list) {

        this.list1 = list;
        this.context = context;



    }

    @Override
    public int getCount() {
        return list1.size();
    }

    @Override
    public Object getItem(int i) {
        return list1.get(i);
    }

    @Override
    public long getItemId(int pos) {
        return pos;
    }


    @Override
    public View getView(int pos, View view, ViewGroup viewGroup) {

        inflater = (LayoutInflater) context.
                getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View rowView = view;

            rowView = inflater.inflate(R.layout.flightcusxml, null);
            final TextView tv = (TextView) rowView.findViewById(R.id.dtime);
            TextView tv1 = (TextView) rowView.findViewById(R.id.flicomp);
            TextView tv2 = (TextView) rowView.findViewById(R.id.atime);
            TextView tv3 = (TextView) rowView.findViewById(R.id.cost);

            final TextView tv4 = (TextView) rowView.findViewById(R.id.fliname);
            ImageView img1 = (ImageView) rowView.findViewById(R.id.flilogo);
            flightlist.myobjec myObj = list1.get(pos);
            tv4.setText("(" + myObj.getFlgtname() + ")");
            tv1.setText(myObj.getFlgtcomp());
            tv.setText(myObj.getFlgttm());
            tv2.setText(myObj.getFlgtmm());
            tv3.setText("Rs."+myObj.getFlgtcost());
            img1.setImageResource(myObj.getImg());

            return rowView;



        }

    }

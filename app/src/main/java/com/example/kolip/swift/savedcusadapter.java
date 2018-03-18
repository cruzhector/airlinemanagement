package com.example.kolip.swift;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.PhoneAuthProvider;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.List;

/**
 * Created by kolip on 17-03-2018.
 */

public class savedcusadapter extends BaseAdapter {
    Context context;
    private List<saved.saveobjec> list;
    FirebaseFirestore firebaseFirestore;
    FirebaseUser firebaseUser;

    DocumentReference documentReference;


    public savedcusadapter(Context context, List<saved.saveobjec> list) {
        this.context = context;
        this.list = list;
    }

    private static LayoutInflater inflater = null;


    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int i) {
        return list.get(i);
    }

    @Override
    public long getItemId(int pos) {
        return pos;
    }

    @Override
    public View getView(int pos, View view, ViewGroup viewGroup) {
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View rowView = view;
        rowView = inflater.inflate(R.layout.savedcus, null);
        final TextView tv = (TextView) rowView.findViewById(R.id.dtime1);
        TextView tv1 = (TextView) rowView.findViewById(R.id.flicomp1);
        TextView tv2 = (TextView) rowView.findViewById(R.id.atime1);
        TextView tv3 = (TextView) rowView.findViewById(R.id.cost1);
        final TextView tv4 = (TextView) rowView.findViewById(R.id.fliname1);
        ImageView img1 = (ImageView) rowView.findViewById(R.id.flilogo1);
        ImageButton imb1=(ImageButton)rowView.findViewById(R.id.delete);
        imb1.setTag(pos);
saved.saveobjec sv = list.get(pos);
tv4.setText("(" + sv.getS7() + ")");
tv1.setText(sv.getS2());
tv2.setText(sv.getS1());
tv3.setText(sv.getS3());
tv.setText(sv.getS6());

imb1.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View view) {
        Integer ind=(Integer)view.getTag();
       list.remove(ind.intValue());
       notifyDataSetChanged();
       int inc=ind+1;
       String del=String.valueOf(inc);
       firebaseUser=FirebaseAuth.getInstance().getCurrentUser();
       firebaseFirestore=FirebaseFirestore.getInstance();
       String ui=firebaseUser.getUid();
       documentReference=firebaseFirestore.collection("savedbooking").document(ui).collection("saved").document(del);
       documentReference.delete().addOnCompleteListener(new OnCompleteListener<Void>() {
           @Override
           public void onComplete(@NonNull Task<Void> task) {
          if (task.isSuccessful()){
              Toast.makeText(context, "deleted", Toast.LENGTH_SHORT).show();
          }

           }
       });

    }
});



return rowView;
    }
}

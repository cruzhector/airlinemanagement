package com.example.kolip.swift;

import android.app.Dialog;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.List;

public class tripbooked extends AppCompatActivity {
TextView t1;
    FirebaseAuth firebaseAuth;
    FirebaseFirestore firebaseFirestore;
    FirebaseUser firebaseUser;
    String um;
 ListView lv;
    List<String> list=new ArrayList<>();
    DocumentReference documentReference,documentReference1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tripbooked);
        lv=(ListView)findViewById(R.id.triplist);
        firebaseAuth= FirebaseAuth.getInstance();
        firebaseUser=FirebaseAuth.getInstance().getCurrentUser();
        firebaseFirestore= FirebaseFirestore.getInstance();
        um=firebaseUser.getUid();

        documentReference=firebaseFirestore.collection("booked").document(um);
        documentReference.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot documentSnapshot = task.getResult();
                    String cnt=documentSnapshot.getString("bookcnt");


                    if(Integer.parseInt(cnt)==1) {
                        Toast.makeText(tripbooked.this, "no bookings", Toast.LENGTH_SHORT).show();
                    }
                    else {
                    for (int i = 1; i < Integer.parseInt(cnt) ; i++) {


                        list.add("trip" + String.valueOf(i));

                        Log.d("tag22",list.toString());
                    }
                        ArrayAdapter arrayAdapter  = new ArrayAdapter(tripbooked.this,android.R.layout.simple_list_item_1,list);
                        lv.setAdapter(arrayAdapter);


                    }




                }
                else {

                }
            }
        });




        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                    TextView vv=(TextView)view;
                String selectedFromList = (String) lv.getItemAtPosition(i);


documentReference1=firebaseFirestore.collection("booked").document(um).collection("trips").document(selectedFromList);
documentReference1.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
    @Override
    public void onComplete(@NonNull Task<DocumentSnapshot> task) {

        if (task.isSuccessful()){

           DocumentSnapshot documentSnapshot=task.getResult();
           String s=documentSnapshot.getString("names");

            final Dialog dialog=new Dialog(tripbooked.this);
            dialog.setContentView(R.layout.paymentcus);
            dialog.setCancelable(true);
            TextView tv1=(TextView)dialog.findViewById(R.id.tripname);
            tv1.setText(s);
            dialog.show();
        }
        else{
            Toast.makeText(tripbooked.this, "no trip", Toast.LENGTH_SHORT).show();
        }

    }
});


            }
        });

    }
}

package com.example.kolip.swift;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ImageButton;
import android.widget.RadioGroup;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.List;

public class seatlayout extends AppCompatActivity  {


    ToggleButton t1,t2,t3,t4,t5,t6,t7,t8;
    List<String> seats=new ArrayList<>();
    FirebaseFirestore firebaseFirestore;
    FirebaseUser firebaseUser;
    FirebaseAuth firebaseAuth;
    String uid;
    DocumentReference documentReference;
    String cnt,dummy;
    ImageButton ib1;
    TextView tv1;
    Button b1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_seatlayout);

        t1=(ToggleButton)findViewById(R.id.tb1);
        t2=(ToggleButton)findViewById(R.id.tb2);
        t3=(ToggleButton)findViewById(R.id.tb3);
        t4=(ToggleButton)findViewById(R.id.tb4);
        t5=(ToggleButton)findViewById(R.id.tb5);
        t6=(ToggleButton)findViewById(R.id.tb6);
        t7=(ToggleButton)findViewById(R.id.tb7);
        t8=(ToggleButton)findViewById(R.id.tb8);
        ib1=(ImageButton)findViewById(R.id.checkit);
        tv1=(TextView)findViewById(R.id.seatcheck);
        b1=(Button)findViewById(R.id.topaym);

        firebaseUser=FirebaseAuth.getInstance().getCurrentUser();
        firebaseFirestore=FirebaseFirestore.getInstance();
uid=firebaseUser.getUid();

documentReference=firebaseFirestore.collection("tempbooked").document(uid);
documentReference.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
    @Override
    public void onComplete(@NonNull Task<DocumentSnapshot> task) {

        if (task.isSuccessful()){
            DocumentSnapshot documentSnapshot=task.getResult();
            cnt=documentSnapshot.getString("totalno");

        }


    }
});

        t1.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b==true){
                    seats.add("s1");
                    dummy="ok";
                }
                else if (b==false){
                    seats.remove("s1");
                    dummy="";

                }
            }
        });


        t2.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b==true){
                    seats.add("s2");
                    dummy="ok";

                }
                else if (b==false){
                    seats.remove("s2");
                    dummy="";
                }
            }
        });



        t3.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b==true){
                    seats.add("s3");
                    dummy="ok";
                }
                else if (b==false){
                    seats.remove("s3");
                    dummy="";
                }
            }
        });



        t4.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b==true){
                    seats.add("s4");
                    dummy="ok";
                }
                else if (b==false){
                    seats.remove("s4");
                    dummy="";
                }
            }
        });




        t5.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b==true){
                    seats.add("s5");
                    dummy="ok";
                }
                else if (b==false){
                    seats.remove("s5");
                    dummy="";
                }
            }
        });




        t6.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b==true){
                    seats.add("s6");
                    dummy="ok";
                }
                else if (b==false){
                    seats.remove("s6");
                    dummy="";
                }
            }
        });


        t7.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b==true){
                    seats.add("s7");
                    dummy="ok";
                }
                else if (b==false){
                    seats.remove("s7");
                    dummy="";
                }
            }
        });


        t8.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b==true){
                    seats.add("s8");
                    dummy="ok";
                }
                else if (b==false){
                    seats.remove("s8");
                    dummy="";
                }
            }
        });

ib1.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View view) {
        if (seats.size()>Integer.parseInt(cnt)){
            tv1.setText("you have selected more than"+" "+cnt+" "+"seats");
        }
        else {
            tv1.setText(String.valueOf( seats));
        }
    }
});

b1.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View view) {
        if (TextUtils.isEmpty(dummy)){
            Toast.makeText(seatlayout.this, "seats are not selected", Toast.LENGTH_SHORT).show();
        }
        else if (seats.size()>Integer.parseInt(cnt)){
            Toast.makeText(seatlayout.this,"you have selected more than"+" "+cnt+" "+"seats" , Toast.LENGTH_SHORT).show();
        }

        else if (seats.size()<Integer.parseInt(cnt)){
            Toast.makeText(seatlayout.this,"you have selected less than"+" "+cnt+" "+"seats" , Toast.LENGTH_SHORT).show();
        }


        else {
            Intent intent=new Intent(seatlayout.this,paymentoption.class);
            startActivity(intent);
        }
    }
});


    }
}

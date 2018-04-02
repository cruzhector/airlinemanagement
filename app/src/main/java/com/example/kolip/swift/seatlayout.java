package com.example.kolip.swift;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
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


    ToggleButton t1,t2,t3,t4,t5,t6,t7,t8,t9,t10,t11,t12,t13,t14,t15,t16;
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
    ProgressDialog progressDialog;

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
        t9=(ToggleButton)findViewById(R.id.tb9);
        t10=(ToggleButton)findViewById(R.id.tb10);
        t11=(ToggleButton)findViewById(R.id.tb11);
        t12=(ToggleButton)findViewById(R.id.tb12);
        t13=(ToggleButton)findViewById(R.id.tb13);
        t14=(ToggleButton)findViewById(R.id.tb14);
        t15=(ToggleButton)findViewById(R.id.tb15);
        t16=(ToggleButton)findViewById(R.id.tb16);
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


        t9.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b==true){
                    seats.add("s9");
                    dummy="ok";
                }
                else if (b==false){
                    seats.remove("s9");
                    dummy="";

                }
            }
        });


        t10.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b==true){
                    seats.add("s10");
                    dummy="ok";

                }
                else if (b==false){
                    seats.remove("s10");
                    dummy="";
                }
            }
        });



        t11.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b==true){
                    seats.add("s11");
                    dummy="ok";
                }
                else if (b==false){
                    seats.remove("s11");
                    dummy="";
                }
            }
        });



        t12.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b==true){
                    seats.add("s12");
                    dummy="ok";
                }
                else if (b==false){
                    seats.remove("s12");
                    dummy="";
                }
            }
        });




        t13.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b==true){
                    seats.add("s13");
                    dummy="ok";
                }
                else if (b==false){
                    seats.remove("s13");
                    dummy="";
                }
            }
        });




        t14.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b==true){
                    seats.add("s14");
                    dummy="ok";
                }
                else if (b==false){
                    seats.remove("s14");
                    dummy="";
                }
            }
        });


        t15.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b==true){
                    seats.add("s15");
                    dummy="ok";
                }
                else if (b==false){
                    seats.remove("s15");
                    dummy="";
                }
            }
        });


        t16.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b==true){
                    seats.add("s16");
                    dummy="ok";
                }
                else if (b==false){
                    seats.remove("s16");
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
        else if (seats.size()<Integer.parseInt(cnt)){

            Toast.makeText(seatlayout.this,"you have selected less than"+" "+cnt+" "+"seats" , Toast.LENGTH_SHORT).show();
        }
        else {
            tv1.setText(String.valueOf( seats));
        }
    }
});

b1.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View view) {
        progressDialog = ProgressDialog.show(seatlayout.this, "", "please wait", true);

        if (seats.size()>Integer.parseInt(cnt)){
            progressDialog.dismiss();
            Toast.makeText(seatlayout.this,"you have selected more than"+" "+cnt+" "+"seats" , Toast.LENGTH_SHORT).show();
        }

        else if (seats.size()<Integer.parseInt(cnt)){
            progressDialog.dismiss();
            Toast.makeText(seatlayout.this,"you have selected less than"+" "+cnt+" "+"seats" , Toast.LENGTH_SHORT).show();
        }


        else {
            progressDialog.dismiss();
            Intent intent=new Intent(seatlayout.this,paymentoption.class);
            startActivity(intent);
        }
    }
});


    }
}

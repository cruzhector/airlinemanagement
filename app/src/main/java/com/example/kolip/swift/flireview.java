package com.example.kolip.swift;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
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
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class flireview extends AppCompatActivity {
Button button,b1;
TextView t1,t2,t3,t4,t5,t6,t7,t8,t9,t10,t11,t12,t13;
EditText e;
FirebaseFirestore firebaseFirestore;
FirebaseUser firebaseUser;
public String ui;
CardView c1;
int scnt;
    String cos;
DocumentReference documentReference,documentReference1,documentReference2,documentReference3;
public String comp,nam,dt,tm1,tm2,fr,dptcity,dstcity;
private int cost;
String p1,p2,p3,savecnt;
    String p4;
ProgressDialog progressDialog;
ImageButton i;
String fun2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_flireview);
        button = (Button)findViewById(R.id.but);
        b1=(Button)findViewById(R.id.butsav);
        i = (ImageButton)findViewById(R.id.edit);
        c1 = (CardView)findViewById(R.id.policy);
        t1 = (TextView)findViewById(R.id.dd1);
        t2 = (TextView)findViewById(R.id.revflicomp);
        t3 = (TextView)findViewById(R.id.revfliname);
        t4 = (TextView)findViewById(R.id.d1);
        t5 = (TextView)findViewById(R.id.d2);
        t6 = (TextView)findViewById(R.id.tm1);
        t7 = (TextView)findViewById(R.id.tm2);
        t8 = (TextView)findViewById(R.id.fare);
        t9 = (TextView)findViewById(R.id.apply);
        t10 = (TextView)findViewById(R.id.dptcity1);
        t11 = (TextView)findViewById(R.id.dstcity1);
        t12 = (TextView)findViewById(R.id.city1);
        t13 = (TextView)findViewById(R.id.city2);
        e = (EditText)findViewById(R.id.promo);
        ImageView imageView=(ImageView)findViewById(R.id.imv1);
        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        ui = firebaseUser.getUid();
        firebaseFirestore=FirebaseFirestore.getInstance();

        Bundle extras = getIntent().getExtras();
        fun2=extras.getString("fun");
        if (fun2.equals("return")){
            imageView.setVisibility(View.VISIBLE);
        }
        else {
            imageView.setVisibility(View.GONE);
        }


        documentReference = firebaseFirestore.collection("tempbooked").document(ui);
        documentReference.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot documentSnapshot = task.getResult();
                    comp = documentSnapshot.getString("comname");
                    nam = documentSnapshot.getString("flightname");
                    dt = documentSnapshot.getString("deptdate");
                    tm1 = documentSnapshot.getString("deptime");
                    tm2 = documentSnapshot.getString("arrtime");
                    fr = documentSnapshot.getString("cost");
                    dptcity=documentSnapshot.getString("deptcity");
                    dstcity=documentSnapshot.getString("arrcity");
                    t1.setText(dt);
                    t2.setText(comp);
                    t3.setText("(" + nam + ")");
                    t4.setText(dt);
                    t5.setText(dt);
                    t6.setText(tm1);
                    t7.setText(tm2);
                    t8.setText(fr);
                    t10.setText(dptcity);
                    t11.setText(dstcity);
                    t12.setText(dptcity);
                    t13.setText(dstcity);

                }


            }
        });


        documentReference1=firebaseFirestore.collection("promos").document("codes");
        documentReference1.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {

                DocumentSnapshot documentSnapshot = task.getResult();

                p1=documentSnapshot.getString("promo1");
                p2=documentSnapshot.getString("promo2");
                p3=documentSnapshot.getString("promo3");
                p4=documentSnapshot.getString("promo4");
            }
        });
        documentReference2=firebaseFirestore.collection("savedbooking").document(ui);
        documentReference2.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {

                DocumentSnapshot documentSnapshot=task.getResult();
                savecnt=documentSnapshot.getString("savecnt");

                try{
                    scnt=Integer.parseInt(savecnt);
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        });



        c1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(flireview.this,cancellation.class);
                startActivity(intent);
            }
        });

        t9.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
             cos= e.getText().toString().trim();
                cost=Integer.parseInt(fr);
                if (TextUtils.isEmpty(cos)){
                    e.setError("enter promo");
                }
                else if(cos.equals(p1)){
                    cost=cost-750;
                    t8.setText(String.valueOf(cost));
                    e.setEnabled(false);
                    i.setVisibility(View.VISIBLE);
                }
                else if(cos.equals(p2)){
                    cost=cost-((cost*25)/100);
                    t8.setText(String.valueOf(cost));
                    e.setEnabled(false);
                    i.setVisibility(View.VISIBLE);
                }
                else if(cos.equals(p3)){
                    cost=cost-((cost*40)/100);
                    t8.setText(String.valueOf(cost));
                    e.setEnabled(false);
                    i.setVisibility(View.VISIBLE);
                }
                else if(cos.equals(p4)){
                    cost=cost-100;
                    t8.setText(String.valueOf(cost));
                    e.setEnabled(false);
                    i.setVisibility(View.VISIBLE);
                }
                else {
                    Toast.makeText(flireview.this, "no such promo exists", Toast.LENGTH_SHORT).show();
                    }
            }
        });
        i.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                e.setEnabled(true);
                e.getText().clear();
                t8.setText(fr);
            }
        });

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                progressDialog = ProgressDialog.show(flireview.this, "", "please wait", true);
                String s= t8.getText().toString().trim();
                Intent intent1 = getIntent();
                final Map<String, Object> hashMap5 = (HashMap<String, Object>) intent1.getSerializableExtra("cost");
                hashMap5.put("cost", s);

                firebaseFirestore.collection("tempbooked").document(ui).set(hashMap5).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            progressDialog.dismiss();
                            Intent intent = new Intent(flireview.this, persondetails.class);
                            startActivity(intent);

                        }
                    }
                });

            }
        });
    isapplied();

    b1.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            scnt++;
            documentReference3=firebaseFirestore.collection("savedbooking").document(ui);
            documentReference3.update("savecnt",String.valueOf(scnt));

        save();
        }
    });
    }

   private Boolean isapplied(){

        if (!(TextUtils.isEmpty(cos))){
            e.setEnabled(false);
            return true;
}
                return false;
   }


   public void save(){

       HashMap<String,Object> hashMap=new HashMap<String, Object>();
       hashMap.put("comname",comp);
       hashMap.put("flightname",nam);
       hashMap.put("deptdate",dt);
       hashMap.put("deptime",tm1);
       hashMap.put("arrtime",tm2);
       hashMap.put("cost",fr);
       hashMap.put("deptcity",dptcity);
       hashMap.put("arrcity",dstcity);

       firebaseFirestore.collection("savedbooking").document(ui).collection("saved").document(String.valueOf(scnt)).set(hashMap).addOnCompleteListener(new OnCompleteListener<Void>() {
           @Override
           public void onComplete(@NonNull Task<Void> task) {
          if (task.isSuccessful());
               Toast.makeText(flireview.this, "Booking Saved", Toast.LENGTH_SHORT).show();
               Toast.makeText(flireview.this, "Applied promo may not be applied", Toast.LENGTH_SHORT).show();
               b1.setVisibility(View.GONE);

           }
       });
   }

}



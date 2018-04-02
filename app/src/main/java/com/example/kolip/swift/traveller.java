package com.example.kolip.swift;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.HashMap;

public class traveller extends AppCompatActivity {
    FirebaseAuth firebaseAuth;
    FirebaseUser firebaseUser;
    FirebaseFirestore firebaseFirestore;
    DocumentReference documentReference,documentReference1;

    EditText e1,e2;
    Button b;
    Bundle extras;
    String tem,tname,tlname,iname,check;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_traveller);
        firebaseAuth=FirebaseAuth.getInstance();
    firebaseUser=FirebaseAuth.getInstance().getCurrentUser();
        firebaseFirestore=FirebaseFirestore.getInstance();
    tem=firebaseUser.getUid();
    e1=(EditText)findViewById(R.id.tfname);
    e2=(EditText)findViewById(R.id.tlname);
        extras = getIntent().getExtras();
        b=(Button)findViewById(R.id.tstore);
        check=extras.getString("name").trim();
        check = check.replace(" ", "");


        documentReference1=firebaseFirestore.collection("tempbooked").document(tem).collection("travellers").document(check);
        documentReference1.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {

                    DocumentSnapshot documentSnapshot = task.getResult();
                    if (documentSnapshot.exists()) {
                        e1.setText(documentSnapshot.getString("fname"));
                        e2.setText(documentSnapshot.getString("lname"));
                    }
                    else {
                        e1.setText("");
                        e2.setText("");
                    }

                }
                else {
                    e1.setText("");
                    e2.setText("");
                }
            }
        });





        b.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            tname=e1.getText().toString().trim();
            tlname=e2.getText().toString().trim();
            iname=extras.getString("name").trim();
            iname = iname.replace(" ", "");
            if (TextUtils.isEmpty(tname)|| TextUtils.isEmpty(tlname)){
                Toast.makeText(traveller.this, "fields are empty", Toast.LENGTH_SHORT).show();
            }
            else {
                HashMap<String, Object> hashMap = new HashMap<String, Object>();
                hashMap.put("fname", tname);
                hashMap.put("lname", tlname);


                firebaseFirestore.collection("tempbooked").document(tem).collection("travellers").document(iname).set(hashMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {

                        if (task.isSuccessful()) {
                            Toast.makeText(traveller.this, "successfully added", Toast.LENGTH_SHORT).show();

                            documentReference=firebaseFirestore.collection("tempbooked").document(tem).collection("travellers").document(iname);
                            documentReference.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                                @Override
                                public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                                    DocumentSnapshot documentSnapshot=task.getResult();
                                    e1.setText(documentSnapshot.getString("fname"));
                                    e2.setText(documentSnapshot.getString("lname"));
                                }
                            });
                        }

                    }
                });

            }


        }
    });



    }



}

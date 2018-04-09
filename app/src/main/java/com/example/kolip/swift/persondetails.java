package com.example.kolip.swift;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
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

import java.util.ArrayList;
import java.util.List;

public class persondetails extends AppCompatActivity {
    ListView ls;
    EditText e1, e2;
    TextView t1;
    FirebaseAuth firebaseAuth;
    FirebaseUser firebaseUser;
    String email;
    String id, totcnt;
    traveller tr = new traveller();
    Button b;
    ProgressDialog progressDialog;

    List<String> list = new ArrayList<String>();
    NonScrollListView nonScrollListView;
    Number cn;
    CollectionReference collectionReference;
    FirebaseFirestore firebaseFirestore;
    DocumentReference documentReference, documentReference1, documentReference2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_persondetails);
        e1 = (EditText) findViewById(R.id.mnum);
        e2 = (EditText) findViewById(R.id.emid);
        t1 = (TextView) findViewById(R.id.fa);
//ls=(ListView)findViewById(R.id.details);
        b = (Button) findViewById(R.id.buttgo);
        nonScrollListView = (NonScrollListView) findViewById(R.id.nlist);
        firebaseAuth = FirebaseAuth.getInstance();
        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        email = firebaseUser.getEmail();
        id = firebaseUser.getUid();
        firebaseFirestore = FirebaseFirestore.getInstance();


        documentReference1 = firebaseFirestore.collection("tempbooked").document(id);
        documentReference1.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot documentSnapshot = task.getResult();
                    String s = documentSnapshot.getString("cost");
                    totcnt = documentSnapshot.getString("totalno");

                    t1.setText(s);
                    Log.d("count", totcnt);


                    for (int i = 1; i < Integer.parseInt(totcnt) + 1; i++) {


                        list.add("passenger" + " " + String.valueOf(i));


                    }


                } else {
                    Toast.makeText(persondetails.this, "cannot", Toast.LENGTH_SHORT).show();
                }
            }
        });

        ArrayAdapter arrayAdapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, list);
        nonScrollListView.setAdapter(arrayAdapter);

        nonScrollListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                TextView t = (TextView) view;
                String stname = t.getText().toString().trim();
                Intent intent = new Intent(persondetails.this, traveller.class);
                intent.putExtra("name", stname);
                startActivity(intent);
            }
        });


        documentReference = firebaseFirestore.collection("users").document(id);
        documentReference.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {

                DocumentSnapshot documentSnapshot = task.getResult();
                String s = documentSnapshot.getString("mobile");
                e1.setText(s);
                e2.setText(email);
            }
        });


        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                progressDialog = ProgressDialog.show(persondetails.this, "", "please wait", true);


                firebaseFirestore.collection("tempbooked").document(id).collection("travellers").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {

                        if (task.isSuccessful()) {
                            QuerySnapshot documentSnapshot = task.getResult();
                            Log.d("tag", String.valueOf(documentSnapshot.size()));
                            if (documentSnapshot.size() != list.size()) {
                                progressDialog.dismiss();
                                Toast.makeText(getApplicationContext(), "check passengers", Toast.LENGTH_SHORT).show();
                            } else {
                                progressDialog.dismiss();
                                Intent intent = new Intent(persondetails.this, seatlayout.class);
                                startActivity(intent);

                            }

                        }

                    }
                });


            }
        });

    }

}

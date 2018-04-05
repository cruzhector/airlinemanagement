package com.example.kolip.swift;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Build;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import io.grpc.internal.zzeo;

import static com.example.kolip.swift.login.procount;

public class profile extends AppCompatActivity {
    EditText name1, name2, phn, em, age;
    Spinner gndr;
    Button pro;
    int count = 0;
    static  String s1,s2,s3,s4,s5,spin,s6="",s7="";
    public String[] gr = {"male","female"};

    FirebaseUser firebaseUser;
    FirebaseFirestore firebaseFirestore;
    String uid,uemail;
    ProgressDialog progressDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);





        name1 = (EditText) findViewById(R.id.fname);
        name2 = (EditText) findViewById(R.id.lname);
        phn = (EditText) findViewById(R.id.mobile);
        em = (EditText) findViewById(R.id.email2);
        age = (EditText) findViewById(R.id.age);
        gndr = (Spinner) findViewById(R.id.gender);
        pro = (Button) findViewById(R.id.profile);



        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        uid = firebaseUser.getUid();
        uemail= firebaseUser.getEmail();
        em.setText(uemail);


        FirebaseFirestore rootRef = FirebaseFirestore.getInstance();
        CollectionReference allUsersRef = rootRef.collection("users");

        allUsersRef.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    for (DocumentSnapshot document : task.getResult()) {
                        String userName = document.getString("email");
                        if (userName.equals(uemail)) {
                            Toast.makeText(profile.this, "That username already exists.", Toast.LENGTH_SHORT).show();
                            Intent i = new Intent(profile.this,MainActivity.class);
                            startActivity(i);
                        } else {
                            Log.d("TAG", "onEvent: username does not exists");
                        }
                    }
                } else {
                    Log.d("TAG", "Error getting documents: ", task.getException());
                }
            }
        });


        ArrayAdapter arrayAdapter = new ArrayAdapter(this,android.R.layout.simple_spinner_dropdown_item,gr);
        gndr.setAdapter(arrayAdapter);



    firebaseFirestore= FirebaseFirestore.getInstance();

        pro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                s1=name1.getText().toString();
                s2=name2.getText().toString();
                s3 =phn.getText().toString();
                s4=em.getText().toString();
                s5=age.getText().toString();
                spin =gndr.getSelectedItem().toString();
                progressDialog = ProgressDialog.show(profile.this,"Please Wait ","While creating your profile",true);

            createprofile();

            }
        });


    }

    public void createprofile() {


        if (TextUtils.isEmpty(s1)) {
            progressDialog.dismiss();
            name1.setError("required");

        } else if (TextUtils.isEmpty(s2)) {
            progressDialog.dismiss();
            name2.setError("required");

        } else if (TextUtils.isEmpty(s3)) {
            progressDialog.dismiss();
            phn.setError("required");

        } else if (TextUtils.isEmpty(s4)) {
            progressDialog.dismiss();
            em.setError("required");

        } else if (TextUtils.isEmpty(s5)) {
            progressDialog.dismiss();
            age.setError("required");

        }

        else if (s3.length()!=10) {
            progressDialog.dismiss();
            phn.setError("error number");

        }
        else if(s3.charAt(0)!='7' && s3.charAt(0)!='8' && s3.charAt(0)!='9'){
            progressDialog.dismiss();
            Toast.makeText(this, "number should start with 7,8,9", Toast.LENGTH_SHORT).show();
        }


        else {

            final profile1 p1 = new profile1(s1, s2, s3, s4, s5, spin,s6,s7);


            firebaseFirestore.collection("users").document(uid).set(p1).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {

                    if (task.isSuccessful()) {
                        Toast.makeText(profile.this, "successfully created", Toast.LENGTH_SHORT).show();
                        HashMap<String, Object> hashMap=new HashMap<String ,Object>();
                        hashMap.put("savecnt",String.valueOf(0));

                        firebaseFirestore.collection("savedbooking").document(uid).set(hashMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if (task.isSuccessful()) {

                                    HashMap<String, Object> hash=new HashMap<String ,Object>();
                                    hash.put("bookcnt",String.valueOf(1));
                                firebaseFirestore.collection("booked").document(uid).set(hash).addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {

                                        if (task.isSuccessful()){
                                            progressDialog.dismiss();
                                            Intent intent = new Intent(profile.this, MainActivity.class);
                                            startActivity(intent);
                                            finish();

                                        }
                                    }
                                });


                                }

                                }
                        });


                    } else {
                        Toast.makeText(profile.this, "unsuccess", Toast.LENGTH_SHORT).show();
                    }
                }
            });


        }


    }


    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    @Override
    public void onBackPressed() {
        count++;
        if (count == 1) {
            Toast.makeText(this, "Press once again to exit", Toast.LENGTH_SHORT).show();
        }

        if (count > 1) {
            finishAffinity();
        }
    }


    public class profile1{


        private String s1,s2,s3,s4,s5,spin,s6,s7;

        public profile1(){}

       public profile1(String s1, String s2, String s3, String s4, String s5, String spin , String s6, String s7) {
           this.s1 = s1;
           this.s2 = s2;
           this.s3 = s3;
           this.s4 = s4;
           this.s5 = s5;
           this.spin = spin;
           this.s6 = s6;
           this.s7 = s7;
        }


        public String getfname() {
            return s1;
        }

        public String getlname() {
            return s2;
        }

        public String getmobile() {
            return s3;
        }

        public String getemail() {
            return s4;
        }

        public String getage() {
            return s5;
        }

        public String getgender(){
            return spin;
        }

        public String getkyc(){
           return s6;
       }

        public String getaadharname(){
           return s7;
       }

    }
//
//    public class profile2{
//
//
//        private String s1;
//        private profile1 p1;
//
//        public profile2(profile1 p1, String s1) {
//            this.s1 = s1;
//            this.p1 = p1;
//        }
//
//    }
}


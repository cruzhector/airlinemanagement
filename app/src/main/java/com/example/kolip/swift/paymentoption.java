package com.example.kolip.swift;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class paymentoption extends AppCompatActivity {

    TextView t1,t2,t3,t4,t5;
    DocumentReference documentReference,documentReference1;
    FirebaseAuth firebaseAuth;
    FirebaseFirestore firebaseFirestore;
    FirebaseUser firebaseUser;
String um;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_paymentoption);

t1=(TextView)findViewById(R.id.namee);
t2 = (TextView)findViewById(R.id.dd1);
t3=(TextView)findViewById(R.id.faree1);
        t4 = (TextView)findViewById(R.id.dptcity11);
        t5=(TextView)findViewById(R.id.dstcity11);


firebaseAuth=FirebaseAuth.getInstance();
firebaseUser=FirebaseAuth.getInstance().getCurrentUser();
firebaseFirestore=FirebaseFirestore.getInstance();
um=firebaseUser.getUid();
documentReference=firebaseFirestore.collection("users").document(um);
documentReference.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
    @Override
    public void onComplete(@NonNull Task<DocumentSnapshot> task) {

        DocumentSnapshot documentSnapshot=  task.getResult();
        String nm = (String) documentSnapshot.get("fname");
        String nm1=documentSnapshot.getString("lname");
        t1.setText(nm + nm1);

    }
});
        documentReference1=firebaseFirestore.collection("tempbooked").document(um);
        documentReference1.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {

                DocumentSnapshot documentSnapshot=  task.getResult();
                String nm = (String) documentSnapshot.get("cost");
               String dt = documentSnapshot.getString("deptdate");
               String dptcity=documentSnapshot.getString("deptcity");
               String dstcity=documentSnapshot.getString("arrcity");
                t2.setText(dt);
                int f = Integer.parseInt(nm);
                f=f+150;
                t3.setText(String.valueOf(f));
                t4.setText(dptcity);
                t5.setText(dstcity);

            }
        });


    }
}

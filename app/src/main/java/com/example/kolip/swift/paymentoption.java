package com.example.kolip.swift;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
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

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Random;

public class paymentoption extends AppCompatActivity {

    TextView t1,t2,t3,t4,t5;
    EditText e1,e2,e3;
    DocumentReference documentReference,documentReference1;
    FirebaseAuth firebaseAuth;
    Button b1;
    FirebaseFirestore firebaseFirestore;
    FirebaseUser firebaseUser;
String um,card1,card2,card3;
ListView lv;
LinearLayout layout;
String [] methods={"credit card","debit card"};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_paymentoption);

        t1=(TextView)findViewById(R.id.namee);
        layout=(LinearLayout)findViewById(R.id.payments);
        t3=(TextView)findViewById(R.id.faree1);
        lv=(ListView)findViewById(R.id.paymethod);
        e1=(EditText)findViewById(R.id.cardno);
        e2=(EditText)findViewById(R.id.cardname);
        e3=(EditText)findViewById(R.id.cardcvv);
        b1=(Button)findViewById(R.id.goo);


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


                int f = Integer.parseInt(nm);
                f=f+150;
                t3.setText(String.valueOf(f));

            }
        });
        ArrayAdapter arrayAdapter  = new ArrayAdapter(this,android.R.layout.simple_list_item_1,methods);
        lv.setAdapter(arrayAdapter);

        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                layout.setVisibility(View.VISIBLE);
            }
        });


b1.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View view) {

        card1=e1.getText().toString().trim();
        card2=e2.getText().toString().trim();
        card3=e3.getText().toString().trim();

        if (TextUtils.isEmpty(card1)||TextUtils.isEmpty(card2)||TextUtils.isEmpty(card3)){
            Toast.makeText(paymentoption.this, "text fields are empty", Toast.LENGTH_SHORT).show();
        }
        else if(card1.length()!=16){
      e1.setError("not valid enter 16 digit");
        }
        else if (card3.length()!=3){
            e3.setError("not valid enter 3 digit");
        }
else {
            pnr();


        }
    }
});


    }

public void pnr() {
    char[] chars = "ABCDEFGHIJKLMNOPQRSTUVWXYZ".toCharArray();
    StringBuilder sb = new StringBuilder(6);
    Random random = new Random();
    for (int i = 0; i < 6; i++) {
        char cc = chars[random.nextInt(chars.length)];
        sb.append(cc);
    }
    String output = sb.toString();


    HashMap<String,Object>hashMap = new HashMap<String,Object>();
    hashMap.put("pnr",output);
      firebaseFirestore.collection("booked").document(um).set(hashMap).addOnCompleteListener(new OnCompleteListener<Void>() {
        @Override
        public void onComplete(@NonNull Task<Void> task) {

            if (task.isSuccessful()){
                Toast.makeText(paymentoption.this, "payment done check my trips", Toast.LENGTH_SHORT).show();
            }
        else {
                Toast.makeText(paymentoption.this, "booking failed", Toast.LENGTH_SHORT).show();
            }
        }
    });

    }
}

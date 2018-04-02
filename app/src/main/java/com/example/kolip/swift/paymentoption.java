package com.example.kolip.swift;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
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
import com.stripe.android.model.Card;
import com.stripe.android.view.CardInputWidget;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

public class paymentoption extends AppCompatActivity {

    TextView t1,t2,t3,t4,t5,t6;
    EditText e1,e2,e3;
    DocumentReference documentReference,documentReference1;
    FirebaseAuth firebaseAuth;
    Button b1;
    FirebaseFirestore firebaseFirestore;
    FirebaseUser firebaseUser;
    ProgressDialog progressDialog;

    CardInputWidget cardInputWidget;
    String um,crdnum,crdexp,crdcvv,crdmon,crdyr;
    String datesep[];
    String count;
    ArrayList<String> al=new ArrayList<>();
    String conc="20",yr;
LinearLayout layout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_paymentoption);

        t1=(TextView)findViewById(R.id.namee);
        t3=(TextView)findViewById(R.id.faree1);
        t6=(TextView)findViewById(R.id.flnamepay);


        cardInputWidget=(CardInputWidget) findViewById(R.id.cardwid);
        t2=(TextView)findViewById(R.id.et_card_number);
        t4=(TextView)findViewById(R.id.et_expiry_date);
        t5=(TextView)findViewById(R.id.et_cvc_number);

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
                count=documentSnapshot.getString("totalno");
                String flnm=documentSnapshot.getString("flightname");



                int f = Integer.parseInt(nm);
                f=f+150;
                t3.setText(String.valueOf(f));
                t6.setText(flnm);

            }
        });



b1.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View view) {

        progressDialog = ProgressDialog.show(paymentoption.this, "", "please wait", true);


        crdnum=t2.getText().toString().trim();
        crdexp=t4.getText().toString().trim();
        crdcvv=t5.getText().toString().trim();


//        Log.d("tag",crdnum);
//        Log.d("tag",crdexp);
//        Log.d("tag",crdcvv);
//        Log.d("tag",crdmon);
//        Log.d("tag",crdyr);
//        Log.d("tag",yr);





if (TextUtils.isEmpty(crdnum)|| TextUtils.isEmpty(crdexp)||TextUtils.isEmpty(crdcvv)){
    progressDialog.dismiss();
    Toast.makeText(paymentoption.this, "enter card detais", Toast.LENGTH_SHORT).show();
}
else {
    datesep=crdexp.split("/");
    crdmon=datesep[0];
    crdyr=datesep[1];
    yr=conc.concat(crdyr);

    cardvalidate();
}





    }
});


    }




  public void cardvalidate(){



      Card card =new Card(crdnum,Integer.valueOf(crdmon),Integer.valueOf(yr),crdcvv);

      if (!(card.validateNumber())){
          progressDialog.dismiss();
          Toast.makeText(paymentoption.this, "check your card", Toast.LENGTH_SHORT).show();
      }
     else if (!(card.validateExpiryDate())){
          progressDialog.dismiss();
          Toast.makeText(paymentoption.this, "check exp date", Toast.LENGTH_SHORT).show();
      }
     else if (!(card.validateCVC())){
          progressDialog.dismiss();
         Toast.makeText(paymentoption.this, "check your cvv", Toast.LENGTH_SHORT).show();
      }

      else {
          pnr();


          progressDialog.dismiss();
          Intent intent=new Intent(paymentoption.this,qrcode.class);
          intent.putExtra("pnrlist",al.toString());
          startActivity(intent);

      }

  }



public void pnr() {
    char[] chars = "ABCDEFGHIJKLMNOPQRSTUVWXYZ".toCharArray();


    for(int x = 0;x<Integer.parseInt(count);x++){
        Random random = new Random();
        StringBuilder sb = new StringBuilder(6);
        for (int i = 0; i < 6; i++) {
            char cc = chars[random.nextInt(chars.length)];
            sb.append(cc);

        }

        al.add(sb.toString());

    }
Log.d("tag",al.toString());


//    Toast.makeText(paymentoption.this, output, Toast.LENGTH_SHORT).show();
//    HashMap<String,Object>hashMap = new HashMap<String,Object>();
//    hashMap.put("pnr",output);
//      firebaseFirestore.collection("booked").document(um).set(hashMap).addOnCompleteListener(new OnCompleteListener<Void>() {
//        @Override
//        public void onComplete(@NonNull Task<Void> task) {
//
//            if (task.isSuccessful()){
//                Toast.makeText(paymentoption.this, "payment done check my trips", Toast.LENGTH_SHORT).show();
//            }
//        else {
//                Toast.makeText(paymentoption.this, "booking failed", Toast.LENGTH_SHORT).show();
//            }
//        }
//    });
//
//    }

}
}

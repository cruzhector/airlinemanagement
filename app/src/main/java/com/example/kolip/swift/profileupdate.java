package com.example.kolip.swift;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseException;
import com.google.firebase.FirebaseTooManyRequestsException;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.concurrent.TimeUnit;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class profileupdate extends AppCompatActivity {

    EditText e1,e2,e3,e4,e5,e6,e7;
    ImageButton im,im1,im2,im3,im4;
    TextView tt1,tt2;
    SwipeRefreshLayout swipeRefreshLayout;
    int c=0;
    FirebaseAuth firebaseAuth;
    ProgressDialog progressDialog;
DocumentReference documentReference,documentReference1,documentReference2;
FirebaseFirestore firebaseFirestore;
FirebaseUser firebaseUser;
    View parentLayout;
    PhoneAuthProvider phoneAuthProvider;
String fnmup,lnmup,phnmup,emnup,age,aadh,aadhnm;
String ui;
@SuppressLint("ResourceAsColor")
@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profileupdate);
    parentLayout = findViewById(android.R.id.content);
        e1 = (EditText)findViewById(R.id.fnameup);
        e2 = (EditText)findViewById(R.id.lnameup);
        e3 = (EditText)findViewById(R.id.dobup);
        e4 = (EditText)findViewById(R.id.emailup);
        e5 = (EditText)findViewById(R.id.phnup);
        e6 = (EditText)findViewById(R.id.aadhar);
    e7 = (EditText)findViewById(R.id.adharname);
        tt1=  (TextView)findViewById(R.id.updatepass);
        tt2 = (TextView)findViewById(R.id.deluser);
        im= (ImageButton)findViewById(R.id.upload);
    im1= (ImageButton)findViewById(R.id.err1);
    im2= (ImageButton)findViewById(R.id.ver1);
    im3 = (ImageButton)findViewById(R.id.err2);
    im4  = (ImageButton)findViewById(R.id.ver2);
    swipeRefreshLayout = (SwipeRefreshLayout)findViewById(R.id.swipe);
    swipeRefreshLayout.setColorSchemeColors(R.color.colorPrimaryDark);
    firebaseAuth = FirebaseAuth.getInstance();
    firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
    ui = firebaseUser.getUid();
phoneAuthProvider=PhoneAuthProvider.getInstance();
firebaseFirestore=FirebaseFirestore.getInstance();


//    PhoneAuthProvider.getInstance().verifyPhoneNumber(
//            ,        // Phone number to verify
//            60,                 // Timeout duration
//            TimeUnit.SECONDS,   // Unit of timeout
//            this               // Activity (for callback binding)
//            );        // OnVerificationStateChangedCallbacks
//    firebaseAuth.setLanguageCode("fr");







    documentReference1= firebaseFirestore.collection("users").document(ui);
    documentReference1.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
        @Override
        public void onComplete(@NonNull Task<DocumentSnapshot> task) {

            if (task.isSuccessful()){
                DocumentSnapshot documentSnapshot= task.getResult();
                fnmup=documentSnapshot.getString("fname");
               lnmup = documentSnapshot.getString("lname");
               emnup = documentSnapshot.getString("email");
               phnmup = documentSnapshot.getString("mobile");
               age = documentSnapshot.getString("age");
               aadh=documentSnapshot.getString("kyc");
               aadhnm = documentSnapshot.getString("aadharname");
               e1.setText(fnmup);
               e2.setText(lnmup);
               e3.setText(age);
               e4.setText(emnup);
               e5.setText(phnmup);
               e6.setText(aadh);
               e7.setText(aadhnm);
check();
            }

        }
    });

        im.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                progressDialog = ProgressDialog.show(profileupdate.this,"Please Wait ","While Updating",true);

    updatedata();

            }
        });


        tt1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent innn = new Intent(profileupdate.this,updatepass.class);
                startActivity(innn);


            }
        });

        tt2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                progressDialog = ProgressDialog.show(profileupdate.this,"Please Wait ","Deleting User",true);


                deleteuser();
            }
        });

        im1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                c++;
                if (c == 1) {
                    AlertDialog.Builder alert = new AlertDialog.Builder(profileupdate.this);
                    alert.setTitle("Verification");
                    alert.setMessage("press yes to send an email verification");
                    alert.setCancelable(true);
                    alert.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            sendverification();

                        }
                    })
                            .setNegativeButton("No", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {

                                    dialog.cancel();
                                }
                            });


                    AlertDialog alertDialog = alert.create();
                    alertDialog.show();
                }
                else

                {
                    Toast.makeText(profileupdate.this, "verification already sent to your email", Toast.LENGTH_SHORT).show();

                }


            }
            });

im3.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View view) {
        aadh = e6.getText().toString().trim();
        aadhar();

    }
});

    checkemail();
    swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
        @Override
        public void onRefresh() {

            finish();
            startActivity(getIntent());
            swipeRefreshLayout.setRefreshing(false);
            checkemail();
            check();
        }
    });

    }

public void updatedata(){
        documentReference=firebaseFirestore.collection("users").document(ui);
        documentReference.update("fname",e1.getText().toString());
        documentReference.update("lname",e2.getText().toString());
    documentReference.update("email",e4.getText().toString());
    documentReference.update("age",e3.getText().toString());
    documentReference.update("kyc",e6.getText().toString());
    documentReference.update("aadharname",e7.getText().toString());
    documentReference.update("mobile",e5.getText().toString()).addOnCompleteListener(new OnCompleteListener<Void>() {
        @Override
        public void onComplete(@NonNull Task<Void> task) {
            if (task.isSuccessful()){
                if(isNetworkAvailable()) {
                    Toast.makeText(profileupdate.this, "Successfully updated", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(profileupdate.this, MainActivity.class);
                    startActivity(intent);
                }
                else {
                    progressDialog.dismiss();
                    Snackbar.make(parentLayout,"No Network",Snackbar.LENGTH_SHORT).show();
                }
            }

        else {
                Toast.makeText(profileupdate.this, "Unsuccessfull", Toast.LENGTH_SHORT).show();
            }
        }
    });




}



public void deleteuser(){



    firebaseUser.delete().addOnCompleteListener(new OnCompleteListener<Void>() {
        @Override
        public void onComplete(@NonNull Task<Void> task) {
            progressDialog.dismiss();

            if (task.isSuccessful()){

                documentReference2=firebaseFirestore.collection("users").document(ui);
                documentReference2.delete().addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()){

                            Toast.makeText(profileupdate.this, "User deleted", Toast.LENGTH_SHORT).show();
                        }
                    }
                });

                Intent intent = new Intent(profileupdate.this,login.class);
                startActivity(intent);

            }
        }
    });



}


public void checkemail(){

    if(firebaseUser.isEmailVerified()){



im2.setVisibility(View.VISIBLE);

    }
else{
    im1.setVisibility(View.VISIBLE);
        Toast.makeText(this, "email is not verified", Toast.LENGTH_SHORT).show();
        Toast.makeText(this, "please re login to verify email for security purpose", Toast.LENGTH_LONG).show();
    }

}

public void aadhar(){

    if (isValidaadhar(aadh)){

        Snackbar.make(parentLayout,"please upload",Snackbar.LENGTH_SHORT).show();
    }
else {
        Toast.makeText(this, "not valid", Toast.LENGTH_SHORT).show();
        im3.setVisibility(View.VISIBLE);
    }
}


public void  sendverification(){

    firebaseUser.sendEmailVerification().addOnCompleteListener(new OnCompleteListener<Void>() {
        @Override
        public void onComplete(@NonNull Task<Void> task) {

            if (task.isSuccessful()){
                Toast.makeText(profileupdate.this, "email has been sent to registered email", Toast.LENGTH_SHORT).show();
            }




        }
    });


}


    private boolean isNetworkAvailable() {
        // Using ConnectivityManager to check for Network Connection
        ConnectivityManager connectivityManager = (ConnectivityManager) this
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager
                .getActiveNetworkInfo();
        return activeNetworkInfo != null;
    }

    private boolean isValidaadhar(String aadhar) {
        String adhar_PATTERN = "^[2-9]{1}[0-9]{11}$";
        Pattern pattern = Pattern.compile(adhar_PATTERN);
        Matcher matcher = pattern.matcher(aadhar);
        return matcher.matches();
    }


    public void check(){


        if (TextUtils.isEmpty(aadh)){
            Snackbar.make(parentLayout,"please enter your aadhar and press upload",Snackbar.LENGTH_LONG).show();
            im3.setVisibility(View.VISIBLE);

        }
        else if (TextUtils.isEmpty(aadhnm)){
            Snackbar.make(parentLayout,"please enter your aadhar and press upload",Snackbar.LENGTH_LONG).show();


        }
        else if (e6.getText().toString().equals(aadh)){
            im3.setVisibility(View.GONE);
            im4.setVisibility(View.VISIBLE);

        }
        else {

        }

    }
}

package com.example.kolip.swift;


import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Gallery;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;

import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class signup extends AppCompatActivity {
    EditText email,pass,cnfmpass;
    Button sgnup,log;
    private FirebaseAuth firebaseAuth;
    ProgressDialog progressDialog;
    View parentLayout;
    Gallery gallery;
    public  String mail,password,cnfmpassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        email=(EditText)findViewById(R.id.email);
        pass = (EditText)findViewById(R.id.password);
        parentLayout = findViewById(android.R.id.content);
        cnfmpass = (EditText)findViewById(R.id.cnfmpassword);

        sgnup = (Button)findViewById(R.id.busign);
        log = (Button)findViewById(R.id.login);
        gallery = (Gallery) findViewById(R.id.gallery);
        gallery.setAdapter(new Imageadapter(this));
        firebaseAuth= FirebaseAuth.getInstance();


    sgnup.setOnClickListener(new View.OnClickListener() {


        @Override
        public void onClick(View view) {
            progressDialog = ProgressDialog.show(signup.this,"Please Wait ","While Registering",true);

        register();


        }
    });

    log.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Intent intent= new Intent(signup.this,login.class);
            startActivity(intent);
        }
    });

    }




public void register(){
    mail = email.getText().toString().trim();
    password = pass.getText().toString().trim();
    cnfmpassword=cnfmpass.getText().toString().trim();
    if(!(isNetworkAvailable())){
        progressDialog.dismiss();
        Snackbar.make(parentLayout,"No Network",Snackbar.LENGTH_SHORT).show();
    }

   else if (!isValidEmail(mail)) {
        progressDialog.dismiss();
        email.setError("please enter your gmail account");
    }

    else if (TextUtils.isEmpty(mail)) {
        progressDialog.dismiss();
        email.setError( "Email is required!" );
    }
    else if(TextUtils.isEmpty(password)){
        progressDialog.dismiss();
        pass.setError( "Password!" );

    }
    else if (TextUtils.isEmpty(cnfmpassword)){
        progressDialog.dismiss();
        cnfmpass.setError( "Password!" );
    }
    else if(!(password.equals(cnfmpassword))){
        progressDialog.dismiss();
        Toast.makeText(this, "password mismatch", Toast.LENGTH_SHORT).show();
    }
    else {
        firebaseAuth.createUserWithEmailAndPassword(mail, password).addOnCompleteListener(signup.this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                progressDialog.dismiss();
                if (task.isSuccessful()) {
                    Intent intent = new Intent(signup.this,profile.class);
                    startActivity(intent);
                    finish();
                }
                else{
                    FirebaseAuthException e = (FirebaseAuthException) task.getException();
                    Toast.makeText(signup.this, "error" + " " + e.getMessage(), Toast.LENGTH_LONG).show();

                }


            }
        });
    }
}

    @Override
    protected void onStart() {
        if(!(isNetworkAvailable())){
            Snackbar.make(parentLayout,"No Network",Snackbar.LENGTH_LONG).show();
        }

    super.onStart();
    }

    private boolean isValidEmail(String email) {
        String EMAIL_PATTERN = "^[a-z0-9](\\.?[a-z0-9]){5,}@g(oogle)?mail\\.com$";

        Pattern pattern = Pattern.compile(EMAIL_PATTERN);
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }

    private boolean isNetworkAvailable() {
        // Using ConnectivityManager to check for Network Connection
        ConnectivityManager connectivityManager = (ConnectivityManager) this
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager
                .getActiveNetworkInfo();
        return activeNetworkInfo != null;
    }
}

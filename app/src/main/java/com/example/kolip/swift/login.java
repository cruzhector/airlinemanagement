package com.example.kolip.swift;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONStringer;
import org.w3c.dom.Text;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class login extends AppCompatActivity {
    Button b5,b3;
    TextView t1;
    EditText e1,e2;
    View parentLayout;
    CardView c11;


    public  static  int procount = 0;
    GoogleApiClient googleApiClient;
    private static final int x=123;
    private FirebaseAuth firebaseAuth;
    private CollectionReference documentReference;
    private FirebaseAuth.AuthStateListener authStateListener;
    private ProgressDialog progressDialog;

    private FirebaseUser firebaseUser;
    private FirebaseFirestore firebaseFirestore;
    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        b3 = (Button) findViewById(R.id.signup);
        b5 = (Button)findViewById(R.id.b);
        e1 = (EditText)findViewById(R.id.email1);
        e2 = (EditText)findViewById(R.id.password1);
        t1 = (TextView)findViewById(R.id.forgotpass);
        c11= (CardView)findViewById(R.id.gsign);
        parentLayout = findViewById(android.R.id.content);
        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();

        firebaseAuth = FirebaseAuth.getInstance();

         GoogleSignInOptions googleSignInOptions = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
        .requestIdToken(getString(R.string.default_web_client_id))
        .requestEmail()
        .build();

         googleApiClient = new GoogleApiClient.Builder(getApplicationContext()).enableAutoManage(this, new GoogleApiClient.OnConnectionFailedListener() {
            @Override
            public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
                Log.d("tag","failed");
            }
        }).addApi(Auth.GOOGLE_SIGN_IN_API,googleSignInOptions)
                .build();






        authStateListener = new FirebaseAuth.AuthStateListener() {
           @Override
           public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {

               if (firebaseAuth.getCurrentUser()!=null ){
                   Intent inten = new Intent(login.this, MainActivity.class);
                   startActivity(inten);
               }

           }
       };


       b3.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {
               if(!(isNetworkAvailable())){
                   Snackbar.make(parentLayout,"No Network",Snackbar.LENGTH_SHORT).show();
               }
else {
                   Intent i4 = new Intent(login.this, signup.class);
                   startActivity(i4);
               }
           }
       });

        b5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                progressDialog = ProgressDialog.show(login.this,"Please Wait ","While Logging in",true);

            startsignin();


            }
        });

        t1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                reset();
            }
        });

    c11.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            procount++;
            progressDialog = ProgressDialog.show(login.this,"Please Wait ","While Logging in",true);
            gsignin();
        }
    });

    }

    protected void onStart() {



        firebaseAuth.addAuthStateListener(authStateListener);
        if(!(isNetworkAvailable())){
            Snackbar.make(parentLayout,"No Network",Snackbar.LENGTH_LONG).show();
        }
        super.onStart();
    }

    public void startsignin(){
        String x1 = e1.getText().toString();
        String x2 = e2.getText().toString();

        if(!(isNetworkAvailable())){
            progressDialog.dismiss();
            Snackbar.make(parentLayout,"No Network",Snackbar.LENGTH_SHORT).show();
        }

       else if (!isValidEmail(x1)) {
            progressDialog.dismiss();
            e1.setError("please enter your gmail account");
        }

        else if (TextUtils.isEmpty(x1)) {
            progressDialog.dismiss();
            e1.setError( "Email is required!" );
        }
      else  if(TextUtils.isEmpty(x2)){
            progressDialog.dismiss();
            e2.setError( "Password!" );

        }
        else {
            firebaseAuth.signInWithEmailAndPassword(x1, x2).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    progressDialog.dismiss();
                    if (task.isSuccessful()) {

                                Intent inn = new Intent(login.this, MainActivity.class);
                                startActivity(inn);
                                finish();


                    }
                    else{
                        Toast.makeText(login.this, "No Record Has Found Please Signup ", Toast.LENGTH_LONG).show();
                    }
                }
            });





        }
    }

    public void reset() {

        String x2 = e1.getText().toString();
        if(!(isNetworkAvailable())){
            Snackbar.make(parentLayout,"No Network",Snackbar.LENGTH_SHORT).show();
        }

       else if (TextUtils.isEmpty(x2)) {
            e1.setError( "Email is required!" );

        } else {
            firebaseAuth.sendPasswordResetEmail(x2).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    if (task.isSuccessful()) {
                        Toast.makeText(login.this, "email has been sent to your registered acount", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(login.this, "unsuccessfull", Toast.LENGTH_SHORT).show();
                    }

                }
            });
        }
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

 public void gsignin(){

        Intent gsign = Auth.GoogleSignInApi.getSignInIntent(googleApiClient);
        startActivityForResult(gsign,x);

 }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

 if (requestCode==x){
     GoogleSignInResult googleSignInResult = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
     if (googleSignInResult.isSuccess()){

         GoogleSignInAccount googleSignInAccount = googleSignInResult.getSignInAccount();
        firbasewithgoogle(googleSignInAccount);

         }

else {
         Toast.makeText(this, "not", Toast.LENGTH_SHORT).show();
     }
 }
 }

   private void firbasewithgoogle(GoogleSignInAccount googleSignInAccount){
       AuthCredential authCredential= GoogleAuthProvider.getCredential(googleSignInAccount.getIdToken(),null);
       firebaseAuth.signInWithCredential(authCredential).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
           @Override
           public void onComplete(@NonNull Task<AuthResult> task) {
               progressDialog.dismiss();
               if (task.isSuccessful()){
                   Intent intent  = new Intent(login.this,profile.class);
                   startActivity(intent);
               }
           else {
                   Toast.makeText(login.this, "unsuccess", Toast.LENGTH_LONG).show();
               }

           }
       });

   }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    @Override
    public void onBackPressed() {
        finishAffinity();
    }
}




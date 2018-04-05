package com.example.kolip.swift;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.nfc.Tag;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
import com.stripe.android.model.Card;
import com.stripe.android.view.CardInputWidget;

import org.w3c.dom.Document;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;


public class MainActivity extends AppCompatActivity {
    FirebaseUser firebaseUser;
    String uid,uemail;
    NavigationView navigationView;
    DocumentReference documentReference,documentReference4;
    CollectionReference documentReference1;
    FirebaseFirestore firebaseFirestore;
    TextView t1,t2,hdname,emhead;
    View parentLayout;
    CardView cardView,svcard,cardView1;

    public String nn,ll;
    int count=0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

     parentLayout = findViewById(android.R.id.content);
        t1= (TextView)findViewById(R.id.name);
        cardView = (CardView) findViewById(R.id.card1);
        cardView1 = (CardView) findViewById(R.id.trips);

        svcard = (CardView) findViewById(R.id.svcard);
        t2 = (TextView)findViewById(R.id.head);
        navigationView = (NavigationView)findViewById(R.id.navigation);
        firebaseFirestore= FirebaseFirestore.getInstance();
        firebaseUser=FirebaseAuth.getInstance().getCurrentUser();
        uid= firebaseUser.getUid();
        uemail= firebaseUser.getEmail();
        View hView =  navigationView.inflateHeaderView(R.layout.header);



        documentReference= firebaseFirestore.collection("users").document(uid);
        documentReference.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {

                if (task.isSuccessful()) {
                    DocumentSnapshot documentSnapshot = task.getResult();
                    if (documentSnapshot.exists()) {
                        nn = documentSnapshot.getString("fname");
                        ll = documentSnapshot.getString("lname");
                        t1.setText("Hello" + " " + nn + " " + ll);
                        hdname = (TextView) findViewById(R.id.headname);
                        emhead = (TextView) findViewById(R.id.emailhead);
                        hdname.setText(nn + " " + ll);
                        emhead.setText("(" + uemail + ")");
                    }
                }
                else{
                    Toast.makeText(MainActivity.this, "please create profile", Toast.LENGTH_SHORT).show();
                }

            }
        });






cardView.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View view) {

        Intent flight = new Intent(MainActivity.this, flightbook.class);
        Intent intent = getIntent();
        final Map<String , Object> hashMap1 =(HashMap<String , Object>)  intent.getSerializableExtra("hash");
        flight.putExtra("hash1", (Serializable) hashMap1);
        startActivity(flight);

    }


});

svcard.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View view) {
        Intent intent = new Intent(MainActivity.this,saved.class);
        startActivity(intent);
    }
});
navigation();


cardView1.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View view) {
        Intent intent = new Intent(MainActivity.this,tripbooked.class);
        startActivity(intent);
    }
});

    }













    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    @Override
    public void onBackPressed() {
        count++;
        if (count==1){
            Toast.makeText(this, "Press once again to exit", Toast.LENGTH_SHORT).show();
        }

if (count>1){
    documentReference=firebaseFirestore.collection("tempbooked").document(uid);

    documentReference.delete().addOnCompleteListener(new OnCompleteListener<Void>() {
        @Override
        public void onComplete(@NonNull Task<Void> task) {
            if (task.isSuccessful()){
//                Toast.makeText(MainActivity.this, "You will lose data", Toast.LENGTH_SHORT).show();
                firebaseFirestore.collection("tempbooked").document(uid).collection("travellers").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {

                        if (task.isSuccessful()){
                            for (DocumentSnapshot documentSnapshot : task.getResult()){
                                documentReference4=firebaseFirestore.collection("tempbooked").document(uid).collection("travellers").document(documentSnapshot.getId());
                                documentReference4.delete().addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                   if (task.isSuccessful()){
                                       Log.d("tag","success");
                                   }
                                   else {
                                       Log.d("tag","success");

                                   }


                                    }
                                });
                            }
                        }

                    }
                });
            }

        }
    });



    finishAffinity();
}

    }

    @Override
    protected void onStart() {

        t2.setText("Welcome To swift!");
        if(!(isNetworkAvailable())){
            Snackbar.make(parentLayout,"No Network",Snackbar.LENGTH_LONG).setDuration(10000).show();
        }


        super.onStart();
    }

public void navigation(){




        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int id = item.getItemId();

             switch (id){
                 case R.id.logout:
                     if(isNetworkAvailable()) {
                         FirebaseAuth.getInstance().signOut();
                         Intent login = new Intent(MainActivity.this, login.class);
                         startActivity(login);
                     }
                      else {
                             Snackbar.make(parentLayout,"No Network",Snackbar.LENGTH_SHORT).setDuration(10000).show();
                         }
                      break;
                 case R.id.acc:
                     if(isNetworkAvailable()) {
                         Intent profchange = new Intent(MainActivity.this, profileupdate.class);
                         startActivity(profchange);
                     }

            else {
                         Snackbar.make(parentLayout,"No Network",Snackbar.LENGTH_SHORT).show();
                     }
                     break;

                 case R.id.check:
                     if(isNetworkAvailable()) {
//                     Intent proto = new Intent(MainActivity.this,prototype.class);
//                     startActivity(proto);
                     }

                     else {
                         Snackbar.make(parentLayout,"No Network",Snackbar.LENGTH_SHORT).show();
                     }
             break;

                 case R.id.book:
                     if(isNetworkAvailable()) {
                         Intent proto = new Intent(MainActivity.this,flightbook.class);
                         startActivity(proto);
                     }

                     else {
                         Snackbar.make(parentLayout,"No Network",Snackbar.LENGTH_SHORT).show();
                     }
                     break;
             }


                return false;
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


}


package com.example.kolip.swift;

import android.app.Dialog;
import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class saved extends AppCompatActivity {
ListView ls;
    FirebaseUser firebaseUser;
    Context context;
    FirebaseFirestore firebaseFirestore;
    String uid,clidepcity,cliarrcity,savdate;
    CollectionReference collectionReference1;
    private List<saveobjec> list=new ArrayList<saveobjec>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_saved);
ls=(ListView)findViewById(R.id.savedlist);
        firebaseFirestore = FirebaseFirestore.getInstance();
        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        uid = firebaseUser.getUid();
        context = this;

        firebaseFirestore.collection("savedbooking").document(uid).collection("saved").addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(QuerySnapshot documentSnapshots, FirebaseFirestoreException e) {

                for (DocumentChange doc : documentSnapshots.getDocumentChanges()){
                    String s  = doc.getDocument().getString("arrcity");
                    String s1 = doc.getDocument().getString("arrtime");
                    String s2 = doc.getDocument().getString("comname");
                    String s3 = doc.getDocument().getString("cost");
                    String s4 = doc.getDocument().getString("deptcity");
                    String s5 = doc.getDocument().getString("deptdate");
                    String s6 = doc.getDocument().getString("deptime");
                    String s7 = doc.getDocument().getString("flightname");
        list.add(new saveobjec(s,s1,s2,s3,s4,s5,s6,s7));


                }

            }
        });
        ls.setAdapter(new savedcusadapter(this,list));

        ls.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                saveobjec so=(saveobjec)adapterView.getItemAtPosition(i);
                clidepcity=so.getS4();
                cliarrcity=so.getS();
                savdate=so.getS5();

                Dialog dialog =new Dialog(saved.this);
                dialog.setContentView(R.layout.saveddialog);
                dialog.setCancelable(true);
                TextView tv1=(TextView)dialog.findViewById(R.id.dptcitysavdialog);
                TextView tv2=(TextView)dialog.findViewById(R.id.dstcitysavdialog);
                TextView tv3=(TextView)dialog.findViewById(R.id.ddsavdialog);
                tv1.setText(clidepcity);
                tv2.setText(cliarrcity);
                tv3.setText(savdate);
                dialog.show();
            }
        });

    }
public class saveobjec{

   private String s;
    private String s1;
    private String s2;
    private String s3;
    private String s4;
    private String s5;
    private String s6;
    private String s7;

    public saveobjec(String s, String s1, String s2, String s3, String s4, String s5, String s6, String s7) {
        this.s = s;
        this.s1 = s1;
        this.s2 = s2;
        this.s3 = s3;
        this.s4 = s4;
        this.s5 = s5;
        this.s6 = s6;
        this.s7 = s7;
    }

    public String getS()  {
        return s;
    }

    public String getS1() {
        return s1;
    }

    public String getS2() {
        return s2;
    }

    public String getS3() {
        return s3;
    }

    public String getS4() {
        return s4;
    }

    public String getS5() {
        return s5;
    }

    public String getS6() {
        return s6;
    }

    public String getS7() {
        return s7;
    }
}
}

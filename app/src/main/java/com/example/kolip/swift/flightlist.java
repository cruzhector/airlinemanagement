package com.example.kolip.swift;

import android.app.ProgressDialog;
import android.content.AsyncTaskLoader;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;

import org.w3c.dom.Document;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class flightlist extends AppCompatActivity {
ListView lv;
private List<myobjec> list=new ArrayList<myobjec>();


    FirebaseUser firebaseUser;
    Context context;
    ProgressBar progressBar;
    DocumentReference documentReference,documentReference1,documentReference2;
    DocumentReference collectionReference1;
public String lisdate,lisflcomp,lisflname,listm1,listm2;
    FirebaseFirestore firebaseFirestore;
  public   String uid,deptdt1,deptchld1,deptadlt1,cls1,emd1,dptcity,dstcity;
    TextView t1,t2,t3,t4,t5,t6;
    int c1,c2,c3;

    ProgressDialog progressDialog;
//    public static int[] img ={R.drawable.air,R.drawable.air,R.drawable.air,R.drawable.go,R.drawable.go,R.drawable.go,R.drawable.spice,R.drawable.spice,R.drawable.spice};
    private DocumentReference document;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_flightlist);
        firebaseFirestore = FirebaseFirestore.getInstance();
        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        uid = firebaseUser.getUid();
        emd1 = firebaseUser.getEmail();
        t1 = (TextView) findViewById(R.id.dptnodate);
        t2 = (TextView) findViewById(R.id.dptnochild);
        t3 = (TextView) findViewById(R.id.dptnoadult);
        t4 = (TextView) findViewById(R.id.deconcls);
        t5=(TextView)findViewById(R.id.dptcity);
        t6=(TextView)findViewById(R.id.dstcity);
        lv = (ListView) findViewById(R.id.flilist);
        progressBar=  (ProgressBar)findViewById(R.id.pro);
        context = this;





        documentReference = firebaseFirestore.collection("tempbooked").document(uid);
        documentReference.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {


            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    progressDialog = ProgressDialog.show(flightlist.this, "", "Fare alerts registering", true);

                    DocumentSnapshot documentSnapshot = task.getResult();
                    deptdt1 = documentSnapshot.getString("deptdate");
                    deptchld1 = documentSnapshot.getString("noofchld");
                    deptadlt1 = documentSnapshot.getString("noofadult");
                    cls1 = documentSnapshot.getString("class");
                    dptcity=documentSnapshot.getString("deptcity");
                    dstcity=documentSnapshot.getString("arrcity");

                    t1.setText(deptdt1);
                    t2.setText(deptchld1 + "children" + ",");
                    t3.setText(deptadlt1 + "adult" + ",");
                    t4.setText(cls1);
                    t5.setText(dptcity);
                    t6.setText(dstcity);

                }

                Bundle extras = getIntent().getExtras();
                c3 = extras.getInt("ncount");

                collectionReference1 = firebaseFirestore.collection("flights").document("airindia");

                collectionReference1.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                        if (task.isSuccessful()) {
                            DocumentSnapshot document = task.getResult();

                            if (document != null) {
                                fliret fl = task.getResult().toObject(fliret.class);
                                String s11 = (String) fl.getAIR319().get("fliname");
                                String s22 = (String) fl.getAIR367().get("fliname");
                                String s23 = (String) fl.getATR12().get("fliname");

                                String s12 = (String) fl.getAIR319().get("compname");
                                String s24 = (String) fl.getAIR367().get("compname");
                                String s25 = (String) fl.getATR12().get("compname");

                                String s31 = (String) fl.getAIR319().get("time1");
                                String s32 = (String) fl.getAIR367().get("time1");
                                String s33 = (String) fl.getATR12().get("time1");

                                String s41 = (String) fl.getAIR319().get("time2");
                                String s42 = (String) fl.getAIR367().get("time2");
                                String s43 = (String) fl.getATR12().get("time2");

                                String s51 = (String) fl.getAIR319().get("cost");
                                String s52 = (String) fl.getAIR367().get("cost");
                                String s53 = (String) fl.getATR12().get("cost");

                                int i1 = c3 * (Integer.parseInt(s51));
                                int i2 = c3 * (Integer.parseInt(s52));
                                int i3 = c3 * (Integer.parseInt(s53));

                                list.add(new myobjec(s11, s12, s31, s41, String.valueOf(i1), R.drawable.air));
                                list.add(new myobjec(s22, s24, s32, s42, String.valueOf(i2), R.drawable.air));
                                list.add(new myobjec(s23, s25, s33, s43, String.valueOf(i3), R.drawable.air));


                            } else {


                                Log.d("TAG", "No such document");
                            }

                        } else {
                            Log.d("TAG", "get failed with ", task.getException());

                        }
                    }
                });


                documentReference1 = firebaseFirestore.collection("flights").document("spicejet");
                documentReference1.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {

                        if (task.isSuccessful()) {
                            DocumentSnapshot document1 = task.getResult();
                            if (document1 != null) {
                                fliretspice flr = task.getResult().toObject(fliretspice.class);


                                String s111 = (String) flr.getSPC143().get("fliname");
                                String s222 = (String) flr.getSPBUS43().get("fliname");
                                String s233 = (String) flr.getSPI123().get("fliname");

                                String s121 = (String) flr.getSPC143().get("compname");
                                String s242 = (String) flr.getSPBUS43().get("compname");
                                String s253 = (String) flr.getSPI123().get("compname");

                                String s311 = (String) flr.getSPC143().get("time1");
                                String s322 = (String) flr.getSPBUS43().get("time1");
                                String s333 = (String) flr.getSPI123().get("time1");

                                String s411 = (String) flr.getSPC143().get("time2");
                                String s422 = (String) flr.getSPBUS43().get("time2");
                                String s433 = (String) flr.getSPI123().get("time2");

                                String s511 = (String) flr.getSPC143().get("cost");
                                String s522 = (String) flr.getSPBUS43().get("cost");
                                String s533 = (String) flr.getSPI123().get("cost");


                                int i11 = c3 * (Integer.parseInt(s511));
                                int i21 = c3 * (Integer.parseInt(s522));
                                int i31 = c3 * (Integer.parseInt(s533));


                                list.add(new myobjec(s111, s121, s311, s411, String.valueOf(i11), R.drawable.spice));
                                list.add(new myobjec(s222, s242, s322, s422, String.valueOf(i21), R.drawable.spice));
                                list.add(new myobjec(s233, s253, s333, s433, String.valueOf(i31), R.drawable.spice));


                            }

                        }


                    }
                });

                documentReference2 = firebaseFirestore.collection("flights").document("indigo");


                documentReference2.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {

                        if (task.isSuccessful()) {
                            DocumentSnapshot document1 = task.getResult();
                            if (document1 != null) {
                                fliretindigo flri = task.getResult().toObject(fliretindigo.class);


                                String s1111 = (String) flri.getIGO613().get("fliname");
                                String s2222 = (String) flri.getindigo1().get("fliname");
                                String s2333 = (String) flri.getindigo2().get("fliname");

                                String s1211 = (String) flri.getIGO613().get("compname");
                                String s2422 = (String) flri.getindigo1().get("compname");
                                String s2533 = (String) flri.getindigo2().get("compname");

                                String s3111 = (String) flri.getIGO613().get("time1");
                                String s3222 = (String) flri.getindigo1().get("time1");
                                String s3333 = (String) flri.getindigo2().get("time1");

                                String s4111 = (String) flri.getIGO613().get("time2");
                                String s4222 = (String) flri.getindigo1().get("time2");
                                String s4333 = (String) flri.getindigo2().get("time2");

                                String s5111 = (String) flri.getIGO613().get("cost");
                                String s5222 = (String) flri.getindigo1().get("cost");
                                String s5333 = (String) flri.getindigo2().get("cost");

                                int i111 = c3 * (Integer.parseInt(s5111));
                                int i211 = c3 * (Integer.parseInt(s5222));
                                int i311 = c3 * (Integer.parseInt(s5333));


                                list.add(new myobjec(s1111, s1211, s3111, s4111, String.valueOf(i111), R.drawable.go));
                                list.add(new myobjec(s2222, s2422, s3222, s4222, String.valueOf(i211), R.drawable.go));
                                list.add(new myobjec(s2333, s2533, s3333, s4333, String.valueOf(i311), R.drawable.go));

                                lv.setAdapter(new flightcusadapter(flightlist.this, list));
progressDialog.dismiss();
                            }

                        }


                    }
                });

                lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                        progressDialog = ProgressDialog.show(flightlist.this, "", "Brace Yourself", true);
                        myobjec clickobj = (myobjec) adapterView.getItemAtPosition(i);
                        lisflcomp = clickobj.getFlgtcomp();
                        lisflname = clickobj.getFlgtname();
                        listm1 = clickobj.getFlgttm();
                        listm2 = clickobj.getFlgtmm();
                        String cs = clickobj.getFlgtcost();
                        Intent hashint = getIntent();
                        final Map<String, Object> hashMap5 = (HashMap<String, Object>) hashint.getSerializableExtra("hashmaplist");

                        hashMap5.put("comname", lisflcomp);
                        hashMap5.put("flightname", lisflname);
                        hashMap5.put("deptime", listm1);
                        hashMap5.put("arrtime", listm2);
                        hashMap5.put("cost", cs);
                        firebaseFirestore.collection("tempbooked").document(uid).set(hashMap5).addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if (task.isSuccessful()) {
                                    progressDialog.dismiss();
                                    Intent intent = new Intent(flightlist.this, flireview.class);
                                    intent.putExtra("cost", (Serializable) hashMap5);
                                    startActivity(intent);


                                }
                            }
                        });


                    }
                });

            }
        });
    }

            public class myobjec {
                private String flgtname;
                private String flgtcomp;
                private String flgttm;
                private String flgtmm;
                private String flgtcost;
                private int img;


                public myobjec(String flgtname, String flgtcomp, String flgttm, String flgtmm, String flgtcost, int img) {
                    this.flgtname = flgtname;
                    this.flgtcomp = flgtcomp;
                    this.flgttm = flgttm;
                    this.flgtmm = flgtmm;
                    this.flgtcost = flgtcost;
                    this.img = img;
                }


                public String getFlgtname() {
                    return flgtname;
                }

                public String getFlgtcomp() {
                    return flgtcomp;
                }

                public String getFlgttm() {
                    return flgttm;
                }

                public String getFlgtmm() {
                    return flgtmm;
                }

                public String getFlgtcost() {
                    return flgtcost;
                }

                public int getImg() {
                    return img;
                }


            }


            @Override
            protected void onStart() {
                super.onStart();

            }
        }
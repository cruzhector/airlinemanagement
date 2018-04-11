package com.example.kolip.swift;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.annotation.NonNull;
import android.support.v4.app.NotificationManagerCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.HashMap;

public class qrcode extends AppCompatActivity {


    Button b1;
    EditText e1;
    ImageView i1, i2;
    String s,conc,nam,city1,city2,cost1,date,fliname1,seats;
    Bitmap bitmap;
    Bundle bundle;
    DocumentReference documentReference, documentReference1, documentReference2;
    String count, um;
    ProgressDialog progressDialog;
    HashMap<String,Object> hashMap;
    ArrayList<String> seatlist=new ArrayList<>();

    FirebaseFirestore firebaseFirestore;
    FirebaseUser firebaseUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_qrcode);
        progressDialog = ProgressDialog.show(qrcode.this, "Please wait", "While connecting to server", true);


        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        firebaseFirestore = FirebaseFirestore.getInstance();
        um = firebaseUser.getUid();


        documentReference1 = firebaseFirestore.collection("booked").document(um);
        documentReference1.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot documentSnapshot = task.getResult();
                    count = documentSnapshot.getString("bookcnt");

                    Log.d("tag11", count);

                    conc="trip".concat(count);
                    Intent intent = getIntent();
                    bundle = intent.getExtras();
                    s = bundle.getString("pnrlist");
                    nam=bundle.getString("namelist");
                    city1 = bundle.getString("dptcity");
                    city2=bundle.getString("dstcity");
                    date = bundle.getString("dptdate");
                    cost1=bundle.getString("cost");
                    fliname1=bundle.getString("fliname");
                    seats=bundle.getString("seats");
                    seatlist=bundle.getStringArrayList("seatlist");

                    String append=s+"\n"+nam+"\n"+seats;

                    Log.d("tag", append);
                    try{
                        bitmap=encode(append);

                        String x=Base64.encodeToString(getBytesFromBitmap(bitmap),Base64.NO_WRAP);
                        byte[] bytes=getBytesFromBitmap(bitmap);
                        hashMap=new HashMap<>();
                        hashMap.put("pnrbar",x);
                        hashMap.put("pnrs",s);
                        hashMap.put("names",nam);
                        hashMap.put("deptcity",city1);
                        hashMap.put("arrcity",city2);
                        hashMap.put("deptdate",date);
                        hashMap.put("totcost",cost1);
                        hashMap.put("fliname",fliname1);
                        hashMap.put("seats",seatlist);
                        hashMap.put("seatlist",seatlist.toString());



                        store();

                    }catch (WriterException e){
                        e.printStackTrace();
                    }

                } else {
                    Log.d("tag11", "error");
                }

            }
        });





            }




    Bitmap encode(String s1) throws WriterException{
        BitMatrix bitMatrix;

        try{
            bitMatrix = new MultiFormatWriter().encode(s1, BarcodeFormat.DATA_MATRIX.QR_CODE,500,500,null);



        }catch (IllegalArgumentException e){
            e.printStackTrace();
            return null;
        }

        int bitw=bitMatrix.getWidth();

        int bitl=bitMatrix.getHeight();

        int[]  pixels = new int[bitl*bitw];

        for (int y=0;y<bitl;y++){
            int off=    y*bitw;
            for (int x=0;x<bitw;x++){
                pixels[off + x]=bitMatrix.get(x,y) ? getResources().getColor(R.color.black) : getResources().getColor(R.color.white);
            }
        }
        Bitmap bitmap = Bitmap.createBitmap(bitw, bitl, Bitmap.Config.ARGB_4444);

        bitmap.setPixels(pixels, 0, 500, 0, 0, bitw, bitl );


        return bitmap;

    }

    public byte[] getBytesFromBitmap(Bitmap bitmap) {
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 70, stream);
        return stream.toByteArray();
    }


    public void store()
    {
     final NotificationManager   notificationManager=(NotificationManager)qrcode.this.getSystemService(Context.NOTIFICATION_SERVICE);


        firebaseFirestore.collection("booked").document(um).collection("trips").document(conc).set(hashMap).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {

                if (task.isSuccessful()) {



                    int z= Integer.parseInt(count)+1;

                    documentReference2=firebaseFirestore.collection("booked").document(um);
                    documentReference2.update("bookcnt",String.valueOf(z)).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()){





                                Notification.Builder builder=new Notification.Builder(qrcode.this);
                                builder.setAutoCancel(true);
                                builder.setContentTitle("Swift notification");
                                builder.setContentText("flight booked");
                                builder.setSmallIcon(R.drawable.ic_airplane);
                                Intent intent2=new Intent(qrcode.this,MainActivity.class);
                                PendingIntent pendingIntent=PendingIntent.getActivity(qrcode.this,2,intent2,PendingIntent.FLAG_UPDATE_CURRENT);
                                builder.setContentIntent(pendingIntent);
                                builder.build();
                               Notification notification=builder.getNotification();
                              notificationManager.notify(11,notification);



                                firebaseFirestore.collection("tempbooked").document(um).collection("travellers").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                    @Override
                                    public void onComplete(@NonNull Task<QuerySnapshot> task) {

                                        if (task.isSuccessful()){


                                            for (DocumentSnapshot documentSnapshot : task.getResult()){
                                               DocumentReference documentReference4=firebaseFirestore.collection("tempbooked").document(um).collection("travellers").document(documentSnapshot.getId());
                                                documentReference4.delete().addOnCompleteListener(new OnCompleteListener<Void>() {
                                                    @Override
                                                    public void onComplete(@NonNull Task<Void> task) {
                                                        if (task.isSuccessful()){
                                                            Log.d("tag","success");
                                                            progressDialog.dismiss();


                                                            Intent intent1 = new Intent(qrcode.this,MainActivity.class);
                                                            startActivity(intent1);
                                                            Toast.makeText(qrcode.this, "payment success", Toast.LENGTH_SHORT).show();
                                                            Toast.makeText(qrcode.this, "check my trips to review your booking", Toast.LENGTH_SHORT).show();



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
                            else {
                                Toast.makeText(qrcode.this, "unsuccessful", Toast.LENGTH_SHORT).show();
                            }

                        }
                    });

                }
                else {
                    Toast.makeText(qrcode.this, "unsuccessful", Toast.LENGTH_SHORT).show();
                }

            }
        });





    }
}

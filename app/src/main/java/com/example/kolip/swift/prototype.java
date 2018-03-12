package com.example.kolip.swift;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.sql.Time;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class prototype extends AppCompatActivity {
Button b1,b2,b3,b4,b5,b6,b7;
String userid1,emailid1;
FirebaseFirestore firebaseFirestore;
FirebaseAuth firebaseAuth;
private List<String> city = new ArrayList<String>();
    static final SimpleDateFormat format = new SimpleDateFormat("HH:mm:ss");
public String s="rk",s1="krk",str="nest";
FirebaseUser firebaseUser;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_prototype);
        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        userid1 = firebaseUser.getUid();
        emailid1 = firebaseUser.getEmail();
        firebaseFirestore = FirebaseFirestore.getInstance();

        b1=(Button)findViewById(R.id.flipush);
        b2=(Button)findViewById(R.id.flipush1);
        b3=(Button)findViewById(R.id.flipush2);
        b4=(Button)findViewById(R.id.flipush3);
        b5=(Button)findViewById(R.id.flipush4);
        b6=(Button)findViewById(R.id.flipush5);
        b7=(Button)findViewById(R.id.flipush6);
        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {



                Map<String , Object> map= new HashMap<>();
                map.put("fliname", "6E3775");
                map.put("compname", "indigo");
                map.put("noofseats", 60);
                map.put("time1", "21:00");
                map.put("time2","22:00");
                Map<String , Object> map1= new HashMap<>();
                map1.put("fliname", "IGO613");
                map1.put("compname", "indigo");
                map1.put("noofseats", 60);
                map1.put("time1", "11:30");
                map1.put("time2","2:30");

                Map<String , Object> map3= new HashMap<>();
                map3.put("fliname", "6E3774");
                map3.put("compname", "indigo");
                map3.put("noofseats", 60);
                map3.put("time1", "4:30");
                map3.put("time2","8:30");

                Map<String,Object> map2 = new HashMap<>();
                map2.put("indigo1",map);
                map2.put("IGO613",map1);
                map2.put("indigo2",map3);

                firebaseFirestore.collection("flights").document("indigo").set(map2).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                   if (task.isSuccessful()){
                       Toast.makeText(prototype.this, "sucess", Toast.LENGTH_SHORT).show();
                   }

                    }
                });




            }



        });

        b2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {



                Map<String , Object> map5= new HashMap<>();
                map5.put("fliname", "SPC143");
                map5.put("compname", "spicejet");
                map5.put("noofseats", 60);
                map5.put("time1", "6:00");
                map5.put("time2","7:45");
                Map<String , Object> map6= new HashMap<>();
                map6.put("fliname", "SPBUS43");
                map6.put("compname", "spicejet");
                map6.put("noofseats", 60);
                map6.put("time1", "11:00");
                map6.put("time2","3:45");
                Map<String , Object> map7= new HashMap<>();
                map7.put("fliname", "SPI123");
                map7.put("compname", "spicejet");
                map7.put("noofseats", 60);
                map7.put("time1", "13:00");
                map7.put("time2","19:45");

                Map<String,Object> map8 = new HashMap<>();
                map8.put("SPC143",map5);
                map8.put("SPBUS43",map6);
                map8.put("SPI123",map7);

                firebaseFirestore.collection("flights").document("spicejet").set(map8).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()){
                            Toast.makeText(prototype.this, "sucess1", Toast.LENGTH_SHORT).show();
                        }

                    }
                });




            }



        });


        b3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {



                Map<String , Object> map10= new HashMap<>();
                map10.put("fliname", "AIR319");
                map10.put("compname", "spicejet");
                map10.put("noofseats", 60);
                map10.put("time1", "9:00");
                map10.put("time2","10:45");
                Map<String , Object> map11= new HashMap<>();
                map11.put("fliname", "AIR367");
                map11.put("compname", "spicejet");
                map11.put("noofseats", 60);
                map11.put("time1", "12:00");
                map11.put("time2","14:45");
                Map<String , Object> map12= new HashMap<>();
                map12.put("fliname", "ATR12");
                map12.put("compname", "spicejet");
                map12.put("noofseats", 60);
                map12.put("time1", "16:20");
                map12.put("time2","19:00");

                Map<String,Object> map13 = new HashMap<>();
                map13.put("AIR319",map10);
                map13.put("AIR367",map11);
                map13.put("ATR12",map12);

                firebaseFirestore.collection("flights").document("airindia").set(map13).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()){
                            Toast.makeText(prototype.this, "sucess1", Toast.LENGTH_SHORT).show();
                        }

                    }
                });




            }



        });


    b4.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View view) {
//
//            Map<String,Object> map20= new HashMap<>();
//            map20.put("x",true);
//            map20.put("y",true);
//            Map<String,Object> map21= new HashMap<>();
//            map21.put("privacy",map20);
storing storing =new storing(s,s1);
nest n1= new nest(storing);
            firebaseFirestore.collection("flights").document(userid1).set(n1).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    if (task.isSuccessful()){
                        Toast.makeText(prototype.this, "suc", Toast.LENGTH_SHORT).show();
                    }

                }
            });
        }
    });


b5.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View view) {
        Map<String,Object> date = new HashMap<String, Object>();
        date.put("date",new Date());

        firebaseFirestore.collection("date").document("new").set(date).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()){

                    Toast.makeText(prototype.this, "enjoy", Toast.LENGTH_SHORT).show();
                }
                else{
                    Toast.makeText(prototype.this, "error", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }
});
//        DocumentReference doc = firebaseFirestore.collection("date").document("new");
//        doc.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
//            @Override
//            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
//                if (task.isSuccessful()) {
//                    DocumentSnapshot documentSnapshot = task.getResult();
//                    if (documentSnapshot != null) {
//                        Map<String, Object> map = documentSnapshot.getData();
//                        Date date = (Date) map.get("date");
//                        Toast.makeText(prototype.this, String.valueOf(date.getDate()) + "/" + String.valueOf(date.getMonth()) + "/" + String.valueOf(date.getYear()), Toast.LENGTH_LONG).show();
//                        Log.d("datee", String.valueOf(date.getMonth()) + String.valueOf(date.getYear()));
//                        Log.d("datee1", String.valueOf(date.getTime()));
//                    }
//                    else {
//                        Log.d("err", "get() failed with ", task.getException());
//                    }
//                }
//
//            }
//            });

        city.add("coimbatore");
        city.add("hyderabad");
        city.add("mumbai");
        city.add("delhi");
        city.add("kolkata");
        city.add("kochi");
        city.add("chennai");
        city.add("bengaluru");
        city.add("ahmedabad");

        b7.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View view) {
        citystore cs=new citystore(city);
        firebaseFirestore.collection("cities").document("names").set(cs).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                Toast.makeText(prototype.this, "city", Toast.LENGTH_SHORT).show();
            }
        });


    }
});

        b6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                savetoDatabase();
            }
        });



    }
    public Date getDateFromString(String datetoSaved){

        try {
            Date date = format.parse(datetoSaved);
            return date ;
        } catch (ParseException e){
            return null ;
        }
    }

    public void savetoDatabase(){



        Map<String,Object> addAnimal = new HashMap<>();
        addAnimal.put("dob",getDateFromString("10:30"));

        firebaseFirestore.collection("date").document("new").set(addAnimal);

    }

    public class storing{


        private String s,s1;

        public storing(String s, String s1) {

            this.s = s;
            this.s1 = s1;
        }

        public String getS() {
            return s;
        }

        public String getS1() {
            return s1;
        }
    }

   public class nest{


        private  storing stor;

       public nest( storing stor) {

           this.stor = stor;
       }


       public storing getStor() {
           return stor;
       }
   }

   public class citystore{
        private List<String> city;



       public citystore(List<String> city) {
           this.city = city;
       }

       public citystore() {}


       public List<String> getcity() {
           return city;
       }
   }


}

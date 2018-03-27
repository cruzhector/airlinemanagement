package com.example.kolip.swift;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.app.ProgressDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;

import java.io.Serializable;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.TimeZone;

public class flightbook extends AppCompatActivity {
    Button b1;
    AutoCompleteTextView at1,at2;
    public Calendar myCalendar, mycalendar1;
    EditText date1, date2;
    TextView e1, e2;
    CardView c1, c2;
    RadioButton radd1, radd2;
    RadioGroup radioGroup;
    ImageButton i1, i2, i3, i4;
    Spinner clas;
    TextView textview,textview1,tv1,tv2;
    int count = 0, count1 = 0,dedat,redat;
    FirebaseUser firebaseUser;
    private FirebaseAuth firebaseAuth;
    private FirebaseAuth.AuthStateListener authStateListener;
    ProgressDialog progressDialog;
    DocumentReference documentReference,documentReference1,documentReference2;
    FirebaseFirestore firebaseFirestore;
    CollectionReference c;
    List<String> list1 = new ArrayList<String>();
    String userid, d, d1, d3, d4, sp, t1, t2, da1, da2, emailid, rad,cit1,cit2;
    String[] cl = {"Economy", "Business"};
    View   parentLayout;
    String fun;
    ImageButton i;
    String shrt1,shrt2;
   private List<String> ctt = new ArrayList<String>();
    private List<String> ctt1 = new ArrayList<String>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_flightbook);

        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        parentLayout = findViewById(android.R.id.content);
        b1 = (Button) findViewById(R.id.go);
        i1 = (ImageButton) findViewById(R.id.minus1);
        i2 = (ImageButton) findViewById(R.id.plus1);
        i3 = (ImageButton) findViewById(R.id.minus2);
        i4 = (ImageButton) findViewById(R.id.plus2);
        e1 = (TextView) findViewById(R.id.child1);
        e2 = (TextView) findViewById(R.id.adult1);
        clas = (Spinner) findViewById(R.id.clas);
        c1 = (CardView) findViewById(R.id.dep);
        c2 = (CardView) findViewById(R.id.ret);
        radd1 = (RadioButton) findViewById(R.id.rad1);
        radd2 = (RadioButton) findViewById(R.id.rad2);
        radioGroup = (RadioGroup) findViewById(R.id.radio);
        myCalendar = Calendar.getInstance();
        mycalendar1 = Calendar.getInstance();
        at1=(AutoCompleteTextView)findViewById(R.id.depcity);
        at2=(AutoCompleteTextView)findViewById(R.id.arricity);
        textview = (TextView) findViewById(R.id.depdate);
        textview1 = (TextView) findViewById(R.id.redate);
        tv1 = (TextView) findViewById(R.id.ctcode);
        tv2 = (TextView) findViewById(R.id.ctcode1);
        i=(ImageButton)findViewById(R.id.swap);
        e1.setText(String.valueOf(count));
        e2.setText(String.valueOf(count1));
        childpassenger();
        adultpassenger();
        radio();
        userid = firebaseUser.getUid();
        emailid = firebaseUser.getEmail();
        firebaseFirestore = FirebaseFirestore.getInstance();
        e1.setText(String.valueOf(count));
        e2.setText(String.valueOf(count1));
        ArrayAdapter arrayAdapter = new ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, cl);
        clas.setAdapter(arrayAdapter);

     firebaseFirestore.collection("city").addSnapshotListener(new EventListener<QuerySnapshot>() {
          @Override
          public void onEvent(QuerySnapshot documentSnapshots, FirebaseFirestoreException e) {

            for (DocumentChange doc : documentSnapshots.getDocumentChanges()){
                String citys = doc.getDocument().getString("name");
                String codes = doc.getDocument().getString("code");
                ctt.add(citys);
                ctt1.add(codes);

            }

          }
      });

        ArrayAdapter ct = new ArrayAdapter(this, android.R.layout.select_dialog_item,ctt);
        at1.setAdapter(ct);
        at1.setThreshold(1);
        at2.setAdapter(ct);
        at2.setThreshold(1);








        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                progressDialog = ProgressDialog.show(flightbook.this,"" , "Get Set Go", true);

                pushdata();

            }
        });


        c1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                depdate();

            }
        });



        i.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cit1=at1.getText().toString();
                cit2=at2.getText().toString();

                at1.setText(cit2);
                at2.setText(cit1);

            }
        });

        c2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               repdate();

            }
        });

    }



    public void radio() {


        radd1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                radd2.setChecked(false);
                textview1.setVisibility(View.GONE);
                fun="";
            }
        });

        radd2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                radd1.setChecked(false);
                textview1.setVisibility(View.VISIBLE);
                fun="return";
            }
        });

    }

    public void childpassenger() {

        i1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                count--;
                if (count >= 0) {
                    d4 = String.valueOf(count);
                    e1.setText(d4);
                } else {
                  count=0;
                  e1.setText(String.valueOf(count));
                }
            }
        });

        i2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                count++;
                d = String.valueOf(count);
                e1.setText(d);
            }
        });

    }


    public void adultpassenger() {

        i3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                count1--;
                if (count1 >= 0) {
                    d1 = String.valueOf(count1);
                    e2.setText(d1);
                } else {
                    count1=0;
                    e2.setText(String.valueOf(count1));
                }
            }
        });

        i4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                count1++;
                d3 = String.valueOf(count1);
                e2.setText(d3);
            }
        });
    }

    public void pushdata() {


        da1 = textview.getText().toString().trim();
        da2 = textview1.getText().toString().trim();
        sp = clas.getSelectedItem().toString().trim();
        t1 = e1.getText().toString();
        t2 = e2.getText().toString();
        cit1=at1.getText().toString();
        cit2=at2.getText().toString();

        if (radd1.isChecked()) {

            if (TextUtils.isEmpty(da1)) {
                progressDialog.dismiss();
                textview.setError("fill");
            } else if (TextUtils.isEmpty(sp)) {
                progressDialog.dismiss();
                Toast.makeText(this, "select class", Toast.LENGTH_SHORT).show();

            }
//            else if (TextUtils.isEmpty(t1) && TextUtils.isEmpty(t2)) {
//                progressDialog.dismiss();
//                Snackbar.make(parentLayout,"please select no of passegers",Snackbar.LENGTH_SHORT).show();
//            }
            else if (t2.equals("0") && t1.equals("0")) {
                progressDialog.dismiss();
                Snackbar.make(parentLayout,"please select no of passegers",Snackbar.LENGTH_SHORT).show();
            }

            else if (TextUtils.isEmpty(cit1)|| TextUtils.isEmpty(cit2)){
                progressDialog.dismiss();
                Snackbar.make(parentLayout,"please select city",Snackbar.LENGTH_SHORT).show();
            }
            else if (!(ctt.contains(cit1.trim()))){
                progressDialog.dismiss();
                Snackbar.make(parentLayout,"city does not exist",Snackbar.LENGTH_SHORT).show();
            }

            else if (!(ctt.contains(cit2.trim()))){
                progressDialog.dismiss();
                Snackbar.make(parentLayout,"city does not exist",Snackbar.LENGTH_SHORT).show();
            }

            else if (TextUtils.equals(cit1,cit2)){
                progressDialog.dismiss();
                Snackbar.make(parentLayout,"Check Destination and Depart city",Snackbar.LENGTH_LONG).show();
            }

            else {
                store();
            }

        } else if (radd2.isChecked()) {
            if (TextUtils.isEmpty(da1)) {
                progressDialog.dismiss();
                textview.setError("fill");
            } else if (TextUtils.isEmpty(da2)) {

                progressDialog.dismiss();
                textview1.setError("fill");

            } else if (TextUtils.isEmpty(sp)) {
                progressDialog.dismiss();
                Toast.makeText(this, "select class", Toast.LENGTH_SHORT).show();

            }
            else if (TextUtils.equals(da1,da2)){
                progressDialog.dismiss();
                Snackbar.make(parentLayout,"Check Destination and Depart dates",Snackbar.LENGTH_SHORT).show();

            }
//
//            else if (TextUtils.isEmpty(t1) || t1.equals("0")) {
//                progressDialog.dismiss();
//                e1.setError("");
//            }
            else if (TextUtils.isEmpty(cit1)&& TextUtils.isEmpty(cit2)){
                progressDialog.dismiss();
                Snackbar.make(parentLayout,"please select city",Snackbar.LENGTH_SHORT).show();
            }
            else if (!(ctt.contains(cit1.trim()))){
                progressDialog.dismiss();
                Snackbar.make(parentLayout,"city does not exist",Snackbar.LENGTH_SHORT).show();
            }

            else if (!(ctt.contains(cit2.trim()))){
                progressDialog.dismiss();
                Snackbar.make(parentLayout,"city does not exist",Snackbar.LENGTH_SHORT).show();
            }


            else if (TextUtils.equals(cit1,cit2)){
                progressDialog.dismiss();
                Snackbar.make(parentLayout,"Check Destination and Depart city",Snackbar.LENGTH_LONG).show();
            }

            else if (t2.equals("0") && t1.equals("0")) {
                progressDialog.dismiss();
                Snackbar.make(parentLayout,"please select no of passegers",Snackbar.LENGTH_SHORT).show();
            } else {
                store();
            }
        } else {
            progressDialog.dismiss();
            Toast.makeText(this, "please select type of trip", Toast.LENGTH_SHORT).show();
        }
    }


    public void store() {

        final int count3=count+count1;
        final Map<String, Object> hashMap2 = new HashMap<String, Object>();
        hashMap2.put("deptdate", da1);
        hashMap2.put("retdate", da2);
        hashMap2.put("noofchld", t1);
        hashMap2.put("noofadult", t2);
        hashMap2.put("class", sp);
        hashMap2.put("deptcity", cit1);
        hashMap2.put("arrcity", cit2);
        hashMap2.put("totalno",String.valueOf(count3));
        firebaseFirestore.collection("tempbooked").document(userid).set(hashMap2).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {

                if (task.isSuccessful()) {
                    progressDialog.dismiss();
                    Intent intent = new Intent(flightbook.this, flightlist.class);
                    intent.putExtra("hashmaplist", (Serializable)hashMap2);
                    intent.putExtra("ncount",count3);
                    intent.putExtra("fun",fun);
                    startActivity(intent);
                } else {
                    Toast.makeText(flightbook.this, "unsuccess", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }


    public void depdate() {
        DialogFragment dialogfragment = new datepickerClass();
        dialogfragment.show(getFragmentManager(), "DatePickerDialog");
    }




    @SuppressLint("ValidFragment")
    public class datepickerClass extends DialogFragment implements DatePickerDialog.OnDateSetListener {
        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState){
            final Calendar calendar = Calendar.getInstance();

            int day = calendar.get(Calendar.DAY_OF_MONTH);
            int month = calendar.get(Calendar.MONTH);
            int year = calendar.get(Calendar.YEAR);

            DatePickerDialog datepickerdialog = new DatePickerDialog(getActivity(),
                    AlertDialog.THEME_DEVICE_DEFAULT_LIGHT,this,year,month,day);
            datepickerdialog.getDatePicker().setMinDate(System.currentTimeMillis() - 1000);

            return datepickerdialog;

        }

        @Override
        public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {



            Calendar calander2 = Calendar.getInstance();

            calander2.setTimeInMillis(0);

            calander2.set(i, i1, i2, 0, 0, 0);

  


            Date SelectedDate = calander2.getTime();


            DateFormat dateformat_UK = DateFormat.getDateInstance(DateFormat.MEDIUM, Locale.UK);
            String StringDateformat_UK = dateformat_UK.format(SelectedDate);

                textview.setText(StringDateformat_UK);


        }
    }

public void repdate(){
    DialogFragment dialogfragment1 = new datepickerClass1();
    dialogfragment1.show(getFragmentManager(), "DatePickerDialog");
}

    @SuppressLint("ValidFragment")
    public class datepickerClass1 extends DialogFragment implements DatePickerDialog.OnDateSetListener {
        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState){
            final Calendar calendar = Calendar.getInstance();

            int day = calendar.get(Calendar.DAY_OF_MONTH);
            int month = calendar.get(Calendar.MONTH);
            int year = calendar.get(Calendar.YEAR);

            DatePickerDialog datepickerdialog = new DatePickerDialog(getActivity(),
                    AlertDialog.THEME_DEVICE_DEFAULT_LIGHT,this,year,month,day);
            datepickerdialog.getDatePicker().setMinDate(System.currentTimeMillis() - 1000);

            return datepickerdialog;

        }

        @Override
        public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {



            Calendar calander2 = Calendar.getInstance();

            calander2.setTimeInMillis(0);

            calander2.set(i, i1, i2, 0, 0, 0);




            Date SelectedDate = calander2.getTime();


            DateFormat dateformat_UK = DateFormat.getDateInstance(DateFormat.MEDIUM, Locale.UK);
            String StringDateformat_UK = dateformat_UK.format(SelectedDate);

            textview1.setText(StringDateformat_UK);


        }
    }





}
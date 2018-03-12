package com.example.kolip.swift;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class updatepass extends AppCompatActivity {

    EditText e1,e2;
    String string1,string2;
    Button button1;
    FirebaseUser firebaseUser;
    FirebaseAuth firebaseAuth;

    ProgressDialog progressDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_updatepass);
        e1=(EditText)findViewById(R.id.chngpass1);
        e2= (EditText)findViewById(R.id.chngpass2);
        button1=  (Button)findViewById(R.id.uppass);
        firebaseUser= FirebaseAuth.getInstance().getCurrentUser();
        firebaseAuth= FirebaseAuth.getInstance();
        String mail = firebaseAuth.getUid();
        button1.setOnClickListener(new View.OnClickListener() {


            @Override
            public void onClick(View view) {
                string1= e1.getText().toString().trim();
                string2= e2.getText().toString().trim();

                if (TextUtils.isEmpty(string1) || TextUtils.isEmpty(string2) ){
                    Toast.makeText(updatepass.this, "fields are empty", Toast.LENGTH_SHORT).show();
                }

else if(!(string1.equals(string2))){
                    Toast.makeText(updatepass.this, "password not matched", Toast.LENGTH_SHORT).show();

                }
else {
//    firebaseUser.updatePassword(string1).addOnCompleteListener(new OnCompleteListener<Void>() {
//        @Override
//        public void onComplete(@NonNull Task<Void> task) {
//            if(task.isSuccessful()) {
//
//                Toast.makeText(updatepass.this, "sucessfully updated", Toast.LENGTH_SHORT).show();
//
//                progressDialog = ProgressDialog.show(updatepass.this, "Please Relogin ", "For security purpose", true);
//
//                Intent intent = new Intent(updatepass.this, login.class);
//                startActivity(intent);
//                progressDialog.dismiss();
//            }
//            else{
//                Toast.makeText(updatepass.this, "server error", Toast.LENGTH_SHORT).show();
//            }
//
//        }
//    });

firebaseUser.updatePassword(string1).addOnCompleteListener(new OnCompleteListener<Void>() {
    @Override
    public void onComplete(@NonNull Task<Void> task) {
        if (task.isSuccessful()){
            Toast.makeText(updatepass.this, "updated successfully", Toast.LENGTH_SHORT).show();
        }

   else {
            Toast.makeText(updatepass.this, "error", Toast.LENGTH_SHORT).show();
        }


    }
});

                }

            }
        });


    }
}

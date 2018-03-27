package com.example.kolip.swift;

import android.graphics.Bitmap;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;

public class qrcode extends AppCompatActivity {


Button b1;
EditText e1;
ImageView i1;
String s;
Bitmap bitmap;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_qrcode);
        b1=(Button)findViewById(R.id.qrbut);
        e1= (EditText) findViewById(R.id.qrstring);
        i1=(ImageView)findViewById(R.id.qr);

        b1.setOnClickListener(new View.OnClickListener() {
                                      @Override
            public void onClick(View view) {
               s=e1.getText().toString().trim();
               try{
                   bitmap=encode(s);
                   i1.setImageBitmap(bitmap);
               }catch (WriterException e){
                    e.printStackTrace();
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


}

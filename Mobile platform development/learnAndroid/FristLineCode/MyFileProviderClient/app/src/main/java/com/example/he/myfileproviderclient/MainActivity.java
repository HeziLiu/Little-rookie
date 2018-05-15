package com.example.he.myfileproviderclient;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.ParcelFileDescriptor;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

public class MainActivity extends AppCompatActivity {

    private ImageView image;//用于展示从第三方应用获取的图片
    private ParcelFileDescriptor mInputPFD;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        image= (ImageView) findViewById(R.id.image_);
        Intent intent=new Intent(Intent.ACTION_PICK);
        intent.setType("image/png");
        startActivityForResult(intent,0);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode==RESULT_OK ){
            Uri returnUri = data.getData();
            if (returnUri!=null){
                try {

                    mInputPFD= getContentResolver().openFileDescriptor(returnUri, "r");
//                   OutputStream imageStream=getContentResolver().openOutputStream(returnUri);
                    FileInputStream in=new FileInputStream(mInputPFD.getFileDescriptor());
                    Bitmap bitmap= BitmapFactory.decodeFileDescriptor(mInputPFD.getFileDescriptor());
                    image.setImageBitmap(bitmap);
//                    Log.d("AAAA",mInputPFD.toString());
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
            }

        }
    }
}

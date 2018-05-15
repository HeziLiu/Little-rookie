package com.example.he.choosepictest;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.ContentUris;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.File;
import java.io.FileNotFoundException;

/**
 * 调用摄像头拍照并进行照片的修剪
 */

public class MainActivity extends Activity {
    private final static int TAKE_PHOTO = 1;
    private final static int CROP_PHOTO = 2;
    private final static int CHOOSE_PHOTO = 3;


    private Button take_photo;
    private ImageView photo;
    private Uri image_uri;
    private File file;
    private Button choose_photo;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.main_layout);
        take_photo = (Button) findViewById(R.id.take_photo);
        photo = (ImageView) findViewById(R.id.photo);
        choose_photo = (Button) findViewById(R.id.choose_from_album);

        take_photo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                file = new File(Environment.getExternalStorageDirectory(), "output_image.jpg");//图片保存到SD卡根目录
                try {
                    //如果存在就删除
                    if (file.exists()) {
                        file.delete();
                    }
                    file.createNewFile();
                    image_uri = Uri.fromFile(file);//将文件转换为uri对象
                    Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    intent.putExtra(MediaStore.EXTRA_OUTPUT, image_uri);
                    startActivityForResult(intent, TAKE_PHOTO);//启动相机程序

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });


        choose_photo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_OPEN_DOCUMENT);//打开系统图库
                intent.setType("image/*");
                startActivityForResult(intent, CHOOSE_PHOTO);
            }
        });


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case TAKE_PHOTO:
                if (resultCode == RESULT_OK) {
                    Intent intent = new Intent("com.android.camera.action.CROP");
                    intent.setDataAndType(image_uri, "image/*");
                    intent.putExtra("scale", true);
                    intent.putExtra(MediaStore.EXTRA_OUTPUT, image_uri);
                    startActivityForResult(intent, CROP_PHOTO);//启动裁剪程序
                }
                break;

            case CROP_PHOTO:
                if (resultCode == RESULT_OK) {
                    try {
                        //对刚拍的相片进行修剪
                        Bitmap bitmap = BitmapFactory.decodeStream(getContentResolver().openInputStream(image_uri));
                        photo.setImageBitmap(bitmap);
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    }
                }
                break;
            case CHOOSE_PHOTO:
                if (resultCode == RESULT_OK) {
                    if (Build.VERSION.SDK_INT >= 19) {
                        handleImageOnKitkat(data);//4.4及以上系统使用这个方法处理图片
                    } else {
                        handleImageBeforeKitKat(data);//4.4以下系统使用这个方法处理图片
                    }
                }
                break;
        }
    }

    //解析图片
    @TargetApi(Build.VERSION_CODES.KITKAT)
    private void handleImageOnKitkat(Intent data) {
        String imagePath = null;
        Uri uri = data.getData();
        if (DocumentsContract.isDocumentUri(this, uri)) {
            //如果是document类型的uri，则通过document id 处理
            String docId = DocumentsContract.getDocumentId(uri);
            if ("com.android.providers.media.documents".equals(uri.getAuthority())) {
                String id = docId.split(":")[1];//解析出数字格式的id
                String selection = MediaStore.Images.Media._ID + "=" + id;
                imagePath = getImagePath(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, selection);

            } else if ("com.android.providers.downloads.documents".equals(uri.getAuthority())) {
                //解析下载的图片
                Uri contentUri = ContentUris.withAppendedId(Uri.parse("content://downloads/public_downloads"), Long.valueOf(docId));
                imagePath = getImagePath(contentUri, null);
            }
        } else if ("content".equalsIgnoreCase(uri.getScheme())) {
            //如果不是document类型的uri就用普通方式处理
            imagePath = getImagePath(uri, null);
        }
        displayImage(imagePath);//根据路径显示图片
    }


    //解析图片
    private void handleImageBeforeKitKat(Intent data) {
        Uri uri = data.getData();
        String imagePath = getImagePath(uri, null);
        displayImage(imagePath);

    }

    //获取照片路径
    private String getImagePath(Uri uri, String selection) {
        String path = null;
        //根据Uri和selection获取图片的真实路径
        Cursor cursor = getContentResolver().query(uri, null, selection, null, null);
        if (cursor != null) {
            if (cursor.moveToFirst()) {
                path = cursor.getString(cursor.getColumnIndex(MediaStore.Images.Media.DATA));
            }
            cursor.close();
        }
        return path;
    }

    //显示图片
    private void displayImage(String imagePath) {
        if (imagePath != null) {
            Bitmap bitmap = BitmapFactory.decodeFile(imagePath);
            photo.setImageBitmap(bitmap);
        } else {
            Toast.makeText(this, "failed to get image", Toast.LENGTH_SHORT).show();
        }

    }
}

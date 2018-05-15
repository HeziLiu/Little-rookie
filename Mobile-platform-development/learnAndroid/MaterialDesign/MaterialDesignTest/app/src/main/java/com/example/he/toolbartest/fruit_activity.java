package com.example.he.toolbartest;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

public class fruit_activity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fruit_activity);
        Intent intent=getIntent();
        String fruitName=intent.getStringExtra("fruit_name");
        int fruitImageId=intent.getIntExtra("fruit_imageId",0);
        Toolbar toolbar= (Toolbar) findViewById(R.id.toolbar);
        CollapsingToolbarLayout collapsingToolbar= (CollapsingToolbarLayout) findViewById(R.id.collapsing_toolbar);
        ImageView fruitImageContent= (ImageView) findViewById(R.id.fruit_content_image);
        TextView fruitNameContent= (TextView) findViewById(R.id.fruit_content_text);
        setSupportActionBar(toolbar);
        ActionBar actionBar=getSupportActionBar();
        if(actionBar!=null)
            actionBar.setDisplayHomeAsUpEnabled(true);
        collapsingToolbar.setTitle(fruitName);
        Glide.with(this).load(fruitImageId).into(fruitImageContent);

        StringBuilder sb=new StringBuilder();
        for(int i=0;i<500;i++)
            sb.append(fruitName);
        fruitNameContent.setText(sb.toString());

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                finish();
                return true;
        }
        return  super.onOptionsItemSelected(item);

    }
}

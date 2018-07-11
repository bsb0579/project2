package com.example.q.project2;
import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
//import android.widget.Adapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.Gallery;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class myGallery extends Activity {
    RecyclerView recyclerView;
    ArrayList<String> ImgUrl= new ArrayList<>();

    private int mCntAdapter = 0;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        LinearLayoutManager Manager;
        Adapter adapter;
        super.onCreate(savedInstanceState);
        setContentView(R.layout.gallery);

        Button btnTest = findViewById(R.id.btnTest);
        btnTest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(myGallery.this, String.valueOf(mCntAdapter), Toast.LENGTH_SHORT).show();
            }
        });

        ArrayList<String> RealImgUrl = getIntent().getStringArrayListExtra("imageIds");
        Log.d("abcd","abcd");
        ImgUrl.add("https://upload.wikimedia.org/wikipedia/commons/thumb/b/b3/Jordan_Lipofsky.jpg/170px-Jordan_Lipofsky.jpg");
        ImgUrl.add("http://cdn.playbuzz.com/cdn/62b7af36-65b7-41aa-8db2-e34fd8a76acf/62c5efd3-fa55-464b-8ee5-9a3e2543c830.jpg");
        ImgUrl.add("http://cdn.playbuzz.com/cdn/fa415381-3e73-4678-915d-7abf8983ce09/813d91c3-f7c9-4a20-9e7b-7e7b6da78941.jpg");
        ImgUrl.add("http://cdn.playbuzz.com/cdn/62b7af36-65b7-41aa-8db2-e34fd8a76acf/1e93e32c-7662-4de7-a441-59d4c29d6faf.jpg");
        ImgUrl.add("http://cdn.playbuzz.com/cdn/5cb29908-40a5-42d4-831d-5bea595bcf05/3e9f0c63-60c6-4a0c-964c-1302d56295da.jpg");
        ImgUrl.add("https://pmcfootwearnews.files.wordpress.com/2015/06/michael-jordan-chicago-bulls.jpg");
        ImgUrl.add("http://healthyceleb.com/wp-content/uploads/2015/03/Michael-Jordan.jpg");
        ImgUrl.add("http://thelegacyproject.co.za/wp-content/uploads/2015/04/Michael_Jordan_Net_Worth.jpg");
        ImgUrl.add("http://www.guinnessworldrecords.com/Images/Michael-Jordan-main_tcm25-15662.jpg");
        ImgUrl.add("http://sportsmockery.com/wp-content/uploads/2015/03/michael-jordan-1600x1200.jpg");
        ImgUrl.add("https://cdn-s3.si.com/s3fs-public/si/dam/assets/13/02/13/130213172915-michael-jordan-05717484-single-image-cut.jpg");
        ImgUrl.add("http://cdn.playbuzz.com/cdn/5cb29908-40a5-42d4-831d-5bea595bcf05/5246cb13-4c32-45ba-89ad-c63cbbcdfde3.jpg");
        ImgUrl.add("http://i.dailymail.co.uk/i/pix/2015/09/24/17/2CB89DDF00000578-0-image-a-1_1443111464150.jpg");
        ImgUrl.add("https://s-media-cache-ak0.pinimg.com/originals/f2/b5/f2/f2b5f2aeb31e079f7e48ac0c338a8507.jpg");
        this.recyclerView = (RecyclerView)findViewById(R.id.recyclerView);
        Manager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(Manager);
        adapter = new Adapter(RealImgUrl, this);
        recyclerView.setAdapter(adapter);
        mCntAdapter = adapter.getItemCount();
    }
}
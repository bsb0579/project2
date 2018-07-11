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
        this.recyclerView = (RecyclerView)findViewById(R.id.recyclerView);
        Manager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(Manager);
        adapter = new Adapter(RealImgUrl, this);
        recyclerView.setAdapter(adapter);
        mCntAdapter = adapter.getItemCount();
    }
}
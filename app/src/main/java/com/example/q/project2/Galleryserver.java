package com.example.q.project2;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import java.util.List;
import java.util.ArrayList;

import java.io.IOException;

import retrofit2.Call;


public class Galleryserver extends Fragment {

    private ArrayList<String> mImageIDs = new ArrayList<>();
    IRetrofitService mService = IRetrofitService.retrofit.create(IRetrofitService.class);

    @Override
    public void onResume() {
        super.onResume();
//        lock[0] = 0;
        Toast.makeText(getActivity(), "onResume", Toast.LENGTH_SHORT).show();
        //getPhotoIds();
    }

    private void getPhotoIds(){
        final Call<List<String>> photoIdArray = mService.getPhotosId();
        new Thread() {
            public void run() {
                try {
                    Log.d("so young wants hime", "firstoneiwant");
                    String Url_left = "http://52.231.153.77:8080/uploads/";
                    String final_Url;
                    Integer i;
                    final List<String> photo_name = photoIdArray.execute().body();
                    int size = photo_name.size();
                    for (i = 0; i < size; i++) {
                        String Url_right = photo_name.get(i);
                        final_Url = Url_left + Url_right;
                        mImageIDs.add(final_Url);
                        Log.d("informoon", mImageIDs.toString());
                    }
                    Galleryserver.this.getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(Galleryserver.this.getActivity(), "hello" + mImageIDs.toString(), Toast.LENGTH_SHORT).show();
                        }
                    });
                } catch (IOException e) {
                    e.printStackTrace();
                }
//                lock[0] += 1;
            }
        }.start();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final Integer[] lock = new Integer[1];
        super.onCreate(savedInstanceState);
        getPhotoIds();
        Toast.makeText(getActivity(), "onCreateView", Toast.LENGTH_SHORT).show();
        final View rootView = inflater.inflate(R.layout.servergallery, container, false);

        Log.d("I want to see him", mImageIDs.toString());
        //Toast.makeText(getContext(), mImageIDs.toString(), Toast.LENGTH_SHORT);
        Button gotobtn = (Button) rootView.findViewById(R.id.button);
        gotobtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Context context = getContext();
                Intent intent = new Intent(context,myGallery.class);
                //while (lock[0] == 0) {

                //    Log.d("This is crazy",mImageIDs.toString());
                //}
                Log.d("WhattheHell", mImageIDs.toString());
//                Log.d("Tellingwhatlockis",lock[0].toString());
                intent.putStringArrayListExtra("imageIds", mImageIDs);
                context.startActivity(intent);
            }
        });
        return rootView;
    }
}

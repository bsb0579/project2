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

import com.google.gson.JsonArray;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;


public class Anonymous extends Fragment {
    IRetrofitService mService = IRetrofitService.retrofit.create(IRetrofitService.class);
    private void getPhotoIds(){
        final Call<JsonArray> mContact = mService.getContactInfo();
        new Thread() {
            public void run() {
                try {
                    Log.d("so young wants hime", "firstoneiwant");
                    String Url_left = "http://52.231.153.77:8080/uploads/";
                    String final_Url;
                    Integer i;
                    final JsonArray realContact = mContact.execute().body();
                    Anonymous.this.getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(Anonymous.this.getActivity(), "OK THIS IS WORKING", Toast.LENGTH_SHORT).show();
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
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        View rootView = inflater.inflate(R.layout.anonymous, container, false);
        Button gotobtn = (Button) rootView.findViewById(R.id.button2);
        gotobtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Context context = getContext();
                Intent intent = new Intent(context,TestActivity.class);
                context.startActivity(intent);
            }
        });
        Button savebtn = (Button) rootView.findViewById(R.id.button3);
        savebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Context context = getContext();
                Intent intent = new Intent(context,UploadActivity.class);
                context.startActivity(intent);
            }
        });
        return rootView;
    }
}


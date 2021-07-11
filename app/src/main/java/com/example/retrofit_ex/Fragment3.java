package com.example.retrofit_ex;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;


import androidx.annotation.RequiresApi;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;


import java.io.IOException;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.HTTP;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

public class Fragment3 extends Fragment{
    private Retrofit retrofit;
    private RetrofitInterface retrofitInterface;
    private String BASE_URL = "http://172.10.18.137:80";
    String name;

    TextView allpost;
    Button showBtn, deleteBtn;


    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent= getActivity().getIntent();
        name=intent.getStringExtra("name");

        retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        retrofitInterface = retrofit.create(RetrofitInterface.class);
    }


    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_3, container, false);
        return view;
    }

    @Override
    public void onStart() {
        super.onStart();

        allpost = getView().findViewById(R.id.allpost);
        showBtn = getView().findViewById(R.id.showBtn);
        deleteBtn = getView().findViewById(R.id.deleteBtn);
        deleteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DeletePost();
            }
        });



        showBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Call<ResponseBody> call = retrofitInterface.getPost();

                call.enqueue(new Callback<ResponseBody>() {
                    @Override
                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                        //List<JSONObject> result= response.body();
                        //showPostResult
                        if (response.code() == 500) {
                            Toast.makeText(getActivity(),
                                    "database has failed", Toast.LENGTH_LONG).show();
                        }
                        else if (response.code() == 200){
                            Toast.makeText(getActivity(),
                                    "Uploaded successfully", Toast.LENGTH_LONG).show();

                            Gson gson = new Gson();
                            try {
                                String jsonString= response.body().string();
                                List<PostInfo> list = gson.fromJson(jsonString, new TypeToken<List<PostInfo>>(){}.getType());
                                allpost.setText(list.get(0).getTitle());

                            } catch (IOException e) {
                                e.printStackTrace();
                            }

                        }
                    }

                    @Override
                    public void onFailure(Call<ResponseBody> call, Throwable t) {
                        Toast.makeText(getActivity(), t.getMessage(),
                                Toast.LENGTH_LONG).show();
                    }
                });

            }
        });
    }
    private void DeletePost() {

        View view = getLayoutInflater().inflate(R.layout.f3_delete, null);

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setView(view).show();

        final EditText title = view.findViewById(R.id.title);
        Button delete = view.findViewById(R.id.delete);


        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                HashMap<String, String> map = new HashMap<>();
                map.put("name", name);
                map.put("title", title.getText().toString());

                Call<Void> call = retrofitInterface.deletePost(map);

                call.enqueue(new Callback<Void>() {
                    @Override
                    public void onResponse(Call<Void> call, Response<Void> response) {

                        if (response.code() == 200) {
                            Toast.makeText(getActivity(),
                                    "Deleted successfully", Toast.LENGTH_LONG).show();
                        } else if (response.code() == 400) {
                            Toast.makeText(getActivity(),
                                    "There's no such title", Toast.LENGTH_LONG).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<Void> call, Throwable t) {
                        Toast.makeText(getActivity(), t.getMessage(),
                                Toast.LENGTH_LONG).show();
                    }
                });

            }
        });


    }
}




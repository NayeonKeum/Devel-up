package com.example.retrofit_ex;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;

import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

import java.util.HashMap;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class Fragment1 extends Fragment {
    private Retrofit retrofit;
    private RetrofitInterface retrofitInterface;
    private String BASE_URL = "http://172.10.18.137:80";
    String name;
    Button postBtn, deleteBtn,updateBtn;

    @Override
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

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater layoutInflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = layoutInflater.inflate(R.layout.fragment_1, container, false);
        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        postBtn = getView().findViewById(R.id.post);
        deleteBtn = getView().findViewById(R.id.deleteBtn);
        updateBtn = getView().findViewById(R.id.updateBtn);

        deleteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DeletePost();
            }
        });
        updateBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectUpdatePost();
            }
        });

        postBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Post();
            }
        });

    }


    private void Post() {

        View view = getLayoutInflater().inflate(R.layout.f1_write, null);

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setView(view).show();

        final EditText title = view.findViewById(R.id.title);
        final EditText content = view.findViewById(R.id.content);
        Button upload = view.findViewById(R.id.upload);


        upload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                HashMap<String, String> map = new HashMap<>();
                map.put("name", name);
                map.put("title", title.getText().toString());
                map.put("content", content.getText().toString());
                //map.put("password", passwordEdit.getText().toString());

                Call<Void> call = retrofitInterface.executePost(map);

                call.enqueue(new Callback<Void>() {
                    @Override
                    public void onResponse(Call<Void> call, Response<Void> response) {

                        if (response.code() == 200) {
                            Toast.makeText(getActivity(),
                                    "Uploaded successfully", Toast.LENGTH_LONG).show();
                        } else if (response.code() == 400) {
                            Toast.makeText(getActivity(),
                                    "Same title, change please", Toast.LENGTH_LONG).show();
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
    private void selectUpdatePost() {

        View view = getLayoutInflater().inflate(R.layout.f1_selectupdate, null);

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setView(view).show();

        final EditText title = view.findViewById(R.id.title);
        Button update = view.findViewById(R.id.update);

        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UpdatePost();
            }
        });

    }

    private void UpdatePost() {
        View view = getLayoutInflater().inflate(R.layout.f1_update, null);

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setView(view).show();

        final EditText utitle = view.findViewById(R.id.utitle);
        final EditText ucontent = view.findViewById(R.id.ucontent);
        Button update = view.findViewById(R.id.update);


        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                HashMap<String, String> map = new HashMap<>();

                map.put("name", name);
                map.put("title", utitle.getText().toString());
                map.put("content", ucontent.getText().toString());

                Call<UpdateResult> call = retrofitInterface.executeUpdate(map);

                call.enqueue(new Callback<UpdateResult>() {
                    @Override
                    public void onResponse(Call<UpdateResult> call, Response<UpdateResult> response) {

                        if (response.code() == 200) {

                            UpdateResult result = response.body();
                            AlertDialog.Builder builder1 = new AlertDialog.Builder(getActivity());
                            builder1.setTitle("Updated title : "+result.getTitle());
                            builder1.setMessage("Updated content : "+result.getContent());

                            builder1.show();

                        } else if (response.code() == 404) {
                            Toast.makeText(getActivity(), "Title already exists",
                                    Toast.LENGTH_LONG).show();
                        }
                    }
                    @Override
                    public void onFailure(Call<UpdateResult> call, Throwable t) {
                        Toast.makeText(getActivity(), t.getMessage(),
                                Toast.LENGTH_LONG).show();
                    }
                });
            }
        });
    }

    private void DeletePost() {

        View view = getLayoutInflater().inflate(R.layout.f1_delete, null);

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
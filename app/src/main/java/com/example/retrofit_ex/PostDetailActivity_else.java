package com.example.retrofit_ex;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class PostDetailActivity_else extends AppCompatActivity {

    ArrayList<String> namesofliked;
    String name, title, content;
    TextView Vname, Vtitle, Vcontent, Vliked;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.f3_elseview);
        Intent intent=getIntent();
        name=intent.getStringExtra("name");
        title=intent.getStringExtra("title");
        content=intent.getStringExtra("content");
        namesofliked=intent.getStringArrayListExtra("namesofliked");


        Vname=findViewById(R.id.name);
        Vtitle=findViewById(R.id.title);
        Vcontent=findViewById(R.id.content);
        Vliked=findViewById(R.id.like);

        Vname.setText("name : "+name);
        Vtitle.setText("title : "+title);
        Vcontent.setText("content : \n"+content);
        Vliked.setText(""+namesofliked.size());
    }
}

package com.example.retrofit_ex;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface RetrofitInterface {
    @POST("/login")
    Call<LoginResult> executeLogin(@Body HashMap<String, String> map);

    @POST("/signup")
    Call<Void> executeSignup (@Body HashMap<String, String> map);

    @POST("/post")
    Call<Void> executePost (@Body HashMap<String, String> map);

    @GET("/post/all")
    //Call<showPostResult> getPost();
    //Call<List<JSONObject>> getPost();
    Call<ResponseBody> getPost();
}




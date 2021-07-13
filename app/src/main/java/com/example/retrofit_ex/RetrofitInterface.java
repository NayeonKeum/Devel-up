package com.example.retrofit_ex;


import java.util.HashMap;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.HTTP;
import retrofit2.http.POST;



public interface RetrofitInterface {
    @POST("/login")
    Call<LoginResult> executeLogin(@Body HashMap<String, String> map);

    @POST("/signup")
    Call<Void> executeSignup (@Body HashMap<String, String> map);

    @POST("/post")
    Call<Void> executePost (@Body HashMap<String, Object> map);

    @GET("/post/all")
    Call<ResponseBody> getPost();

    @HTTP(method = "DELETE", path = "/post/delete", hasBody = true)
    Call<Void> deletePost(
            @Body HashMap<String, String> map);

    @POST("/post/update")
    Call<UpdateResult> executeUpdate (@Body HashMap<String, Object> map);

    @GET("/comment/all")
    Call<ResponseBody> getComment();

    @POST("/comment/post")
    Call<Void> executeCommentPost (@Body HashMap<String, Object> map);

    @HTTP(method = "DELETE", path = "/comment/delete", hasBody = true)
    Call<Void> deleteComment(
            @Body HashMap<String, String> map);

}




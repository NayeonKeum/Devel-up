package com.example.retrofit_ex;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.HashMap;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class PostVAdapter extends RecyclerView.Adapter<PostVAdapter.ViewHolder> {
    private Context context;
    private ArrayList<PostInfo> mList = new ArrayList<PostInfo>();
    public MainActivity activity;
    int position;
    String userName;
    boolean Isliked=false;

    private Retrofit retrofit;
    private RetrofitInterface retrofitInterface;
    private String BASE_URL = "http://172.10.18.137:80";


    public void onAttach(Activity activity){
        this.activity= (MainActivity) activity;
    }
    // 아이템 뷰를 저장하는 뷰홀더 클래스.


    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView name, title, content, like;
        LinearLayout like_layout;
        ImageView likeImg;


        public ViewHolder(View itemView) {
            super(itemView) ;

            // 뷰 객체에 대한 참조. (hold strong reference)
            this.name = itemView.findViewById(R.id.name) ;
            this.title = itemView.findViewById(R.id.title);
            this.content = itemView.findViewById(R.id.content);
            this.like=itemView.findViewById(R.id.like);
            this.like_layout=itemView.findViewById(R.id.like_layout);
            this.likeImg=itemView.findViewById(R.id.likeImg);
        }
    }
    public PostVAdapter(ArrayList<PostInfo> list, String userName) {
        this.mList = list ;
        this.userName=userName;
    }

    // onCreateViewHolder() - 아이템 뷰를 위한 뷰홀더 객체 생성하여 리턴.
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext() ;
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) ;
        View view = inflater.inflate(R.layout.rc_item, parent, false) ;
        ViewHolder vh = new ViewHolder(view) ;

        return vh ;
    }
    // onBindViewHolder() - position에 해당하는 데이터를 뷰홀더의 아이템뷰에 표시.
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        PostInfo modal = mList.get(position);

        holder.name.setSelected(true);

        //holder.img.setImageResource(R.drawable.per);
        holder.name.setText(mList.get(position).getName()) ;
        holder.title.setText(mList.get(position).getTitle()) ;
        holder.content.setText(mList.get(position).getContent()) ;
        holder.like.setText(mList.get(position).getLike());

//        holder.like_layout.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                if (!Isliked){
//
//                    retrofit = new Retrofit.Builder()
//                            .baseUrl(BASE_URL)
//                            .addConverterFactory(GsonConverterFactory.create())
//                            .build();
//                    retrofitInterface = retrofit.create(RetrofitInterface.class);
//
//                    HashMap<String, String> map = new HashMap<>();
//
//                    map.put("name",  modal.getName());
//                    map.put("title",  modal.getTitle());
//                    map.put("content",  modal.getContent());
//                    map.put("like", Integer.toString(Integer.parseInt(like)+1));
//
//                    likeImg.setColorFilter(Color.parseColor("#ff2e2e"), PorterDuff.Mode.SRC_IN);
//
//
//                    Call<UpdateResult> call = retrofitInterface.executeUpdate(map);
//
//                    call.enqueue(new Callback<UpdateResult>() {
//                        @Override
//                        public void onResponse(Call<UpdateResult> call, Response<UpdateResult> response) {
//                            if (response.code() == 200) {
//                                Toast.makeText(PostDetailActivity_my.this, "Liked!",
//                                        Toast.LENGTH_LONG).show();
//                            } else if (response.code() == 404) {
//                                Toast.makeText(PostDetailActivity_my.this, "Somthings Wrong",
//                                        Toast.LENGTH_LONG).show();
//                            }
//                        }
//
//                        @Override
//                        public void onFailure(Call<UpdateResult> call, Throwable t) {
//                            Toast.makeText(PostDetailActivity_my.this, t.getMessage(),
//                                    Toast.LENGTH_LONG).show();
//                        }
//                    });
//
//                }
//                else {
//                    Log.d("좋아요", "Already Liked!");
//            }
//        }});

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //on below line we are opening a new activity and passing data to it.
                Log.d("PostVAdapter", "리사이클러뷰 아이템 누름"+position);

                if (userName.equals(modal.getName())) {
                    Intent i = new Intent(holder.itemView.getContext(), PostDetailActivity_my.class);
                    Log.d("이름 같", userName+", "+modal.getName());
                    i.putExtra("name", modal.getName());
                    i.putExtra("title", modal.getTitle());
                    i.putExtra("content", modal.getContent());
                    i.putExtra("like", modal.getLike());
                    //on below line we are starting a new activity,
                    holder.itemView.getContext().startActivity(i);
                } else{
                    Intent i = new Intent(holder.itemView.getContext(), PostDetailActivity_else.class);
                    Log.d("이름 다름",userName+", "+modal.getName());
                    i.putExtra("name", modal.getName());
                    i.putExtra("title", modal.getTitle());
                    i.putExtra("content", modal.getContent());
                    i.putExtra("like", modal.getLike());
                    //on below line we are starting a new activity,
                    holder.itemView.getContext().startActivity(i);
                }
            }
        });

    }

    // getItemCount() - 전체 데이터 갯수 리턴.
    @Override
    public int getItemCount() {
        if (mList != null) {
            return mList.size();
        }
        return 0;
    }


}

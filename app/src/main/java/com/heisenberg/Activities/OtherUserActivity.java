package com.heisenberg.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.heisenberg.Adapters.UserContestAdaptor;
import com.heisenberg.R;
import com.heisenberg.Retrofit.JsonPlaceHolderApi;
import com.heisenberg.Retrofit.RetrofitClient;
import com.heisenberg.Retrofit.Serializers.Participant;
import com.heisenberg.Retrofit.Serializers.User;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;
import pl.droidsonroids.gif.GifImageView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class OtherUserActivity extends AppCompatActivity {
    LinearLayout parent;
    GifImageView imageView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_other_user);
        Toolbar toolbar=findViewById(R.id.toolbar4);
        setSupportActionBar(toolbar);
        manageUIBefore();
    }
    public void manageUIBefore(){
        imageView=findViewById(R.id.other_loading);
        parent=findViewById(R.id.other_parent);
        parent.setVisibility(View.INVISIBLE);
        String username=getIntent().getStringExtra("username");
        getUserDetails(username);

    }
    public void manageUIAfter(){
        imageView.setVisibility(View.GONE);
        parent.setVisibility(View.VISIBLE);
    }
    public void getUserDetails(String username){
        RetrofitClient retrofitClient=new RetrofitClient();
        JsonPlaceHolderApi jsonPlaceHolderApi=retrofitClient.jsonPlaceHolderApi;
        Call<User> call=jsonPlaceHolderApi.getUserDetail(username);
        call.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                if(response.isSuccessful()){
                    User user=response.body();
                    displayDetails(user);
                    manageUIAfter();
                    return;
                }
                Toast.makeText(OtherUserActivity.this, response.message(), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                Toast.makeText(OtherUserActivity.this, "Something went wrong", Toast.LENGTH_SHORT).show();
            }
        });
    }
    public void displayDetails(User user){
        TextView name=findViewById(R.id.other_name);
        TextView username=findViewById(R.id.other_username);
        CircleImageView image=findViewById(R.id.other_image);
        TextView contest=findViewById(R.id.other_contests);
        name.setText(user.getFirstName()+" "+user.getLastName());
        username.setText("@"+user.getUsername());
        contest.setText(Integer.toString(user.getParticipations().size()));
        if(user.getImage()!=null) {
            Glide.with(this).load(user.getImage()).centerCrop().placeholder(R.drawable.loadingc).into(image);
        }

        ArrayList<Participant> participations=new ArrayList<Participant>(user.getParticipations());
        displayTimeline(participations);
    }
    public void displayTimeline(ArrayList<Participant> participations){
        RecyclerView timelineRecyclerView=findViewById(R.id.other_user_contests);
        timelineRecyclerView.setHasFixedSize(true);
        UserContestAdaptor adaptor=new UserContestAdaptor(participations,this);
        timelineRecyclerView.setAdapter(adaptor);
        LinearLayoutManager layoutManager=new LinearLayoutManager(this);
        timelineRecyclerView.setLayoutManager(layoutManager);
    }
}

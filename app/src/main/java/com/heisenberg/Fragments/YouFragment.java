package com.heisenberg.Fragments;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

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


public class YouFragment extends Fragment {
    View v;
    String uname;
    LinearLayout parent;
    GifImageView imageView;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        v= inflater.inflate(R.layout.fragment_you, container, false);
        SharedPreferences sharedPreferences=getContext().getSharedPreferences(getContext().getPackageName(),Context.MODE_PRIVATE);
         uname=sharedPreferences.getString("username",null);
         manageUIBefore();
        return v;
    }
    public void manageUIBefore(){
        imageView=v.findViewById(R.id.you_loading);
        parent=v.findViewById(R.id.you_parent);
        parent.setVisibility(View.INVISIBLE);
        fetchAndDisplayUserDetails(uname);

    }
    public void manageUIAfter(){
        imageView.setVisibility(View.GONE);
        parent.setVisibility(View.VISIBLE);
    }


    public void fetchAndDisplayUserDetails(String username){
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
                }
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {

            }
        });
    }
    public void displayDetails(User user){
        TextView name=v.findViewById(R.id.you_name);
        TextView username=v.findViewById(R.id.you_username);
        TextView contest=v.findViewById(R.id.you_contests);
        CircleImageView image=v.findViewById(R.id.you_image);
        name.setText(user.getFirstName()+" "+user.getLastName());
        username.setText("@"+user.getUsername());
        contest.setText(Integer.toString(user.getParticipations().size()));
        if(user.getImage()!=null) {
            Glide.with(getContext()).load(user.getImage()).centerCrop().placeholder(R.drawable.loadingc).into(image);
        }
        ArrayList<Participant> participations=new ArrayList<Participant>(user.getParticipations());
        displayTimeline(participations);
    }

    public void displayTimeline(ArrayList<Participant> participations){
        RecyclerView timelineRecyclerView=v.findViewById(R.id.user_contests);
        timelineRecyclerView.setHasFixedSize(true);
        UserContestAdaptor adaptor=new UserContestAdaptor(participations,getContext());
        timelineRecyclerView.setAdapter(adaptor);
        LinearLayoutManager layoutManager=new LinearLayoutManager(getContext());
        timelineRecyclerView.setLayoutManager(layoutManager);
    }

}

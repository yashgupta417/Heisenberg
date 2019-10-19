package com.heisenberg.Fragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.heisenberg.Activities.ContestActivity;
import com.heisenberg.Adapters.ContestAdapter;
import com.heisenberg.Adapters.QuestionAdaptor;
import com.heisenberg.R;
import com.heisenberg.Retrofit.JsonPlaceHolderApi;
import com.heisenberg.Retrofit.RetrofitClient;
import com.heisenberg.Retrofit.Serializers.Contest;

import java.util.ArrayList;
import java.util.List;

import pl.droidsonroids.gif.GifImageView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class ContestFragment extends Fragment {
    ArrayList<Contest> upcomingContests,finishedContests;
    View v;
    LinearLayout parent;
    GifImageView imageView;
    SwipeRefreshLayout refreshLayout;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        v=inflater.inflate(R.layout.fragment_contest, container, false);
        manageUIBefore();
        refreshLayout=v.findViewById(R.id.c_f_refresh);
        refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                refreshContent();
            }
        });
        return v;
    }
    public void refreshContent(){
        refreshLayout.setRefreshing(true);
        fetchFinishedContests(1);

    }
    public void refreshDone(){
        refreshLayout.setRefreshing(false);

    }
    public void manageUIBefore(){
        imageView=v.findViewById(R.id.c_fragment_loading);
        parent=v.findViewById(R.id.c_fragment_parent);
        parent.setVisibility(View.INVISIBLE);
        fetchFinishedContests(0);

    }
    public void manageUIAfter(){
        imageView.setVisibility(View.GONE);
        parent.setVisibility(View.VISIBLE);
    }

    public void fetchUpcomingContests(final Integer source){
        RetrofitClient retrofitClient=new RetrofitClient();
        JsonPlaceHolderApi jsonPlaceHolderApi=retrofitClient.jsonPlaceHolderApi;
        Call<List<Contest>> call= jsonPlaceHolderApi.getContests("upcoming");
        call.enqueue(new Callback<List<Contest>>() {
            @Override
            public void onResponse(Call<List<Contest>> call, Response<List<Contest>> response) {
                upcomingContests=new ArrayList<Contest>(response.body());
                setUpcomingRecyclerView();
                if(source==0)
                    manageUIAfter();
                else if(source==1)
                    refreshDone();
            }

            @Override
            public void onFailure(Call<List<Contest>> call, Throwable t) {
                Toast.makeText(getContext(), t.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
            }
        });

    }
    public void fetchFinishedContests(final Integer source){
        RetrofitClient retrofitClient=new RetrofitClient();
        JsonPlaceHolderApi jsonPlaceHolderApi=retrofitClient.jsonPlaceHolderApi;
        Call<List<Contest>> call= jsonPlaceHolderApi.getContests("finished");
        call.enqueue(new Callback<List<Contest>>() {
            @Override
            public void onResponse(Call<List<Contest>> call, Response<List<Contest>> response) {
                if(response.isSuccessful()) {
                    finishedContests = new ArrayList<Contest>(response.body());
                    setFinishedRecyclerView();
                    fetchUpcomingContests(source);
                    return;
                }
                Toast.makeText(getContext(), response.message(), Toast.LENGTH_SHORT).show();

            }

            @Override
            public void onFailure(Call<List<Contest>> call, Throwable t) {
                Toast.makeText(getContext(), t.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
    public void setUpcomingRecyclerView(){
        RecyclerView recyclerView=v.findViewById(R.id.upcoming_contests);
        recyclerView.setHasFixedSize(true);
        final ContestAdapter adapter=new ContestAdapter(upcomingContests);
        recyclerView.setAdapter(adapter);
        LinearLayoutManager layoutManager=new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);

        adapter.setOnItemClickListener(new QuestionAdaptor.onItemClickListener() {
            @Override
            public void onItemClick(int position) {
                Contest contest=adapter.getContestAtIndex(position);
                Intent intent=new Intent(getContext(), ContestActivity.class);
                intent.putExtra("contest_id",Integer.toString(contest.getId()));
                startActivity(intent);
            }
        });

    }
    public void setFinishedRecyclerView(){
        RecyclerView recyclerView=v.findViewById(R.id.finished_contests);
        recyclerView.setHasFixedSize(true);
        final ContestAdapter adapter=new ContestAdapter(finishedContests);
        recyclerView.setAdapter(adapter);
        LinearLayoutManager layoutManager=new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);
        adapter.setOnItemClickListener(new QuestionAdaptor.onItemClickListener() {
            @Override
            public void onItemClick(int position) {
                Contest contest=adapter.getContestAtIndex(position);
                Intent intent=new Intent(getContext(), ContestActivity.class);
                intent.putExtra("contest_id",Integer.toString(contest.getId()));
                startActivity(intent);
            }
        });
    }
}

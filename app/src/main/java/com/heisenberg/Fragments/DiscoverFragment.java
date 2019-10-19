package com.heisenberg.Fragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.heisenberg.Activities.OtherUserActivity;
import com.heisenberg.Adapters.UserAdaptor;
import com.heisenberg.R;
import com.heisenberg.Retrofit.JsonPlaceHolderApi;
import com.heisenberg.Retrofit.RetrofitClient;
import com.heisenberg.Retrofit.Serializers.User;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class DiscoverFragment extends Fragment {
    View v;
    ArrayList<User> users;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        v= inflater.inflate(R.layout.fragment_discover, container, false);
        activateSearchView();
        return v;
    }
    public void activateSearchView(){
        androidx.appcompat.widget.SearchView searchView=v.findViewById(R.id.searchview);
        searchView.setOnQueryTextListener(new androidx.appcompat.widget.SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                searchUsers(query);
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                searchUsers(newText);
                return true;
            }
        });
    }
    public void searchUsers(String query){
        RetrofitClient retrofitClient=new RetrofitClient();
        JsonPlaceHolderApi jsonPlaceHolderApi=retrofitClient.jsonPlaceHolderApi;
        Call<List<User>> call=jsonPlaceHolderApi.searchUsers(query);
        call.enqueue(new Callback<List<User>>() {
            @Override
            public void onResponse(Call<List<User>> call, Response<List<User>> response) {
                List<User> list=response.body();
                users=new ArrayList<User>(list);
                setUpRecyclerView();

            }

            @Override
            public void onFailure(Call<List<User>> call, Throwable t) {
                Toast.makeText(getContext(), "Something went wrong", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void setUpRecyclerView(){
        RecyclerView recyclerView=v.findViewById(R.id.query_recycler_view);
        recyclerView.setHasFixedSize(true);
        final UserAdaptor adapter=new UserAdaptor(users,getContext());
        recyclerView.setAdapter(adapter);
        LinearLayoutManager layoutManager=new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);
        adapter.setOnItemClickListener(new UserAdaptor.onItemClickListener() {
            @Override
            public void onItemClick(int position) {
                User user=adapter.getUserAtIndex(position);
                Intent intent=new Intent(getContext(), OtherUserActivity.class);
                intent.putExtra("username",user.getUsername());
                startActivity(intent);
            }
        });
    }
}

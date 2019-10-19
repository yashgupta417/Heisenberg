package com.heisenberg.Fragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.heisenberg.Activities.QuestionActivity;
import com.heisenberg.Adapters.QuestionAdaptor;
import com.heisenberg.R;
import com.heisenberg.Retrofit.JsonPlaceHolderApi;
import com.heisenberg.Retrofit.Serializers.Question;
import com.heisenberg.Retrofit.RetrofitClient;

import java.util.ArrayList;
import java.util.List;

import pl.droidsonroids.gif.GifImageView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class PracticeFragment extends Fragment {
    View v;
    ArrayList<Question> questions;
    LinearLayout parent;
    GifImageView imageView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
        v= inflater.inflate(R.layout.fragment_practice, container, false);
        manageUIBefore();
        return v;
    }
    public void manageUIBefore(){
        imageView=v.findViewById(R.id.practice_loading);
        parent=v.findViewById(R.id.practice_parent);
        parent.setVisibility(View.INVISIBLE);
        fetchQuestions();

    }
    public void manageUIAfter(){
        imageView.setVisibility(View.GONE);
        parent.setVisibility(View.VISIBLE);
    }
    public void fetchQuestions(){
        RetrofitClient retrofitClient=new RetrofitClient();
        JsonPlaceHolderApi jsonPlaceHolderApi=retrofitClient.jsonPlaceHolderApi;
        Call<List<Question>> call= jsonPlaceHolderApi.getQuestions();
        call.enqueue(new Callback<List<Question>>() {
            @Override
            public void onResponse(Call<List<Question>> call, Response<List<Question>> response) {
                 questions=new ArrayList<Question>(response.body());
                 setRecyclerView();
                 manageUIAfter();
            }

            @Override
            public void onFailure(Call<List<Question>> call, Throwable t) {
                Toast.makeText(getContext(), "Something went wrong", Toast.LENGTH_SHORT).show();
            }
        });
    }
    public void setRecyclerView(){
        RecyclerView recyclerView=v.findViewById(R.id.que_recyclerView);
        recyclerView.setHasFixedSize(true);
        QuestionAdaptor adapter=new QuestionAdaptor(questions);
        recyclerView.setAdapter(adapter);
        LinearLayoutManager layoutManager=new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);

        adapter.setOnItemClickListener(new QuestionAdaptor.onItemClickListener() {
            @Override
            public void onItemClick(int position) {
                goToQueActivity(position);
            }
        });
    }

    public void goToQueActivity(int i){
        Intent intent=new Intent(getContext(), QuestionActivity.class);
        intent.putExtra("q_name",questions.get(i).getProblemName());
        intent.putExtra("q_problem",questions.get(i).getProblem());
        intent.putExtra("q_answer",questions.get(i).getAnswer());
        intent.putExtra("q_solution",questions.get(i).getSolution());
        intent.putExtra("q_id",Integer.toString(questions.get(i).getId()));
        startActivity(intent);
    }
}

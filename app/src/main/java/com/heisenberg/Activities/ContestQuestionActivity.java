package com.heisenberg.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.heisenberg.LoadingDialog;
import com.heisenberg.R;
import com.heisenberg.Retrofit.JsonPlaceHolderApi;
import com.heisenberg.Retrofit.RetrofitClient;
import com.heisenberg.Retrofit.Serializers.SubmitResponse;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ContestQuestionActivity extends AppCompatActivity {
    SharedPreferences sharedPreferences;
    TextView p_name;
    TextView p_problem;
    EditText my_answer;
    Integer q_id,p_id,c_id;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contest_question);
        Toolbar toolbar=findViewById(R.id.toolbar_2);
        setSupportActionBar(toolbar);
        String name=getIntent().getStringExtra("name");
        String problem=getIntent().getStringExtra("problem");
        q_id=getIntent().getIntExtra("q_id",0);
        p_id=getIntent().getIntExtra("p_id",0);
        c_id=getIntent().getIntExtra("c_id",0);
        p_name=findViewById(R.id.cq_name);
        p_problem=findViewById(R.id.cque_problem);
        p_name.setText(name);
        p_problem.setText(problem);
        my_answer=findViewById(R.id.cqmy_answer);
    }
    public void submitAnswer(View view){
        if(my_answer.getText().toString().trim().isEmpty()){
            Toast.makeText(this, "Enter Answer First", Toast.LENGTH_SHORT).show();
            return;
        }
        submitAnswer(Integer.parseInt(my_answer.getText().toString().trim()));

    }
    public void submitAnswer(Integer ans){
        if(ContestActivity.status!=1){
            Toast.makeText(this, "Contest Over", Toast.LENGTH_SHORT).show();
            return;
        }
        final LoadingDialog dialog=new LoadingDialog(this);
        dialog.showDialog();
        RetrofitClient retrofitClient=new RetrofitClient();
        JsonPlaceHolderApi jsonPlaceHolderApi=retrofitClient.jsonPlaceHolderApi;
        Call<SubmitResponse> call=jsonPlaceHolderApi.submitAnswer(c_id,q_id,p_id,ans);
        call.enqueue(new Callback<SubmitResponse>() {
            @Override
            public void onResponse(Call<SubmitResponse> call, Response<SubmitResponse> response) {
                if(response.isSuccessful()){
                    SubmitResponse response1=response.body();
                    dialog.hideDialog();
                    Toast.makeText(ContestQuestionActivity.this,response1.getMessage() , Toast.LENGTH_SHORT).show();
                    return;
                }
                Toast.makeText(ContestQuestionActivity.this, response.message(), Toast.LENGTH_SHORT).show();
                dialog.hideDialog();
            }

            @Override
            public void onFailure(Call<SubmitResponse> call, Throwable t) {
                Toast.makeText(ContestQuestionActivity.this, "something went wrong", Toast.LENGTH_SHORT).show();
                dialog.hideDialog();
            }
        });
    }
}

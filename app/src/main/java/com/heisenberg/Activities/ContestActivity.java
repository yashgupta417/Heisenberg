package com.heisenberg.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.heisenberg.Adapters.ParticipantAdaptor;
import com.heisenberg.Adapters.QuestionAdaptor;
import com.heisenberg.LoadingDialog;
import com.heisenberg.R;
import com.heisenberg.Retrofit.JsonPlaceHolderApi;
import com.heisenberg.Retrofit.RetrofitClient;
import com.heisenberg.Retrofit.Serializers.Contest;
import com.heisenberg.Retrofit.Serializers.Participant;
import com.heisenberg.Retrofit.Serializers.Question;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import pl.droidsonroids.gif.GifImageView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ContestActivity extends AppCompatActivity {
    RecyclerView recyclerView;
    TextView c_name,c_length,c_startingTime,c_timeLeft;
    String contest_id,my_username;
    LinearLayout parent;
    GifImageView imageView;
    Participant me;
    SwipeRefreshLayout refreshLayout;
    Button button;
    static int status;
    TextView textView;
    private static final int EVALUATING = 2;
    private static final int FINISHED = 3;
    private static final int RUNNING = 1;
    private static final int TO_BEGIN = 0;
    boolean registered;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contest);
        Toolbar toolbar=findViewById(R.id.toolbar_contest_activity);
        setSupportActionBar(toolbar);
        contest_id=getIntent().getStringExtra("contest_id");
        SharedPreferences sharedPreferences=this.getSharedPreferences(getPackageName(), Context.MODE_PRIVATE);
        my_username=sharedPreferences.getString("username",null);
        refreshLayout=findViewById(R.id.c_a_refresh);
        button=findViewById(R.id.register);
        registered=false;
        textView=findViewById(R.id.time_textview);
        refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                refreshContent();
            }
        });
        manageUIBefore();
    }
    public void refreshContent(){
        refreshLayout.setRefreshing(true);
        fetchContestDetail(contest_id,1);
    }
    public void manageUIBefore(){
        imageView=findViewById(R.id.c_loading);
        parent=findViewById(R.id.contest_parent);
        parent.setVisibility(View.INVISIBLE);
        imageView.setVisibility(View.VISIBLE);
        fetchContestDetail(contest_id,0);

    }
    public void manageUIAfter(){
        imageView.setVisibility(View.GONE);
        parent.setVisibility(View.VISIBLE);
    }
    public void refreshDone(){
        refreshLayout.setRefreshing(false);
    }

    LoadingDialog registrationDialog;
    public void registerForContest(View view){
        if(registered){
            Toast.makeText(this, "Already Registered", Toast.LENGTH_SHORT).show();
            return;
        }

        registrationDialog=new LoadingDialog(this);
        registrationDialog.showDialog();
        RetrofitClient retrofitClient=new RetrofitClient();
        JsonPlaceHolderApi jsonPlaceHolderApi=retrofitClient.jsonPlaceHolderApi;
        Call<Participant> call=jsonPlaceHolderApi.registerForContest(contest_id,my_username);
        call.enqueue(new Callback<Participant>() {
            @Override
            public void onResponse(Call<Participant> call, Response<Participant> response) {
                if(response.isSuccessful()){
                    fetchStandings(contest_id,2);
                    return;
                }
                Toast.makeText(ContestActivity.this, response.message(), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(Call<Participant> call, Throwable t) {
                Toast.makeText(ContestActivity.this, "registration failed", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void fetchContestDetail(String id, final Integer source){
        RetrofitClient retrofitClient=new RetrofitClient();
        JsonPlaceHolderApi jsonPlaceHolderApi=retrofitClient.jsonPlaceHolderApi;
        Call<Contest> call=jsonPlaceHolderApi.getContestDetail(id);
        call.enqueue(new Callback<Contest>() {
            @Override
            public void onResponse(Call<Contest> call, Response<Contest> response) {
                if(response.isSuccessful()){
                    Contest contest=response.body();
                    setUI(contest);
                    fetchStandings(contest_id,source);
                    return;
                }
                Toast.makeText(ContestActivity.this, response.message(), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(Call<Contest> call, Throwable t) {
                Toast.makeText(ContestActivity.this, "Something went wrong", Toast.LENGTH_SHORT).show();
            }
        });
    }
    public void fetchStandings(String id, final Integer source){
        RetrofitClient retrofitClient=new RetrofitClient();
        JsonPlaceHolderApi jsonPlaceHolderApi=retrofitClient.jsonPlaceHolderApi;
        Call<List<Participant>> call=jsonPlaceHolderApi.getParticipantsOfContest(id);
        call.enqueue(new Callback<List<Participant>>() {
            @Override
            public void onResponse(Call<List<Participant>> call, Response<List<Participant>> response) {
                if(response.isSuccessful()){
                    ArrayList<Participant> participants=new ArrayList<Participant>(response.body());
                    checkIfRegisterd(participants);
                    setUpStandings(participants);
                    if(source==0)
                        manageUIAfter();
                    else if(source==1)
                        refreshDone();
                    else if(source==2) {
                        registerInUI();
                        registrationDialog.hideDialog();
                    }
                    return;
                }
                Toast.makeText(ContestActivity.this, response.message(), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(Call<List<Participant>> call, Throwable t) {
                Toast.makeText(ContestActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

    }
    public void checkIfRegisterd(ArrayList<Participant> participants){
        for(int i=0;i<participants.size();i++){
            if (participants.get(i).getUser().getUsername().equals(my_username)){
                me=participants.get(i);

                registerInUI();
            }
        }
    }
    public void registerInUI(){

        registered=true;
        button.setText("Registerd");
        button.setBackground(getResources().getDrawable(R.drawable.button_back));

    }

    public void setUI(Contest contest){
        c_name=findViewById(R.id.contest_name);
        c_length=findViewById(R.id.contest_length);
        c_startingTime=findViewById(R.id.contest_startingTime);
        c_timeLeft=findViewById(R.id.contest_timeLeft);
        c_name.setText(contest.getName());

        c_startingTime.setText(contest.getStartingTime());

        CalculateTimeLeft(contest);

        setUpQuestions(new ArrayList<Question>(contest.getQuestions()));
    }

    public void setUpQuestions(ArrayList<Question> questions){
        RecyclerView recyclerView=findViewById(R.id.contest_recyclerView);
        recyclerView.setHasFixedSize(true);
        final QuestionAdaptor adapter=new QuestionAdaptor(questions);
        recyclerView.setAdapter(adapter);
        LinearLayoutManager layoutManager=new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        adapter.setOnItemClickListener(new QuestionAdaptor.onItemClickListener() {
            @Override
            public void onItemClick(int position) {
                Question question=adapter.getQuestionAtIndex(position);
                movToQueActivity(question.getId(),question.getProblem(),question.getProblemName());
            }
        });
    }
    public void movToQueActivity(Integer q_id,String problem,String name){

        if(status==TO_BEGIN){
            Toast.makeText(this, "Locked", Toast.LENGTH_SHORT).show();
            return;
        }
        if(registered==false){
            Toast.makeText(this, "Not Registered", Toast.LENGTH_SHORT).show();
            return;
        }
        Intent intent=new Intent(getApplicationContext(),ContestQuestionActivity.class);
        intent.putExtra("q_id",q_id);
        intent.putExtra("p_id",me.getId());
        intent.putExtra("c_id",Integer.parseInt(contest_id));
        intent.putExtra("problem",problem);
        intent.putExtra("name",name);
        startActivity(intent);
    }
    public void setUpStandings(ArrayList<Participant> participants){
        RecyclerView recyclerView=findViewById(R.id.standings_recyclerView);
        recyclerView.setHasFixedSize(true);
        ParticipantAdaptor adapter=new ParticipantAdaptor(participants,this);
        recyclerView.setAdapter(adapter);
        LinearLayoutManager layoutManager=new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
    }
    long timeToFinish,timeToContest,length;
    public void CalculateTimeLeft(Contest contest){
        SimpleDateFormat dateformat=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Calendar calendar=Calendar.getInstance();
        try {
            Log.i("msg****","1");
            Date contestTime=dateformat.parse(contest.getStartingDate()+" "+contest.getStartingTime());
            Date endTime=dateformat.parse(contest.getEndDate()+" "+contest.getEndTime());
            String currTimeString=dateformat.format(calendar.getTime());
            Date currentTime=dateformat.parse(currTimeString);
            Log.i("msg****","3");
            timeToContest=contestTime.getTime()-currentTime.getTime();
            timeToFinish=endTime.getTime()-currentTime.getTime();
           length=endTime.getTime()-contestTime.getTime();
            int lsec=(int) length/1000;
            int lmin=(int) lsec/60;
            lsec=lsec%60;
            int lhour=(int) lmin/60;
            lmin=lmin%60;
            String h=String.format("%02d",lhour);
            String m=String.format("%02d",lmin);
            String s=String.format("%02d",lsec);
            c_length.setText(h+":"+m+":"+s);
            status=TO_BEGIN;
            if(contest.getIs_finished()==true){
                c_timeLeft.setText("Finished!");
                textView.setVisibility(View.GONE);
                status=FINISHED;
                button.setEnabled(false);
                button.setVisibility(View.GONE);
                return;
            }
            setOnBeginTimer();

        } catch (ParseException e) {
            e.printStackTrace();
            Toast.makeText(this, e.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
        }
    }
    public void setOnBeginTimer(){
        new CountDownTimer(timeToContest, 1000) {

            public void onTick(long millisUntilFinished) {

                int secs=(int) millisUntilFinished/1000;
                int mins=(int) secs/60;
                secs=secs%60;
                int hours=(int) mins/60;
                mins=mins%60;
                String h=String.format("%02d",hours);
                String m=String.format("%02d",mins);
                String s=String.format("%02d",secs);
                c_timeLeft.setText(h+":"+m+":"+s);
            }

            public void onFinish() {
                status=RUNNING;

                textView.setText("Time Left");
                if(timeToFinish>length)
                    timeToFinish=length;
                setOnFinishTimer();
            }
        }.start();
    }

    public void setOnFinishTimer(){
        new CountDownTimer(timeToFinish, 1000) {

            public void onTick(long millisUntilFinished) {

                int secs=(int) millisUntilFinished/1000;
                int mins=(int) secs/60;
                secs=secs%60;
                int hours=(int) mins/60;
                mins=mins%60;
                String h=String.format("%02d",hours);
                String m=String.format("%02d",mins);
                String s=String.format("%02d",secs);
                c_timeLeft.setText(h+":"+m+":"+s);
            }

            public void onFinish() {
                c_timeLeft.setText("Evaluating!");
                textView.setVisibility(View.GONE);
                button.setEnabled(false);
                button.setVisibility(View.GONE);
                status=EVALUATING;
            }
        }.start();
    }
}

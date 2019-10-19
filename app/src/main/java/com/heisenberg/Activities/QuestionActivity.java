package com.heisenberg.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.os.Bundle;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.heisenberg.R;

public class QuestionActivity extends AppCompatActivity {
    TextView name,problem,answer,solution;
    EditText myAns;
    LinearLayout solutionBlock;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_question);
        name=findViewById(R.id.question_name);
        problem=findViewById(R.id.question_problem);
        answer=findViewById(R.id.question_answer);
        solution=findViewById(R.id.question_solution);
        //myAns=findViewById(R.id.my_answer);
        Toolbar toolbar=findViewById(R.id.toolbar_3);
        setSupportActionBar(toolbar);
        solutionBlock=findViewById(R.id.solution_block);
        setUp();
    }

    public void setUp(){
        name.setText(getIntent().getStringExtra("q_name"));
        problem.setText(getIntent().getStringExtra("q_problem"));
        answer.setText(getIntent().getStringExtra("q_answer"));
        solution.setText(getIntent().getStringExtra("q_solution"));
    }

    public void showSolution(){
        int cx=solutionBlock.getWidth()/2;
        int cy=solutionBlock.getHeight()/2;
        float finalRadius=(float) Math.hypot(cx,cy);
        Animator animator= ViewAnimationUtils.createCircularReveal(solutionBlock,cx,cy,0,finalRadius);
        solutionBlock.setVisibility(View.VISIBLE);

        animator.start();
        solutionBlock.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                hideSolution();
            }
        });
    }
    public void showSolution(View v){
        showSolution();
    }
    public void hideSolution(){
        int cx=solutionBlock.getWidth()/2;
        int cy=solutionBlock.getHeight()/2;
        float initialRadius=(float) Math.hypot(cx,cy);
        Animator animator= ViewAnimationUtils.createCircularReveal(solutionBlock,cx,cy,initialRadius,0);
        animator.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                solutionBlock.setVisibility(View.INVISIBLE);
            }
        });
        animator.start();
        solutionBlock.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showSolution();
            }
        });
    }
}

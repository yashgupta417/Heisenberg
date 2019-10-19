package com.heisenberg.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.heisenberg.R;
import com.heisenberg.Retrofit.Serializers.Question;

import java.util.ArrayList;

public class QuestionAdaptor extends RecyclerView.Adapter<QuestionAdaptor.MyViewHolder> {
    ArrayList<Question> questions;
    Context context;
    private onItemClickListener mlistener;
    public interface onItemClickListener{
        void onItemClick(int position);
    }


    public void setOnItemClickListener(onItemClickListener listener){
        mlistener=listener;
    }
    public static class MyViewHolder extends RecyclerView.ViewHolder{
        TextView q_difficulty;
        TextView q_name;
        TextView q_points;
        TextView q_solvedBy;
        public MyViewHolder(@NonNull View itemView, final onItemClickListener listener) {
            super(itemView);
            q_difficulty=itemView.findViewById(R.id.q_difficulty);
            q_name=itemView.findViewById(R.id.q_name);
            q_points=itemView.findViewById(R.id.q_points);
            q_solvedBy=itemView.findViewById(R.id.q_solvedBy);

            itemView.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View v) {
                    if(listener!=null){
                        int position=getAdapterPosition();
                        if(position!=RecyclerView.NO_POSITION){
                            listener.onItemClick(position);
                        }
                    }
                }
            });
        }
    }
    public  QuestionAdaptor(ArrayList<Question> questions){
        this.questions=questions;
    }
    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v= LayoutInflater.from(parent.getContext()).inflate(R.layout.que_item_layout,parent,false);
        MyViewHolder myViewHolder=new MyViewHolder(v,mlistener);
        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.q_difficulty.setText(questions.get(position).getDifficulty());
        holder.q_name.setText(questions.get(position).getProblemName());
        holder.q_points.setText(Integer.toString(questions.get(position).getPoints()));
        holder.q_solvedBy.setText("x"+Integer.toString(questions.get(position).getSolvedByCount()));
    }

    public int getItemCount(){
        return questions.size();
    }

    public Question getQuestionAtIndex(int i){
        return questions.get(i);
    }
}

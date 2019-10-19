package com.heisenberg.Adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.heisenberg.R;
import com.heisenberg.Retrofit.Serializers.Contest;

import java.util.ArrayList;

public class ContestAdapter extends RecyclerView.Adapter<ContestAdapter.MyViewHolder> {
    ArrayList<Contest> contests;
    private QuestionAdaptor.onItemClickListener mlistener;

    public ContestAdapter(ArrayList<Contest> contests) {
        this.contests=contests;
    }


    public interface onItemClickListener{
        void onItemClick(int position);
    }


    public void setOnItemClickListener(QuestionAdaptor.onItemClickListener listener){
        mlistener=listener;
    }
    public static class MyViewHolder extends RecyclerView.ViewHolder{
        TextView c_noOfQue;
        TextView c_name;
        TextView c_starting_time;
        TextView c_end_time;
        public MyViewHolder(@NonNull View itemView, final QuestionAdaptor.onItemClickListener listener) {
            super(itemView);
            c_noOfQue=itemView.findViewById(R.id.c_noOfQue);
            c_name=itemView.findViewById(R.id.c_name);
            c_starting_time=itemView.findViewById(R.id.c_starting_time);
            c_end_time=itemView.findViewById(R.id.c_end_time);

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
    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v= LayoutInflater.from(parent.getContext()).inflate(R.layout.contest_item_layout,parent,false);
        ContestAdapter.MyViewHolder myViewHolder=new ContestAdapter.MyViewHolder(v,mlistener);
        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int i) {
        holder.c_noOfQue.setText(Integer.toString(contests.get(i).getNoOfQuestions()));
        holder.c_name.setText(contests.get(i).getName());
        holder.c_starting_time.setText(contests.get(i).getStartingTime());
        holder.c_end_time.setText(contests.get(i).getEndTime());
    }
    public int getItemCount(){
        return contests.size();
    }

    public Contest getContestAtIndex(int i){
        return contests.get(i);
    }
}

package com.heisenberg.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.heisenberg.R;
import com.heisenberg.Retrofit.Serializers.Participant;
import com.github.vipulasri.timelineview.TimelineView;

import java.util.ArrayList;

public class UserContestAdaptor extends RecyclerView.Adapter<UserContestAdaptor.MyViewHolder> {
    ArrayList<Participant> participants;
    Context context;
    private onItemClickListener mlistener;
    public interface onItemClickListener{
        void onItemClick(int position);
    }


    public void setOnItemClickListener(onItemClickListener listener){
        mlistener=listener;
    }
    public static class MyViewHolder extends RecyclerView.ViewHolder{
        TextView uc_name;
        TextView uc_prob_solved;
        TextView uc_score;
        TimelineView mTimelineView;
        public MyViewHolder(@NonNull View itemView, final onItemClickListener listener,int viewType) {
            super(itemView);
            uc_name=itemView.findViewById(R.id.uc_name);
            uc_prob_solved=itemView.findViewById(R.id.uc_prob_solved);
            uc_score=itemView.findViewById(R.id.uc_score);
            mTimelineView = (TimelineView) itemView.findViewById(R.id.timeline);
            mTimelineView.initLine(viewType);
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
    public  UserContestAdaptor(ArrayList<Participant> participants,Context context){
        this.participants=participants;
        this.context=context;
    }
    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v= LayoutInflater.from(parent.getContext()).inflate(R.layout.user_contest_item_layout,parent,false);
        MyViewHolder myViewHolder=new MyViewHolder(v,mlistener,viewType);
        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int i) {
        Participant participant=participants.get(i);
        holder.uc_name.setText(participant.getContest().getName());
        String q_solved=Integer.toString(participant.getQuestionSolvedCount());
        String total=Integer.toString(participant.getContest().getNoOfQuestions());
        holder.uc_prob_solved.setText(q_solved+"/"+total);
        holder.uc_score.setText(Integer.toString(participant.getScore()));
    }
    @Override
    public int getItemViewType(int position) {
        return TimelineView.getTimeLineViewType(position, getItemCount());
    }
    public int getItemCount(){
        return participants.size();
    }

    public Participant getParticipantAtIndex(int i){
        return participants.get(i);
    }
}

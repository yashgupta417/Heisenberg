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

import java.util.ArrayList;

public class ParticipantAdaptor extends RecyclerView.Adapter<ParticipantAdaptor.MyViewHolder> {
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
        TextView p_rank;
        TextView p_username;
        TextView p_score;
        public MyViewHolder(@NonNull View itemView, final onItemClickListener listener) {
            super(itemView);
            p_rank=itemView.findViewById(R.id.p_rank);
            p_username=itemView.findViewById(R.id.p_username);
            p_score=itemView.findViewById(R.id.p_score);

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
    public  ParticipantAdaptor(ArrayList<Participant> participants,Context context){
        this.participants=participants;
        this.context=context;
    }
    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v= LayoutInflater.from(parent.getContext()).inflate(R.layout.participant_item_layout,parent,false);
        MyViewHolder myViewHolder=new MyViewHolder(v,mlistener);
        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int i) {
        Participant participant=participants.get(i);

        holder.p_rank.setText(Integer.toString(participant.getRank()));
        holder.p_score.setText(Integer.toString(participant.getScore()));
        holder.p_username.setText(participant.getUser().getUsername());
    }

    public int getItemCount(){
        return participants.size();
    }

    public Participant getParticipantAtIndex(int i){
        return participants.get(i);
    }
}

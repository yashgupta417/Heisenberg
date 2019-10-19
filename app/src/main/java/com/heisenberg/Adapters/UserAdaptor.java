package com.heisenberg.Adapters;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.heisenberg.R;
import com.heisenberg.Retrofit.Serializers.User;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class UserAdaptor extends RecyclerView.Adapter<UserAdaptor.MyViewHolder> {
    ArrayList<User> users;
    Context context;
    private onItemClickListener mlistener;
    public interface onItemClickListener{
        void onItemClick(int position);
    }


    public void setOnItemClickListener(onItemClickListener listener){
        mlistener=listener;
    }
    public static class MyViewHolder extends RecyclerView.ViewHolder{
        TextView user_name;
        TextView user_username;
        CircleImageView user_image;
        public MyViewHolder(@NonNull View itemView, final onItemClickListener listener) {
            super(itemView);
            user_name=itemView.findViewById(R.id.user_name);
            user_username=itemView.findViewById(R.id.user_username);
            user_image=itemView.findViewById(R.id.user_image);

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
    public  UserAdaptor(ArrayList<User> users,Context context){
        this.users=users;
        this.context=context;
    }
    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v= LayoutInflater.from(parent.getContext()).inflate(R.layout.query_item_layout,parent,false);
        MyViewHolder myViewHolder=new MyViewHolder(v,mlistener);
        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int i) {
        User user=users.get(i);
        if(user.getImage()!=null) {
            Glide.with(context).load(user.getImage()).centerCrop().placeholder(R.drawable.loadingc).into(holder.user_image);
            Log.i("msg*****",user.getImage());
        }
        holder.user_name.setText(user.getFirstName()+" "+user.getLastName());
        holder.user_username.setText("@" +user.getUsername());
    }

    public int getItemCount(){
        return users.size();
    }

    public User getUserAtIndex(int i){
        return users.get(i);
    }
}

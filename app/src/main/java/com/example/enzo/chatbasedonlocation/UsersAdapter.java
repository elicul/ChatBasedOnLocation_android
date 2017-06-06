package com.example.enzo.chatbasedonlocation;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

/**
 * Created by Enzo on 28.5.2017..
 */

public class UsersAdapter extends RecyclerView.Adapter<UsersAdapter.UserViewHolder> {
    private List<String> mDataSet;
    private Context mContext;

    public UsersAdapter(Context context,List<String> list){
        this.mDataSet = list;
        mContext = context;
    }


    //////////

    private ItemClickCallback itemClickCallback;

    public interface ItemClickCallback {
        void onItemClick(int p);

    }

    public void setItemClickCallback(final ItemClickCallback itemClickCallback) {
        this.itemClickCallback = itemClickCallback;
    }

    ////////

    public static class UserViewHolder extends RecyclerView.ViewHolder {
        TextView userTextView;
        View container;



        public UserViewHolder(View v) {
            super(v);

            //  title = (TextView)itemView.findViewById(R.id.lbl_item_text);
         //   container = (View)v.findViewById(R.id.userRecyclerView);
           // container.setOnClickListener(this);

            userTextView = (TextView) itemView.findViewById(R.id.userNameTextView);
        }


    }

   /* @Override
    public void onClick(View v) {

        itemClickCallback.onItemClick(getAdapterPosition());

    }*/


    @Override
    public UserViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(mContext).inflate(R.layout.users_list,parent,false);
        UserViewHolder vh = new UserViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(UserViewHolder holder, int position) {
        holder.userTextView.setText((String)mDataSet.get(position));
    }


    @Override
    public int getItemCount() {
        return mDataSet.size();
    }




}

package com.example.enzo.chatbasedonlocation;

import android.content.Context;
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
        mDataSet = list;
        mContext = context;
    }

    public static class UserViewHolder extends RecyclerView.ViewHolder {
        TextView userTextView;

        public UserViewHolder(View v) {
            super(v);
            userTextView = (TextView) itemView.findViewById(R.id.userNameTextView);
        }
    }

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

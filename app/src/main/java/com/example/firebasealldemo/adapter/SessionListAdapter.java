package com.example.firebasealldemo.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.firebasealldemo.R;
import com.example.firebasealldemo.bean.ChatListBean;

import java.util.ArrayList;

/**
 * Created by seeker on 2017/2/27.
 */

public class SessionListAdapter extends RecyclerView.Adapter<SessionListAdapter.EditproductHolder> {
    public static Context ctx;
    public static ArrayList<ChatListBean> list;

    public SessionListAdapter(Context ctx, ArrayList<ChatListBean> list) {
        this.ctx = ctx;
        this.list = list;
    }

    @Override
    public SessionListAdapter.EditproductHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = View.inflate(ctx, R.layout.chat_list, null);
        return new EditproductHolder(view);
    }

    @Override
    public void onBindViewHolder(SessionListAdapter.EditproductHolder holder, int position) {
        initData(holder,position);
    }

    @Override
    public int getItemCount() {
        return 5;
    }

    public void initData(EditproductHolder holder, int position) {
//        ChatListBean itemBean = list.get(position);
//        holder.time.setText(itemBean.timestamp);
//        holder.last_message.setText(itemBean.lastMessage);
//        holder.friends_name.setText(itemBean.member);
//        GlideImageLoader.getInstance(ctx).displayImage(itemBean.fridlogo,holder.header);
    }

    public class EditproductHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private  TextView time, last_message,friends_name;
        private ImageView header;

        public EditproductHolder(View rootView) {
            super(rootView);
            time = (TextView) rootView.findViewById(R.id.time);
            last_message = (TextView) rootView.findViewById(R.id.last_message);
            friends_name = (TextView) rootView.findViewById(R.id.friends_name);
            header = (ImageView) rootView.findViewById(R.id.header);
            rootView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            if (click != null) {
                click.onRootItemclick(getAdapterPosition());
            }
        }
    }

    public void setOnItemClickListener(OnItemClick listener) {
        this.click = listener;
    }

    OnItemClick click;
    public interface OnItemClick{
       void  onRootItemclick(int position);
    }
}

package com.example.firebasealldemo.adapter;

import android.content.Context;
import android.graphics.BitmapFactory;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.firebasealldemo.R;
import com.example.firebasealldemo.bean.SessionBean;
import com.example.firebasealldemo.view.CircleImageView;

import java.util.ArrayList;

/**
 * Created by seeker on 2017/2/27.
 */

public class SessionAdapter extends RecyclerView.Adapter<SessionAdapter.EditproductHolder> {
    public static Context ctx;
    private static BitmapFactory.Options opts;
    public static ArrayList<SessionBean> list;

    public SessionAdapter(Context ctx, ArrayList<SessionBean> list) {
        this.ctx = ctx;
        this.list = list;
        opts = new BitmapFactory.Options();
        opts.inSampleSize = 6;
    }

    @Override
    public SessionAdapter.EditproductHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = View.inflate(ctx, R.layout.item_session, null);
        return new EditproductHolder(view);
    }

    @Override
    public void onBindViewHolder(SessionAdapter.EditproductHolder holder, int position) {
        initData(holder,position);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public void initData(EditproductHolder holder, int position) {
        SessionBean sessionBean = list.get(position);
        if (sessionBean.isReceive) {
            holder.iv_receive.setVisibility(View.VISIBLE);
            holder.iv_send.setVisibility(View.GONE);
            holder.tvReceive.setVisibility(View.VISIBLE);
            holder.tvSend.setVisibility(View.GONE);
            holder.tvReceive.setText(sessionBean.content);
        } else {
            holder.iv_receive.setVisibility(View.GONE);
            holder.iv_send.setVisibility(View.VISIBLE);
            holder.tvReceive.setVisibility(View.GONE);
            holder.tvSend.setVisibility(View.VISIBLE);
            holder.tvSend.setText(sessionBean.content);
        }
    }

    public class EditproductHolder extends RecyclerView.ViewHolder {

        private final CircleImageView iv_send, iv_receive;
        private final TextView tvSend, tvReceive;

        public EditproductHolder(View rootView) {
            super(rootView);
            iv_send = (CircleImageView) rootView.findViewById(R.id.iv_send);
            iv_receive = (CircleImageView) rootView.findViewById(R.id.iv_receive);
            tvSend = (TextView) rootView.findViewById(R.id.textSend);
            tvReceive = (TextView) rootView.findViewById(R.id.textReceive);
        }
    }
}

package com.example.firebasealldemo.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.firebasealldemo.R;
import com.example.firebasealldemo.activity.session.SessionActivity;
import com.example.firebasealldemo.bean.SessionBean;
import com.example.firebasealldemo.utils.imageUtil.GlideImageLoader;
import com.example.firebasealldemo.utils.imageUtil.SPUtil;
import com.example.firebasealldemo.view.CircleImageView;

import java.util.ArrayList;

/**
 * Created by seeker on 2017/2/27.
 */

public class SessionAdapter extends RecyclerView.Adapter<SessionAdapter.EditproductHolder> {
    public static Context ctx;
    public static ArrayList<SessionBean> list;
    public boolean isFirst = true;

    public SessionAdapter(Context ctx, ArrayList<SessionBean> list) {
        this.ctx = ctx;
        this.list = list;
//        this.list = new ArrayList<>();
//        if (list != null) {
//         this.list.addAll(list);
//        }
    }

    @Override
    public SessionAdapter.EditproductHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = View.inflate(ctx, R.layout.item_session, null);
        return new EditproductHolder(view);
    }

    @Override
    public void onBindViewHolder(SessionAdapter.EditproductHolder holder, int position) {
        initData(holder, position);
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
            if (isFirst) {
            GlideImageLoader.getInstance(ctx).displayImage(SessionActivity.friendsLogo, holder.iv_receive);

            }
//            GlideImageLoader.getInstance(ctx).displayImage(
// "https://firebasestorage.googleapis.com/v0/b/fir-alldemo-6e7e0.appspot.com/o/imaes%2FJ72v7pA3VaSrqVGvzOJaO2uO3lI3.jpeg?alt=media&token=cbf45bd5-277b-4f9d-aff6-553f0cbeb4b7", holder.iv_receive);
            holder.tvReceive.setVisibility(View.VISIBLE);
            holder.tvSend.setVisibility(View.GONE);
            holder.tvReceive.setText(sessionBean.content);
        } else {
            holder.iv_receive.setVisibility(View.GONE);
            holder.iv_send.setVisibility(View.VISIBLE);
            if (isFirst) {
                GlideImageLoader.getInstance(ctx).displayImage(SPUtil.getString("logo", ""), holder.iv_send);
            }
//            GlideImageLoader.getInstance(ctx).displayImage("https://firebasestorage.googleapis.com/v0/b/fir-alldemo-6e7e0.appspot.com/o/imaes%2FJ72v7pA3VaSrqVGvzOJaO2uO3lI3.jpeg?alt=media&token=cbf45bd5-277b-4f9d-aff6-553f0cbeb4b7", holder.iv_send);
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

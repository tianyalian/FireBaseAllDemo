package com.example.firebasealldemo.bean;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by seeker on 2017/4/18.
 */

public class SessionBean implements Parcelable {
    public String content,img_receive,img_send;
    public boolean isReceive;

    public SessionBean(String content, String img_receive, String img_send, boolean isReceive) {
        this.content = content;
        this.img_receive = img_receive;
        this.img_send = img_send;
        this.isReceive = isReceive;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.content);
        dest.writeString(this.img_receive);
        dest.writeString(this.img_send);
        dest.writeByte(this.isReceive ? (byte) 1 : (byte) 0);
    }

    protected SessionBean(Parcel in) {
        this.content = in.readString();
        this.img_receive = in.readString();
        this.img_send = in.readString();
        this.isReceive = in.readByte() != 0;
    }

    public static final Parcelable.Creator<SessionBean> CREATOR = new Parcelable.Creator<SessionBean>() {
        @Override
        public SessionBean createFromParcel(Parcel source) {
            return new SessionBean(source);
        }

        @Override
        public SessionBean[] newArray(int size) {
            return new SessionBean[size];
        }
    };
}

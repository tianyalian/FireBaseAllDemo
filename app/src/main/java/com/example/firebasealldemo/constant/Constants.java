package com.example.firebasealldemo.constant;

import android.os.Environment;

/**
 * Created by seeker on 2017/4/20.
 */

public class Constants {
    public static final String ROOT_gs = "gs://fir-alldemo-6e7e0.appspot.com"
                                , root_sdCard = Environment.getExternalStorageDirectory().getPath()+"/"
                                , header_refence = "imaes/header.jpeg"
                                , realTimeDb_refence = "fir-alldemo-6e7e0"
                                ,Users="users",
                                id = "id",
                                createTime = "createTime",
                                level = "level",
                                nick = "nick",
                                birthday = "birthday",
                                cellphoneNumber = "cellphoneNumber",
                                emailAddress = "emailAddress",
                                name = "name",
                                address = "address",
                                friends = "friends",
                                logo = "logo"
                                ,ChatList="chatlist"
                                ,Messages="messages"
                                ,UserID="userId"
                                ,messageid="123456789"
                                , sp = "sp"
                                ,content="content://notify"
                                ,UserCount="userCount"
                                ,image="imaes/"
                                ,header_compressed= root_sdCard +"0.jpg";


}

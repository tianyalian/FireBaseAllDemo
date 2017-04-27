package com.example.firebasealldemo.bean;

import java.util.ArrayList;

/**
 * Created by seeker on 2017/4/21.
 */

public class User {
    public User() {
    }

    public User(String id, String createTime, String level, String nick,
                 String birthday, String cellphoneNumber,
                String emailAddress, String name, String logo,
                String address, ArrayList<String> friends) {
        this.id = id;
        this.createTime = createTime;
        this.level = level;
        this.nick = nick;
        this.birthday = birthday;
        this.cellphoneNumber = cellphoneNumber;
        this.emailAddress = emailAddress;
        this.name = name;
        this.address = address;
        this.friends = friends;
        this.logo = logo;
    }

    /**
     * id : 00000000-0000-0000-0000-000000000000
     * createTime : 0001-01-01 00:00:00
     * String : 0
     * nick : string
     * gender : false
     * birthday : 0001-01-01 00:00:00
     * cellphoneNumber : string
     * emailAddress : string
     * name : string
     * picture : string
     * address : string
     * friends:
     */

    public String id;
    public String createTime;
    public String level;
    public String nick;
    public String birthday;
    public String cellphoneNumber;
    public String emailAddress;
    public String name;
    public String address;
    public String logo;
    public ArrayList<String> friends;

}

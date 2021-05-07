package com.group.foodmanagement.repository;

import com.group.foodmanagement.model.User;

import java.util.ArrayList;
import java.util.List;

public class UserRepository {

    static User loginUser;
    static List<User> users;

    static
    {
        users = new ArrayList<>();
        users.add(new User("mao","1"));
        users.add(new User("user1","1"));
    }

    public static User login(String username,String password){
        for(User u : users){
            if(u.getUsername().equals(username) && u.getPassword().equals(password)){
                setLoginUser(u);
                return u;
            }
        }
        return null;
    }

    public static void setLoginUser(User user) {
        loginUser = user;
    }
    public static User getLoginUser(){
        if(loginUser == null){
            return new User("ano","1");
        }
        return loginUser;
    }


}

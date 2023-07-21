package com.entity;

import java.util.Scanner;

public class ManageUser extends User{
    public ManageUser (String account, String password){
        super(account,password);
    }
    public String getStorage(){
        return String.format("%s %s",account,password);
    }
    public ManageUser(){

    }
}

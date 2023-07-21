package com.entity;

import com.exception.JumpExeception;

public abstract class User implements TableFillIn {

    String account;

    String password;
    public User(){

    }
    public void signIn(Table table) {
        this.fillInSignInTable(table);
    }
    public User(String account, String password){
        this.account = account;
        this.password = password;
    }
    public void setSign(SignInTable table){
        this.account = table.getInfo()[0];
        this.password = table.getInfo()[1];
    }
    public void signUp(Table table) throws JumpExeception {
        this.fillInSignUpTable(table);
    }
    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }



}

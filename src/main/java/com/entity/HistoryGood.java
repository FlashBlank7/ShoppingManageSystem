package com.entity;

import java.text.SimpleDateFormat;
import java.util.Date;

public class HistoryGood {
    CartGood cartGood;
    Date date;
    public HistoryGood(CartGood cartGood){
        date = new Date();
        this.cartGood = cartGood;
    }
    public void showInfo(){
        System.out.println(cartGood.getInfo() + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format( new Date()));
    }
}

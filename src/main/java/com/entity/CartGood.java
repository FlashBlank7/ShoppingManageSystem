package com.entity;

public class CartGood {
    public static int count = 1;
    public Good good;
    int num;
    int id;
    public CartGood(Good good,int num){
        this.num = num;
        this.good = good;
        this.id = count;
        count++;
    }
    public String getInfo(){
        return String.format("%-4s",id)+good.getInfo(1)+String.format("%4s",num);
    }

    public int getId() {
        return id;
    }

    public void setNum(int num){this.num = num;}
    public int getNum(){return num;}

}

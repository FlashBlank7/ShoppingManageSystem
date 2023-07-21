package com.entity;

import com.controler.UserLevel;

import javax.xml.crypto.Data;
import java.io.Serializable;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class NormalUser extends User implements Serializable {

    private final static int locked = 1;
    private final static int unlocked = 0;
    private static int id_seed;
    private long id;
    String email;
    private int isLock;
    private UserLevel userLevel;
    private String phoneNumber;
    private double accumCon;
    private LocalDate signDate;
    private int count;
    private List<HistoryGood> historyGoods = new ArrayList<>();
    private List<CartGood> cartGoods = new ArrayList<>();


    static {
        id_seed = 0;
    }

    private String genUserLevel(){
        switch (this.userLevel){
            case GOLD_USER -> {return "Gold";}
            case SILVER_USER -> {return "Silver";}
            case LOWEST_USER -> {return "Copper";}
        }
        return null;
    }

    public static long genId(){
        id_seed += 1;
        return id_seed;
    }
    public long getId(){
        return this.id;
    }
    public NormalUser(long id, String account, String password,String email, UserLevel userLevel,String signDate, String phoneNumber, double money){
        super(account,password);
        this.id = id;
        this.signDate= parseDate(signDate);
        this.email = email;
        this.id = genId();
        this.userLevel = userLevel;
        this.phoneNumber = phoneNumber;
        this.accumCon = money;
        cartGoods = new ArrayList<>();
        historyGoods = new ArrayList<>();
        setUnLock();
    }
    public NormalUser(String account, String password,String email, UserLevel userLevel,String signDate, String phoneNumber, double money){
        super(account,password);
        this.signDate= parseDate(signDate);
        this.email = email;
        this.id = genId();
        this.userLevel = userLevel;
        this.phoneNumber = phoneNumber;
        this.accumCon = money;
        cartGoods = new ArrayList<>();
        historyGoods = new ArrayList<>();
        setUnLock();
    }
    public LocalDate parseDate(String signDate){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-M-d");
        return LocalDate.parse(signDate,formatter);
    }
    public NormalUser(String account, String password,String email, UserLevel userLevel,String signDate){
        super(account,password);
        this.email = email;
        this.signDate= LocalDate.now();
        this.id = genId();
        this.userLevel = userLevel;
        this.phoneNumber = "";
        this.accumCon = 0;
        cartGoods = new ArrayList<>();
        historyGoods = new ArrayList<>();
        setUnLock();
    }
    public NormalUser(String account, String password){
        super(account,password);
        this.signDate= LocalDate.now();
        this.email = "";
        this.id = genId();
        this.userLevel = UserLevel.LOWEST_USER;
        this.phoneNumber = "";
        this.accumCon = 0;
        cartGoods = new ArrayList<>();
        historyGoods = new ArrayList<>();
        setUnLock();
    }
    public NormalUser(String account, String password,String email){
        super(account,password);
        this.signDate= LocalDate.now();
        this.email = email;
        this.id = genId();
        this.userLevel = UserLevel.LOWEST_USER;
        this.phoneNumber = "";
        this.accumCon = 0;
        cartGoods = new ArrayList<>();
        historyGoods = new ArrayList<>();
        setUnLock();
    }
    public String getInfo(){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        String output;
        output= String.format("%-4s", this.id)+ String.format("%-15s",this.account) +String.format("%-8s",genUserLevel())
                +String.format("%-12s",this.signDate.format(formatter))+String.format("%-15s",this.email);
        return output + String.format("%-13s", phoneNumber) + String.format("%.2f", this.accumCon);


    }
    public String getStorage(){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        return String.format("%s %s %s %s %s %s %s %f",id,account,password,genUserLevel(),signDate.format(formatter),email,phoneNumber,accumCon);
    }
    public List<HistoryGood> getHistoryGoods() {
        return historyGoods;
    }

    public void setHistoryGoods(List<HistoryGood> historyGoods) {
        this.historyGoods = historyGoods;
    }

    public List<CartGood> getCartGoods() {
        return cartGoods;
    }

    public void setCartGoods(List<CartGood> cartGoods) {
        this.cartGoods = cartGoods;
    }

    public void setAccumCon(double money){
        accumCon = money;
    }
    public double getAccumCon(){
        return accumCon;
    }
    public int getCount(){
        return count;
    }
    public void setCount(int count){
        this.count = count;
    }
    public boolean isLocked(){
        return isLock==locked;
    }
    public void setEmail(String email) {
        this.email = email;
    }

    public String getEmail() {
        return email;
    }

    public void setLock() {
        this.isLock = locked;
    }
    public void setUnLock(){
        this.isLock = unlocked;
    }

    public void addGoods(Table table){

    }
//    public Table signUp(){
//        Table table = new SignInTable();
//        table.fillIn();
//        return table;
//    }



    private class DefineDate{
        String year;
        String month;
        String day;
        DefineDate(String date){

        }
    }

}

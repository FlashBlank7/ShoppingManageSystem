package com.entity;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.SimpleTimeZone;

public class Good {
    private int id;
    private double price;
    private String name;

    private String producer;
    // 看下这个对象怎么自定义？
    private LocalDate produceDate;
    private String model;
    private double importPrice;
    private int num;
    private static int id_seed;

    static{
        id_seed = 0;
    }

    public int genId(){
        id_seed += 1;
        return id_seed;
    }
    public String getStorage(){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        return String.format("%s %s %s %s %s %s %s %s",id, name,price,producer,produceDate.format(formatter),model,importPrice,num);
    }
    public LocalDate parseDate(String signDate){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-M-d");
        return LocalDate.parse(signDate,formatter);
    }
    public Good(String id,String name,double price,String producer,String produceDate, String model,double importPrice,int num){
        this.price =  price;
        this.name = name;
        this.id = Integer.parseInt(id);
        this.producer = producer;
        this.produceDate = parseDate(produceDate);
        this.model = model;
        this.importPrice = importPrice;
        this.num = num;

    }
    public Good(String name,double price,String producer,String produceDate, String model,double importPrice,int num){
        this.price =  price;
        this.name = name;
        this.id = genId();
        this.producer = producer;
        this.produceDate = parseDate(produceDate);
        this.model = model;
        this.importPrice = importPrice;
        this.num = num;

    }
    public Good(String name,double price){
        this.price =  price;
        this.name = name;
        this.id = genId();
    }
    public String getInfo(){

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        return String.format("%-5s",id) + String.format("%-16s",name) +String.format("%-6s",price)
        +String.format("%-15s",producer)+String.format("%-15s",produceDate.format(formatter)) +
                String.format("%-10s",model)+String.format("%-15.2f",importPrice)+String.format("%-6d",num);
    }
    public String getInfo(int temp){

        return String.format("%-16s",name) +String.format("%-6s",price);
    }

    public String getName(){return name;}
    public void setName(String name){this.name = name;}
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getProducer() {
        return producer;
    }

    public void setProducer(String producer) {
        this.producer = producer;
    }

    public LocalDate getProduceDate() {
        return produceDate;
    }

    public void setProduceDate(String produceDate) {
        this.produceDate = parseDate(produceDate);
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public double getImportPrice() {
        return importPrice;
    }

    public void setImportPrice(double importPrice) {
        this.importPrice = importPrice;
    }

    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
    }
}

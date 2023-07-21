package com.service;

import com.controler.ControlSignInStatus;
import com.controler.ControlTransInd;
import com.controler.UserLevel;
import com.entity.*;

import javax.print.DocFlavor;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class ShoppingManageSystem{
    public static String quitSignStr = "q";
    public static double quitSignDouble = 0;
    public static int quitSignInt = 0;
    static User user;
    static List<HistoryGood> historyGoods;
    static List<Good> goods = new ArrayList<>();
    static List<NormalUser> normalUsers = new ArrayList<>();
    static List<ManageUser> manageUsers = new ArrayList<>();
    static List<CartGood> cartGoods;
    static ControlSignInStatus signInStatus = ControlSignInStatus.NONE_SIGN_IN;
    private ControlTransInd conTransInd = ControlTransInd.SYSTEM_HOME;

    /**
     * 传入一个用户为当前系统中登录的账户
     * @param u
     */
    public static void setPreUsrMan(ManageUser u){
        user = u;
    }
    public static void setPreUsrNor(NormalUser u){
        user = u;
        historyGoods = ((NormalUser)user).getHistoryGoods();
        cartGoods = ((NormalUser)user).getCartGoods();
    }
//    public static boolean signInOrNot(){
//        return (signInStatus == ControlSignInStatus.MANAGE_SIGN || signInStatus == ControlSignInStatus.NORMAL_SIGN);
//    }
    public static UserLevel genUserLevel(String level){
        switch (level){
            case "Gold"->{
                return UserLevel.GOLD_USER;
            }
            case "Silver"->{
                return UserLevel.SILVER_USER;
            }
            case "Copper"->{
                return UserLevel.LOWEST_USER;
            }
        }
        return UserLevel.LOWEST_USER;
    }
    public static NormalUser loadNormalUserFromTxt() {
        try (BufferedReader reader = new BufferedReader(new FileReader("src/main/java/com/service/NormalUser.txt"))) {
            while (true){
                String line = reader.readLine();
                if (line != null) {
                    String[] data = line.split(" ");
                    String id = data[0];
                    String account = data[1];
                    String password = data[2];
                    UserLevel userLevel = genUserLevel(data[3]);
                    String signDate = data[4];
                    String email = data[5];
                    String phoneNumber = data[6];
                    double money = Double.parseDouble(data[7]);

                    normalUsers.add(new NormalUser(account, password, email, userLevel, signDate, phoneNumber, money));
                }else {
                    break;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
    public static NormalUser loadManageUserFromTxt() {
        try (BufferedReader reader = new BufferedReader(new FileReader("src/main/java/com/service/ManageUser.txt"))) {
            while (true){
                String line = reader.readLine();
                if (line != null) {
                    String[] data = line.split(" ");
                    String account = data[0];
                    String password = data[1];

                    manageUsers.add(new ManageUser(account,password));
                }else {
                    break;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
    public static NormalUser loadGoodrFromTxt() {
        try (BufferedReader reader = new BufferedReader(new FileReader("src/main/java/com/service/Goods.txt"))) {
            while (true){
                String line = reader.readLine();
                if (line != null) {
                    String[] data = line.split(" ");
                    String id = data[0];
                    String name = data[1];
                    double price = Double.parseDouble(data[2]);
                    String producer = data[3];
                    String produceDate = data[4];
                    String model = data[5];
                    double importPrice = Double.parseDouble(data[6]);
                    int num = Integer.parseInt(data[7]);

                    goods.add(new Good(id, name, price, producer, produceDate, model, importPrice,num));
                }else {
                    break;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
    // 将NormalUser对象保存为txt文件
    public void saveNormalUserToTxt() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("src/main/java/com/service/NormalUser.txt"))) {
            for (NormalUser user: normalUsers) {
                String line = user.getStorage()+"\n";
                writer.write(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void saveManagerUserToTxt() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("src/main/java/com/service/ManageUser.txt"))) {
            for (ManageUser user: manageUsers) {
                String line = user.getStorage()+"\n";
                writer.write(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void saveGoodsToTxt() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("src/main/java/com/service/Goods.txt"))) {
            for (Good good: goods) {
                String line = good.getStorage()+"\n";
                writer.write(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    static {
//Good(String name,double price,String producer,String produceDate, String model,double importPrice,int num)
//        goods.add(new Good("Noodle",4.9,"KonSifu","2022-2-24","GD-34",3.0,80));
//        goods.add(new Good("Pen",3.2,"SimSquare","2023-4-8","GH-2",2,400));
//        goods.add(new Good("PortablePot",16.5,"MikkyTasty","2023-7-1","JK-4",13.8,20));

//        1:c4ca4238a0b923820dcc509a6f75849b
//        pass10000:ce4539f58252a1c99f6ce2ae77a87b0b
//        pass10001:120b506e73fa9b09021f83919c1dc6ec
//        pass10002:83c4463d3d0fb91f6f41e08b2bb907b0
//        normalUsers.add(new NormalUser("1","c4ca4238a0b923820dcc509a6f75849b","1@ynu.com", UserLevel.GOLD_USER,"2014-5-21","13888888888",888888.8));
//        normalUsers.add(new NormalUser("normalUser1","ce4539f58252a1c99f6ce2ae77a87b0b","10000@ynu.com",UserLevel.SILVER_USER,"2019-12-21","15336388888",3400.5));
//        normalUsers.add(new NormalUser("normalUser2","120b506e73fa9b09021f83919c1dc6ec","10001@ynu.com",UserLevel.LOWEST_USER,"2021-5-21","14738389968",200));
//        normalUsers.add(new NormalUser("normalUser3","83c4463d3d0fb91f6f41e08b2bb907b0","10002@ynu.com",UserLevel.LOWEST_USER,"2022-5-21","13267553834",380));
//        ynuinfo#777:49f06cf86f1c826e55d0428fd3402e83
//        1:c4ca4238a0b923820dcc509a6f75849b
//        manageUsers.add(new ManageUser("admin","49f06cf86f1c826e55d0428fd3402e83"));
//        manageUsers.add(new ManageUser("1","c4ca4238a0b923820dcc509a6f75849b"));

        loadManageUserFromTxt();
        loadNormalUserFromTxt();
        loadGoodrFromTxt();
    }


    public void start(){
        while (true){
            switch (conTransInd){
                case SYSTEM_SHUT -> {
                    saveNormalUserToTxt();
                    saveManagerUserToTxt();
                    saveGoodsToTxt();
                    return;
                } //
                case SYSTEM_HOME -> conTransInd = new SystemHomePageService().run(); //
                case SHOPPING_HOME -> conTransInd = new ShoppingHomePageService().run(); //
                case SHOPPING_MARKET -> conTransInd = new ShoppingMarketPageService().run();//
                case SHOPPING_CART -> conTransInd = new ShoppingCartPageService().run();//
                case PASSWORD_SETTING -> conTransInd = new PasswordPageService().run();
                case MANAGE_HOME -> conTransInd = new ManageHomePageService().run();
                case GOODS_MANAGE -> conTransInd = new GoodsManagePageService().run();
                case CUSTOM_MANAGE -> conTransInd = new CustomersManagePageService().run();
                case PASSWORD_MANAGE -> conTransInd = new PasswordManagePageService().run();
                default -> {
                    System.out.println("默认解决方案——重启——执行");
                    conTransInd = ControlTransInd.SYSTEM_HOME;
                }
            }
        }
    }

    public void storeInfo() {

    }
    public void storeNormalUser(){
        for (NormalUser user: normalUsers) {
            String storage = user.getStorage();
        }
    }
}

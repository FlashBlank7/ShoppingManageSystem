package com.entity;

import com.utils.InputDetectUtils;
import com.exception.JumpExeception;

import java.util.Scanner;

import static com.service.ShoppingManageSystem.quitSignStr;

public interface TableFillIn {
    public default void fillInSignInTable(Table table){
        Scanner scan = new Scanner(System.in);
        System.out.println("输入账号：");
        String account = inputWithDetect();
        System.out.println("输入密码：");
        String password = inputWithDetect();
        table.setInfo(new String[]{account,password});
    };

    public default void fillInSignUpTable(Table table) throws JumpExeception{
        Scanner scan = new Scanner(System.in);
        System.out.println("新用户注册");
        System.out.println("输入账号：");
        String account = InputDetectUtils.strInput();
        System.out.println("输入密码：");
        String password = getPasswordString();
        table.setInfo(new String[]{account,password});
    };

//    public default void fillAddTabel(AddGoodsTable table){
//        Scanner scanner = new Scanner(System.in);
//        String temp =
//    }

    public default String inputWithDetect(){
        Scanner scan = new Scanner(System.in);
        String temp;
        while(true){
            try {
                temp = scan.nextLine();
                break;
            }catch (Exception e){
                System.out.println("请给出合法输入");
                scan.next();
            }
        }
        return temp;
    }

    public static String getPasswordString() throws JumpExeception {
        String password;
        while (true){
            password = InputDetectUtils.strInput();
            if (password.equals(quitSignStr))
                throw new JumpExeception();
            if(InputDetectUtils.isValidPassword(password))
                break;
            else
                System.out.println("请输入正确的密码格式，长度必须大于8，必须是大小写字母、数字和标点符号的组合，输入"+quitSignStr+"返回：");
        }
        return password;
    }
}

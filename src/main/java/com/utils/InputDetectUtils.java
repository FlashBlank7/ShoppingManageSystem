package com.utils;

import com.exception.JumpExeception;
import com.service.ShoppingManageSystem;

import java.util.Scanner;

import static com.service.ShoppingManageSystem.quitSignInt;
import static com.service.ShoppingManageSystem.quitSignStr;
import static com.service.ShoppingManageSystem.quitSignDouble;

public interface InputDetectUtils {
    public static double doubleInput(){
        Scanner scan = new Scanner(System.in);
        double input;
        while (true){
            try {
                input = scan.nextDouble();
                break;
            }catch (Exception e){
                System.out.println("请输入符合规格的数！");
                scan.next();
            }
        }
        return input;
    }
    public static int intInput(int sub, int inf) {
        Scanner scan = new Scanner(System.in);
        int input;
        while (true){
            try {
                input = scan.nextInt();
                if(input>inf || input < sub){
                    System.out.println("请输入"+sub+"到"+inf+"的整数");
                }else
                    break;
            }catch (Exception e){
                System.out.println("请输入一个整数！");
                scan.next();
            }
        }
        return input;
    }
    public static String strInputWithException (String noteInfo) throws JumpExeception{
        String jumpInfo = "(输入"+ quitSignStr+"退出)：";
        System.out.println(noteInfo + jumpInfo);
        String input = strInput();
        if (input.equals(quitSignStr))
            throw new JumpExeception();
        return input;
    }
    public static double doubleInputWithException (String noteInfo) throws JumpExeception{
        String jumpInfo = "(输入"+ ShoppingManageSystem.quitSignDouble+"退出)：";
        System.out.println(noteInfo + jumpInfo);
        double input = doubleInput();
        if (input == quitSignDouble)
            throw new JumpExeception();
        return input;
    }
    public static int intInputWithException (String noteInfo) throws JumpExeception{
        String jumpInfo = "(输入"+ ShoppingManageSystem.quitSignDouble+"退出)：";
        System.out.println(noteInfo + jumpInfo);
        int input = intInput(0,65525);
        if (input == quitSignInt)
            throw new JumpExeception();
        return input;
    }
    public static String strInput(){
        Scanner scan = new Scanner(System.in);
        String input = scan.next();
        return input;
    }
    public static boolean isValidEmail(String email) {
        String regex = "^[A-Za-z0-9]+@[A-Za-z0-9]+\\.[A-Za-z0-9]+$";
        return email.matches(regex);
    }
    public static boolean isValidPassword(String password) {
        // 使用正则表达式进行验证
        String patternLowCase = ".*[a-z]+.*";
        String patternUpCase = ".*[A-Z]+.*";
        String patternNumber = ".*[0-9]+.*";
        String patternCharacter = ".*[^A-Za-z0-9]+.*";
        if (password.length() >= 8 && password.matches(patternUpCase) && password.matches(patternLowCase) && password.matches(patternNumber) && password.matches(patternCharacter))
            return true;
        return false;

    }
//    public static int[] intInputForIdAndNum(){
//        Scanner scan = new Scanner(System.in);
//        /* id:int[0], num:int[1]*/
//        int[]input = new int[2];
//        for (int i = 0; i < 2; i++){
//            input[i] = intInput(0,65525);
//
//        }
//
//        return input;
//    }

//    public static String idInput(){
//        Scanner scan = new Scanner(System.in);
//        String temp;
//        while (true){
//            try {
//                return null;
////                Integer.parseInt(scan.next());
//            }catch (Exception e){
//                return null;
//            }
//        }
//    }
}

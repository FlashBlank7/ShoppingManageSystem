package com.service;

import com.controler.ControlSignInStatus;
import com.controler.ControlTransInd;
import com.controler.DefaultPrintInformation;
import com.utils.InputDetectUtils;

public class ShoppingHomePageService {
    public void show(){
        System.out.println("购物主界面");
        System.out.println("1——商城");
        System.out.println("2——购物车");
        System.out.println("3——密码设置");
        System.out.println("4——退出当前账号");
        System.out.println("0——返回");
    }
    public ControlTransInd run(){
        show();// 显示主界面
        int choice = InputDetectUtils.intInput(0,4);
        switch (choice){
            case 4->{
                System.out.println("正在退出当前账号");
                ShoppingManageSystem.signInStatus = ControlSignInStatus.NONE_SIGN_IN;
                ShoppingManageSystem.user = null;
                return genTransInd(4);
            }
            default -> {}
        }
        return genTransInd(choice);
    }

    public ControlTransInd genTransInd(int choice){
        switch (choice){
            case 0-> {
                return ControlTransInd.SYSTEM_HOME;
            }
            case 1-> {
                return ControlTransInd.SHOPPING_MARKET;
            }
            case 2-> {
                return ControlTransInd.SHOPPING_CART;
            }
            case 3-> {
                return ControlTransInd.PASSWORD_SETTING;
            }
            case 4->{
                return ControlTransInd.SYSTEM_HOME;
            }
        }
        DefaultPrintInformation.unknownErrorPrint();
        return ControlTransInd.SYSTEM_HOME;
    }
}

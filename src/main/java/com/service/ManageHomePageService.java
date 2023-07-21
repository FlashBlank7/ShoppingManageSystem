package com.service;

import com.controler.ControlTransInd;
import com.controler.DefaultPrintInformation;
import com.utils.InputDetectUtils;

public class ManageHomePageService {
    public void show(){
        System.out.println("1-密码管理");
        System.out.println("2-客户管理");
        System.out.println("3-商品管理");
        System.out.println("0-退出登录");
//        System.out.println("0-返回主界面");
    }
    public ControlTransInd run(){
        show();
        int choice = InputDetectUtils.intInput(0,3);
        return genTransInd(choice);
    }
    public ControlTransInd genTransInd(int choice){
        switch (choice){
            case 0-> {
                return ControlTransInd.SYSTEM_HOME;
            }
            case 1-> {
                return ControlTransInd.PASSWORD_MANAGE;
            }
            case 2-> {
                return ControlTransInd.CUSTOM_MANAGE;
            }
            case 3->  {
                return ControlTransInd.GOODS_MANAGE;
            }
        }
        DefaultPrintInformation.unknownErrorPrint();
        return ControlTransInd.SYSTEM_HOME;

    }
}



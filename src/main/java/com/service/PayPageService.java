package com.service;

import com.controler.ControlTransInd;

public class PayPageService{
    public void show(){
        System.out.println("我是支付界面");
    }

    public ControlTransInd run(){
        show();
        System.out.println("接下来是我要执行的逻辑");
        return null;
    }
    public ControlTransInd genTransInd(int choice){
        return null;
    }
}

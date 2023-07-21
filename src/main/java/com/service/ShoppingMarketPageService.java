package com.service;

import com.controler.ControlTransInd;
import com.controler.DefaultPrintInformation;
import com.utils.InputDetectUtils;
import com.entity.CartGood;
import com.entity.Good;
import com.exception.JumpExeception;

import java.util.Scanner;


import static com.service.ShoppingManageSystem.quitSignInt;

public class ShoppingMarketPageService{
    public void show(){
        System.out.println("购物界面");
    }

    public void showGoods(){
        for (Good good:ShoppingManageSystem.goods) {
            System.out.println(good.getInfo());
        }
    }

    public ControlTransInd run(){
        Scanner scan = new Scanner(System.in);
        while (true){
            show();
            showGoods();
            while (true){
                System.out.println("-----输入编号和个数，将物品加入购物车---输入"+quitSignInt+"返回--");
                try {
                    if(AddCartGood())
                        System.out.println("添加成功!");
                    else
                        System.out.println("添加失败，没有该商品");
                }catch (JumpExeception je){
                    return genTransInd(0);
                }
            }
//            return null;
        }
    }

    /**
     * 处理将新商品加入购物车的逻辑，并返回是否加入成功的布尔值与调用方法通讯，将加入商品的逻辑和显示结果的逻辑分开。
     * @return
     * @throws JumpExeception：自定义的异常，用于从被调用方法的业务逻辑中退出并与调用方法通讯
     */
    public boolean AddCartGood() throws JumpExeception{
        int[] input = idAndNumInput();
        for (Good good: ShoppingManageSystem.goods){
            if(good.getId() == input[0]){
                ShoppingManageSystem.cartGoods.add(new CartGood(good,input[1]));
                return true;
            }
        }
        return false;
    }
    public int[] idAndNumInput() throws JumpExeception{
        int id = InputDetectUtils.intInputWithException("输入编号");
        int num = InputDetectUtils.intInputWithException("输入个数");
        return new int[]{id, num};
    }

    public ControlTransInd genTransInd(int choice){
        switch (choice){
            case 0:return ControlTransInd.SHOPPING_HOME;
//            default:return ControlTransInd.SYSTEM_NULL;
        }
        DefaultPrintInformation.unknownErrorPrint();
        return ControlTransInd.SYSTEM_NULL;
    }
}

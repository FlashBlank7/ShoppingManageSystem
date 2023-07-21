package com.service;

import com.controler.ControlTransInd;
import com.controler.DefaultPrintInformation;
import com.utils.InputDetectUtils;
import com.entity.NormalUser;
import com.exception.JumpExeception;

import java.util.MissingFormatArgumentException;

public class CustomersManagePageService {

    public void show(){
        System.out.println("客户管理");
        System.out.println("1-所有客户信息");
        System.out.println("2-查询客户信息");
        System.out.println("3-删除客户信息");
        System.out.println("0-返回");
    }
    public ControlTransInd run(){
        while (true){
            show();
            int choice = InputDetectUtils.intInput(0,4);
            switch (choice){
                case 0->{
                    return genTransInd(choice);
                }
                case 1->{
                    System.out.println("客户信息");
                    showCustomerInfoBar();
                    showCustomers();
                }
                case 2->{
                    try{
                        if (searchCustomer())
                            System.out.println("信息如上");
                        else
                            DefaultPrintInformation.unknownErrorPrint();
                    }catch (JumpExeception jr){
                        DefaultPrintInformation.backInfoPrint();
                    }
                }
                case 3->{
//                System.out.println("输入编号删除商品");
                    try{
                        if(removeCustomers())
                            System.out.println("删除成功");
                        else
                            System.out.println("删除失败，没有对应的账号的用户");

                    }catch (JumpExeception je){
                        DefaultPrintInformation.backInfoPrint();
                    }
                }
            }
        }
    }
    public void showCustomerInfoBar(){
        System.out.println(String.format("%-4s", "ID")+ String.format("%-15s","Account") +String.format("%-8s","Level")
                +String.format("%-12s","SignDate")+String.format("%-15s","Email")+ String.format("%-13s", "PhoneNumber") + String.format("%-15s","MoneyConsuption"));
    }
    public boolean searchCustomer() throws JumpExeception{
        String account = InputDetectUtils.strInputWithException("输入用户的账号或者ID查询用户信息");
        for (NormalUser user:ShoppingManageSystem.normalUsers){
            if(user.getAccount().equals(account)){
                showCustomerInfoBar();
                System.out.println(user.getInfo());
                return true;
            }
        }
        try {
            long id = Long.parseLong(account);
            for (NormalUser user:ShoppingManageSystem.normalUsers){
                if(user.getId() == id){
                    System.out.println(user.getInfo());
                    return true;
                }
            }
        }catch (MissingFormatArgumentException e){

        }
        return false;
    }
    public void showCustomers(){
        for (NormalUser user:ShoppingManageSystem.normalUsers) {
            System.out.println(user.getInfo());
        }
    }
    public ControlTransInd genTransInd(int choice){
        switch (choice){
            case 0 ->{
                return ControlTransInd.MANAGE_HOME;
            }
        }
        DefaultPrintInformation.unknownErrorPrint();
        return ControlTransInd.CUSTOM_MANAGE;
    }
    public boolean removeCustomers() throws JumpExeception{
        String account = InputDetectUtils.strInputWithException("输入要删除用户的账号");
        for (NormalUser user:ShoppingManageSystem.normalUsers){
            if(user.getAccount().equals(account)){
                removal(user);
                return true;
            }
        }
        return false;
    }
    public void removal(NormalUser user) throws JumpExeception{
        String input = InputDetectUtils.strInputWithException("用户查找成功，你真的要删除该用户吗？输入y继续删除");
        ShoppingManageSystem.normalUsers.remove(user);
    }
}
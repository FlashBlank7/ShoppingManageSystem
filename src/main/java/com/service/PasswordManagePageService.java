package com.service;

import com.controler.ControlTransInd;
import com.controler.DefaultPrintInformation;
import com.utils.InputDetectUtils;
import com.entity.ManageUser;
import com.entity.NormalUser;
import com.exception.JumpExeception;
import com.utils.MD5Utils;

import static com.service.ShoppingManageSystem.quitSignStr;

public class PasswordManagePageService {
    public void show(){
        System.out.println("密码管理");
        System.out.println("1-修改管理员密码");
        System.out.println("2-重置指定客户密码");
        System.out.println("0-返回");
    }
    public ControlTransInd run(){
        while (true){
            show();// 显示主界面
            int choice = InputDetectUtils.intInput(0,3);
            switch (choice){
                case 0->{
                    return genTransInd(choice);
                }
                case 1->{
                    try{
                        if(revisePasswordService())
                            System.out.println("密码修改成功");
                        else
                            DefaultPrintInformation.unknownErrorPrint();
                    }catch (JumpExeception je){
                        DefaultPrintInformation.backInfoPrint();
                    }
                }
                case 2->{
                    try {
                        if(resetPasswordForNor()){
                            System.out.println("密码重置成功");
                        }else {
                            DefaultPrintInformation.unknownErrorPrint();
                        }
                    }catch (JumpExeception je){
                        DefaultPrintInformation.backInfoPrint();
                    }
                }
            }
        }


    }
    public boolean resetPasswordForNor() throws JumpExeception{
        String account;
        NormalUser user;

        while(true){
            account = InputDetectUtils.strInputWithException("重置用户密码——输入要重置用户密码的用户名");
            user = findUser(account);
            if (user == null){
                System.out.println("不存在这个用户，请重新输入用户账号：");
                continue;
            }
            break;
        }
        String password = getPasswordString("输入重置后的密码");
        user.setPassword(MD5Utils.generateMD5(password));
        return true;
    }
    public NormalUser findUser(String account){
        for (NormalUser user: ShoppingManageSystem.normalUsers) {
            if (user.getAccount().equals(account))
                return user;
        }
        return null;
    }
    public ControlTransInd genTransInd(int choice){
        switch (choice){
            case 0-> {
                return ControlTransInd.MANAGE_HOME;
            }
        }
        DefaultPrintInformation.unknownErrorPrint();
        return ControlTransInd.SYSTEM_HOME;
    }
    public boolean revisePasswordService() throws JumpExeception{
        String input;
        String md5Password;
//        String temp1,temp2;
        while (true){
            input = InputDetectUtils.strInputWithException("请输入原密码");
            md5Password = MD5Utils.generateMD5(input);
            if(ShoppingManageSystem.user.getPassword().equals(md5Password)){
                while (true){
                    input = getPasswordString("请输入新密码");
                    String temp = getPasswordString("再次输入新密码");
                    if (input.equals(temp))
                        break;
                    else
                        System.out.println("新旧密码不一致，请重新输入");
                }
                break;
            } else
                System.out.println("原密码错误，请重新输入");
        }
        md5Password = getPasswordString(input);
        if(revisePasswordMan(input, ShoppingManageSystem.user.getAccount())){
            return true;
        }
        return false;
    }
    public static String getPasswordString(String info) throws JumpExeception{
        String password;
        while (true){
            password = InputDetectUtils.strInputWithException(info);
            if(InputDetectUtils.isValidPassword(password))
                break;
            else
                System.out.println("请输入正确的密码格式，长度必须大于8，必须是大小写字母、数字和标点符号的组合，输入"+quitSignStr+"返回：");
        }
        return password;
    }

    public boolean revisePasswordMan(String password, String account){
        for (ManageUser user:ShoppingManageSystem.manageUsers) {
            if(user.getAccount().equals(account)){
                user.setPassword(password);
                return true;
            }
        }
        return false;
    }

}

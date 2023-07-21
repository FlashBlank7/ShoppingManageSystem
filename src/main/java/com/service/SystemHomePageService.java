package com.service;

import com.controler.ControlSignInStatus;
import com.controler.ControlTransInd;
import com.controler.DefaultPrintInformation;
import com.utils.InputDetectUtils;
import com.entity.*;
import com.exception.JumpExeception;
import com.utils.MD5Utils;

//import com.service.ShoppingManageSystem;

public class SystemHomePageService {


    /**
     *
     * @return 转移控制信号
     */
    public ControlTransInd run(){
        while (true){
            show();// 显示主界面
            int choice = InputDetectUtils.intInput(0,3);
            switch (choice){
                case 0 -> {
                    return genTransInd(choice);
                }
                case 1 ->{
                    if(ShoppingManageSystem.signInStatus == ControlSignInStatus.NORMAL_SIGN){
                        System.out.println("检测到你已经登录，正在进入商场，");
                        System.out.println("用户名是："+ ShoppingManageSystem.user.getAccount());
                        return genTransInd(choice);
                    }
                    try {
                        if (SignIn((account, password)->{
                            for (NormalUser user: ShoppingManageSystem.normalUsers) {
                                if(user.getAccount().equals(account)){
                                    if(user.getCount() == 5){
                                        System.out.println("该账户已锁定，请联系工作人员解锁");
                                        return null;
                                    }
                                    if(user.getPassword().equals(password)){
                                        ShoppingManageSystem.setPreUsrNor(user);
                                        return user;
                                    }
                                    else{
                                        user.setCount(user.getCount() + 1);
                                        System.out.println("密码错误，该账户还有" +(5-user.getCount())+"次机会");
                                        if(user.getCount() == 5)
                                            System.out.println("已锁定该账户");
                                        return null;
                                    }
                                }
                            }
                            System.out.println("该账户不存在");
                            return null;
                        })) {
                            System.out.println("登录成功");
                            ShoppingManageSystem.signInStatus = ControlSignInStatus.NORMAL_SIGN;
                            return genTransInd(choice);
                        } else{
                            DefaultPrintInformation.backInfoPrint();
                        }
                    }catch (JumpExeception je){

                    }
                }
                case 2 ->{
                    if(ShoppingManageSystem.signInStatus == ControlSignInStatus.MANAGE_SIGN) {
                        System.out.print("检测到你已经以管理员身份登录，正在以管理员身份进入商场，");
                        System.out.println("用户名是："+ ShoppingManageSystem.user.getAccount());
                        return genTransInd(choice);
                    }
                    try {
                        if(SignIn((account, password)->{
                            for (ManageUser user: ShoppingManageSystem.manageUsers) {
                                if(user.getAccount().equals(account))
                                    if(user.getPassword().equals(password)){
                                        ShoppingManageSystem.setPreUsrMan(user);
                                        return user;
                                    }
                                    else{
                                        System.out.println("账号或者密码错误");
                                        return null;
                                    }
                            }
                            System.out.println("账号或者密码错误");
                            return null;
                        })){
                            System.out.println("登录成功，欢迎管理员");
                            ShoppingManageSystem.signInStatus = ControlSignInStatus.MANAGE_SIGN;// 设置当前登录状态
                            return genTransInd(choice);
                        }
                        else{
                            System.out.println("用户名或者密码错误");
                        }
                    }catch (JumpExeception je){

                    }

                }
                case 3 ->{
                    System.out.println("新用户注册");
                    try {
                        if(registration()){
                            System.out.println("注册成功");
                            System.out.println("正在登入");
                            System.out.println("登入成功");
                            ShoppingManageSystem.signInStatus = ControlSignInStatus.NORMAL_SIGN;
                            return genTransInd(choice);
                        }else{
                            System.out.println("正在返回...");
                        }
                    }catch (JumpExeception e){

                    }
                }
                default -> {
                    System.out.println("功能未开发");
                    System.out.println("正在返回...");
                }
            }
        }
    }

    /**
     * 生成转移控制信号
     * @param
     * @return 转移控制信号
     */
    public ControlTransInd genTransInd(int choice){
        switch (choice){
            case 0: return ControlTransInd.SYSTEM_SHUT;
            case 1: return ControlTransInd.SHOPPING_HOME;
            case 2: return ControlTransInd.MANAGE_HOME;
            case 3: return ControlTransInd.SHOPPING_HOME;
        }
        System.out.println("系统发生了未知错误，将采用默认解决方案");
        return ControlTransInd.SYSTEM_NULL;
    }
    public NormalUser getNormalUserInDatabase(Table table){
        for (NormalUser user: ShoppingManageSystem.normalUsers) {
            if(table.getInfo()[0].equals(user.getAccount()))
                return user;
        }
        return null;
    }

    public void show(){
            System.out.println("系统主界面");
            System.out.println("1——用户登录");
            System.out.println("2——管理员登录");
            System.out.println("3——新用户注册");
            System.out.println("0——退出系统");
    }

    /**
     * 处理登录逻辑，接收一个实现根据账号和密码搜索用户的接口，使得同一个登录函数可以实现对管理员
     * 和用户的登录。
     * @param s
     * @return
     * @throws JumpExeception
     */
    public boolean SignIn(searchInList s)throws JumpExeception{
        String account;
        String password;
        while (true){
            account = InputDetectUtils.strInputWithException("请输入账号");
            password = InputDetectUtils.strInputWithException("请输入密码");
            String md5Password = MD5Utils.generateMD5(password);
            if(s.searchAndSetUser(account,md5Password) == null)
                continue;
            else
                break;
        }
        return true;
    }
    public static boolean findNorUser(String account){
        for (NormalUser user:ShoppingManageSystem.normalUsers) {
            if (user.getAccount().equals(account))
                return true;
        }
        return false;
    }

    public boolean registration() throws JumpExeception{
        String account;
        while (true){
            boolean flag = true;
            account = InputDetectUtils.strInputWithException("请输入用户名");
            if (account.length() < 5){
                System.out.println("用户名长度不得小于5");
                continue;
            }
            if(findNorUser(account))
                System.out.println("该用户名已存在，请重新输入");
            else
                break;
        }
        String password;
        while (true){
            password = InputDetectUtils.strInputWithException("请输入密码");
            if(!InputDetectUtils.isValidPassword(password))
                System.out.println("密码不符合规范——长度大于8个字符，必须是大小写字母、数字和标点符号的组合，请重新输入：");
            else
                break;
        }
        String email;
        while (true){
            email = InputDetectUtils.strInputWithException("请输入邮箱");
            if(!InputDetectUtils.isValidEmail(email))
                System.out.println("邮箱不符合规范，请重新输入：");
            else
                break;
        }

        ShoppingManageSystem.normalUsers.add(new NormalUser(account,MD5Utils.generateMD5(password),email));
        ShoppingManageSystem.setPreUsrNor(new NormalUser(account,password,email));
        return true;
    }

    /**
     * 这个接口将实现登录方法搜索的列表有所不同
     */
    interface searchInList{
        public User searchAndSetUser(String account, String password);
    }

}

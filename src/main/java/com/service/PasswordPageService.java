package com.service;

import com.controler.ControlTransInd;
import com.controler.DefaultPrintInformation;
import com.utils.InputDetectUtils;
import com.entity.NormalUser;
import com.exception.JumpExeception;
import com.utils.MD5Utils;


import java.security.SecureRandom;

import static com.service.ShoppingManageSystem.quitSignStr;

public class PasswordPageService {

    public void show(){
        System.out.println("密码管理");
        System.out.println("1——修改密码");
        System.out.println("2——重置密码");
        System.out.println("0——返回");
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
                        if(genNewPasswordAndSend()){
                            System.out.println("密码修改成功，新密码已经发送到你的邮箱，请及时查收并修改");
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
    public ControlTransInd genTransInd(int choice){
        switch (choice){
            case 0: return ControlTransInd.SHOPPING_HOME;
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
        md5Password = MD5Utils.generateMD5(input);
        if(revisePassword(md5Password, ShoppingManageSystem.user.getAccount())){
//            ShoppingManageSystem.user.setPassword(md5Password);
            return true;
        }
        return false;
    }
    public boolean revisePassword(String password, String account){
        for (NormalUser user:ShoppingManageSystem.normalUsers) {
            if(user.getAccount().equals(account)){
                user.setPassword(password);
                return true;
            }
        }
        return false;
    }
    public boolean genNewPasswordAndSend() throws JumpExeception{
        InputDetectUtils.strInputWithException("系统将发送随机生成的密码到你注册时使用的邮箱，发送后当前密码将被销毁，输入任意字符串继续");
//        String account =InputDetect.strInputWithException("请输入用户名");
//        String email = getEmailString("请输入注册时的邮箱");
        String newPassword = PasswordGenerator.generatePassword();
        if(send(((NormalUser)ShoppingManageSystem.user).getEmail(), newPassword) && revisePassword(MD5Utils.generateMD5(newPassword),ShoppingManageSystem.user.getAccount())){
            return true;
        } else
            return false;
    }

    public static String getEmailString(String info) throws JumpExeception{
        String email;
        while (true){
            email = InputDetectUtils.strInputWithException(info);
            if(InputDetectUtils.isValidEmail(email))
                break;
            else
                System.out.print("请输入正确的邮箱格式，输入"+quitSignStr+"返回：");
        }
        return email;
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

    public String getRandomPassword(){
        return "00000@ynu.com";
    }
    public boolean send(String email, String newPassword){
        SendMailService.sendEmail(email,newPassword);
        return true;
    }

    public class PasswordGenerator {
        private static final String CHARACTERS_ALPHA_UPCASE = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
        private static final String CHARACTERS_ALPHA_LOWCASE = "abcdefghijklmnopqrstuvwxyz";
        private static final String CHARACTERS_NUM = "0123456789!@#$%^&*()";
        private static final String CHARACTERS_SPECIAL = "!@#$%^&*()";
        private static final String CHARACTERS= "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789!@#$%^&*()";
        public static String generatePassword() {
            StringBuilder password = new StringBuilder();
            SecureRandom random = new SecureRandom();
            int length = 14;
            password.append(CHARACTERS_NUM.charAt(random.nextInt(CHARACTERS_NUM.length())));
            password.append(CHARACTERS_SPECIAL.charAt(random.nextInt(CHARACTERS_SPECIAL.length())));
            password.append(CHARACTERS_ALPHA_LOWCASE.charAt(random.nextInt(CHARACTERS_ALPHA_LOWCASE.length())));
            password.append(CHARACTERS_ALPHA_UPCASE.charAt(random.nextInt(CHARACTERS_ALPHA_UPCASE.length())));
            while (password.length() < length) {
                int index = random.nextInt(CHARACTERS.length());
                char character = CHARACTERS.charAt(index);
                password.append(character);
            }

            return password.toString();
        }

    }
}

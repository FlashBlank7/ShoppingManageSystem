package com.service;

import com.controler.ControlTransInd;
import com.controler.DefaultPrintInformation;
import com.utils.InputDetectUtils;
import com.entity.CartGood;
import com.entity.Good;
import com.entity.HistoryGood;
import com.entity.NormalUser;
import com.exception.JumpExeception;

import java.util.List;

import static com.service.ShoppingManageSystem.quitSignInt;

public class ShoppingCartPageService{
    private final static int DEFAULT_RETURN = 77777;
    public void show(){
        System.out.println("购物车");
    }
    public void showCartGoods(){
        if (ShoppingManageSystem.cartGoods.isEmpty()){
            System.out.println("当前购物车中无商品");
            return;
        }
        for(CartGood cartGood:ShoppingManageSystem.cartGoods){
            System.out.println(cartGood.getInfo());
        }
    }
    public void showChoice(){
        System.out.println("1——移除商品");
        System.out.println("2——修改商品个数");
        System.out.println("3——结算");
        System.out.println("4——购物记录");
        System.out.println("0——返回");
    }
    public ControlTransInd run(){
        show();
        showCartGoods();
        while (true){
            showChoice();
            int choice = InputDetectUtils.intInput(0,4);
            switch (choice){
                case 0 ->{
                    return genTransInd(DEFAULT_RETURN);
                }
                case 1 ->{
                    try {
                        if(remove()){
                            DefaultPrintInformation.dataRemovalPrint();
                            showCartGoods();
                        }else {
                            DefaultPrintInformation.goodNotFoundPrint();
                        }
                    }catch (JumpExeception je){
                        DefaultPrintInformation.backInfoPrint();
                    }

                }
                case 2 ->{
                    System.out.println("----输入订单编号和数量，修改商品的个数，例：\"3 1\"--输入"+quitSignInt+"返回：----");
                    try {
                        if(revision()){
                            DefaultPrintInformation.revisionInfoPrint();
                            showCartGoods();
                        }else {
                            DefaultPrintInformation.goodNotFoundPrint();
                        }
                    }catch (JumpExeception e){
                        DefaultPrintInformation.backInfoPrint();
                    }
                }
                case 3 ->{
                    System.out.println("结算——购物车中物品总价为"+String.format("%.2f",getTatalMoney())+"，结算吗？1--结算；0--退出");
                    while (true){
                        int choi = InputDetectUtils.intInput(0,1);
                        if(choi == 1){
                            if(!settleClosure()){
                                DefaultPrintInformation.unknownErrorPrint();
                                break;
                            }
                            double temp = ((NormalUser)ShoppingManageSystem.user).getAccumCon();
                            ((NormalUser)ShoppingManageSystem.user).setAccumCon(temp + getTatalMoney());
                            addHistoryGoods(ShoppingManageSystem.cartGoods);
                            System.out.println("结算成功，购物车已清空");
                            System.out.println("正在返回");
                            break;
                        }else {
                            System.out.println("正在返回");
                            break;
                        }
                    }
                }
                case 4->{
                    System.out.println("购物记录");
                    showHistory();
                }
            }
        }
    }
    public boolean settleClosure(){
        for (CartGood cartGood: ShoppingManageSystem.cartGoods) {
            for (Good good: ShoppingManageSystem.goods) {
                if (good.getName().equals(cartGood.good.getName())){
                    good.setNum(good.getNum() - cartGood.getNum());
                    break;
                }
            }
        }
        return true;
    }
    public void addHistoryGoods(List<CartGood> list){
        for(CartGood good:ShoppingManageSystem.cartGoods){
            ShoppingManageSystem.historyGoods.add(new HistoryGood(good));
        }
    }
    public void showHistory(){
        if (ShoppingManageSystem.historyGoods.isEmpty()){
            System.out.println("当前购物记录为空");
            return;
        }
        for (HistoryGood good:ShoppingManageSystem.historyGoods) {
            good.showInfo();
        }
    }
    public double getTatalMoney(){
        double sum = 0;
        for (CartGood good:ShoppingManageSystem.cartGoods) {
            sum += good.getNum()*good.good.getPrice();
        }
        return sum;
    }
    public boolean remove() throws JumpExeception{
        int id = InputDetectUtils.intInputWithException("输入订单编号");
        for (CartGood cartGood: ShoppingManageSystem.cartGoods) {
            if (cartGood.getId() == id){
                ShoppingManageSystem.cartGoods.remove(cartGood);
                return true;
            }
        }
        return false;

    }
    public boolean revision() throws JumpExeception{
        int []input = idAndNumInput();
        for (CartGood cartgood:ShoppingManageSystem.cartGoods){
            if (cartgood.getId() == input[0]){
                cartgood.setNum(input[1]);
                return true;
            }
        }
        return false;
    }
    public int[] idAndNumInput() throws JumpExeception{
        int id = InputDetectUtils.intInputWithException("输入订单编号");
        int num = InputDetectUtils.intInputWithException("输入修改后的个数");
        return new int[]{id, num};
    }

    public ControlTransInd genTransInd(int choice){
        switch (choice){
            case DEFAULT_RETURN: return ControlTransInd.SHOPPING_HOME;
        }
        DefaultPrintInformation.unknownErrorPrint();
        return ControlTransInd.SHOPPING_HOME;
    }
}

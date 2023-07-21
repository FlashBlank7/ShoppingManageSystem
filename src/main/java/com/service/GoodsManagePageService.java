package com.service;

import com.controler.ControlTransInd;
import com.controler.DefaultPrintInformation;
import com.utils.InputDetectUtils;
import com.entity.Good;
import com.exception.JumpExeception;

import java.util.ArrayList;
import java.util.List;

public class GoodsManagePageService {
    public void show(){
        System.out.println("商品管理");
        System.out.println("1-商品信息");
        System.out.println("2-添加商品信息");
        System.out.println("3-删除商品信息");
        System.out.println("4-查询商品信息");
        System.out.println("5-修改商品信息");
        System.out.println("0-返回管理主界面");
    }

    public ControlTransInd run(){
        while (true){
            show();
            int choice = InputDetectUtils.intInput(0,5);
            switch (choice){
                case 0->{
                    return genTransInd(choice);
                }
                case 1->{
                    System.out.println("商品信息");
                    showGoodsInfoBar();
                    showGoods();
                }
                case 2->{
                    System.out.println("输入商品名称和价格添加新商品");
                    try{
                        if (addNewGoods())
                            System.out.println("添加成功");
                        else
                            DefaultPrintInformation.unknownErrorPrint();
                    }catch (JumpExeception jr){
                        DefaultPrintInformation.backInfoPrint();
                    }
                }
                case 3->{
//                System.out.println("输入编号删除商品");
                    try{
                        if(removeGood())
                            System.out.println("删除成功");
                        else
                            System.out.println("删除失败，没有对应的编号的商品");

                    }catch (JumpExeception je){
                        DefaultPrintInformation.backInfoPrint();
                    }
                }
                case 4->{
                    try {
                        searchGood();
                    }catch (JumpExeception je){
                        DefaultPrintInformation.backInfoPrint();
                    }
                }
                case 5->{
                    try {
                        reviseGoodInfo();
                    }catch (JumpExeception e){
                        DefaultPrintInformation.backInfoPrint();
                    }
                }
            }
        }

    }
    public void reviseGoodInfo() throws JumpExeception{
        Good good;
        int id;
        while (true){
            id = InputDetectUtils.intInputWithException("请输入商品编号");
            good = findId(id);
            if(good == null)
                System.out.println("找不到该编号的商品");
            else
                break;
        }
        int chioce;
        System.out.println("选择要修改的信息");
        System.out.println("1-名字");
        System.out.println("2-价格");
        System.out.println("3-生产商");
        System.out.println("4-生产日期");
        System.out.println("5-型号");
        System.out.println("6-进口价");
        System.out.println("7-库存");
        System.out.println("0-返回");
        chioce = InputDetectUtils.intInput(0,7);
        switch (chioce){
            case 0 ->{
                throw new JumpExeception();
            }
            case 1 ->{
                good.setName(InputDetectUtils.strInputWithException("请输入修改后的名字"));
            }
            case 2 ->{
                good.setPrice(InputDetectUtils.doubleInputWithException("请输入修改后的价格"));
            }
            case 3 ->{
                good.setProducer(InputDetectUtils.strInputWithException("请输入修改后的生产商"));
            }
            case 4 ->{
                good.setProducer(InputDetectUtils.strInputWithException("请输入修改后的生产日期"));
            }
            case 5 ->{
                good.setModel(InputDetectUtils.strInputWithException("请输入修改后的型号"));
            }
            case 6 ->{
                good.setImportPrice(InputDetectUtils.doubleInputWithException("请输入修改后的进口价"));
            }
            case 7 ->{
                good.setNum(InputDetectUtils.intInputWithException("请输入修改后的库存"));
            }

        }

    }
    public Good findId(int id) throws JumpExeception{
        for (Good goodTemp: ShoppingManageSystem.goods) {
            if(goodTemp.getId() == id)
                return goodTemp;
        }

        return null;
    }
    public void showGoodsInfoBar(){
        System.out.println(String.format("%-5s","ID") + String.format("%-16s","Name") +String.format("%-6s","Price")
                +String.format("%-15s","Producer")+String.format("%-15s","Produced Date") +
                String.format("%-10s","Model")+String.format("%-15s","Import Price")+String.format("%-6s","Num"));
    }
    public void showGoods(List<Good> list){
        if (list.isEmpty()){
            System.out.println("没有找到符合条件的商品");
            return;
        }
        for (Good good:list) {
            System.out.println(good.getInfo());
        }
    }
    public boolean searchGood()throws JumpExeception{
        List<Good> tempList = new ArrayList<>(ShoppingManageSystem.goods);
        String name = InputDetectUtils.strInputWithException("请输入商品名称，若输入\"next\"则不使用商品名称进行搜索");
        String producer = InputDetectUtils.strInputWithException("请输入生产商名字，若输入\"next\"则不使用生产商名称进行搜索");
        double priceLow = InputDetectUtils.doubleInputWithException("请输入价格的下界");
        double priceHigh = InputDetectUtils.doubleInputWithException("请输入价格的上界");
        tempList = filterName(name,tempList);
        tempList = filerProducer(producer,tempList);
        tempList = filterPrice(priceLow,priceHigh,tempList);
        showGoodsInfoBar();
        showGoods(tempList);
        return false;
    }

    public List<Good> filterName(String name,List<Good> list){
        if (name.toLowerCase().equals("next"))
            return list;
        List<Good> listTemp=new ArrayList<>();

        for (Good good:list) {
            if(good.getName().equals(name))
                listTemp.add(good);
        }
        return listTemp;
    }
    public List<Good> filerProducer(String producer,List<Good> list){
        if (producer.toLowerCase().equals("next"))
            return list;
        List<Good> listTemp=new ArrayList<>();
        for (Good good:list) {
            if(good.getName().equals(producer))
                listTemp.add(good);
        }
        return listTemp;
    }
    public List<Good> filterPrice(double a, double b,List<Good> list){
        List<Good> listTemp = new ArrayList<>();

        for (Good good:list) {
            if(a<=good.getPrice() && good.getPrice()<=b)
                listTemp.add(good);
        }
        return listTemp;
    }

    public boolean removeGood() throws JumpExeception{
        int id = InputDetectUtils.intInputWithException("输入要删除商品的编号");
        for (Good good:ShoppingManageSystem.goods){
            if(good.getId() == id){
                InputDetectUtils.strInputWithException("删除后不可恢复，输入任意字符串继续删除");
                ShoppingManageSystem.goods.remove(good);
                return true;
            }
        }
        return false;
    }
    public ControlTransInd genTransInd(int choice){
        switch (choice){
            case 0->{
                return ControlTransInd.MANAGE_HOME;
            }
        }
        DefaultPrintInformation.unknownErrorPrint();
        return ControlTransInd.SYSTEM_HOME;
    }
    public void showGoods(){
        for (Good good:ShoppingManageSystem.goods) {
            System.out.println(good.getInfo());
        }
    }

    public boolean addNewGoods() throws JumpExeception{
        String name = InputDetectUtils.strInputWithException("请输入商品名称");
        double price;
        String producer;
        String produecDate;
        String model;
        double importPrice;
        int num;
        while (true){
            price= InputDetectUtils.doubleInputWithException("请输入商品价格");
            if(price < 0){
                System.out.println("价格不得小于0");
                continue;
            }
            break;
        }
        producer = InputDetectUtils.strInputWithException("请输入生产商");
        produecDate = InputDetectUtils.strInputWithException("请输入生产日期");
        model = InputDetectUtils.strInputWithException("请输入信号");
        importPrice = InputDetectUtils.doubleInputWithException("请输入进货价格");
        num = InputDetectUtils.intInputWithException("请输入件数");
        ShoppingManageSystem.goods.add(new Good(name,price,producer,produecDate,model,importPrice,num));
        return true;
    }
}

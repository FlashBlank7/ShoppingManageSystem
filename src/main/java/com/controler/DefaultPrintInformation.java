package com.controler;

public interface DefaultPrintInformation {
    // 出现了未知的错误
    public static void unknownErrorPrint(){System.out.println("发生了未知错误");}
    public static void backInfoPrint(){System.out.println("正在返回");}
    public static void revisionInfoPrint(){System.out.println("修改成功！");}
    public static void goodNotFoundPrint(){System.out.println("未找到该商品，请检查输入的id是否正确");}
    public static void dataRemovalPrint(){System.out.println("删除成功");}

}

package edu.upc.mishu.utils;

public class DoubleClick {//防止连续点击
    private static long lastClickTime;
    public static void setLastClickTime(long lasttime){
        lastClickTime=lasttime;
    }
    public static long getLastClickTime(){
        return lastClickTime;
    }
    public static Long isFastDoubleClick() {
        long time = System.currentTimeMillis();
        return 10-((time/1000)-(lastClickTime/1000));
    }
}
package edu.upc.mishu.utils;

import android.util.Log;

import java.util.Random;

public class SimplePasswordGenerator {
    private static String TAG="简单密码生成工具类";
    private static int length;
    private static boolean uppercaseLetters;
    private static boolean lowercaseLetters;
    private static boolean numbers;
    private static boolean symbols;

    private static boolean inited=false;

    private static void loadConfig(){
        length=Integer.parseInt(StringSettingUtil.getString("generator_length","0"));
        uppercaseLetters=Boolean.parseBoolean(StringSettingUtil.getString("generator_upper","true"));
        lowercaseLetters=Boolean.parseBoolean(StringSettingUtil.getString("generator_lower","true"));
        numbers=Boolean.parseBoolean(StringSettingUtil.getString("generator_number","true"));
        symbols=Boolean.parseBoolean(StringSettingUtil.getString("generator_symbol","true"));
        inited=true;
    }

    private static void saveConfig(){
        StringSettingUtil.writeSetting("generator_length",Integer.toString(length));
        StringSettingUtil.writeSetting("generator_upper",Boolean.toString(uppercaseLetters));
        StringSettingUtil.writeSetting("generator_lower",Boolean.toString(lowercaseLetters));
        StringSettingUtil.writeSetting("generator_number",Boolean.toString(numbers));
        StringSettingUtil.writeSetting("generator_symbol",Boolean.toString(symbols));
    }

    public static void setLength(int length){
        SimplePasswordGenerator.length=length;
    }

    public static void setUppercaseLetters(boolean uppercaseLetters) {
        SimplePasswordGenerator.uppercaseLetters = uppercaseLetters;
        saveConfig();
    }

    public static void setLowercaseLetters(boolean lowercaseLetters) {
        SimplePasswordGenerator.lowercaseLetters = lowercaseLetters;
        saveConfig();
    }

    public static void setNumbers(boolean numbers) {
        SimplePasswordGenerator.numbers = numbers;
        saveConfig();
    }

    public static void setSymbols(boolean symbols) {
        SimplePasswordGenerator.symbols = symbols;
        saveConfig();
    }

    public static String GenerateAPassword(){
        if(!inited){
            loadConfig();
        }

        if(length<=0||(!uppercaseLetters&&!lowercaseLetters&&!numbers&&!symbols)){
            Log.e(TAG,"不合法的生成器配置，重置为默认值");
            length=5;
            uppercaseLetters=true;
            lowercaseLetters=true;
            numbers=true;
            symbols=true;
            saveConfig();
        }
        char[] chUppercaseLetters={'A','B','C','D','E','F','G','H','J','K','L','M','N','P','Q','R','S','T','U','V','W','X','Y','Z'};
        char[] chLowercaseLetters={'a','b','c','d','e','f','g','h','j','k','m','n','p','q','r','s','t','u','v','w','x','y','z'};
        char[] chNumbers={'2','3','4','5','6','7','8','9'};
        char[] chSymbols={'!','@','$','%','^','&','*'};
        Random random=new Random(System.currentTimeMillis());
        StringBuilder sb=new StringBuilder();
        while (sb.length()<length) {
            switch (random.nextInt(4)) {
                case 0:
                    if(uppercaseLetters)
                        sb.append(chUppercaseLetters[random.nextInt(chUppercaseLetters.length)]);
                    break;
                case 1:
                    if(lowercaseLetters)
                        sb.append(chLowercaseLetters[random.nextInt(chLowercaseLetters.length)]);
                    break;
                case 2:
                    if(numbers)
                        sb.append(chNumbers[random.nextInt(chNumbers.length)]);
                    break;
                default:
                    if(symbols)
                        sb.append(chSymbols[random.nextInt(chSymbols.length)]);
            }
        }
        return sb.toString();
    }
}

package edu.upc.mishu.utils;

import java.util.List;

import edu.upc.mishu.dto.StringSetting;

/**
 * 用于存取一些配置信息
 */
public class StringSettingUtil {
    /**
     * 获取配饰
     * @param key
     * @return
     */
    public static String getSetting(String key){
        List<StringSetting> res=StringSetting.find(StringSetting.class,"item=?",key);
        return res.isEmpty()?null:res.get(0).getValue();
    }

    /**
     * 写入配置
     * @param key
     * @param value
     */
    public static void writeSetting(String key,String value){
        List<StringSetting> res=StringSetting.find(StringSetting.class,"item=?",key);
        StringSetting setting;
        if(res.isEmpty()){
            setting=StringSetting.builder().item(key).build();
        }else{
            setting=res.get(0);
        }
        setting.setValue(value);
        setting.save();
    }
}

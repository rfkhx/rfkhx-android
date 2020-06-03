package edu.upc.mishu.utils;

import android.util.Log;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.xuexiang.xutil.common.StringUtils;

import org.apache.commons.codec.digest.DigestUtils;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import edu.upc.mishu.dto.CheckResult;
import lombok.Data;
import lombok.SneakyThrows;

@Data
public class CheckPassword implements Runnable{
    private final static String TAG ="CheckPassword";
    private String password;
    private String hash;
    private List<CheckResult> list =new ArrayList<>();
    private int count;

    public void gethash(String password){
        if (StringUtils.isEmpty(password)) {
            setHash(null);
        }else {
            setHash(DigestUtils.sha1Hex(password));
        }
    }

    @SneakyThrows
    @Override
    public void run() {
        Log.i(TAG, "run: ");
        HttpURLConnection connection = null;
        String res ="";
        String url ="https://upccaishu.top/api/leakedpassword";
        gethash(getPassword());
        String shorthash = getHash();
        Log.i(TAG, "password is " + shorthash);
        if(shorthash.length()>5){
            shorthash = shorthash.substring(0,5);
            Log.i(TAG, "short password is " + shorthash);
        }
        URL connectUrl =new URL(url+"?shorthash="+shorthash);
        try{
            connection = (HttpURLConnection) connectUrl.openConnection();
            connection.setRequestMethod("GET");
            connection.setConnectTimeout(5000);
            connection.setReadTimeout(60000);
            connection.connect();
            if(connection.getResponseCode() == 200){
                InputStream inputStream = connection.getInputStream();
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream,"UTF-8"));
                String str="";
                if ((str = bufferedReader.readLine()) != null){
                    res = new String(str.getBytes());
                }
                Log.i(TAG, "res is " + res);
                JSONArray json = JSONArray.parseArray(res);
                setList(JSONObject.parseArray(json.toJSONString(),CheckResult.class)) ;
                inputStream.close();
            }

        }catch (Exception e){
            e.printStackTrace();
        }finally {
            connection.disconnect();
        }
    }


    public int getSamePasswordCount(){
        Log.i(TAG, "getSamePasswordCount: "+getHash());
        Log.i(TAG, "getSamePasswordCount: "+getList());
        int flag = 0;
        for(CheckResult item : list){
            if(item.getHash().equals(hash)){
                flag ++;
            }
        }
        return flag;
    }

}

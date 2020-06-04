package edu.upc.mishu.http;

import android.util.Log;

import java.io.IOException;
import java.util.concurrent.atomic.AtomicBoolean;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class OKHttpAccountService {

    public boolean registUser(String username,String password){
        AtomicBoolean flag= new AtomicBoolean(true);
        Thread thread=new Thread(()->{
            OkHttpClient client = new OkHttpClient().newBuilder()
                    .build();
            MediaType mediaType = MediaType.parse("text/plain");
            RequestBody body = RequestBody.create(mediaType, "");
            Request request = new Request.Builder()
                    .url("https://upccaishu.top/api/user?username="+username+"&password="+password)
                    .method("POST", body)
                    .build();

            try {
                Response response = client.newCall(request).execute();
                if(response.code()!=200){
                    flag.set(false);
                    System.out.println("注册"+flag.toString());
                }
                System.out.println("注册返回值"+response.code());
            } catch (IOException e) {
                e.printStackTrace();
                flag.set(false);
                System.out.println("注册2"+flag.toString());
            }
        });
        thread.start();
        try {
            thread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
            flag.set(false);
        }
        System.out.println("注册服务"+flag.toString());
        Log.e("注册服务",flag.toString());
        return flag.get();
    }
    public boolean loginUser(String username,String password) {
        AtomicBoolean flag= new AtomicBoolean(true);
        Thread thread=new Thread(()->{
            System.out.println("输入的用户名密码"+username+password);
            OkHttpClient client = new OkHttpClient().newBuilder()
                    .build();
            MediaType mediaType = MediaType.parse("application/json");
            RequestBody body = RequestBody.create(mediaType, "{\n\t\"username\":\""+username+"\",\n\t\"password\":\""+password+"\"\n}");
            Request request = new Request.Builder()
                    .url("https://upccaishu.top/api/login")
                    .method("POST", body)
                    .addHeader("Content-Type", "application/json")
                    .build();
            Response response = null;
            try {
                String token=null;
                response = client.newCall(request).execute();
                token=response.header("Authorization");
                System.out.println(token);
                if(token==null||(response.code()!=200)){
                    flag.set(false);
                }
                Log.e("登录1",String.valueOf(flag));
            } catch (IOException e) {
                e.printStackTrace();
                flag.set(false);
                Log.e("登录2",String.valueOf(flag));
            }
            Log.e("登录3",String.valueOf(flag));
        });
        thread.start();
        try {
            thread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return flag.get();
    }
}

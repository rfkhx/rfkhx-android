package edu.upc.mishu.http;

import android.util.Log;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;

import com.alibaba.fastjson.JSON;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.Collections;
import java.util.List;

import edu.upc.mishu.App;
import edu.upc.mishu.dto.PasswordRecordJSON;
import okhttp3.Credentials;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class OKHttpSyncHttpService {

    private String TAG="OKHttpSyncHttpService";
    private  String token=null;
    public String login(String username,String password){
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
            response = client.newCall(request).execute();
            token=response.header("Authorization");
            System.out.println(token);
            return token;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
        //Log.e(TAG,response.toString());
    }

    public void deleteAll(){
        OkHttpClient client = new OkHttpClient().newBuilder()
                .build();
        MediaType mediaType = MediaType.parse("text/plain");
        RequestBody body = RequestBody.create(mediaType, "");
        Request request = new Request.Builder()
                .url("https://upccaishu.top/api/records")
                .method("DELETE", body)
                .addHeader("Authorization", token)
                .build();
        try {
            Response response = client.newCall(request).execute();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public List<PasswordRecordJSON> getAllRecords(){//向下获取json数据
        login(App.user,App.password);
        OkHttpClient client = new OkHttpClient().newBuilder()
                .build();
        Request request = new Request.Builder()
                .url("https://upccaishu.top/api/records")
                .method("GET", null)
                .addHeader("Authorization",token)
                //.addHeader("Cookie", "JSESSIONID=6733F0C17A2AF70A7CF799D7B887419D")
                .build();
        Response response = null;
        try {
            response = client.newCall(request).execute();
            String json=response.body().string();
            List<PasswordRecordJSON> passwordRecordJSON = JSON.parseArray(json, PasswordRecordJSON.class);
            return Collections.unmodifiableList(passwordRecordJSON);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public boolean createOrEditRecord(List<PasswordRecordJSON> passwordRecordJSONS) {//上传
        login(App.user,App.password);
        deleteAll();
        OkHttpClient client = new OkHttpClient().newBuilder()
                .build();
        MediaType mediaType = MediaType.parse("text/plain");
        RequestBody body = RequestBody.create(mediaType, "");
        for(PasswordRecordJSON item:passwordRecordJSONS){
            Request request = new Request.Builder()
                    .url("https://upccaishu.top/api/records?type=login&name="+item.getName().toString()+"&url="+item.getUrl().toString()+"&username="+item.getUsername().toString()+"&password="+item.getPassword().toString()+"&note="+item.getNote().toString())
                    .method("POST", body)
                    .addHeader("Authorization", token)
                    //.addHeader("Cookie", "JSESSIONID=6733F0C17A2AF70A7CF799D7B887419D")
                    .build();
            try {
                Response response = client.newCall(request).execute();
            } catch (IOException e) {
                Log.e("OKHttpSyncHttpService","上传同步失败"+item.toString());
                e.printStackTrace();
                return false;
            }
        }
        return true;
    }

}

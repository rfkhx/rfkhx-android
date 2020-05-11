package edu.upc.mishu.utils;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Environment;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.core.app.ActivityCompat;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.util.List;

import edu.upc.mishu.App;
import edu.upc.mishu.dto.PasswordRecord;

public class ImportAndExport {
    private static final String TAG = "ImportAndExport";
    private List<PasswordRecord> list;
    private String dir=null;
    public String getDir(){
        return dir;
    }


    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public void Export(String Filename, Activity activity) {
        GetPermission getPermission = new GetPermission();
        getPermission.requestPermission(activity);
        String publicfiledir = null;



        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
            publicfiledir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOCUMENTS).getPath();
            File filedir = new File(publicfiledir, Filename);
            if (filedir.exists()) {
                Log.i(TAG, "Export: filedir exist");
            } else {
                try {
                    filedir.mkdir();
                    Log.i(TAG, "Export: create");
                } catch (Exception e) {
                    e.printStackTrace();
                    Log.i(TAG, "Export: create unsuccessful");
                }
            }

            Log.i(TAG, "Export: " + publicfiledir + File.separator + Filename);
        } else {
            Log.i(TAG, "Export: no sd or no permission");
        }

        try{
            //写入数据
            File file =new File(publicfiledir + File.separator +Filename+File.separator+"userdata.csv");
            dir = file.getPath();
            List<PasswordRecord> data = PasswordRecord.listAll(PasswordRecord.class);
//            JSONArray jsonArray = new JSONArray();
//            for (PasswordRecord item :data ){
//                item.decode(App.encoder,1);
//                JSONObject jsonObject = new JSONObject();
//                jsonObject.put("类型",item.getType());
//                jsonObject.put("项目名",item.getName());
//                jsonObject.put("网址",item.getUrl());
//                jsonObject.put("用户名",item.getUsername());
//                jsonObject.put("密码",item.getPassword());
//                jsonObject.put("备注",item.getNote());
//                jsonArray.put(jsonObject);
//            }
            StringBuilder stringBuilder =new StringBuilder();
            stringBuilder.append("类型").append(",");
            stringBuilder.append("项目名").append(",");
            stringBuilder.append("网址").append(",");
            stringBuilder.append("用户名").append(",");
            stringBuilder.append("密码").append(",");
            stringBuilder.append("备注").append(",");
            stringBuilder.append("\n");
            for (PasswordRecord item : data ){
                item.decode(App.encoder,1);
                stringBuilder.append(item.getType()).append(",");
                stringBuilder.append(item.getName()).append(",");
                stringBuilder.append(item.getUrl()).append(",");
                stringBuilder.append(item.getUsername()).append(",");
                stringBuilder.append(item.getPassword()).append(",");
                stringBuilder.append(item.getNote()).append(",");
                stringBuilder.append("\n");
            }
            FileOutputStream outputStream = new FileOutputStream(file);
            //outputStream.write(jsonArray.toString().getBytes());
            outputStream.write(stringBuilder.toString().getBytes());
            outputStream.close();
        }catch (Exception e){
            e.printStackTrace();
        }


    }
}

class GetPermission implements RequestPermission {
    private static final String TAG = "GetPermission";

    @Override
    public void requestPermission(Activity activity) {
        int checkPermission = ActivityCompat.checkSelfPermission(activity, Manifest.permission.WRITE_EXTERNAL_STORAGE);
        if (checkPermission != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(activity, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 3);
            Log.i(TAG, "getting permission");
        }
    }
}


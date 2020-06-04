package edu.upc.mishu.ui.fragment;

import android.os.Bundle;
import android.os.Looper;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import java.util.ArrayList;
import java.util.List;

import edu.upc.mishu.App;
import edu.upc.mishu.R;
import edu.upc.mishu.dto.PasswordRecord;
import edu.upc.mishu.dto.PasswordRecordJSON;
import edu.upc.mishu.http.OKHttpSyncHttpService;
import edu.upc.mishu.utils.DoubleClick;

public class SynchronousFragment extends Fragment {
    private static SynchronousFragment instance  = null;
    private ImageButton button_sync_up;
    private  ImageButton button_sync_down;
    private List<PasswordRecord> passwordRecordList ;
    private List<PasswordRecordJSON> passwordRecordJSONList=new ArrayList<>();
    private String TAG="SynchronousFragment";
    public static SynchronousFragment newInstance(){
        if(instance == null){
            instance = new SynchronousFragment();
        }
        return instance;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view= inflater.inflate(R.layout.fragment_synchronous,container,false);
        button_sync_up=view.findViewById(R.id.sync_up);
        button_sync_down=view.findViewById(R.id.sync_down);
        return view;
    }
    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        OKHttpSyncHttpService okHttpSyncHttpService=new OKHttpSyncHttpService();
        button_sync_down.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(DoubleClick.isFastDoubleClick()>0){
                    Toast.makeText(getContext(),
                            "请"+DoubleClick.isFastDoubleClick()+"秒后重试", Toast.LENGTH_SHORT).show();
                }else {
                    new Thread(() -> {
                        DoubleClick.setLastClickTime(System.currentTimeMillis());
                        PasswordRecord.deleteAll(PasswordRecord.class);
                        passwordRecordJSONList=okHttpSyncHttpService.getAllRecords();
                        System.out.println("同步下载"+passwordRecordJSONList.toString());
                        for(PasswordRecordJSON itemJSON:passwordRecordJSONList){
                            PasswordRecord item=new PasswordRecord();
                            item.setType("login");
//                        item.setNameEncoded(App.encoder.encode(itemJSON.getName()));
//                        item.setUrlEncoded(itemJSON.getUrl());
//                        item.setUsernameEncoded(App.encoder.encode(itemJSON.getUsername()));
//                        item.setPasswordEncoded(itemJSON.getPassword());
//                        item.setNoteEncoded(itemJSON.getNote());
                            item.setName(itemJSON.getName());
                            item.setUrl(App.encoder.decode(itemJSON.getUrl().replaceAll(" ","+")));
                            item.setUsername(itemJSON.getUsername());
                            item.setPassword(App.encoder.decode(itemJSON.getPassword().replaceAll(" ","+")));
                            item.setNote(App.encoder.decode(itemJSON.getNote().replaceAll(" ","+")));
                            item.encode(App.encoder,1);
                            Log.e(TAG,item.toString());

                            item.save();
                        }
                    }).start();
                    Toast.makeText(getContext(),
                            "已同步", Toast.LENGTH_SHORT).show();
                }

            }
        });
        button_sync_up.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(DoubleClick.isFastDoubleClick()>0){
                    Toast.makeText(getContext(),
                            "请"+DoubleClick.isFastDoubleClick()+"秒后再点击", Toast.LENGTH_SHORT).show();
                }else {
                    DoubleClick.setLastClickTime(System.currentTimeMillis());
                    passwordRecordList=PasswordRecord.listAll(PasswordRecord.class);
                    for(PasswordRecord item:passwordRecordList){
                        item.decode(App.encoder,1);
                        PasswordRecordJSON itemJSON=new PasswordRecordJSON();
                        itemJSON.setType("login");
                        itemJSON.setName(item.getName());
                        itemJSON.setUrl(item.getUrlEncoded());
                        itemJSON.setUsername(item.getUsername());
                        itemJSON.setPassword(item.getPasswordEncoded());
                        itemJSON.setNote(item.getNoteEncoded());
                        passwordRecordJSONList.add(itemJSON);//这个地方总是出玄学bug?
                    }
                    new Thread(() -> {
                        boolean flag=okHttpSyncHttpService.createOrEditRecord(passwordRecordJSONList);
                        if(flag){
                            Looper.prepare();
                            Toast.makeText(getContext(),
                                    "上传成功", Toast.LENGTH_SHORT).show();
                            Looper.loop();
                        }else {
                            Looper.prepare();
                            Toast.makeText(getContext(),
                                    "上传失败，请稍后重试", Toast.LENGTH_SHORT).show();
                            Looper.loop();
                        }
                    }).start();
                }
            }
        });
    }
}

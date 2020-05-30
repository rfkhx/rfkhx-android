package edu.upc.mishu.ui.fragment;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

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

public class SynchronousFragment extends Fragment {
    private static SynchronousFragment instance  = null;
    private Button button_sync_up;
    private  Button button_sync_down;
    private List<PasswordRecord> passwordRecordList ;
    private List<PasswordRecordJSON> passwordRecordJSONList=new ArrayList<>();
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

            }
        });
        button_sync_up.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
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
                    passwordRecordJSONList.add(itemJSON);
                }
                new Thread(() -> {
                    okHttpSyncHttpService.createOrEditRecord(passwordRecordJSONList);
                }).start();
            }
        });
    }
}

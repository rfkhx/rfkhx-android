package edu.upc.mishu.ui.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.daimajia.swipe.SimpleSwipeListener;
import com.daimajia.swipe.SwipeLayout;

import java.util.ArrayList;
import java.util.List;

import edu.upc.mishu.App;
import edu.upc.mishu.R;
import edu.upc.mishu.dto.PasswordRecord;
import edu.upc.mishu.ui.activities.AddPasswordActivity;
import edu.upc.mishu.ui.activities.ModifyPssswordActivity;
import edu.upc.mishu.ui.activities.ShowPasswordActivity;
import edu.upc.mishu.ui.adapter.ListViewAdapter;
import edu.upc.mishu.vo.PasswordItem;


public class PasswordFragment extends Fragment {
    private static final String TAG = "PasswordFragment";
    private static PasswordFragment instance  = null;
    private ListView listView;
    private ListViewAdapter listViewAdapter;
    private List<PasswordItem> list = new ArrayList<>();
    private List<PasswordRecord> passwordRecordList ;
    private SwipeLayout swipeLayout;

//    private Handler handler = new Handler(){
//        @Override
//        public void handleMessage(@NonNull Message msg) {
//            super.handleMessage(msg);
//            switch (msg.what){
//                case 1:
//                   listViewAdapter.notifyDataSetChanged();
//            }
//        }
//    };

    public static PasswordFragment newInstance(){
        if(instance == null){
            instance = new PasswordFragment();
        }
        return instance;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_password,container,false);
        listView = view.findViewById(R.id.list_view);
        init();
        return view;
    }

    private void init(){

        passwordRecordList = PasswordRecord.listAll(PasswordRecord.class);
        for(PasswordRecord item:passwordRecordList){
            item.decode(App.encoder,1);
            Log.e(TAG, "init: "+item.toString() +item.getId());
            PasswordItem pt = new PasswordItem();
            pt.setImageId(R.drawable.reset);
            pt.setUsername(item.getUsername());
            pt.setWebsite(item.getName());
            if(!list.contains(pt)){
                list.add(pt);
            }
        }
        listViewAdapter = new ListViewAdapter(getActivity(),list);
        listView.setAdapter(listViewAdapter);

    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        registerForContextMenu(listView);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                PasswordItem passwordItem = list.get(position);
                Log.i(TAG, "onItemClick: "+passwordItem.toString());
                Toast.makeText(getActivity(),passwordItem.getWebsite(),Toast.LENGTH_SHORT).show();
                Intent intent_show=new Intent(getActivity(), ShowPasswordActivity.class);
                intent_show.putExtra("project_name",passwordItem.getUsername());
                startActivity(intent_show);
            }
        });

        listView.setOnItemLongClickListener((parent, view, position, id) -> false);

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        Log.e(TAG, "onActivityResult: 返回值requestCode：" +requestCode);
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode){
            case 1:
                if(resultCode == -1) {
                    Log.e(TAG, "onActivityResult: " );
                    PasswordItem passwordItem = new PasswordItem();
                    passwordItem.setImageId(R.drawable.reset);
                    passwordItem.setUsername(data.getStringExtra("username"));
                    passwordItem.setWebsite(data.getStringExtra("name"));
                    list.add(passwordItem);
                    listViewAdapter.notifyDataSetChanged();
                }
                break;
            case 2:
                if(resultCode == -1){
                    Log.i(TAG, "onActivityResult: "+data.toString());
                    Log.e(TAG,"增加后返回活动");
                    PasswordItem passwordItem = new PasswordItem();
                    passwordItem.setImageId(R.drawable.reset);
                    assert data != null;
                    passwordItem.setUsername(data.getStringExtra("username"));
                    passwordItem.setWebsite(data.getStringExtra("name"));
                    list.set(data.getIntExtra("id",0),passwordItem);
                    listViewAdapter.notifyDataSetChanged();
                }
        }
    }
}

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
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.daimajia.swipe.SimpleSwipeListener;
import com.daimajia.swipe.SwipeLayout;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

import edu.upc.mishu.App;
import edu.upc.mishu.R;
import edu.upc.mishu.dto.PasswordRecord;
import edu.upc.mishu.ui.activities.AddPasswordActivity;
import edu.upc.mishu.ui.activities.MainActivity;
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
    private FloatingActionButton floatingActionButton;
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
        floatingActionButton=view.findViewById(R.id.floatingActionButton);
        init();
        return view;
    }

    private void init(){

        passwordRecordList = PasswordRecord.listAll(PasswordRecord.class);
        for(PasswordRecord item:passwordRecordList){
            item.decode(App.encoder,1);
            Log.e(TAG, "init: "+item.toString() +item.getId());
            PasswordItem pt = new PasswordItem();
            pt.setId_database(item.getId());//数据库记录ID
            pt.setImageId(R.drawable.reset);
            pt.setUsername(item.getUsername());
            pt.setWebsite(item.getName());
            pt.setUrl(item.getUrl());
            if(!list.contains(pt)){
                list.add(pt);
            }
        }
        listViewAdapter = new ListViewAdapter(getActivity(),list);
        listView.setAdapter(listViewAdapter);
//        PasswordItem pi = new PasswordItem();
//        pi.setUsername("wait");
//        pi.setWebsite("wait");
//        pi.setImageId(R.drawable.mishu_icon_background);
//        list.add(pi);
//        listViewAdapter = new ListViewAdapter(getActivity(),list);
//        listView.setAdapter(listViewAdapter);
//
//        new Thread(new Runnable() {
//            @Override
//            public void run() {
//                list.remove(list.get(0));
//                passwordRecordList = PasswordRecord.listAll(PasswordRecord.class);
//                for(PasswordRecord item:passwordRecordList){
//                    item.decode(encoder,1);
//                    Log.e(TAG, "init: "+item.toString() +item.getId());
//                    PasswordItem pt = new PasswordItem();
//                    pt.setImageId(R.drawable.reset);
//                    pt.setUsername(item.getUsername());
//                    pt.setWebsite(item.getName());
//                    if(!list.contains(pt)){
//                        list.add(pt);
//                    }
//                }
//
//            }
//        }).start();
        floatingActionButton.setOnClickListener(v -> {
            Intent intent_add=new Intent(getActivity(), AddPasswordActivity.class);
            startActivityForResult(intent_add,1);
        });
    }

    @Override//生成长安菜单
    public void onCreateContextMenu(@NonNull ContextMenu menu, @NonNull View v, @Nullable ContextMenu.ContextMenuInfo menuInfo) {
        MenuInflater menuInflater = getActivity().getMenuInflater();
        menuInflater.inflate(R.menu.contextmenu,menu);
        super.onCreateContextMenu(menu, v, menuInfo);
    }

    @Override//增删改按钮
    public boolean onContextItemSelected(@NonNull MenuItem item) {
        AdapterView.AdapterContextMenuInfo menuInfo = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        switch (item.getItemId()){
            case R.id.menu_del:
                Toast.makeText(getActivity(),"del",Toast.LENGTH_SHORT).show();
                Log.e(TAG, "Del"+ list.get(menuInfo.position).getWebsite());
                for(PasswordRecord p1:passwordRecordList){
                    if(p1.getName().equals(list.get(menuInfo.position).getWebsite())){
                        p1.delete();
                    }
                }
                passwordRecordList = PasswordRecord.listAll(PasswordRecord.class);
                for(PasswordRecord p1:passwordRecordList){
                    p1.decode(App.encoder,1);
                    Log.e(TAG, "Del after"+p1.toString() );
                }
                list.remove(menuInfo.position);
                listViewAdapter.notifyDataSetChanged();
                break;
            case R.id.menu_alter:
                Intent intent_alter = new Intent (getActivity(), ModifyPssswordActivity.class);
                intent_alter.putExtra("name",list.get(menuInfo.position).getWebsite());
                intent_alter.putExtra("id",menuInfo.position);
                Log.e("修改传参",list.get(menuInfo.position).getWebsite());
                startActivityForResult(intent_alter,2);
                break;
            case R.id.menu_add:
                Intent intent = new Intent (getActivity(), AddPasswordActivity.class);
                startActivityForResult(intent,1);

        }
        return super.onContextItemSelected(item);
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

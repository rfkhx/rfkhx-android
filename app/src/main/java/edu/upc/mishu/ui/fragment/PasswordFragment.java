package edu.upc.mishu.ui.fragment;

import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.drawable.Drawable;
import android.os.Build;
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
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.daimajia.swipe.SimpleSwipeListener;
import com.daimajia.swipe.SwipeLayout;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.scwang.smartrefresh.header.BezierCircleHeader;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

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
import edu.upc.mishu.utils.AppInfo;
import edu.upc.mishu.vo.PasswordItem;


public class PasswordFragment extends Fragment {
    private static final String TAG = "PasswordFragment";
    private static PasswordFragment instance  = null;
    private ListView listView;
    private ListViewAdapter listViewAdapter = new ListViewAdapter();
    private List<PasswordItem> list = new ArrayList<>();
    private List<PasswordRecord> passwordRecordList ;
    private FloatingActionButton floatingActionButton;
    private SmartRefreshLayout smartRefreshLayout;


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

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_password,container,false);
        listView = view.findViewById(R.id.list_view);
        floatingActionButton=view.findViewById(R.id.floatingActionButton);
        smartRefreshLayout = view.findViewById(R.id.smart_refresh_layout);
        init();
        return view;
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    private void init(){

        smartRefreshLayout.setRefreshHeader(new BezierCircleHeader(getContext()));
        smartRefreshLayout.setOnRefreshListener(refreshLayout -> {
            refresh();
            listViewAdapter.notifyDataSetChanged();
            refreshLayout.finishRefresh();
        });


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
        listViewAdapter = new ListViewAdapter(getActivity(),list,this);
        listView.setAdapter(listViewAdapter);


        floatingActionButton.setOnClickListener(v -> {
            Intent intent_add=new Intent(getActivity(), AddPasswordActivity.class);
            startActivityForResult(intent_add,1);
        });

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
                intent_show.putExtra("project_id",passwordItem.getId_database());
                startActivity(intent_show);
            }
        });

        listView.setOnItemLongClickListener((parent, view, position, id) -> false);

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        Log.i(TAG, "onActivityResult: 返回值requestCode：" +requestCode);
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode){
            case 1:
                if(resultCode == -1) {
                    refresh();
                }
                break;
        }
    }

    private void refresh(){
        passwordRecordList = PasswordRecord.listAll(PasswordRecord.class);
        list.removeAll(list);
        for(PasswordRecord item:passwordRecordList){
            item.decode(App.encoder,1);
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
        listViewAdapter.notifyDataSetChanged();
    }
}



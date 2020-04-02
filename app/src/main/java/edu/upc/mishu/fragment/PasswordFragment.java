package edu.upc.mishu.fragment;

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

import java.util.ArrayList;
import java.util.List;

import edu.upc.mishu.R;
import edu.upc.mishu.adapter.ListViewAdapter;
import edu.upc.mishu.vo.PasswordItem;

public class PasswordFragment extends Fragment {
    private static final String TAG = "PasswordFragment";
    private static PasswordFragment instance  = null;
    private ListView listView;
    private ListViewAdapter listViewAdapter;
    private List<PasswordItem> list = new ArrayList<>();

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
        View view = inflater.inflate(R.layout.activity_password,container,false);
        listView = (ListView) view.findViewById(R.id.list_view);
        init();
        return view;
    }

    private void init(){

        for (int i=0;i<15;i++){
            PasswordItem passwordItem = new PasswordItem();
            passwordItem.setImageId(R.drawable.reset);
            passwordItem.setWebsite("test"+i);
            passwordItem.setUsername("test"+i);
            list.add(passwordItem);
        }
        listViewAdapter = new ListViewAdapter(getActivity(),list);
        listView.setAdapter(listViewAdapter);
    }

    @Override//生成长安菜单
    public void onCreateContextMenu(@NonNull ContextMenu menu, @NonNull View v, @Nullable ContextMenu.ContextMenuInfo menuInfo) {
        MenuInflater menuInflater = getActivity().getMenuInflater();
        menuInflater.inflate(R.menu.contextmenu,menu);
        super.onCreateContextMenu(menu, v, menuInfo);
    }

    @Override
    public boolean onContextItemSelected(@NonNull MenuItem item) {
        AdapterView.AdapterContextMenuInfo menuInfo = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        switch (item.getItemId()){
            case R.id.menu_del:
                Toast.makeText(getActivity(),"del",Toast.LENGTH_SHORT).show();
                list.remove(menuInfo.position);
                listViewAdapter.notifyDataSetChanged();
                break;
            case R.id.menu_alter:
                Toast.makeText(getActivity(),"alter",Toast.LENGTH_SHORT).show();
                break;
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
                Log.e(TAG, "onItemClick: "+passwordItem.getUsername());
                Toast.makeText(getActivity(),passwordItem.getWebsite(),Toast.LENGTH_SHORT).show();
            }
        });

        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                return false;
            }
        });



    }
}

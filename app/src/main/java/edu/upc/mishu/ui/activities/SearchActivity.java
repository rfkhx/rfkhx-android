package edu.upc.mishu.ui.activities;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.ContextMenu;
import android.view.KeyEvent;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import edu.upc.mishu.App;
import edu.upc.mishu.R;
import edu.upc.mishu.dto.PasswordRecord;
import edu.upc.mishu.ui.adapter.ListViewAdapter;
import edu.upc.mishu.vo.PasswordItem;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;

public class SearchActivity extends AppCompatActivity {

    private static final String TAG = "SearchActivity";
    private ListView listView;
    private ListViewAdapter listViewAdapter;
    private List<PasswordItem> list = new ArrayList<>();
    private List<PasswordRecord> passwordRecordList ;


    @Override
    protected void onCreate(Bundle savedInstanceState){
     super.onCreate(savedInstanceState);
     setContentView(R.layout.search_layout);

     EditText editText=findViewById(R.id.search_et_input);
     listView=findViewById(R.id.search_list_view);
     passwordRecordList = PasswordRecord.listAll(PasswordRecord.class);

     editText.setOnEditorActionListener(new TextView.OnEditorActionListener() {
         @Override
         public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
             if (actionId == EditorInfo.IME_ACTION_SEARCH) {//监视回车键按下搜索
                 String str=editText.getText().toString();
                 if(!list.isEmpty()){
                     list.clear();
                 }
                 for(PasswordRecord item:passwordRecordList){
                     item.decode(App.encoder,1);
                     PasswordItem pt = new PasswordItem();
                     pt.setId_database(item.getId());//数据库记录ID
                     pt.setImageId(R.drawable.reset);
                     pt.setUsername(item.getUsername());
                     pt.setWebsite(item.getName());
                     if(!list.contains(pt)&&(pt.getWebsite().toString().contains(str)||pt.getUsername().toString().contains(str)||item.getUrl().toString().contains(str))){
                         Log.e(TAG, "Search: "+pt.toString());
                         list.add(pt);
                     }
                 }
                 listViewAdapter = new ListViewAdapter(SearchActivity.this,list);
                 listView.setAdapter(listViewAdapter);
                 //listViewAdapter.notifyDataSetChanged();
                 return true;
             }
             return false;
         }
     });

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                PasswordItem passwordItem = list.get(position);
                Log.e(TAG, "onItemClick: "+passwordItem.getUsername());
                Intent intent_show=new Intent(SearchActivity.this, ShowPasswordActivity.class);
                intent_show.putExtra("project_name",passwordItem.getUsername());
                startActivity(intent_show);
            }
        });
    }
}

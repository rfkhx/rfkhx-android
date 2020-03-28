package edu.upc.mishu;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.xuexiang.xupdate.XUpdate;
import com.xuexiang.xutil.tip.ToastUtils;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private TextView textView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button btnCheckUpdate=findViewById(R.id.btn_chkupdate);
        btnCheckUpdate.setOnClickListener(this);
        //test
        //第一次打开启动授权界面
        SharedPreferences shared=getSharedPreferences("is", MODE_PRIVATE);
        boolean isfer=shared.getBoolean("isfer", true);
        SharedPreferences.Editor editor=shared.edit();
        if(isfer){
            //第一次进入跳转
            Intent in=new Intent(MainActivity.this,LiscenceActivity.class);
            startActivity(in);
            finish();
        }else{
            XUpdate.newBuild(this)
                    .updateUrl("http://upccaishu.top/update/Android")
                    .update();
        }
        Log.i(this.getLocalClassName(),"MainActivity退出");
//        finish();
        textView  =  findViewById(R.id.text_view);
        BottomNavigationView navigation = findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNacigationItemSelectedListener);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_chkupdate:
                ToastUtils.toast("Hello World!");
                break;
        }
    }
    private BottomNavigationView.OnNavigationItemSelectedListener mOnNacigationItemSelectedListener = new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
            switch (menuItem.getItemId()){
                case R.id.navigation_pass :
                    textView.setText(R.string.navigation_pass);
                    return true;
                case R.id.navigation_synchronous:
                    textView.setText(R.string.navigation_synchronous);
                    return  true;
                case R.id.navigation_item3:
                    textView.setText(R.string.navigation_item3);
                    return  true;
                case R.id.navigation_setting:
                    textView.setText(R.string.navigation_setting);
                    return true;
            }
            return false;
        }
    };
}

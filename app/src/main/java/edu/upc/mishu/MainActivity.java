package edu.upc.mishu;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.xuexiang.xupdate.XUpdate;
import com.xuexiang.xutil.tip.ToastUtils;

import java.util.ArrayList;
import java.util.List;

import edu.upc.mishu.Adapter.ViewPagerAdapter;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {


    private View view1 , view2 , view3 ,view4;
    private ViewPager viewPager;
    private List<View> listview;
    private BottomNavigationView navigation;




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

        navigation = findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                int itemId = menuItem.getItemId();
                switch (itemId){
                    case R.id.navigation_pass:
                        viewPager.setCurrentItem(0);
                        break;
                    case  R.id.navigation_synchronous:
                        viewPager.setCurrentItem(1);
                        break;
                    case R.id.navigation_item3:
                        viewPager.setCurrentItem(2);
                        break;
                    case R.id.navigation_setting:
                        viewPager.setCurrentItem(3);
                        break;
                }
                return true;
            }
        });

        init();
    }

    @SuppressLint("ResourceType")
    private void init(){
        viewPager = (ViewPager) findViewById(R.id.viewpager);

        view1 = findViewById(R.layout.activity_password);
        view2 = findViewById(R.layout.activity_synchronous);
        view3 = findViewById(R.layout.activity_etc);
        view4 = findViewById(R.layout.activity_setting);

        LayoutInflater layoutInflater = getLayoutInflater().from(this);
        view1 = layoutInflater.inflate(R.layout.activity_password,null);
        view2 = layoutInflater.inflate(R.layout.activity_synchronous,null);
        view3 = layoutInflater.inflate(R.layout.activity_etc,null);
        view4 = layoutInflater.inflate(R.layout.activity_setting,null);

        listview = new ArrayList<View>();
        listview.add(view1);
        listview.add(view2);
        listview.add(view3);
        listview.add(view4);

        ViewPagerAdapter viewPagerAdapter = new ViewPagerAdapter(listview);
        viewPager.setAdapter(viewPagerAdapter);
        viewPager.setCurrentItem(0);
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                navigation.getMenu().getItem(position).setChecked(true);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_chkupdate:
                ToastUtils.toast("Hello World!");
                break;
        }
    }

}

package edu.upc.mishu;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;
import java.util.List;

import edu.upc.mishu.adapter.ViewPagerAdapter;
import edu.upc.mishu.fragment.EctFragment;
import edu.upc.mishu.fragment.PasswordFragment;
import edu.upc.mishu.fragment.SettingFragment;
import edu.upc.mishu.fragment.SynchronousFragment;

public class MainActivity extends AppCompatActivity  {

    private View view1 , view2 , view3 ,view4;
    private ViewPager viewPager;
    private List<Fragment> fragmentList;
    private BottomNavigationView navigation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


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

        }
        Log.i(this.getLocalClassName(),"MainActivity退出");

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

    private void init(){
        viewPager = (ViewPager) findViewById(R.id.viewpager);

        fragmentList = new ArrayList<>();
        fragmentList.add(PasswordFragment.newInstance());
        fragmentList.add(SynchronousFragment.newInstance());
        fragmentList.add(EctFragment.newInstance());
        fragmentList.add(SettingFragment.newInstance());

        ViewPagerAdapter viewPagerAdapter = new ViewPagerAdapter(fragmentList,getSupportFragmentManager());
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




}

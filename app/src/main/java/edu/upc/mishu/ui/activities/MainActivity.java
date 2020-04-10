package edu.upc.mishu.ui.activities;

import android.os.Bundle;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;
import java.util.List;

import edu.upc.mishu.App;
import edu.upc.mishu.R;
import edu.upc.mishu.interfaces.Transformable;
import edu.upc.mishu.ui.adapter.ViewPagerAdapter;
import edu.upc.mishu.dto.PasswordRecord;
import edu.upc.mishu.ui.fragment.EctFragment;
import edu.upc.mishu.ui.fragment.PasswordFragment;
import edu.upc.mishu.ui.fragment.SettingFragment;
import edu.upc.mishu.ui.fragment.SynchronousFragment;
import edu.upc.mishu.model.AES256Enocder;

public class MainActivity extends AppCompatActivity  {

    private static final String TAG = "MainActivity";

    private ViewPager viewPager;
    private List<Fragment> fragmentList;
    private BottomNavigationView navigation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

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
        Transformable encoder= App.encoder;
        PasswordRecord.deleteAll(PasswordRecord.class);
        PasswordRecord.builder().type("login").name("腾讯").url("http://www.tencent.com/").username("test1").password("123123").note("test").build().encode(encoder,1).save();
        PasswordRecord.builder().type("login").name("百度").url("https://www.baidu.com/").username("test2").password("123456").note("test").build().encode(encoder,1).save();
        PasswordRecord.builder().type("login").name("阿里云").url("https://www.aliyun.com/").username("test3").password("123789").note("test").build().encode(encoder,1).save();
        PasswordRecord.builder().type("login").name("新浪").url("https://www.sina.com.cn/").username("test4").password("123321").note("test").build().encode(encoder,1).save();
        PasswordRecord.builder().type("login").name("知乎").url("https://www.zhihu.com/hot").username("test5").password("12312431").note("test").build().encode(encoder,1).save();
        PasswordRecord.builder().type("login").name("163邮箱").url("https://mail.163.com/").username("test6").password("123").note("test").build().encode(encoder,1).save();
        PasswordRecord.builder().type("login").name("3DM").url("https://www.3dmgame.com/").username("test7").password("1238892").note("test").build().encode(encoder,1).save();
        PasswordRecord.builder().type("login").name("游民星空").url("https://www.gamersky.com/").username("test8").password("2310").note("test").build().encode(encoder,1).save();
        PasswordRecord.builder().type("login").name("百度贴吧").url("https://tieba.baidu.com/").username("test9").password("1239").note("test").build().encode(encoder,1).save();
        PasswordRecord.builder().type("login").name("腾讯邮箱").url("https://mail.qq.com/").username("test10").password("0901").note("test").build().encode(encoder,1).save();

        viewPager = findViewById(R.id.viewpager);

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

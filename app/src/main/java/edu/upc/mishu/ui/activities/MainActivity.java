package edu.upc.mishu.ui.activities;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.provider.Settings;
import android.security.keystore.KeyGenParameterSpec;
import android.security.keystore.KeyProperties;
import android.security.keystore.UserNotAuthenticatedException;
import android.util.Log;
import android.view.autofill.AutofillManager;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.biometric.BiometricManager;
import androidx.biometric.BiometricPrompt;
import androidx.core.content.ContextCompat;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;
import com.xuexiang.xupdate.XUpdate;
import com.xuexiang.xutil.tip.ToastUtils;

import java.io.IOException;
import java.nio.charset.Charset;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.UnrecoverableKeyException;
import java.security.cert.CertificateException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executor;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.KeyGenerator;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;

import edu.upc.mishu.App;
import edu.upc.mishu.R;
import edu.upc.mishu.dto.PasswordRecord;
import edu.upc.mishu.interfaces.Transformable;
import edu.upc.mishu.services.AutofillServiceTest;
import edu.upc.mishu.ui.fragment.EctFragment;
import edu.upc.mishu.ui.fragment.PasswordFragment;
import edu.upc.mishu.ui.fragment.SettingFragment;
import edu.upc.mishu.ui.fragment.SynchronousFragment;
import edu.upc.mishu.utils.AppInfo;

@RequiresApi(api = Build.VERSION_CODES.M)
public class MainActivity extends AppCompatActivity  {

    private static final String TAG = "MainActivity";
    private static final String KEY_NAME ="MiShu";
    private static final int VALIDITY_DURATION = 300;

    private PasswordFragment passwordFragment;
    private SynchronousFragment synchronousFragment;
    private EctFragment ectFragment;
    private SettingFragment settingFragment;
    private Fragment[] fragments;
    private int lastFragment;//上一个fragment
    private Toolbar toolbar;
    private TextView title;
    private AutofillManager autofillManager;
    private BiometricManager biometricManager;


    private DrawerLayout drawerLayout;
    private BottomNavigationView navigation;
    private NavigationView leftnavigation;



    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();
    }


    @SuppressLint("NewApi")
    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    private void init(){

        autofillManager = getSystemService(AutofillManager.class);
        if(!autofillManager.hasEnabledAutofillServices()){
            Intent intent = new Intent(Settings.ACTION_REQUEST_SET_AUTOFILL_SERVICE);
            intent.setData(Uri.parse("package:com.android.settings"));
            startActivityForResult(intent, 0);
        }
        startService(new Intent(getBaseContext(),AutofillServiceTest.class));


        drawerLayout = findViewById(R.id.main_drawer_layout);

//        drawerLayout.setScrimColor(Color.WHITE);

        toolbar = findViewById(R.id.toolbar);
        toolbar.setNavigationIcon(R.drawable.default_personlogo);

        toolbar.inflateMenu(R.menu.toolbarmenu);
        toolbar.setNavigationOnClickListener(v -> {
            Toast.makeText(MainActivity.this,"click",Toast.LENGTH_SHORT).show();
            drawerLayout.openDrawer(GravityCompat.START);
        });

        //搜索的入口
        toolbar.setOnMenuItemClickListener(item -> {
            switch (item.getItemId()){
                case R.id.toolbar_search:
                    Intent intent = new Intent (this,SearchActivity.class);
                    startActivity(intent);
                    return true;
            }
            return false;
        });

        title = findViewById(R.id.toolbar_title);
        title.setText("密码" );




        passwordFragment = new PasswordFragment();
        synchronousFragment = new SynchronousFragment();
        ectFragment = new EctFragment();
        fragments = new Fragment[]{passwordFragment,synchronousFragment,ectFragment};
        lastFragment = 0;
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment,passwordFragment).show(passwordFragment).commit();

        navigation = findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(menuItem -> {
            switch (menuItem.getItemId()){
                case R.id.navigation_pass:
                    if (lastFragment !=0){
                        title.setText("密码");
                        switchFragment(lastFragment,0);
                        lastFragment = 0;
                    }
                    return true;
                case R.id.navigation_synchronous:
                    if(lastFragment != 1){
                        title.setText("同步");
                        switchFragment(lastFragment,1);
                        lastFragment = 1;
                    }
                    return true;
                case R.id.navigation_etc:
                    if(lastFragment != 2){
                        title.setText("密码生成");
                        switchFragment(lastFragment,2);
                        lastFragment = 2;
                    }
                    return  true;
            }
            return false;
        });

        leftnavigation = findViewById(R.id.left_navigation);
        leftnavigation.setNavigationItemSelectedListener(menuItem -> {
            switch (menuItem.getItemId()) {
                case R.id.left_navigation_export:
                    new AlertDialog.Builder(this)
                            .setTitle("提醒")
                            .setMessage("您是否要导出数据到本地")
                            .setNegativeButton("否",null)
                            .setPositiveButton("是", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    Intent intent = new Intent(MainActivity.this,ExportActivity.class);
                                    startActivity(intent);
                                    Toast.makeText(MainActivity.this,"123123",Toast.LENGTH_SHORT).show();
                                }
                            })

                            .show();

                    break;
                case R.id.left_navigation_update:
                    ToastUtils.toast(getString(R.string.update_checking));
                    XUpdate.newBuild(MainActivity.this)
                            .updateUrl(getString(R.string.update_url))
                            .update();
                    break;
                case R.id.left_navigation_autoFill:
//                    autofillManager = getSystemService(AutofillManager.class);
//                    if(!autofillManager.hasEnabledAutofillServices()){
//                        Intent intent = new Intent(Settings.ACTION_REQUEST_SET_AUTOFILL_SERVICE);
//                        intent.setData(Uri.parse("package:com.android.settings"));
//                        startActivityForResult(intent, 0);
//                    }
//                    startService(new Intent(getBaseContext(),AutofillServiceTest.class));
                    adddata();
                    break;
                case R.id.iv_close:
                    SharedPreferences sharedPreferences =getSharedPreferences("Mishu", Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putInt("flag",0);
                    editor.commit();

            }
            return false;
        });


//        DisplayMetrics metric = new DisplayMetrics();
//        getWindowManager().getDefaultDisplay().getMetrics(metric);
//        int windowsWight = metric.widthPixels;
//        int windowsHeight = metric.heightPixels;
//        View leftMenu = findViewById(R.id.left_layout);
//        ViewGroup.LayoutParams leftParams = leftMenu.getLayoutParams();
//        leftParams.height = windowsHeight;
//        leftParams.width = windowsWight;
//        leftMenu.setLayoutParams(leftParams);
    }

    private void switchFragment(int lastFragment,int index)
    {
        FragmentTransaction transaction =getSupportFragmentManager().beginTransaction();
        transaction.hide(fragments[lastFragment]);//隐藏上个Fragment
        if(!fragments[index].isAdded())
        {
            transaction.add(R.id.fragment,fragments[index]);
        }
        transaction.show(fragments[index]).commitAllowingStateLoss();
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch(requestCode){
            case 3:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                } else {
                    Toast.makeText(this, "权限被拒绝了", Toast.LENGTH_LONG).show();
                }
                break;
            default:
                break;

        }
    }

    public void adddata(){
        Transformable encoder= App.encoder;
        PasswordRecord.deleteAll(PasswordRecord.class);
        List<PasswordRecord> list = PasswordRecord.listAll(PasswordRecord.class);
        PasswordRecord.builder().type("PC").name("腾讯").url("http://www.tencent.com/").username("test1").password("123123").note("test").build().encode(encoder,1).save();
        PasswordRecord.builder().type("Android").name("百度").url("https://www.baidu.com/").username("test2").password("123456").note("test").build().encode(encoder,1).save();
        PasswordRecord.builder().type("Website").name("阿里云").url("https://www.aliyun.com/").username("test3").password("123789").note("test").build().encode(encoder,1).save();
        PasswordRecord.builder().type("PC").name("新浪").url("https://www.sina.com.cn/").username("test4").password("123321").note("test").build().encode(encoder,1).save();
        PasswordRecord.builder().type("Android").name("知乎").url("https://www.zhihu.com/hot").username("test5").password("12312431").note("test").build().encode(encoder,1).save();
        PasswordRecord.builder().type("PC").name("163邮箱").url("https://mail.163.com/").username("test6").password("123").note("test").build().encode(encoder,1).save();
        PasswordRecord.builder().type("Android").name("3DM").url("https://www.3dmgame.com/").username("test7").password("1238892").note("test").build().encode(encoder,1).save();
        PasswordRecord.builder().type("Website").name("游民星空").url("https://www.gamersky.com/").username("test8").password("2310").note("test").build().encode(encoder,1).save();
        PasswordRecord.builder().type("PC").name("百度贴吧").url("https://tieba.baidu.com/").username("test9").password("1239").note("test").build().encode(encoder,1).save();
        PasswordRecord.builder().type("PC").name("腾讯邮箱").url("https://mail.qq.com/").username("test10").password("0901").note("test").build().encode(encoder,1).save();
    }




}

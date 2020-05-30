package edu.upc.mishu;

import com.orm.SugarApp;
import com.xuexiang.xhttp2.XHttp;
import com.xuexiang.xhttp2.XHttpSDK;
import com.xuexiang.xupdate.XUpdate;
import com.xuexiang.xupdate.utils.UpdateUtils;
import com.xuexiang.xutil.XUtil;
import com.xuexiang.xutil.tip.ToastUtils;
import com.zhy.http.okhttp.OkHttpUtils;

import java.util.concurrent.TimeUnit;

import edu.upc.mishu.http.OKHttpUpdateHttpService;
import edu.upc.mishu.interfaces.Transformable;
import okhttp3.OkHttpClient;

import static com.xuexiang.xupdate.entity.UpdateError.ERROR.CHECK_NO_NEW_VERSION;

public class App extends SugarApp {
    public static String user=null;
    public static Transformable encoder=null;
    public static String password=null;

    @Override
    public void onCreate() {
        super.onCreate();


        XUtil.init(this);
        XUtil.debug(true);

        initXHttp();
        initOKHttpUtils();
        initUpdate();


    }

    private void initUpdate() {
        //设置版本更新出错的监听
        XUpdate.get()
                .debug(true)
                .isWifiOnly(false)                                               //默认设置只在wifi下检查版本更新
                .isGet(true)                                                    //默认设置使用get请求检查版本
                .isAutoMode(false)                                              //默认设置非自动模式，可根据具体使用配置
                .param("versionCode", UpdateUtils.getVersionCode(this))  //设置默认公共请求参数
                .param("appKey", getPackageName())
                .setOnUpdateFailureListener(error -> {
                    if (error.getCode() != CHECK_NO_NEW_VERSION) {          //对不同错误进行处理
                        ToastUtils.toast(error.toString());
                    }
                })
                .supportSilentInstall(true)                                     //设置是否支持静默安装，默认是true
                .setIUpdateHttpService(new OKHttpUpdateHttpService())           //这个必须设置！实现网络请求功能。
                .init(this);                                          //这个必须初始化

    }

    private void initXHttp() {
        XHttpSDK.init(this);   //初始化网络请求框架，必须首先执行
        XHttpSDK.debug("XHttp");  //需要调试的时候执行
        XHttp.getInstance().setTimeout(20000);
    }

    private void initOKHttpUtils() {
        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .connectTimeout(20000L, TimeUnit.MILLISECONDS)
                .readTimeout(20000L, TimeUnit.MILLISECONDS)
                .build();
        OkHttpUtils.initClient(okHttpClient);
    }
}

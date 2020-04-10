package edu.upc.mishu.model;

import com.xuexiang.xutil.tip.ToastUtils;

import edu.upc.mishu.App;
import edu.upc.mishu.dto.User;
import edu.upc.mishu.interfaces.LoginObserver;
import edu.upc.mishu.interfaces.RegisterObserver;

/**
 * 一个用户登录事件的观察者的例子
 */
public class ExampleLoginObserver implements LoginObserver {

    @Override
    public void onLogin(User user) {
        if(user!=null){
            ToastUtils.toast("登录成功");
        }else{
            ToastUtils.toast("用户名或密码错误。");
        }
    }
}

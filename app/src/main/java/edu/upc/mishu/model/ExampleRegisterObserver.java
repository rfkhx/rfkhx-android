package edu.upc.mishu.model;

import com.xuexiang.xutil.tip.ToastUtils;

import edu.upc.mishu.dto.User;
import edu.upc.mishu.interfaces.RegisterObserver;

/**
 * 一个用户注册成功事件的观察者的例子
 */
public class ExampleRegisterObserver implements RegisterObserver {
    @Override
    public void onUserRegister(User user) {
        if(user!=null){
            ToastUtils.toast("用户注册成功");
        }
    }
}

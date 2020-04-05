package edu.upc.mishu.interfaces;

import edu.upc.mishu.dto.User;

/**
 * 抽象登录观察者
 */
public interface LoginObserver {
    void onLogin(User user);
}

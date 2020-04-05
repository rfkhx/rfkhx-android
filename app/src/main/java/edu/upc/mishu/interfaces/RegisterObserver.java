package edu.upc.mishu.interfaces;

import edu.upc.mishu.dto.User;

/**
 * 抽象用户注册观察者
 */
public interface RegisterObserver {
    void onUserRegister(User user);
}

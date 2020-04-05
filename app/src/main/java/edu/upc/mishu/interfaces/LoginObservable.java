package edu.upc.mishu.interfaces;

import edu.upc.mishu.dto.User;

/**
 * 抽象登录被观察者
 */
public interface LoginObservable {

    void attach(LoginObserver observer);

    void detach(LoginObserver observer);

    void notify(User user);
}

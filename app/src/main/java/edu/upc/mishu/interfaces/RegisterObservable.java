package edu.upc.mishu.interfaces;

import edu.upc.mishu.dto.User;

/**
 * 抽象注册被观察者
 */
public interface RegisterObservable {
    /**
     * 增加观察者
     * @param observer 观察者
     */
    void attach(RegisterObserver observer);

    /**
     * 删除观察者
     * @param observer 观察者
     */
    void detach(RegisterObserver observer);

    /**
     * 通知观察者有用户注册
     * @param user 注册的用户
     */
    void notify(User user);
}

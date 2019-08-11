package cn.itcast.travel.service;

import cn.itcast.travel.domain.User;

public interface UserService {
    /**
     * 用户注册方法
     * @return
     */
    public Boolean register(User user);

    /**
     * 用户激活方法
     * @param code 用户唯一标识
     * @return
     */
    public Boolean active(String code);

    /**
     * 用户登录方法
     * @param username
     * @param password
     * @return
     */
    public User login(User user);
}

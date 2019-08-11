package cn.itcast.travel.dao;

import cn.itcast.travel.domain.User;

public interface UserDao {
    /**
     * 通过用户名查询user
     * @return
     */
    public User findByUsername(String username);

    /**
     * 保存user对象到数据库
     * @param user
     * @return
     */
    public void save(User user);

    /**
     * 通过激活码找到用户
     * @param code
     * @return
     */
    public User findByCode(String code);

    /**
     * 修改用户的状态码
     * @param user
     */
    public void updateStatus(User user);

    /**
     * 通过用户名和密码查询user
     * @param username
     * @param password
     * @return
     */
    public User findByUsernameAndPassword(String username, String password);
}

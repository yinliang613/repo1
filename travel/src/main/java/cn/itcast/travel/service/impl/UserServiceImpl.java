package cn.itcast.travel.service.impl;

import cn.itcast.travel.dao.UserDao;
import cn.itcast.travel.dao.impl.UserDaoImpl;
import cn.itcast.travel.domain.User;
import cn.itcast.travel.service.UserService;
import cn.itcast.travel.util.MailUtils;
import cn.itcast.travel.util.UuidUtil;

public class UserServiceImpl implements UserService {

   private UserDao userDao = new UserDaoImpl();
    @Override
    public Boolean register(User user) {
        String username = user.getUsername();
        //验证用户名是否已经存在
        if(userDao.findByUsername(username)!=null){
            return false;
        }else{
            //设置状态和用户唯一code
            user.setStatus("N");
            user.setCode(UuidUtil.getUuid());
            //System.out.println(user);
            userDao.save(user);
            //邮件激活路径
            String content = "点击<a href=http://172.21.145.93/travel/user/active?code="+user.getCode()+">激活</a>";
            //System.out.println(content);
            MailUtils.sendMail(user.getEmail(),content,"黑马旅游网激活邮件");

            return true;

        }

    }

    @Override
    public Boolean active(String code) {
        User user = userDao.findByCode(code);
        if(user != null){
        userDao.updateStatus(user);
        return true;
        }else{
            return false;
        }

    }

    @Override
    public User login(User user) {
        return userDao.findByUsernameAndPassword(user.getUsername(),user.getPassword());

    }
}

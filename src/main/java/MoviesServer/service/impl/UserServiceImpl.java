package MoviesServer.service.impl;

import MoviesServer.dao.UserDao;
import MoviesServer.dao.impl.UserDaoImpl;
import MoviesServer.entity.User;
import MoviesServer.service.UserService;

public class UserServiceImpl implements UserService {
    private UserDao userDao = new UserDaoImpl();

    @Override
    public boolean register(User user) {
        User u = userDao.findByUserId(user.getUserId());
        if(u == null){//数据库user表中不存在该用户
            userDao.save(user);
            return true;
        }

        return false;
    }

    @Override
    public boolean login(User user) {
        User u = userDao.findByUserIdAndPassword(user);
        if(u != null){
            return true;
        }

        return false;
    }

    @Override
    public void updatePassword(int userid, String password) {
        userDao.updateUserPassword(userid,password);
    }
}

package MoviesServer.service;

import MoviesServer.entity.User;

public interface UserService {
    /**
     * 用户注册
     * @param user
     * @return
     */
    boolean register(User user);

    /**
     * 用户登陆
     * @param user
     * @return
     */
    boolean login(User user);

    void updatePassword(int userid,String password);

}

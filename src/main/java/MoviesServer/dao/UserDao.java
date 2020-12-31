package MoviesServer.dao;

import MoviesServer.entity.User;

public interface UserDao {

    /**
     * 根据用户userId查询数据库中该用户是否存在
     * @param userId
     * @return
     */
    User findByUserId(int userId);

    /**
     * 将该用户保存到数据库user表中
     * @param user
     */
    void save(User user);

    /**
     * 根据该用户查询该用户是否存在是数据库user表中
     * @param user
     * @return
     */
    User findByUserIdAndPassword(User user);

    void updateUserPassword(int userId,String password);
}

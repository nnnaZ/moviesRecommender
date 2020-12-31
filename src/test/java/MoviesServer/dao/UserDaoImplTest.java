package MoviesServer.dao;

import MoviesServer.dao.impl.UserDaoImpl;
import MoviesServer.entity.User;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

public class UserDaoImplTest {

    @Autowired
    private UserDao userDao = new UserDaoImpl();

    @Test
    public void UserDaoImplTest(){
//        User user = new User(1234567890, "123456");
        User usera = new User(123456789, "123456");

//        User user1 = userDao.findByUserIdAndPassword(user);
//        if(user1 != null) System.out.println(user1.toString());

        User user2 = userDao.findByUserId(usera.getUserId());
        if(user2 == null)
        System.out.println("null");

//        userDao.save(usera);


    }

}

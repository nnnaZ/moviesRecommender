package MoviesServer.service;

import MoviesServer.dao.UserDao;
import MoviesServer.dao.impl.UserDaoImpl;
import MoviesServer.entity.User;
import MoviesServer.service.impl.UserServiceImpl;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

public class UserServiceImplTest {
    @Autowired
    private UserService userService = new UserServiceImpl();

    @Test
    public void UserServiceImplTest(){
        User user = new User(1234567890, "123456");
        User usera = new User(123456789, "123456");

        boolean b = userService.login(user);
        System.out.println("login:"+b);
        boolean b1 = userService.register(usera);
        System.out.println("register:"+b);

    }
}

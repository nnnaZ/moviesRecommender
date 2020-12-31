package MoviesServer.dao.impl;

import MoviesServer.dao.UserDao;
import MoviesServer.entity.User;
import MoviesServer.utils.JDBCUtils;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;

public class UserDaoImpl implements UserDao {
    //创建JdbcTemplate(spring-jdbc连接)
    private JdbcTemplate jdbcTemplate = new JdbcTemplate(JDBCUtils.getDataSource());

    @Override
    public User findByUserId(int userId) {
        User u = null;
        String sql;
        //加try catch原因：如果queryForObject查询出的结果为空则会出现异常，所以加
        try{
            sql = "select count(*) from ratings where userId = ?";
            Integer count1 = jdbcTemplate.queryForObject(sql, Integer.class, userId);
            if(count1 != 0){
                u = new User();
                u.setUserId(userId);
                u.setPassword("");
            }else {
                sql = "select * from user where userId = ?";
                u = jdbcTemplate.queryForObject(sql,new BeanPropertyRowMapper<User>(User.class),userId);
            }
        }catch (Exception e){

        }
        return u;
    }

    @Override
    public void save(User user) {
        String sql = "insert into user values(?,?)";
        jdbcTemplate.update(sql,user.getUserId(),user.getPassword());
    }


    @Override
    public User findByUserIdAndPassword(User user) {
        User u = null;
        try{
            String sql = "select * from user where userId =? and password = ?";
            u = jdbcTemplate.queryForObject(sql,new BeanPropertyRowMapper<User>(User.class),user.getUserId(),user.getPassword());
        }catch (Exception e){

        }
        return u;
    }

    @Override
    public void updateUserPassword(int userId, String password) {
        String sql = "update user set password = ? where userId = ?";
        jdbcTemplate.update(sql,password,userId);
    }
}

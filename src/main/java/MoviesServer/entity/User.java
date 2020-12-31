package MoviesServer.entity;

public class User {
    private int userId;
    private String password;

    public User(){}

    public User(int userId,String password){
        this.userId = userId;
        this.password = password;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }


    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String toString() {
        return userId + "-" + password;
    }
}

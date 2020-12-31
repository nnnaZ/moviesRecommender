package MoviesServer.entity;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Tag implements Comparable<Tag>{
    private int moviesId;
    private int userId;
    private String tag;
    private String date;

    public int getMoviesId() {
        return moviesId;
    }

    public void setMoviesId(int moviesId) {
        this.moviesId = moviesId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }


    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        this.date = simpleDateFormat.format(new Date(Integer.parseInt(date) * 1000L));
    }

    @Override
    public String toString() {
        return moviesId + "-" + userId + "-" + tag + "-" + date;
    }

    @Override
    public int compareTo(Tag o) {

        //电影升序 ；电影相同 用户升序 ；相同用户 时间升序
        int b0 = this.moviesId - o.moviesId;
        int b1 = b0 == 0 ? this.userId - o.userId : b0;
        int b2 = b1 == 0 ? o.date.compareTo(this.date) : b1;
        return b2;
    }
}

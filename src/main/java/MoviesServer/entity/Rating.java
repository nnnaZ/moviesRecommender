package MoviesServer.entity;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Rating implements Comparable<Rating> {
    private int moviesId;
    private int userId;
    private double rating;
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


    public double getRating() {
        return rating;
    }

    public void setRating(double rating) {
        this.rating = rating;
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
        return moviesId + "-" + userId  + "-" + rating + "-" + date;
    }

    @Override
    public int compareTo(Rating o) {

        //电影升序 ；电影相同 用户升序 ；相同用户 时间升序
        int b0 = this.moviesId - o.moviesId;
        int b1 = b0 == 0 ? this.userId - o.userId : b0;
        int b2 = b1 == 0 ? o.date.compareTo(this.date) : b1;
        return b2;
    }
}

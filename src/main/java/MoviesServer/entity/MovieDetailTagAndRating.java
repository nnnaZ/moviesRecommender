package MoviesServer.entity;

/**
 * 用户评论以及分数的集合
 */
public class MovieDetailTagAndRating implements Comparable<MovieDetailTagAndRating> {
    private int moviesId;
    private int userId;
    private String title;
    private double rating;
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

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public double getRating() {
        return rating;
    }

    public void setRating(double rating) {
        this.rating = rating;
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
        this.date = date;
    }

    @Override
    public String toString() {
        return moviesId + "-" + title + "-" + userId + "-" + rating + "-" + tag + "-" + date;
    }

    @Override
    public int compareTo(MovieDetailTagAndRating o) {
        //按照时间降序
        return o.date.compareTo(this.date);
    }
}

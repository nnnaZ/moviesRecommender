package MoviesServer.entity;

import java.util.Set;

public class MovieDetailTagAndRatingTable {
    private int code;
    private String msg;
    private int count;
    private Set<MovieDetailTagAndRating> data;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public Set<MovieDetailTagAndRating> getData() {
        return data;
    }

    public void setData(Set<MovieDetailTagAndRating> data) {
        this.data = data;
    }
}

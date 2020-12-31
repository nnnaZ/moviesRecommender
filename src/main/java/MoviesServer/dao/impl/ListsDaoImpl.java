package MoviesServer.dao.impl;

import MoviesServer.dao.ListsDao;
import MoviesServer.entity.*;
import MoviesServer.utils.DateToTimestamp;
import MoviesServer.utils.JDBCUtils;
import MoviesServer.utils.LoadPictures;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.*;

public class ListsDaoImpl implements ListsDao {
    private JdbcTemplate jdbcTemplate = new JdbcTemplate(JDBCUtils.getDataSource());

    @Override
    public List<Movie> queryMoviesByPageNumberAndGenres(int pid, String genres, String searchText) {
        String sql = "select movies.moviesId,links.imdbId,movies.title,movies.genres,movies.pictureUrl from movies,links where movies.moviesId = links.moviesId";
        StringBuilder stringBuilder = new StringBuilder(sql);
        List params = new ArrayList();//存储sql参数值

        //搜索框中搜索内容
        if (searchText != null && !searchText.equals("")  && !searchText.equals("null")) {
            stringBuilder.append(" and movies.title like ?");
            searchText = "%" + searchText + "%";
            params.add(searchText);
        }

        if (genres == null || genres.equals("") || genres.equals("null")) {// 显示20条记录
            stringBuilder.append(" limit ?,20");
            int start = (pid - 1) * 20;
            params.add(start);
        } else {// 显示分类的20条记录
            stringBuilder.append(" and movies.genres like ? limit ?,20");
            genres = "%" + genres + "%";
            int start = (pid - 1) * 20;
            params.add(genres);
            params.add(start);

        }

        sql = stringBuilder.toString();
        return jdbcTemplate.query(sql, new BeanPropertyRowMapper<Movie>(Movie.class), params.toArray());
    }

    @Override
    public int queryTotalPage(String genres, String searchText) {
        String sql = "select count(*) from movies where 1 = 1";
        StringBuilder stringBuilder = new StringBuilder(sql);
        List params = new ArrayList();//存储sql参数值

        //搜索框中搜索内容
        if (searchText != null  && !searchText.equals("null") ) {
            stringBuilder.append(" and title like ?");
            searchText = "%" + searchText + "%";
            params.add(searchText);
        }

        //判断类别是否为空
        if (genres != null && !genres.equals("null")) {
            stringBuilder.append(" and genres like ?");
            genres = "%" + genres + "%";
            params.add(genres);
        }
        return jdbcTemplate.queryForObject(stringBuilder.toString(), Integer.class, params.toArray());
    }

    @Override
    public void updateMoviesToPictureUrl(int moviesId) {
        String sql = "update movies set pictureUrl = ? where moviesId = ?";
        jdbcTemplate.update(sql, Integer.toString(moviesId), moviesId);
    }

    @Override
    public MovieDetail queryMovieDetail(int moviesId) {
        String sql = "select movies.moviesId, movies.title, movies.genres, AVG(ratings.rating) as avg from movies,ratings where movies.moviesId = ratings.moviesId and movies.moviesId = ?";
        return jdbcTemplate.queryForObject(sql,new BeanPropertyRowMapper<MovieDetail>(MovieDetail.class),moviesId);
    }

    @Override
    public List<Tag> queryTags(int moviesId,int userId) {
        String sql = "select moviesId,userId,tag,`timestamp` as date from tags where 1 = 1";
        StringBuilder stringBuilder = new StringBuilder(sql);
        List params = new ArrayList();

        if(moviesId != 0){
            stringBuilder.append(" and moviesId = ?");
            params.add(moviesId);
        }

        if(userId != 0){
            stringBuilder.append(" and userId = ?");
            params.add(userId);
        }

        return jdbcTemplate.query(stringBuilder.toString(), new BeanPropertyRowMapper<Tag>(Tag.class),params.toArray());
    }

    @Override
    public List<Rating> queryRatings(int moviesId,int userId) {
        String sql = "select moviesId,userId,rating,`timestamp` as date from ratings where 1 = 1";
        StringBuilder stringBuilder = new StringBuilder(sql);
        List params = new ArrayList();

        if(moviesId != 0){
            stringBuilder.append(" and moviesId = ?");
            params.add(moviesId);
        }

        if(userId != 0){
            stringBuilder.append(" and userId = ?");
            params.add(userId);
        }

        return jdbcTemplate.query(stringBuilder.toString(), new BeanPropertyRowMapper<Rating>(Rating.class),params.toArray());
    }

    @Override
    public void deleteRating(Rating rating) {
        String sql = "delete from ratings where userId = ? and moviesId = ? and rating = ? and `timestamp` = ?";
        String timestamp = DateToTimestamp.dateToTimestamp(rating.getDate());
        jdbcTemplate.update(sql, rating.getUserId(), rating.getMoviesId(), rating.getRating(), timestamp);
    }

    @Override
    public void updateRating(Rating rating,String time) {
        //原来时间的时间戳
        String timestamp = null;
        if(rating.getDate() != null){
            timestamp = DateToTimestamp.dateToTimestamp(rating.getDate());
        }
        String sql;
        List params = new ArrayList();

        sql = "select count(*) from ratings where userId = ? and moviesId = ? and `timestamp` = ?";
        params.add(rating.getUserId());
        params.add(rating.getMoviesId());
        params.add(timestamp);
        Integer count = jdbcTemplate.queryForObject(sql, Integer.class, params.toArray());
        if(count > 0){
            sql = "update ratings set rating = ? , `timestamp` = ? where userId = ? and moviesId = ? and `timestamp` = ?";
            jdbcTemplate.update(sql,rating.getRating(),time,rating.getUserId(),rating.getMoviesId(),timestamp);
        }else{
            sql = "insert into ratings values(?,?,?,?)";
            jdbcTemplate.update(sql,rating.getUserId(),rating.getMoviesId(),rating.getRating(),time);
        }

    }

    @Override
    public void deleteTag(Tag tag) {
        String sql = "delete from tags where userId = ? and moviesId = ? and tag = ? and `timestamp` = ?";
        String timestamp = DateToTimestamp.dateToTimestamp(tag.getDate());
        jdbcTemplate.update(sql, tag.getUserId(), tag.getMoviesId(), tag.getTag(), timestamp);
    }

    @Override
    public void updateTag(Tag tag,String time) {
        //原来时间的时间戳
        String timestamp = null;
        if(tag.getDate() != null){
            timestamp = DateToTimestamp.dateToTimestamp(tag.getDate());
        }
        String sql;
        List params = new ArrayList();

        sql = "select count(*) from tags where userId = ? and moviesId = ? and `timestamp` = ?";
        params.add(tag.getUserId());
        params.add(tag.getMoviesId());
        params.add(timestamp);
        Integer count = jdbcTemplate.queryForObject(sql,Integer.class, params.toArray());
        if(count > 0){
            sql = "update tags set tag = ? , `timestamp` = ? where userId = ? and moviesId = ? and `timestamp` = ?";
            jdbcTemplate.update(sql,tag.getTag(),time,tag.getUserId(),tag.getMoviesId(),timestamp);
        }else {
            sql = "insert into tags values(?,?,?,?)";
            jdbcTemplate.update(sql,tag.getUserId(),tag.getMoviesId(),tag.getTag(),time);
        }
    }

    @Override
    public List<Integer> queryUserIdList() {
        String sql = "select distinct userId from ratings";
        return jdbcTemplate.queryForList(sql,Integer.class);
    }

    @Override
    public List<Integer> queryMoviesIdList() {
        String sql = "select distinct moviesId from ratings";
        return jdbcTemplate.queryForList(sql,Integer.class);
    }

    @Override
    public Movie queryMovie(int moviesId) {
        String sql = "select movies.moviesId,imdbId,title,genres,pictureUrl from movies,links where movies.moviesId = links.moviesId and movies.moviesId = ?";
        return jdbcTemplate.queryForObject(sql,new BeanPropertyRowMapper<Movie>(Movie.class),moviesId);
    }

}

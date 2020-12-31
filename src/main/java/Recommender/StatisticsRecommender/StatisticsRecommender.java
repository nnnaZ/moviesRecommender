package Recommender.StatisticsRecommender;

import MoviesServer.entity.MoviesAvgRating;
import MoviesServer.entity.MoviesTagNumbers;
import MoviesServer.utils.JDBCUtils;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.List;

/**
 * 统计：电影历史的评论数，倒叙    电影平均分，倒叙
 */
public class StatisticsRecommender {
    private JdbcTemplate jdbcTemplate = new JdbcTemplate(JDBCUtils.getDataSource());


    //电影历史的评论数，倒叙
    public List<MoviesTagNumbers> tagNumbers(){

        String sql = "select tags.moviesId,links.imdbId, movies.title, COUNT(tags.moviesId) as num,movies.pictureUrl from tags,movies,links where tags.moviesId = movies.moviesId and tags.moviesId = links.moviesId group by tags.moviesId,links.imdbId order by num desc limit 6";
        List<MoviesTagNumbers> tagNumber = jdbcTemplate.query(sql, new BeanPropertyRowMapper<MoviesTagNumbers>(MoviesTagNumbers.class));

        return tagNumber;
    }

    //电影平均分，倒叙
    public List<MoviesAvgRating> averageRating(){

        String sql = "select ratings.moviesId,links.imdbId, movies.title,AVG(ratings.rating) as avg,movies.pictureUrl from ratings,movies,links where ratings.moviesId = movies.moviesId and ratings.moviesId = links.moviesId group by ratings.moviesId,links.imdbId order by  avg desc limit 6";
        List<MoviesAvgRating> avgRatings = jdbcTemplate.query(sql, new BeanPropertyRowMapper<MoviesAvgRating>(MoviesAvgRating.class));

        return avgRatings;
    }

}

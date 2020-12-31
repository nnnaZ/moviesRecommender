package MoviesServer.service;

import MoviesServer.entity.*;

import java.util.List;
import java.util.Set;
import java.util.concurrent.ExecutionException;

/**
 * 功能：查询当前页面的电影相关信息和页码信息  推荐电影功能
 */
public interface ListsService {

    /**
     *查询该页的电影信息
     * @param pid 页码 从1开始
     * @param genres 电影分类  没有为null
     * @return
     */
    List<Movie> moviesQuery(int pid,String genres,String searchText);

    /**
     * 查询电影记录总数  总页码数=记录总数/20
     * @param genres 电影分类  没有为null
     * @return
     */
    int totalPageNumsQuery(String genres,String searchText);

    //推荐算法
    /**
     * 电影历史的评论数，倒叙
     * @return
     */
    List<MoviesTagNumbers> moviesTagNums();

    /**
     * 电影平均分，倒叙
     * @return
     */
    List<MoviesAvgRating> moviesAvgRating();

    /**
     * 查询电影的详细信息
     * @param moviesId
     * @return
     */
    MovieDetail movieDetailQuery(int moviesId);

    /**
     * 查询电影和相应用户的评论以及评分
     * @param moviesId
     * @return
     */
    Set<MovieDetailTagAndRating> detailTagAndRatingQuery(int moviesId,int userId);


    /**
     * 将MovieDetailTagAndRating变量存储到set结合中，简化detailTagAndRatingQuery操作
     * @param set
     * @param moviesId
     * @param userId
     * @param rating
     * @param tag
     * @param date
     */
    void saveMovieDetailTagAndRatingToSet(Set<MovieDetailTagAndRating> set,int moviesId,int userId,double rating,String tag,String date);


    /**
     * 获取电影的评论和评分 ，将其删除
     * @param movieDetailTagAndRating
     */
    void deleteTagAndRatingToMovieAndUserId(MovieDetailTagAndRating movieDetailTagAndRating);

    /**
     * 获取电影的评论和评分 ，将其更新
     * @param movieDetailTagAndRating
     * @param time 当前时间的时间戳
     */
    void updateTagAndRatingToMovieAndUserId(MovieDetailTagAndRating movieDetailTagAndRating,String time);

    /**
     * 获取电影对应的相似电影 或者该用户的推荐电影 参数总有一个为0
     * @param moviesId
     * @param userId
     * @return
     */
    List<Movie> queryItemCFRecommender(int moviesId,int userId) throws ExecutionException, InterruptedException;
}

package MoviesServer.dao;

import MoviesServer.entity.*;

import java.util.List;
import java.util.Map;

public interface ListsDao {


    /**
     *根据当前页码和分类的信息一起查询电影信息  （要判断movies表中图片url是否存在）
     * @param pid 当前页码 从1开始
     * @param genres 分类的标签；没有分类查询查询全部，则为null
     * @param searchText 搜索框文本内容  没有,则为null
     * @return
     */
    List<Movie> queryMoviesByPageNumberAndGenres(int pid,String genres,String searchText);

    /**
     * 查询总共的记录数
     * @param genres 分类
     * @param searchText 搜索条件
     * @return
     */
    int queryTotalPage(String genres,String searchText);

    /**
     * 插入图片的本地地址
     * @param moviesId 电影的id
     */
    void updateMoviesToPictureUrl(int moviesId);


    /**
     * 获取电影详情页 moviesId title genres avg
     * @param moviesId
     * @return
     */
    MovieDetail queryMovieDetail(int moviesId);

    /**
     * 查询指定电影和用户的评论集合
     * @param moviesId
     * @param userId
     * @return 用户和其对应的评论
     */
    List<Tag> queryTags(int moviesId,int userId);

    /**
     * 查询指定电影和用户的评分集合
     * @param moviesId
     * * @param userId
     * @return 用户和其对应的评论
     */
    List<Rating> queryRatings(int moviesId,int userId);

    /**
     * 删除该条评分 该评分有可以删除，否则删除不了 影响行数为0
     * @param rating
     */
    void deleteRating(Rating rating);

    /**
     * 更新评分参数数据 userId moviesId date为原来的没有改变，要根据这些条件找到需要更改评分的数据，以及更新时间为现在
     * @param rating rating.rating为修改过的数据，其他没有改变(根据这些条件找到需要更改评论的数据)
     * @param time 要插入的时间
     */
    void updateRating(Rating rating,String time);

    /**
     * 删除该条评论  该评论有可以删除，否则删除不了 影响行数为0
     * @param tag
     */
    void deleteTag(Tag tag);

    /**
     * 更新评论参数数据 userId moviesId date为原来的没有改变，要根据这些条件找到需要更改评论的数据，以及更新时间为现在
     * @param tag tag.tag为修改过的数据 其他没有改变(根据这些条件找到需要更改评论的数据)
     * @param time  要插入的时间
     */
    void updateTag(Tag tag,String time);

    /**
     * 查询用户数目
     */
    List<Integer> queryUserIdList();

    /**
     * 查询电影数目
     */
    List<Integer> queryMoviesIdList();

    Movie queryMovie(int moviesId);

}

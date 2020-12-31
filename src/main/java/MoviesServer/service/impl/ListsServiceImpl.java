package MoviesServer.service.impl;

import MoviesServer.dao.ListsDao;
import MoviesServer.dao.impl.ListsDaoImpl;
import MoviesServer.entity.*;
import MoviesServer.service.ListsService;
import MoviesServer.utils.DateToTimestamp;
import MoviesServer.utils.LoadPictures;
import Recommender.ItemCFRecommender.ItemCFRecommender;
import Recommender.ItemCFRecommender.ItemCFRecommenderThread;
import Recommender.StatisticsRecommender.StatisticsRecommender;
//import org.apache.commons.collections.list.TreeList;

import java.util.*;
import java.util.concurrent.ExecutionException;

public class ListsServiceImpl implements ListsService {
    private ListsDao listsDao = new ListsDaoImpl();
    private StatisticsRecommender statisticsRecommender = new StatisticsRecommender();
    private LoadPictures loadPictures = new LoadPictures();

    @Override
    public List<Movie> moviesQuery(int pid, String genres, String searchText) {
        List<Movie> movieList = listsDao.queryMoviesByPageNumberAndGenres(pid, genres, searchText);
        //存储没有下载图片的电影id和imdbId
        Map<String, Integer> imdbAndMoviesIdMap = new HashMap<>();

        //判断movieList中的图片url是否为空，不为空，说明图片已经下载了
        for (Movie m : movieList) {
            if (m.getPictureUrl() == null) {//图片没有下载
                imdbAndMoviesIdMap.put(m.getImdbId(), m.getMoviesId());
            }
        }

        if (imdbAndMoviesIdMap.size() != 0) {
            //下载图片
            loadPictures.loadPicturesUrl(imdbAndMoviesIdMap);
            //下载完图片之后,更新数据movies表中的图片url以及将movieList中的pictureUrl值设置为moviesId
            for (Movie m : movieList) {
                if (m.getPictureUrl() == null) {//图片没有下载
                    m.setPictureUrl(Integer.toString(m.getMoviesId()));
                    //数据库更新
                    listsDao.updateMoviesToPictureUrl(m.getMoviesId());
                }
            }
        }

        return movieList;
    }

    @Override
    public int totalPageNumsQuery(String genres, String searchText) {
        return listsDao.queryTotalPage(genres, searchText);
    }

    @Override
    public List<MoviesTagNumbers> moviesTagNums() {
        List<MoviesTagNumbers> moviesTagNumbers = statisticsRecommender.tagNumbers();
        //存储没有下载图片的电影id和imdbId
        Map<String, Integer> imdbAndMoviesIdMap = new HashMap<>();

        //判断movieList中的图片url是否为空，不为空，说明图片已经下载了
        for (MoviesTagNumbers m : moviesTagNumbers) {
            if (m.getPictureUrl() == null) {//图片没有下载
                imdbAndMoviesIdMap.put(m.getImdbId(), m.getMoviesId());
            }
        }

        if (imdbAndMoviesIdMap.size() != 0) {
            //下载图片
            loadPictures.loadPicturesUrl(imdbAndMoviesIdMap);
            //下载完图片之后,更新数据movies表中的图片url以及将movieList中的pictureUrl值设置为moviesId
            for (MoviesTagNumbers m : moviesTagNumbers) {
                if (m.getPictureUrl() == null) {//图片没有下载
                    m.setPictureUrl(Integer.toString(m.getMoviesId()));
                    //数据库更新
                    listsDao.updateMoviesToPictureUrl(m.getMoviesId());
                }
            }
        }
        return moviesTagNumbers;
    }

    @Override
    public List<MoviesAvgRating> moviesAvgRating() {
        List<MoviesAvgRating> moviesAvgRatings = statisticsRecommender.averageRating();
        //存储没有下载图片的电影id和imdbId
        Map<String, Integer> imdbAndMoviesIdMap = new HashMap<>();

        //判断movieList中的图片url是否为空，不为空，说明图片已经下载了
        for (MoviesAvgRating m : moviesAvgRatings) {
            if (m.getPictureUrl() == null) {//图片没有下载
                imdbAndMoviesIdMap.put(m.getImdbId(), m.getMoviesId());
            }
        }

        if (imdbAndMoviesIdMap.size() != 0) {
            //下载图片
            loadPictures.loadPicturesUrl(imdbAndMoviesIdMap);
            //下载完图片之后,更新数据movies表中的图片url以及将movieList中的pictureUrl值设置为moviesId
            for (MoviesAvgRating m : moviesAvgRatings) {
                if (m.getPictureUrl() == null) {//图片没有下载
                    m.setPictureUrl(Integer.toString(m.getMoviesId()));
                    //数据库更新
                    listsDao.updateMoviesToPictureUrl(m.getMoviesId());
                }
            }
        }
        return moviesAvgRatings;
    }

    @Override
    public MovieDetail movieDetailQuery(int moviesId) {
        return listsDao.queryMovieDetail(moviesId);
    }

    @Override
    public Set<MovieDetailTagAndRating> detailTagAndRatingQuery(int moviesId,int userId) {
        List<Rating> ratingList = listsDao.queryRatings(moviesId,userId);
        List<Tag> tagList = listsDao.queryTags(moviesId,userId);

        Set<MovieDetailTagAndRating> movieDetailTagAndRatingSet = new TreeSet<>();

        Collections.sort(ratingList);
        Collections.sort(tagList);

        int i = 0;
        int j = 0;
        //查电影详情页的用户的评论和评分
        if(userId == 0){
            while (i < ratingList.size() && j < tagList.size()) {
                if (ratingList.get(i).getUserId() == tagList.get(j).getUserId()) {
                    if (ratingList.get(i).getDate().equals(tagList.get(j).getDate())) {
                        saveMovieDetailTagAndRatingToSet(movieDetailTagAndRatingSet
                                ,moviesId
                                ,ratingList.get(i).getUserId()
                                ,ratingList.get(i).getRating()
                                ,tagList.get(j).getTag()
                                ,ratingList.get(i).getDate()
                        );
                        i++;
                        j++;
                    } else {//时间不相等，就不是同一个时间的评论和评分
                        //rating
                        saveMovieDetailTagAndRatingToSet(movieDetailTagAndRatingSet
                                ,moviesId
                                ,ratingList.get(i).getUserId()
                                ,ratingList.get(i).getRating()
                                ,null
                                ,ratingList.get(i).getDate()
                        );
                        i++;
                        //tag
                        saveMovieDetailTagAndRatingToSet(movieDetailTagAndRatingSet
                                ,moviesId
                                ,tagList.get(j).getUserId()
                                ,0
                                ,tagList.get(j).getTag()
                                ,tagList.get(j).getDate()
                        );
                        j++;
                    }
                } else {
                    if (ratingList.get(i).getUserId() < tagList.get(j).getUserId()) {//rating 添加进去
                        saveMovieDetailTagAndRatingToSet(movieDetailTagAndRatingSet
                                ,moviesId
                                ,ratingList.get(i).getUserId()
                                ,ratingList.get(i).getRating()
                                ,null
                                ,ratingList.get(i).getDate()
                        );
                        i++;
                    } else {//tag 添加进去
                        saveMovieDetailTagAndRatingToSet(movieDetailTagAndRatingSet
                                ,moviesId
                                ,tagList.get(j).getUserId()
                                ,0
                                ,tagList.get(j).getTag()
                                ,tagList.get(j).getDate()
                        );
                        j++;
                    }
                }
            }

            if (i != ratingList.size()) {
                for (; i < ratingList.size(); i++) {
                    saveMovieDetailTagAndRatingToSet(movieDetailTagAndRatingSet
                            ,moviesId
                            ,ratingList.get(i).getUserId()
                            ,ratingList.get(i).getRating()
                            ,null
                            ,ratingList.get(i).getDate()
                    );
                }
            }
            if (j != tagList.size()) {
                for (; j < tagList.size(); j++) {
                    saveMovieDetailTagAndRatingToSet(movieDetailTagAndRatingSet
                            ,moviesId
                            ,tagList.get(j).getUserId()
                            ,0
                            ,tagList.get(j).getTag()
                            ,tagList.get(j).getDate()
                    );
                }
            }
        }
        //查询用户评论过或者评分的电影对应的评论和评分
        if(moviesId == 0){
            while (i < ratingList.size() && j < tagList.size()) {
                if (ratingList.get(i).getMoviesId() == tagList.get(j).getMoviesId()) {
                    if (ratingList.get(i).getDate().equals(tagList.get(j).getDate())) {
                        saveMovieDetailTagAndRatingToSet(movieDetailTagAndRatingSet
                                ,ratingList.get(i).getMoviesId()
                                ,userId
                                ,ratingList.get(i).getRating()
                                ,tagList.get(j).getTag()
                                ,ratingList.get(i).getDate()
                        );
                        i++;
                        j++;
                    } else {//时间不相等，就不是同一个时间的评论和评分
                        //rating
                        saveMovieDetailTagAndRatingToSet(movieDetailTagAndRatingSet
                                ,ratingList.get(i).getMoviesId()
                                ,userId
                                ,ratingList.get(i).getRating()
                                ,null
                                ,ratingList.get(i).getDate()
                        );
                        i++;
                        //tag
                        saveMovieDetailTagAndRatingToSet(movieDetailTagAndRatingSet
                                ,tagList.get(j).getMoviesId()
                                ,userId
                                ,0
                                ,tagList.get(j).getTag()
                                ,tagList.get(j).getDate()
                        );
                        j++;
                    }
                } else {
                    if (ratingList.get(i).getMoviesId() < tagList.get(j).getMoviesId()) {//rating 添加进去
                        saveMovieDetailTagAndRatingToSet(movieDetailTagAndRatingSet
                                ,ratingList.get(i).getMoviesId()
                                ,userId
                                ,ratingList.get(i).getRating()
                                ,null
                                ,ratingList.get(i).getDate()
                        );
                        i++;
                    } else {//tag 添加进去
                        saveMovieDetailTagAndRatingToSet(movieDetailTagAndRatingSet
                                ,tagList.get(j).getMoviesId()
                                ,userId
                                ,0
                                ,tagList.get(j).getTag()
                                ,tagList.get(j).getDate()
                        );
                        j++;
                    }
                }
            }

            if (i != ratingList.size()) {
                for (; i < ratingList.size(); i++) {
                    saveMovieDetailTagAndRatingToSet(movieDetailTagAndRatingSet
                            ,ratingList.get(i).getMoviesId()
                            ,userId
                            ,ratingList.get(i).getRating()
                            ,null
                            ,ratingList.get(i).getDate()
                    );
                }
            }
            if (j != tagList.size()) {
                for (; j < tagList.size(); j++) {
                    saveMovieDetailTagAndRatingToSet(movieDetailTagAndRatingSet
                            ,tagList.get(j).getMoviesId()
                            ,userId
                            ,0
                            ,tagList.get(j).getTag()
                            ,tagList.get(j).getDate()
                    );
                }
            }
        }

        for (MovieDetailTagAndRating m: movieDetailTagAndRatingSet) {
            MovieDetail movieDetail = listsDao.queryMovieDetail(m.getMoviesId());
            m.setTitle(movieDetail.getTitle());
        }

        return movieDetailTagAndRatingSet;
    }

    @Override
    public void saveMovieDetailTagAndRatingToSet(Set<MovieDetailTagAndRating> movieDetailTagAndRatingSet, int moviesId, int userId, double rating, String tag, String date) {
        MovieDetailTagAndRating movieDetailTagAndRating = new MovieDetailTagAndRating();

        movieDetailTagAndRating.setMoviesId(moviesId);
        movieDetailTagAndRating.setUserId(userId);
        movieDetailTagAndRating.setTitle(null);
        movieDetailTagAndRating.setRating(rating);
        movieDetailTagAndRating.setTag(tag);
        movieDetailTagAndRating.setDate(date);

        movieDetailTagAndRatingSet.add(movieDetailTagAndRating);
    }

    @Override
    public void deleteTagAndRatingToMovieAndUserId(MovieDetailTagAndRating movieDetailTagAndRating) {
            if (movieDetailTagAndRating.getRating() != 0){
                Rating rating = new Rating();
                rating.setMoviesId(movieDetailTagAndRating.getMoviesId());
                rating.setUserId(movieDetailTagAndRating.getUserId());
                rating.setRating(movieDetailTagAndRating.getRating());
                String timestamp = DateToTimestamp.dateToTimestamp(movieDetailTagAndRating.getDate());
                rating.setDate(timestamp);
                listsDao.deleteRating(rating);
            }
            if (movieDetailTagAndRating.getTag() != null && !movieDetailTagAndRating.getTag().equals("无评论")){
                Tag tag = new Tag();
                tag.setMoviesId(movieDetailTagAndRating.getMoviesId());
                tag.setUserId(movieDetailTagAndRating.getUserId());
                tag.setTag(movieDetailTagAndRating.getTag());
                String timestamp = DateToTimestamp.dateToTimestamp(movieDetailTagAndRating.getDate());
                tag.setDate(timestamp);
                listsDao.deleteTag(tag);
            }
    }

    @Override
    public void updateTagAndRatingToMovieAndUserId(MovieDetailTagAndRating movieDetailTagAndRating,String time) {

        if (movieDetailTagAndRating.getRating() != 0){
            Rating rating = new Rating();
            rating.setMoviesId(movieDetailTagAndRating.getMoviesId());
            rating.setUserId(movieDetailTagAndRating.getUserId());
            rating.setRating(movieDetailTagAndRating.getRating());
            if(movieDetailTagAndRating.getDate() != null){
                String timestamp = DateToTimestamp.dateToTimestamp(movieDetailTagAndRating.getDate());
                rating.setDate(timestamp);
            }
            listsDao.updateRating(rating,time);
        }
        if (movieDetailTagAndRating.getTag() != null && !movieDetailTagAndRating.getTag().equals("无评论") && movieDetailTagAndRating.getTag() != ""){
            Tag tag = new Tag();
            tag.setMoviesId(movieDetailTagAndRating.getMoviesId());
            tag.setUserId(movieDetailTagAndRating.getUserId());
            tag.setTag(movieDetailTagAndRating.getTag());
            if(movieDetailTagAndRating.getDate() != null){
                String timestamp = DateToTimestamp.dateToTimestamp(movieDetailTagAndRating.getDate());
                tag.setDate(timestamp);
            }
            listsDao.updateTag(tag,time);
        }
    }

    @Override
    public List<Movie> queryItemCFRecommender(int moviesId, int userId) throws ExecutionException, InterruptedException {
        if(moviesId != 0){//查看该电影的相关电影
            List<Integer> moviesIdList = listsDao.queryMoviesIdList();
//            double[][] itemsCorrelationCoefficient = ItemCFRecommender.itemsCorrelationCoefficient();
            double[][] itemsCorrelationCoefficient = ItemCFRecommenderThread.itemsCorrelationCoefficient();

            int index = moviesIdList.indexOf(moviesId);
            Map<Integer,Double> itemRecommenderNoSort = new HashMap<>();


            for(int j = 0; j<itemsCorrelationCoefficient[index].length;j++){
                if(itemsCorrelationCoefficient[index][j] >0){
                    itemRecommenderNoSort.put(moviesIdList.get(j),itemsCorrelationCoefficient[index][j]);
                }
            }
            //给该电影的相似电影排序 倒序
            List<Map.Entry<Integer, Double>> list = new ArrayList<Map.Entry<Integer, Double>>(itemRecommenderNoSort.entrySet());
            Collections.sort(list, new Comparator<Map.Entry<Integer, Double>>() {
                @Override
                public int compare(Map.Entry<Integer, Double> o1, Map.Entry<Integer, Double> o2) {
                    return o2.getValue().compareTo(o1.getValue());
                }
            });

            Map<String,Integer> loadPictureList = new HashMap<>();
            List<Movie> movieList = new LinkedList<>();
            int k = 0;
            for (Map.Entry<Integer, Double> map: list){
                if(k < 6){
                    Movie movie = listsDao.queryMovie(map.getKey());
                    if(movie.getPictureUrl() == null){
                        loadPictureList.put(movie.getImdbId(),movie.getMoviesId());
                        movie.setPictureUrl(Integer.toString(movie.getMoviesId()));
                    }
                    movieList.add(movie);
                    k++;
                }
            }
            //下载图片
            if (loadPictureList.size() != 0){
                LoadPictures loadPictures = new LoadPictures();
                loadPictures.loadPicturesUrl(loadPictureList);
            }

            return movieList;
        }
        if(userId != 0){//查询该用户的推荐电影
            List<Movie> movieList = new LinkedList<>();
            Map<String,Integer> loadPictureList = new HashMap<>();
//            Map<Integer, Double> itemCFRecommender = ItemCFRecommender.itemCFRecommender(userId);
            Map<Integer, Double> itemCFRecommender = ItemCFRecommenderThread.itemCFRecommender(userId);
            Set<Integer> keySet = itemCFRecommender.keySet();
            for (Integer mId : keySet) {
                Movie movie = listsDao.queryMovie(mId);
                if(movie.getPictureUrl() == null){
                    loadPictureList.put(movie.getImdbId(),movie.getMoviesId());
                    movie.setPictureUrl(Integer.toString(movie.getMoviesId()));
                }
                movieList.add(movie);
            }
            //下载图片
            if (loadPictureList.size() != 0){
                LoadPictures loadPictures = new LoadPictures();
                loadPictures.loadPicturesUrl(loadPictureList);
            }

            return movieList;
        }
        return null;
    }


}

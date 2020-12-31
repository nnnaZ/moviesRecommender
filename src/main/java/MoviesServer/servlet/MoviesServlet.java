package MoviesServer.servlet;

import MoviesServer.dao.ListsDao;
import MoviesServer.dao.impl.ListsDaoImpl;
import MoviesServer.entity.*;
import MoviesServer.service.CategoryService;
import MoviesServer.service.ListsService;
import MoviesServer.service.impl.CategoryServiceImpl;
import MoviesServer.service.impl.ListsServiceImpl;
import com.alibaba.fastjson.JSONArray;

import javax.servlet.ServletException;
import javax.servlet.ServletInputStream;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ExecutionException;

@WebServlet("/movies/*")
public class MoviesServlet extends BaseServlet {

    /**
     * 查询电影分类
     *
     * @param request
     * @param response
     * @return
     */
    public void classify(HttpServletRequest request, HttpServletResponse response) throws IOException {
        CategoryService categoryService = new CategoryServiceImpl();
        Set<String> classificationQuery = categoryService.moviesClassificationQuery();

        write(classificationQuery, response);
    }

    /**
     * 查询当前页的电影的列表以及页码
     *
     * @param request
     * @param response
     */
    public void moviesAndPageNumber(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String pidStr = request.getParameter("pid");//查询页码
        String genres = request.getParameter("genres");//查询分类
        String searchText = request.getParameter("searchText");//搜索条件
        int pid = Integer.parseInt(pidStr);

        ListsService listsService = new ListsServiceImpl();
        int totalPageNums = listsService.totalPageNumsQuery(genres,searchText);

        List<Movie> movieList = listsService.moviesQuery(pid, genres,searchText);

        Lists<Movie> lists = new Lists<>();
        lists.setCurrentPage(pid);
        lists.setTotalPageNums(totalPageNums);
        lists.setList(movieList);

//        //保存这一页中对应的查询的点用的其实moviesId
//        HttpSession session = request.getSession();
//        session.setAttribute(pidStr,Integer.parseInt(moviesId));

        write(lists, response);
    }

    /**
     * 电影评论数前TopN
     * @param request
     * @param response
     * @throws IOException
     */
    public void moviesTagNumsTopN(HttpServletRequest request, HttpServletResponse response) throws IOException {
        ListsService listsService = new ListsServiceImpl();
        List<MoviesTagNumbers> moviesTagNumbers = listsService.moviesTagNums();

        write(moviesTagNumbers, response);
    }

    /**
     * 电影平均分前TopN
     * @param request
     * @param response
     * @throws IOException
     */
    public void moviesAvgRatingTopN(HttpServletRequest request, HttpServletResponse response) throws IOException {
        ListsService listsService = new ListsServiceImpl();
        List<MoviesAvgRating> moviesAvgRatings = listsService.moviesAvgRating();

        write(moviesAvgRatings, response);
    }

    /**
     * 查询电影详细信息
     * @param request
     * @param response
     * @throws IOException
     */
    public void movieDetail(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String moviesId = request.getParameter("moviesId");//电影

        ListsService listsService = new ListsServiceImpl();
        MovieDetail movieDetail = listsService.movieDetailQuery(Integer.parseInt(moviesId));

        write(movieDetail, response);
    }


        /**
         * 查询电影和相应用户的全部评分和评论
         * @param request
         * @param response
         * @throws IOException
         */
    public void movieDetailRatingAndTag(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String moviesId = request.getParameter("moviesId");
        String userId = request.getParameter("userId");

        ListsService listsService = new ListsServiceImpl();

        Set<MovieDetailTagAndRating> movieDetailTagAndRatings = listsService.detailTagAndRatingQuery(Integer.parseInt(moviesId),Integer.parseInt(userId));

        write(movieDetailTagAndRatings, response);
    }

    /**
     * 获取上一次的上一页中保存的moviesId
     * @param request
     * @param response
     * @throws IOException
     */
//    public void getPrePageMoviesId(HttpServletRequest request, HttpServletResponse response) throws IOException {
//        String pidStr = request.getParameter("pid");//查询页码
//        Object moviesId = request.getSession().getAttribute(pidStr);
//
//        write(moviesId,response);
//    }


    /**
     * 查询用户页面上该用户的全部电影评论和评分
     * @param request
     * @param response
     * @throws IOException
     */
    public void movieDetailRatingAndTagTable(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String moviesId = request.getParameter("moviesId");
        String userId = request.getParameter("userId");

        ListsService listsService = new ListsServiceImpl();

        Set<MovieDetailTagAndRating> movieDetailTagAndRatings = listsService.detailTagAndRatingQuery(Integer.parseInt(moviesId),Integer.parseInt(userId));
        for (MovieDetailTagAndRating m : movieDetailTagAndRatings) {
            if(m.getTag() == null){
                m.setTag("无评论");
            }
        }

        MovieDetailTagAndRatingTable movieDetailTagAndRatingTable = new MovieDetailTagAndRatingTable();
        movieDetailTagAndRatingTable.setCode(0);
        movieDetailTagAndRatingTable.setMsg("表格信息");
        movieDetailTagAndRatingTable.setCount(movieDetailTagAndRatings.size());
        movieDetailTagAndRatingTable.setData(movieDetailTagAndRatings);

        write(movieDetailTagAndRatingTable, response);
    }

    /**
     * 用户删除该条评论和评分
     * @param request
     * @param response
     * @throws IOException
     */
    public void movieDetailRatingAndTagTableDel(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String dataRows_json = request.getParameter("dataRows_json");
//        ServletInputStream inputStream = request.getInputStream();
        MovieDetailTagAndRating movieDetailTagAndRating = JSONArray.parseObject(dataRows_json, MovieDetailTagAndRating.class);

        ListsService listsService = new ListsServiceImpl();
        listsService.deleteTagAndRatingToMovieAndUserId(movieDetailTagAndRating);

    }

    /**
     * 用户修改该条评论和评分
     * @param request
     * @param response
     * @throws IOException
     */
    public void movieDetailRatingAndTagTableUpdate(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String data_json = request.getParameter("data_json");
        String time = request.getParameter("time");//时间戳
//        ServletInputStream inputStream = request.getInputStream();
//        List<MovieDetailTagAndRating> movieDetailTagAndRatingList = JSONArray.parseArray(data_json, MovieDetailTagAndRating.class);
        MovieDetailTagAndRating movieDetailTagAndRating = JSONArray.parseObject(data_json, MovieDetailTagAndRating.class);

        ListsService listsService = new ListsServiceImpl();
        listsService.updateTagAndRatingToMovieAndUserId(movieDetailTagAndRating,time);
    }

    /**
     * 保存用户对该电影的评分
     * @param request
     * @param response
     * @throws IOException
     */
    public void saveUserIdRating(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String rating = request.getParameter("rating");
        HttpSession session = request.getSession();
        Object rating1 = request.getSession().getAttribute("rating");
        if(rating1 == null){
            session.setAttribute("rating",rating);
        }else {
            rating1 = rating;
            session.setAttribute("rating",rating1);
        }

    }

    /**
     * 保存该用户对该电影的评分和评论
     * @param request
     * @param response
     * @throws IOException
     */
    public void saveUserIdRatingAndTag(HttpServletRequest request, HttpServletResponse response) throws IOException {
        Object rating = request.getSession().getAttribute("rating");
        Object userId = request.getSession().getAttribute("userId");
        String tag = request.getParameter("tag");
        String moviesId = request.getParameter("moviesId");

        //当前时间戳
        long ts = System.currentTimeMillis() / 1000;
        String time = String.valueOf(ts);

        MovieDetailTagAndRating movieDetailTagAndRating = new MovieDetailTagAndRating();
        movieDetailTagAndRating.setMoviesId(Integer.parseInt(moviesId));
        movieDetailTagAndRating.setUserId(Integer.parseInt((String)userId));
        movieDetailTagAndRating.setTag(tag);
        movieDetailTagAndRating.setRating(Integer.parseInt((String)rating));
        movieDetailTagAndRating.setDate(null);

        ListsService listsService = new ListsServiceImpl();
        listsService.updateTagAndRatingToMovieAndUserId(movieDetailTagAndRating,time);

    }


    public void getItemCFRecommender(HttpServletRequest request, HttpServletResponse response) throws IOException, ExecutionException, InterruptedException {
        String moviesIdStr = request.getParameter("moviesId");
        String userIdStr = request.getParameter("userId");

        int userId;
        int moviesId;
        if(userIdStr == null || userIdStr == ""){
            userId = 0;
            moviesId = Integer.parseInt(moviesIdStr);
        }else {
            userId = Integer.parseInt(userIdStr);
            moviesId = 0;
        }

        ListsService listsService = new ListsServiceImpl();
        List<Movie> movieList = listsService.queryItemCFRecommender(moviesId, userId);

        write(movieList,response);
    }

}

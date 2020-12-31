package MoviesServer.dao;

import MoviesServer.dao.impl.ListsDaoImpl;
import MoviesServer.entity.Movie;
import MoviesServer.entity.Rating;
import MoviesServer.entity.Tag;
import MoviesServer.utils.DateToTimestamp;
import org.junit.Test;

import java.util.List;

public class ListsDaoImplTest {

    @Test
    public void listsDaoImplTest() {
//        char c= 108;
//        System.out.println(c);

        ListsDao listsDao = new ListsDaoImpl();

        List<Movie> movies1 = listsDao.queryMoviesByPageNumberAndGenres(3, "null", "");
        System.out.println("1---null");
        for (Movie m : movies1) {
            System.out.println(m.toString());
        }

        List<Movie> movies2 = listsDao.queryMoviesByPageNumberAndGenres(1, "Drama", null);
        System.out.println("1---Drama");
        for (Movie m : movies2) {
            System.out.println(m.toString());
        }

        int totalPage1 = listsDao.queryTotalPage(null, null);
        System.out.println("null--null--:" + totalPage1);

        int totalPage2 = listsDao.queryTotalPage("Drama", null);
        System.out.println("Drama--null--:" + totalPage2);

        int totalPage3 = listsDao.queryTotalPage("Drama", "Toy");
        System.out.println("Drama--Toy--:" + totalPage3);
    }

    @Test
    public void queryTagAndRating() {
        ListsDao listsDao = new ListsDaoImpl();

        List<Rating> ratingList1 = listsDao.queryRatings(2, 0);
        List<Tag> tagList1 = listsDao.queryTags(2, 0);
        System.out.println("ratingList-moviesId:2-----------");
        for (Rating r : ratingList1) {
            System.out.println(r.toString());
        }
        System.out.println("-----------tagList-moviesId:2");
        for (Tag t : tagList1) {
            System.out.println(t.toString());
        }

        List<Rating> ratingList2 = listsDao.queryRatings(0, 2);
        List<Tag> tagList2 = listsDao.queryTags(0, 2);
        System.out.println("ratingList-userId:2-----------");
        for (Rating r : ratingList2) {
            System.out.println(r.toString());
        }
        System.out.println("-----------tagList-userId:2");
        for (Tag t : tagList2) {
            System.out.println(t.toString());
        }
    }

    @Test
    public void deleteTagAndRating(){
        ListsDao listsDao = new ListsDaoImpl();

        //1 1 4 964982703
        Rating rating = new Rating();
        rating.setUserId(610);
        rating.setMoviesId(111111111);
        rating.setRating(3);
        rating.setDate("1493846415");
        listsDao.deleteRating(rating);

        //2 60756 funny 1445714994
        Tag tag = new Tag();
        tag.setUserId(610);
        tag.setMoviesId(1111111);
        tag.setTag("fffffffffffff");
        tag.setDate("1493844280");
        listsDao.deleteTag(tag);

    }

    @Test
    public void test(){
        Rating rating = new Rating();
        rating.setDate("1608042942");
        System.out.println(rating.getDate());
    }

    @Test
    public void testTimeStamp(){
        //1608043650   2020-12-15 22:47:30
        //1608042942   2020-12-15 22:35:41
        //1608043816   2020-12-15 22:50:16
        String timestamp = DateToTimestamp.dateToTimestamp("2020-12-15 22:50:16");
        System.out.println(timestamp);

        long ts = System.currentTimeMillis() / 1000;
        String time = String.valueOf(ts);
        System.out.println(time);
    }

    @Test
    public void asciiTest(){
        String s1 = "2020-12-16 16:34:41";
        String s2 = "2020-02-16 02:34:41";
        System.out.println(s1.compareTo(s2));
    }

    @Test
    public void moviesIdAndUserIdnumsTest(){
        ListsDao listsDao = new ListsDaoImpl();
        List<Integer> userIdList = listsDao.queryUserIdList();
        for (Integer userId : userIdList) {
            System.out.println(userId);
        }

        System.out.println("moviesId-------------------------------------------------");
        List<Integer> moviesIdList = listsDao.queryMoviesIdList();
        for (Integer moviesId : moviesIdList) {
            System.out.println(moviesId);
        }

//        double[][] i = new double[10][10];
//        System.out.println(i[0][5]);
    }


}
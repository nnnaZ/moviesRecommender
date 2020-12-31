package MoviesServer.service;

import MoviesServer.entity.*;
import MoviesServer.service.impl.ListsServiceImpl;
import org.junit.Test;

import java.util.List;
import java.util.Set;
import java.util.concurrent.ExecutionException;

public class ListsServiceImplTest {

    @Test
    public void listsServiceImplTest(){
        ListsService listsService = new ListsServiceImpl();
        List<Movie> movies1= listsService.moviesQuery(3,null,null);
        System.out.println("3---null--null");
        for (Movie m : movies1) {
            System.out.println(m.toString());
        }

        List<Movie> movies2= listsService.moviesQuery(1, null,"Toy");
        System.out.println("1---null--Toy");
        for (Movie m : movies2) {
            System.out.println(m.toString());
        }

        List<Movie> movies3 = listsService.moviesQuery(1, "Drama",null);
        System.out.println("1---Drama--null");
        for (Movie m : movies3) {
            System.out.println(m.toString());
        }
        List<Movie> movies4 = listsService.moviesQuery(1, "Drama","Toy");
        System.out.println("1---Drama--Toy");
        for (Movie m : movies4) {
            System.out.println(m.toString());
        }

    }

    @Test
    public void moviesTagNumsAndMoviesAvgRatingTest(){
        ListsServiceImpl listsServiceImpl = new ListsServiceImpl();
        List<MoviesTagNumbers> moviesTagNumbers = listsServiceImpl.moviesTagNums();
        System.out.println("tagNums-------------");
        for (MoviesTagNumbers m: moviesTagNumbers) {
            System.out.println(m.toString());
        }

        List<MoviesAvgRating> moviesAvgRatings = listsServiceImpl.moviesAvgRating();
        System.out.println("avgRatings-------------");
        for (MoviesAvgRating m: moviesAvgRatings) {
            System.out.println(m.toString());
        }
    }

    @Test
    public void moviesDetailTagAndRatingQueryTest(){
        ListsService listsService = new ListsServiceImpl();
        Set<MovieDetailTagAndRating> movieDetailTagAndRatings1 = listsService.detailTagAndRatingQuery(2, 0);
        System.out.println("movies:2------userId:0");
        for (MovieDetailTagAndRating m1: movieDetailTagAndRatings1) {
            System.out.println(m1.toString());
        }

        Set<MovieDetailTagAndRating> movieDetailTagAndRatings2 = listsService.detailTagAndRatingQuery(0, 2);
        System.out.println("movies:0------userId:2");
        for (MovieDetailTagAndRating m1: movieDetailTagAndRatings2) {
            System.out.println(m1.toString());
        }
    }

    @Test
    public void ItemCFRecommender() throws ExecutionException, InterruptedException {
        ListsService listsService = new ListsServiceImpl();
        List<Movie> movieList = listsService.queryItemCFRecommender(0, 2);
        System.out.println("0------2");
        for (Movie m : movieList) {
            System.out.println(m.toString());
        }
        List<Movie> movieList1 = listsService.queryItemCFRecommender(1, 0);
        System.out.println("1--------0");
        for (Movie m : movieList) {
            System.out.println(m.toString());
        }
    }

}

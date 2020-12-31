package Recommender.StatisticsRecommender;

import MoviesServer.entity.MoviesAvgRating;
import MoviesServer.entity.MoviesTagNumbers;
import com.mysql.cj.LicenseConfiguration;
import org.junit.Test;

import java.sql.SQLException;
import java.util.List;

public class StatisticsRecommenderTest {

    @Test
    public void statisticsRecommender() {
        StatisticsRecommender statisticsRecommender = new StatisticsRecommender();

        List<MoviesTagNumbers> moviesTagNumbers = statisticsRecommender.tagNumbers();
        System.out.println("------moviesTagNumbers---------");
        for (MoviesTagNumbers tagNums: moviesTagNumbers) {
            System.out.println(tagNums.toString());
        }

        List<MoviesAvgRating> moviesAvgRatings = statisticsRecommender.averageRating();
        System.out.println("------moviesAvgRatings-------");
        for (MoviesAvgRating avgRating : moviesAvgRatings) {
            System.out.println(avgRating.toString());
        }
    }
}

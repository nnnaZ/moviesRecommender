package Recommender.ItemCFRecommender;

//import org.apache.mahout.cf.taste.common.TasteException;
//import org.apache.mahout.cf.taste.recommender.RecommendedItem;
import org.junit.Test;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ExecutionException;

public class ItemCFRecommenderTest {

//    @Test
//    public void MahoutItemCFRecommenderTest() throws IOException, TasteException {
//        mahoutItemCFRecommender itemCFRecommender = new mahoutItemCFRecommender();
//        List<RecommendedItem> recommendedItems = itemCFRecommender.itemCF(1);
//
//        for (RecommendedItem ri:recommendedItems) {
//            System.out.println("moviesId:"+ri.getItemID()+"-rating:"+ri.getValue());
//        }
//    }

    @Test
    public void ItemCFRecommender() throws ExecutionException, InterruptedException {
        double[][] doubles = ItemCFRecommender.itemsCorrelationCoefficient();
//        Map<Integer, Double> itemCFRecommender = ItemCFRecommender.itemCFRecommender(2);
//        Set<Integer> keySet = itemCFRecommender.keySet();
//
//        for (Integer key : keySet) {
//            System.out.println(key+"-----"+itemCFRecommender.get(key));
//        }

//        double[][] doubles1 = ItemCFRecommender1.itemsCorrelationCoefficient();
        double[][] doubles1 = ItemCFRecommenderThread.itemsCorrelationCoefficient();
        for(int i = 0;i< doubles.length;i++){
            for (int j = 0;j<doubles[i].length;j++){
                if(doubles[i][j] >0 && doubles[i][j] != doubles1[i][j]) System.out.println("i:"+i+",j:"+j+"::::"+doubles[i][j]+"--------------doubles1:"+"i:"+i+",j:"+j+"::::"+doubles1[i][j]);
            }
        }
//         ItemCFRecommender1 结果
//        i:2347,j:2346::::0.1049727762162956--------------
//        i:2348,j:2349::::0.2211159971291128--------------
//        i:2349,j:2348::::0.2211159971291128--------------
//        i:2349,j:2350::::0.13193785541335581--------------
//        i:2350,j:2349::::0.13193785541335581--------------
//        i:2351,j:2352::::1.0606601717798212--------------
//        i:2352,j:2351::::1.0606601717798212--------------
//        i:2359,j:2360::::0.15974461276617433--------------
//        i:2360,j:2359::::0.15974461276617433--------------
//        i:2360,j:2361::::0.50709255283711--------------
//        i:2361,j:2360::::0.50709255283711--------------

//        ItemCFRecommenderSpark itemCFRecommenderSpark = new ItemCFRecommenderSpark();
//        double[][] doubles = itemCFRecommenderSpark.itemsCorrelationCoefficient();

    }
}

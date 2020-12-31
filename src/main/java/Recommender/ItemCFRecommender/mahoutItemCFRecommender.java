//package Recommender.ItemCFRecommender;
//
//import org.apache.mahout.cf.taste.common.TasteException;
//import org.apache.mahout.cf.taste.common.Weighting;
//import org.apache.mahout.cf.taste.impl.common.LongPrimitiveIterator;
//import org.apache.mahout.cf.taste.impl.model.file.FileDataModel;
//import org.apache.mahout.cf.taste.impl.recommender.GenericItemBasedRecommender;
//import org.apache.mahout.cf.taste.impl.similarity.EuclideanDistanceSimilarity;
//import org.apache.mahout.cf.taste.impl.similarity.PearsonCorrelationSimilarity;
//import org.apache.mahout.cf.taste.model.DataModel;
//import org.apache.mahout.cf.taste.recommender.RecommendedItem;
//import org.apache.mahout.cf.taste.recommender.Recommender;
//import org.apache.mahout.cf.taste.similarity.ItemSimilarity;
//import org.springframework.jdbc.core.BeanPropertyRowMapper;
//import org.springframework.jdbc.core.JdbcTemplate;
//
//import java.io.File;
//import java.io.IOException;
//import java.util.List;
//
///**
// * 基于物品的协同过滤推荐
// */
//public class mahoutItemCFRecommender {
//
////    private JdbcTemplate jdbcTemplate = new JdbcTemplate(JDBCUtils.getDataSource());
//
//    public List<RecommendedItem> itemCF(int userId) throws IOException, TasteException {
//
//        String fileName = "D:/Java/IdeaProjects/Movies/target/classes/ratings.csv";
//        //构造数据模型  也可以从数据库中读取数据,创建模型
//        DataModel model = new FileDataModel(new File(fileName));
//        ////计算内容相似度
//        ItemSimilarity item = new PearsonCorrelationSimilarity(model);
//        //构造推荐引擎
//        Recommender r = new GenericItemBasedRecommender(model,item);
//        //得到推荐结果  RecommendedItem数据格式：recommendations.getItemID(),recommendations.getValue()
//        List<RecommendedItem> recommendations = r.recommend(userId, 6);
//
//        //获取全部用户的推荐结果
////        LongPrimitiveIterator iter = model.getUserIDs();
////        while(iter.hasNext())
////        {
////            long uid = iter.nextLong();
////            Lists<RecommendedItem> list = r.recommend(uid,6);  //获取推荐结果
////            System.out.printf("uid:%s",uid);
////            //遍历推荐结果
////            for(RecommendedItem ritem : list)
////            {
////                System.out.printf("(%s,%f)",ritem.getItemID(),ritem.getValue());
////            }
////            System.out.println();
////        }
//
//        return recommendations;
//    }
//}

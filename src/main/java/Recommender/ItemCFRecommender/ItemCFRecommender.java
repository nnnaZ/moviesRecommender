package Recommender.ItemCFRecommender;

import MoviesServer.dao.ListsDao;
import MoviesServer.dao.impl.ListsDaoImpl;
import MoviesServer.entity.Lists;
import MoviesServer.entity.Rating;

import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.*;

/**
 * 基于物品的协同推荐系统（采用改进后的余弦相似度）
 */
public class ItemCFRecommender {

    public static double[][] itemsCorrelationCoefficient() {
        ListsDao listsDao = new ListsDaoImpl();
        List<Rating> ratingList = listsDao.queryRatings(0, 0);
        List<Integer> userIdList = listsDao.queryUserIdList();
        List<Integer> moviesIdList = listsDao.queryMoviesIdList();

        //用户-物品表
        double[][] user_item = new double[userIdList.size()][moviesIdList.size()];
        for (Rating r : ratingList) {
            int userIdIndex = userIdList.indexOf(r.getUserId());
            int moviesIdIndex = moviesIdList.indexOf(r.getMoviesId());

            user_item[userIdIndex][moviesIdIndex] = r.getRating();
        }

        //计算用户的打分平均分
        double[] userAvg = new double[userIdList.size()];
        for (int i = 0; i < user_item.length; i++) {
            double sum = 0;
            int num = 0;
            for (int j = 0; j < user_item[i].length; j++) {
                if (user_item[i][j] != 0) {
                    sum += user_item[i][j];
                    num++;
                }
            }
            if (num != 0) {
                userAvg[i] = keepTwoDecimal(sum / num);
            }
        }

        //物品之间的相关系数
        double[][] item_item = new double[moviesIdList.size()][moviesIdList.size()];
        for (int j = 0; j < moviesIdList.size() - 1; j++) {
            for (int jj = j + 1; jj < moviesIdList.size(); jj++) {
                int count1 = 0;
                int count2 = 0;
                int f1 = 0;//分子
                int m1 = 0;//分母的一部分
                int m2 = 0;//分母的另一部分
                for (int i = 0; i < userIdList.size(); i++) {
                    if (user_item[i][j] == 0) count1++;
                    if (user_item[i][jj] == 0) count2++;
                    if (user_item[i][j] != 0 || user_item[i][jj] != 0) {
                        double avg = userAvg[i];
                        f1 += (user_item[i][j] - avg) * (user_item[i][jj] - avg);
                        m1 += (user_item[i][j] - avg) * (user_item[i][j] - avg);
                        m2 += (user_item[i][jj] - avg) * (user_item[i][jj] - avg);
                    }
                }
                if (count1 != userIdList.size() && count2 != userIdList.size() && m1 != 0 && m2 != 0) {
                    double relative = f1 / (Math.sqrt(m1) * Math.sqrt(m2));
//                    double relativeT = keepTwoDecimal(relative);
                    item_item[j][jj] = relative;
                    item_item[jj][j] = relative;
                } else {
                    item_item[j][jj] = 0;
                    item_item[jj][j] = 0;
                }
            }
        }

        return item_item;
    }

    /**
     * 获取该用户的推荐电影
     *
     * @param userId
     * @return 推荐电影moviesId，预测评分
     */
    public static Map<Integer, Double> itemCFRecommender(int userId) {

        ListsDao listsDao = new ListsDaoImpl();
        //物品之间的相关系数
        double[][] item_item = itemsCorrelationCoefficient();

        //获取用户对所有电影的评分情况
        List<Rating> userIdRatingList = listsDao.queryRatings(0, userId);
        //获取用户对电影的评分 moviesId  rating
        Map<Integer, Double> ratings = new HashMap<>();
        for (Rating r : userIdRatingList) {
            ratings.put(r.getMoviesId(), r.getRating());
        }
        //获取所有电影moviesId
        List<Integer> moviesIdList = listsDao.queryMoviesIdList();

        //给用户推荐的电影未排序
        Map<Integer, Double> recommenderNoSort = new HashMap<>();
        //给用户推荐的电影排序后的
        Map<Integer, Double> recommenderSort = new HashMap<>();

        //对未评分的电影进行评分预测
        for (Integer moviesId : moviesIdList) {
            //找出用户没有评分过的电影
            if (!ratings.containsKey(moviesId)) {
                double fz = 0;
                double fm = 0;
                int moviesIdIndex = moviesIdList.indexOf(moviesId);
                //与该电影相关的电影的相关系数
                int i = moviesIdList.indexOf(moviesId);
                for (int j = 0; j < item_item[i].length; j++) {
                    //去相关系数大于0 的部分
                    if (item_item[i][j] > 0 && ratings.containsKey(moviesIdList.get(j))) {
                        fz += item_item[i][j] * ratings.get(moviesIdList.get(j));
                        fm += item_item[i][j];
                    }
                }
                if (fm != 0) {
                    double pro = keepTwoDecimal(fz / fm);
                    if (pro > 5) System.out.println(pro + "------fz:" + fz + "---------fm:" + fm);
                    recommenderNoSort.put(moviesId, pro);
                }
            }
        }

        //将给用户的电影推荐系统排序 倒序
        List<Map.Entry<Integer, Double>> list = new ArrayList<Map.Entry<Integer, Double>>(recommenderNoSort.entrySet());
        Collections.sort(list, new Comparator<Map.Entry<Integer, Double>>() {
            @Override
            public int compare(Map.Entry<Integer, Double> o1, Map.Entry<Integer, Double> o2) {
                return o2.getValue().compareTo(o1.getValue());
            }
        });

        //推荐6个
        int k = 0;
        for (Map.Entry<Integer, Double> moviesIdRating : list) {
            if (k < 6) {
                recommenderSort.put(moviesIdRating.getKey(), moviesIdRating.getValue());
                k++;
            } else {
                break;
            }
        }
        return recommenderSort;
    }

    public static double keepTwoDecimal(double value) {
        if (value > 0) {
            DecimalFormat df = new DecimalFormat("0.00");
            df.setRoundingMode(RoundingMode.HALF_UP);
            return Double.parseDouble(df.format(value));
        } else {
            value = -value;
            DecimalFormat df = new DecimalFormat("0.00");
            df.setRoundingMode(RoundingMode.HALF_UP);
            return -Double.parseDouble(df.format(value));
        }
    }

}

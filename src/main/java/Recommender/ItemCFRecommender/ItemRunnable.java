package Recommender.ItemCFRecommender;

import java.util.concurrent.Callable;

class ItemRunnable implements Callable<double[][]> {
    //   全局变量 item_item        储存结果 物品之间的相似度
    static volatile double[][] item_item;
    static double[][] user_item; //用户-物品之间的评分表
    static double[] userAvg;//用户平均打分
    static int userIdListLength;//用户数目
    static int moviesIdListLength;//电影数目

    int start;
    int end;

    /**
     * 计算物品之间的相似度
     * @param start
     * @param end
     */
    public ItemRunnable(int start, int end) {
        this.start = start;
        this.end = end;
    }

    @Override
    public double[][] call() {
        int j = start;
        while (j <= end) {
            for (int jj = j + 1; jj < moviesIdListLength; jj++) {
                int count1 = 0;
                int count2 = 0;
                int f1 = 0;//分子
                int m1 = 0;//分母的一部分
                int m2 = 0;//分母的另一部分
                for (int i = 0; i < userIdListLength; i++) {
                    if (user_item[i][j] == 0) count1++;
                    if (user_item[i][jj] == 0) count2++;
                    if (user_item[i][j] != 0 || user_item[i][jj] != 0) {
                        double avg = userAvg[i];
                        f1 += (user_item[i][j] - avg) * (user_item[i][jj] - avg);
                        m1 += (user_item[i][j] - avg) * (user_item[i][j] - avg);
                        m2 += (user_item[i][jj] - avg) * (user_item[i][jj] - avg);
                    }
                }
                if (count1 != userIdListLength && count2 != userIdListLength && m1 != 0 && m2 != 0) {
                    double relative = f1 / (Math.sqrt(m1) * Math.sqrt(m2));
                    item_item[j][jj] = relative;
                    item_item[jj][j] = relative;
                } else {
                    item_item[j][jj] = 0;
                    item_item[jj][j] = 0;
                }
            }
            j++;
        }
        return item_item;
    }
}

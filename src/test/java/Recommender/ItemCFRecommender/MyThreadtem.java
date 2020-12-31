package Recommender.ItemCFRecommender;

public class MyThreadtem extends Thread{
    double[][] item_item;
    double[][] user_item;
    double[] userAvg;
    int userIdListLength;
    int start;
    int end;

//    public MyThreadtem(double[][] item_item, double[][] user_item, double[] userAvg, int userIdListLength, int start, int end) {
//        this.item_item = item_item;
//        this.user_item = user_item;
//        this.userAvg = userAvg;
//        this.userIdListLength = userIdListLength;
//        this.start = start;
//        this.end = end;
//    }

    @Override
    public void run() {
//        int j = start;
//        while (j < end) {
//            int count1 = 0;
//            int count2 = 0;
//            int f1 = 0;//分子
//            int m1 = 0;//分母的一部分
//            int m2 = 0;//分母的另一部分
//            for (int i = 0; i < userIdListLength; i++) {
//                if (user_item[i][j] == 0) count1++;
//                if (user_item[i][j + 1] == 0) count2++;
//                if (user_item[i][j] != 0 || user_item[i][j + 1] != 0) {
//                    double avg = userAvg[i];
//                    f1 += (user_item[i][j] - avg) * (user_item[i][j + 1] - avg);
//                    m1 += (user_item[i][j] - avg) * (user_item[i][j] - avg);
//                    m2 += (user_item[i][j + 1] - avg) * (user_item[i][j + 1] - avg);
//                }
//            }
//            if (count1 != userIdListLength && count2 != userIdListLength) {
//                double relative = f1 / (Math.sqrt(m1) * Math.sqrt(m2));
//                item_item[j][j + 1] = relative;
//                item_item[j + 1][j] = relative;
//            } else {
//                item_item[j][j + 1] = 0;
//                item_item[j + 1][j] = 0;
//            }
//            j++;
//        }

        for (int i = 0; i < 10; i++) {
            System.out.println(Thread.currentThread().getName()+"-------"+i);
        }
    }
}

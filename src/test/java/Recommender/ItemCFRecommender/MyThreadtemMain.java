package Recommender.ItemCFRecommender;

import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class MyThreadtemMain {

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        double[][] user_item = {{0,1,5,3,4,3.5}
                                ,{2.6,3.5,4.5,3,4,2}
                                ,{0,1,3.5,3,2.6,3.5}
                ,{3,2.5,3.5,3,4,2}
                ,{3.5,1,5,2.5,4,3.5}
                ,{3,4.5,5,3,2.6,2}
                ,{2.7,2,5,3,4,2.5}
                ,{1,3,4,3,3.5,3.5}
                ,{2,5,5,3.5,2.5,3}
                ,{1,2,3,4,4,3}};
        List<Integer> userIdList = new LinkedList<>();
        userIdList.add(1);
        userIdList.add(2);
        userIdList.add(3);
        userIdList.add(4);
        userIdList.add(5);
        userIdList.add(6);
        userIdList.add(7);
        userIdList.add(8);
        userIdList.add(9);
        userIdList.add(10);
        double[] userAvg = {1,2,1,3,1.5,2,4,3,2.6,1.8};
        double[][] item_item = new double[user_item[0].length][user_item[0].length];

//        MyThreadtem myThreadtem = new MyThreadtem(item_item,user_item,userAvg,10,0,3);
//        MyThreadtem myThreadtem1 = new MyThreadtem();
//        MyThreadtem myThreadtem2 = new MyThreadtem();
//        myThreadtem1.start();
//        myThreadtem2.start();

        ItemRunnable itemRunnable = new ItemRunnable(0,5);
//        ItemRunnable.item_item = item_item;
        ItemRunnable.user_item = user_item;
        ItemRunnable.userAvg = userAvg;
        ItemRunnable.userIdListLength = userIdList.size();
//        Thread thread1 = new Thread(itemRunnable);
//        Thread thread2 = new Thread(itemRunnable);
//        thread1.start();
//        thread2.start();
        ExecutorService executor = Executors.newFixedThreadPool(3);
        long start1 = System.currentTimeMillis();
        int nums = 6 / 3;
        int start = 0;
        int end = -1;

        List<Future<double[][]>> doubleList = new LinkedList<>();
        for(int i=0; i<3; i++){
            start = end +1;
            if (i == 2) end = 5;
            else end = start + nums;
            Future<double[][]> items = executor.submit(
                    new ItemRunnable( start, end));
            doubleList.add(items);
        }
        executor.shutdown();

//        Future<double[][]> items = executor.submit(
//                    new ItemRunnable(user_item, userAvg, userIdList.size(), 0, 5));
//
        for (Future<double[][]> item: doubleList) {
            double[][] doubles = item.get();
            System.out.println();
            System.out.println("----------------item---------------------------------------------------------");
            for(int i =0;i<doubles.length;i++){
                for(int j = 0;j<doubles[i].length;j++){
                    System.out.print("item_item"+doubles[i][j]+"----------");
                }
                System.out.println();
            }
        }




    }
}

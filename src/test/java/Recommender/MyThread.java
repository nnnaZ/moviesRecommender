package Recommender;

public class MyThread extends Thread {
    @Override
    public void run() {
        System.out.println("线程:" + Thread.currentThread().getName());
        Integer num = DySchedule.getLine();
        System.out.println("startline = " +(num-1000)+",endline = " + num);
    }
}
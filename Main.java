package org.example;

import java.util.Arrays;
import java.util.List;

public class Main {
    private static List<String> data = Arrays.asList(
            "aa", "bbb", "c", "d", "aa", "bbb", "c", "d", "aa", "bbb", "c", "d", "aa", "bbb", "c", "d",
            "aa", "bbb", "c", "d", "aa", "bbb", "c", "d", "aa", "bbb", "c", "d", "aa", "bbb", "c", "d",
            "aa", "bbb", "c", "d", "aa", "bbb", "c", "d", "aa", "bbb", "c", "d", "aa", "bbb", "c", "d",
            "aa", "bbb", "c", "d", "aa", "bbb", "c", "d", "aa", "bbb", "c", "d", "aa", "bbb", "c", "d");

    public static void main(String[] args) throws InterruptedException {
        int totalNumber = doParallelWork(data);
        System.out.println("Total number of elements: " + totalNumber);
    }

    public static int doParallelWork(List<String> data) throws InterruptedException {
        Thread t1 = new CounterThread(0, data.size()/3-1, data);
        Thread t2 = new CounterThread(data.size()/3, 2*data.size()/3-1, data);
        Thread t3 = new CounterThread(2*data.size()/3, data.size()-1, data);

        t1.start();
        t2.start();
        t3.start();

        t1.join();
        t2.join();
        t3.join();

        return CounterThread.count;
    }
}
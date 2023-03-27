package org.example;

import java.util.ArrayList;
import java.util.List;


class CounterThread extends Thread {
    public static int count = 0;
    private int startIndex;
    private int endIndex;

    private List<String> data;

    public CounterThread(int startIndex, int endIndex, List<String> data) {
        this.startIndex = startIndex;
        this.endIndex = endIndex;
        this.data = data;
    }

    @Override
    public void run() {
        synchronized (data) {
            for (int i = startIndex; i <= endIndex; i++) {
                count += data.get(i).length();
            }
            data.notify();
        }

    }
}

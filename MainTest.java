package org.example;

import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class MainTest {

    @Test
    void basicTest() {
        ParallelWorker parallelWorker = new ParallelWorker();
        final List<String> data = Arrays.asList(
                "aa", "bbb", "c", "d", "aa", "bbb", "c", "d", "aa", "bbb", "c", "d", "aa", "bbb", "c", "d",
                "aa", "bbb", "c", "d", "aa", "bbb", "c", "d", "aa", "bbb", "c", "d", "aa", "bbb", "c", "d",
                "aa", "bbb", "c", "d", "aa", "bbb", "c", "d", "aa", "bbb", "c", "d", "aa", "bbb", "c", "d",
                "aa", "bbb", "c", "d", "aa", "bbb", "c", "d", "aa", "bbb", "c", "d", "aa", "bbb", "c", "d");


        int totalNumber = parallelWorker.doParallelWork(data);
        assertEquals(totalNumber, 112);
    }

    @Test
    void emptyTest() {
        ParallelWorker parallelWorker = new ParallelWorker();
        final List<String> data = Arrays.asList();


        int totalNumber = parallelWorker.doParallelWork(data);
        assertEquals(totalNumber, 0);
    }

    @Test
    void oneItemTest() {
        ParallelWorker parallelWorker = new ParallelWorker();
        final List<String> data = Arrays.asList("a");


        int totalNumber = parallelWorker.doParallelWork(data);
        assertEquals(totalNumber, 1);
    }
}
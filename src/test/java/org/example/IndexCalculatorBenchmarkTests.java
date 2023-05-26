package org.example;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.openjdk.jmh.annotations.Mode;
import org.openjdk.jmh.annotations.*;
import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.options.*;
import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.function.BiFunction;

public class IndexCalculatorBenchmarkTests {

    private static final Book book;
    private static final int[] everyPageNo = new int[100];
    static {
        List<String> words = new ArrayList<>(List.of("alpha", "bravo", "charlie", "delta", "echo", "foxtrot", "golf", "hotel", "india", "juliett", "kilo",
                "lima", "mike", "november", "oscar", "papa", "quebec", "romeo", "sierra", "tango", "uniform", "victor", "whiskey", "xray", "yankee", "zulu"));
        List<Page> pages = new ArrayList<>();
        for(int i = 1; i<= 100; i++){
            everyPageNo[i - 1] = i;
            pages.add(new Page(i, words));
            Collections.shuffle(words);
        }
        book = new Book(pages);
    }

    @Test
    public void runBenchmarks() throws Exception {
        Options options = new OptionsBuilder()
                .include(this.getClass().getName() + ".*")
                .mode(Mode.AverageTime)
                .warmupTime(TimeValue.seconds(1))
                .warmupIterations(6)
                .threads(1)
                .measurementIterations(10)
                .forks(1)
                .shouldFailOnError(true)
                .shouldDoGC(true)
                .build();
        new Runner(options).run();
    }

    @Benchmark
    @OutputTimeUnit(TimeUnit.MILLISECONDS)
    public void many_page_book_has_keywords_sorted_test() {
        many_page_book_has_keywords_test(IndexCalculator::calculateIndexSorted);
    }

    @Benchmark
    @OutputTimeUnit(TimeUnit.MILLISECONDS)
    public void many_page_book_has_keywords_set_test() {
        many_page_book_has_keywords_test(IndexCalculator::calculateIndexWithSet);
    }

    void many_page_book_has_keywords_test(BiFunction<Book, List<String>, Index> indexCalculatorFunction){
        List<String> keywords = List.of("bravo", "tango", "alpha", "zulu", "notIncluded");
        Index expectedIndex = new Index();
        expectedIndex.addPages("bravo", everyPageNo);
        expectedIndex.addPages("tango", everyPageNo);
        expectedIndex.addPages("alpha", everyPageNo);
        expectedIndex.addPages("zulu", everyPageNo);
        Index actualIndex = indexCalculatorFunction.apply(book, keywords);
        Assertions.assertEquals(expectedIndex, actualIndex);
    }

}
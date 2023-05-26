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

    private static final List<String> words = new ArrayList<>(List.of("alpha", "bravo", "charlie", "delta", "echo", "foxtrot", "golf", "hotel", "india", "juliett", "kilo",
            "lima", "mike", "november", "oscar", "papa", "quebec", "romeo", "sierra", "tango", "uniform", "victor", "whiskey", "xray", "yankee", "zulu"));
    static {
        Collections.shuffle(words, new Random(5));
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
    public void multiple_page_book_has_keywords_sorted_test() {
        multiple_page_book_has_keywords_test(IndexCalculator::calculateIndexSorted);
    }

    @Benchmark
    @OutputTimeUnit(TimeUnit.MILLISECONDS)
    public void multiple_page_book_has_keywords_set_test() {
        multiple_page_book_has_keywords_test(IndexCalculator::calculateIndexWithSet);
    }

    void multiple_page_book_has_keywords_test(BiFunction<Book, List<String>, Index> indexCalculatorFunction){
        Book book = new Book(List.of(new Page(1, words), new Page(2, words.subList(words.size() / 2, words.size()))));
        List<String> keywords = List.of("bravo", "tango", "alpha", "zulu", "notIncluded");
        Index expectedIndex = new Index();
        expectedIndex.addPage("bravo", 1);
        expectedIndex.addPages("tango", 1, 2);
        expectedIndex.addPage("alpha", 1);
        expectedIndex.addPages("zulu", 1, 2);
        Index actualIndex = indexCalculatorFunction.apply(book, keywords);
        Assertions.assertEquals(expectedIndex, actualIndex);
    }

}

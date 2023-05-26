package org.example;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import java.util.*;
import java.util.function.*;

public class IndexCalculatorTests {

    private final List<String> words = List.of("alpha", "bravo", "charlie", "delta", "echo", "foxtrot", "golf", "hotel", "india", "juliett", "kilo",
            "lima", "mike", "november", "oscar", "papa", "quebec", "romeo", "sierra", "tango", "uniform", "victor", "whiskey", "xray", "yankee", "zulu");

    @Test
    void empty_book_sorted_test(){
        empty_book_test(IndexCalculator::calculateIndexSorted);
    }

    @Test
    void empty_book_set_test(){
        empty_book_test(IndexCalculator::calculateIndexWithSet);
    }

    void empty_book_test(BiFunction<Book, List<String>, Index> indexCalculatorFunction){
        Book book = new Book(List.of());
        List<String> keywords = List.of();
        Index expectedIndex = new Index();
        Assertions.assertEquals(expectedIndex, indexCalculatorFunction.apply(book, keywords));
    }

    @Test
    void empty_book_has_keywords_sorted_test(){
        empty_book_has_keywords_test(IndexCalculator::calculateIndexSorted);
    }

    @Test
    void empty_book_has_keywords_set_test(){
        empty_book_has_keywords_test(IndexCalculator::calculateIndexWithSet);
    }

    void empty_book_has_keywords_test(BiFunction<Book, List<String>, Index> indexCalculatorFunction){
        Book book = new Book(List.of());
        List<String> keywords = List.of("a", "b", "c", "d");
        Index expectedIndex = new Index();
        Assertions.assertEquals(expectedIndex, indexCalculatorFunction.apply(book, keywords));
    }

    @Test
    void single_page_book_no_keywords_sorted_test(){
        single_page_book_no_keywords_test(IndexCalculator::calculateIndexSorted);
    }

    @Test
    void single_page_book_no_keywords_set_test(){
        single_page_book_no_keywords_test(IndexCalculator::calculateIndexWithSet);
    }

    void single_page_book_no_keywords_test(BiFunction<Book, List<String>, Index> indexCalculatorFunction){
        Book book = new Book(List.of(new Page(1, words)));
        List<String> keywords = List.of();
        Index expectedIndex = new Index();
        Assertions.assertEquals(expectedIndex, indexCalculatorFunction.apply(book, keywords));
    }

    @Test
    void single_page_book_has_keywords_sorted_test(){
        single_page_book_has_keywords_test(IndexCalculator::calculateIndexSorted);
    }

    @Test
    void single_page_book_has_keywords_set_test(){
        single_page_book_has_keywords_test(IndexCalculator::calculateIndexWithSet);
    }

    void single_page_book_has_keywords_test(BiFunction<Book, List<String>, Index> indexCalculatorFunction){
        Book book = new Book(List.of(new Page(1, words)));
        List<String> keywords = List.of("bravo", "tango", "alpha", "zulu", "notIncluded");
        Index expectedIndex = new Index();
        expectedIndex.addPage("bravo", 1);
        expectedIndex.addPage("tango", 1);
        expectedIndex.addPage("alpha", 1);
        expectedIndex.addPage("zulu", 1);
        Index actualIndex = indexCalculatorFunction.apply(book, keywords);
        Assertions.assertEquals(expectedIndex, actualIndex);
    }

    @Test
    void multiple_page_book_has_keywords_sorted_test(){
        multiple_page_book_has_keywords_test(IndexCalculator::calculateIndexSorted);
    }

    @Test
    void multiple_page_book_has_keywords_set_test(){
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

    @Test
    void single_shuffled_page_book_has_keywords_sorted_test(){
        single_shuffled_page_book_has_keywords_test(IndexCalculator::calculateIndexSorted);
    }

    @Test
    void single_shuffled_page_book_has_keywords_set_test(){
        single_shuffled_page_book_has_keywords_test(IndexCalculator::calculateIndexWithSet);
    }

    void single_shuffled_page_book_has_keywords_test(BiFunction<Book, List<String>, Index> indexCalculatorFunction){
        List<String> shuffledWords = new ArrayList<>(words);
        Collections.shuffle(shuffledWords, new Random(1));
        Book book = new Book(List.of(new Page(1, shuffledWords)));
        List<String> keywords = List.of("bravo", "tango", "alpha", "zulu", "notIncluded");
        Index expectedIndex = new Index();
        expectedIndex.addPage("bravo", 1);
        expectedIndex.addPage("tango", 1);
        expectedIndex.addPage("alpha", 1);
        expectedIndex.addPage("zulu", 1);
        Index actualIndex = indexCalculatorFunction.apply(book, keywords);
        Assertions.assertEquals(expectedIndex, actualIndex);
    }
}
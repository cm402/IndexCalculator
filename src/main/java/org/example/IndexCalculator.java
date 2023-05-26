package org.example;

import java.util.*;
public class IndexCalculator {

    public static Index calculateIndexWithSet(Book book, List<String> keywords){
        Index index = new Index();
        if(keywords.isEmpty())
            return index;
        for(Page page: book.getPages()){
            HashSet<String> words = new HashSet<>(page.getWords());
            for(String keyword: keywords){
                if(words.contains(keyword)){
                    index.addPage(keyword, page.getPageNo());
                }
            }
        }
        return index;
    }

    public static Index calculateIndexSorted(Book book, List<String> keywords){
        Index index = new Index();
        if(keywords.isEmpty())
            return index;
        List<String> keywordsCopy = new ArrayList<>(keywords); // to ensure keywords isn't changed
        Collections.sort(keywordsCopy);
        for(Page page: book.getPages()){
            List<String> words = new ArrayList<>(page.getWords());
            Collections.sort(words);
            int currentKeywordIndex = 0;
            for(String word: words){
                if(keywordsCopy.get(currentKeywordIndex).equals(word)){
                    index.addPage(keywordsCopy.get(currentKeywordIndex++), page.getPageNo());
                } else if(keywordsCopy.get(currentKeywordIndex).compareTo(word) < 0){
                    currentKeywordIndex++;
                }
                if(currentKeywordIndex == keywordsCopy.size())
                    break;
            }
        }
        return index;
    }
}

class Index {
    private final Map<String, Set<Integer>> keywordsToPages = new HashMap<>();
    void addPage(String keyword, int pageNo){
        if(keywordsToPages.containsKey(keyword)){
            keywordsToPages.get(keyword).add(pageNo);
        } else {
            keywordsToPages.put(keyword, new HashSet<>(List.of(pageNo)));
        }
    }
    void addPages(String keyword, int... pageNos){
        for(int pageNo: pageNos)
            addPage(keyword, pageNo);
    }
    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null)
            return false;
        if (getClass() != o.getClass())
            return false;
        Index index = (Index) o;
        return index.keywordsToPages.equals(this.keywordsToPages);
    }
}

class Book {
    private final List<Page> pages;
    public Book(List<Page> pages){
        this.pages = pages;
    }
    public Page getPage(int pageNo){
        return pages.get(pageNo - 1);
    }
    List<Page> getPages(){
        return pages;
    }
}

class Page {
    private final int pageNo;
    private final List<String> words;
    public Page(int pageNo, List<String> words){
        this.pageNo = pageNo;
        this.words = words;
    }
    int getPageNo() {
        return pageNo;
    }
    List<String> getWords(){
        return words;
    }
}
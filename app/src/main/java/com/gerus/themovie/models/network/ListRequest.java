package com.gerus.themovie.models.network;

import com.gerus.themovie.models.Movie;

import java.util.List;

/**
 * Created by gerus-mac on 23/03/17.
 */

public class ListRequest<T> {

    private int page;
    private int total_results;
    private int total_pages;
    private List<T> results;

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public int getTotal_results() {
        return total_results;
    }

    public void setTotal_results(int total_results) {
        this.total_results = total_results;
    }

    public int getTotal_pages() {
        return total_pages;
    }

    public void setTotal_pages(int total_pages) {
        this.total_pages = total_pages;
    }

    public List<T> getResults() {
        return results;
    }

    public void setResults(List<T> results) {
        this.results = results;
    }

}

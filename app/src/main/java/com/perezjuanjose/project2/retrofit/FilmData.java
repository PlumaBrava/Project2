package com.perezjuanjose.project2.retrofit;

/**
 * Created by perez.juan.jose on 25/09/2015.
 */
//@Generated("org.jsonschema2pojo")

import java.util.ArrayList;
import java.util.List;

public class FilmData {
    private int page;
    private List<Result> results = new ArrayList<Result>();
    private int total_pages;
    private int total_results;

    public FilmData(int page, List<Result> results, int total_pages, int total_results) {
        this.page = page;
        this.results = results;
        this.total_pages = total_pages;
        this.total_results = total_results;
    }

    public FilmData() {

    }

    //@Generated("org.jsonschema2pojo")


    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public List<Result> getResults() {
        return results;
    }

    public void setResults(List<Result> results) {
        this.results = results;
    }

    public int getTotal_pages() {
        return total_pages;
    }

    public void setTotal_pages(int total_pages) {
        this.total_pages = total_pages;
    }

    public int getTotal_results() {
        return total_results;
    }

    public void setTotal_results(int total_results) {
        this.total_results = total_results;
    }


public class Result {

    private boolean adult;
    private String backdrop_path;
    private List<Integer> genreIds = new ArrayList<Integer>();
    private int id;
    private String original_language;
    private String original_title;
    private String overview;
    private String release_date;
    private String poster_path;
    private float popularity;
    private String title;
    private boolean video;
    private float vote_average;
    private int vote_count;

    public Result(boolean adult, String backdrop_path, List<Integer> genreIds, int id, String original_language, String original_title, String overview, String release_date, String poster_path, float popularity, String title, boolean video, float vote_average, int vote_count) {
        this.adult = adult;
        this.backdrop_path = backdrop_path;
        this.genreIds = genreIds;
        this.id = id;
        this.original_language = original_language;
        this.original_title = original_title;
        this.overview = overview;
        this.release_date = release_date;
        this.poster_path = poster_path;
        this.popularity = popularity;
        this.title = title;
        this.video = video;
        this.vote_average = vote_average;
        this.vote_count = vote_count;
    }

    public Result() {

    }

    public boolean isAdult() {
        return adult;
    }

    public void setAdult(boolean adult) {
        this.adult = adult;
    }

    public Object getBackdrop_path() {
        return backdrop_path;
    }

    public void setBackdrop_path(String backdrop_path) {
        this.backdrop_path = backdrop_path;
    }

    public List<Integer> getGenreIds() {
        return genreIds;
    }

    public void setGenreIds(List<Integer> genreIds) {
        this.genreIds = genreIds;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getOriginal_language() {
        return original_language;
    }

    public void setOriginal_language(String original_language) {
        this.original_language = original_language;
    }

    public String getOriginal_title() {
        return original_title;
    }

    public void setOriginal_title(String original_title) {
        this.original_title = original_title;
    }

    public String getOverview() {
        return overview;
    }

    public void setOverview(String overview) {
        this.overview = overview;
    }

    public Object getRelease_date() {
        return release_date;
    }

    public void setRelease_date(String release_date) {
        this.release_date = release_date;
    }

    public Object getPoster_path() {
        return poster_path;
    }

    public void setPoster_path(String poster_path) {
        this.poster_path = poster_path;
    }

    public float getPopularity() {
        return popularity;
    }

    public void setPopularity(float popularity) {
        this.popularity = popularity;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public boolean isVideo() {
        return video;
    }

    public void setVideo(boolean video) {
        this.video = video;
    }

    public float getVote_average() {
        return vote_average;
    }

    public void setVote_average(float vote_average) {
        this.vote_average = vote_average;
    }

    public int getVote_count() {
        return vote_count;
    }

    public void setVote_count(int vote_count) {
        this.vote_count = vote_count;
    }
}
}
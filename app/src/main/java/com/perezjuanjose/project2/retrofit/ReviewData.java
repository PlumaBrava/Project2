package com.perezjuanjose.project2.retrofit;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by perez.juan.jose on 25/09/2015.
 */
public class ReviewData {

    public int id;
    public int page;
    public List<Result> results = new ArrayList<Result>();
    public int totalPages;
    public int totalResults;

    public ReviewData(int id, int page, List<Result> results, int totalPages, int totalResults) {
        this.id = id;
        this.page = page;
        this.results = results;
        this.totalPages = totalPages;
        this.totalResults = totalResults;
    }

    public ReviewData() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getTotalResults() {
        return totalResults;
    }

    public void setTotalResults(int totalResults) {
        this.totalResults = totalResults;
    }

    public int getTotalPages() {
        return totalPages;
    }

    public void setTotalPages(int totalPages) {
        this.totalPages = totalPages;
    }

    public List<Result> getResults() {
        return results;
    }

    public void setResults(List<Result> results) {
        this.results = results;
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    //}
//-----------------------------------com.example.Result.java-----------------------------------
//
//        package com.example;
//
//        import javax.annotation.Generated;
//
//@Generated("org.jsonschema2pojo")
public class Result {

    public String id;
    public String author;
    public String content;
    public String url;

        public Result(String id, String url, String content, String author) {
            this.id = id;
            this.url = url;
            this.content = content;
            this.author = author;
        }

        public Result() {
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public String getAuthor() {
            return author;
        }

        public void setAuthor(String author) {
            this.author = author;
        }
    }




}

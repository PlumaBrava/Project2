package com.perezjuanjose.project2;


/**
 * Created by JJ_PEREZ on 22/09/2015.
 */
public class Film {


    String name;
    int votes;
    String review;

    public Film( String name, int votes, String review) {

        this.name = name;
        this.votes = votes;
        this.review = review;
    }



    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getVotes() {
        return votes;
    }

    public void setVotes(int votes) {
        this.votes = votes;
    }

    public String getReview() {
        return review;
    }

    public void setReview(String review) {
        this.review = review;
    }
}

package com.perezjuanjose.project2.retrofit;



import java.util.ArrayList;
import java.util.List;


public class VideoData {
    public static String API_KEY="79424eca98daa0b906a464bf7d8f9f0f";
    private int id; //Nro de Id de la pelicula en la base de datos
    private List<Films> results = new ArrayList<Films>();

    /**
     * No args constructor for use in serialization
     *
     */
    public VideoData() {
    }

    /**
     *
     * @param id
     * @param results
     */
    public VideoData(int id, List<Films> results) {
        this.id = id;
        this.results = results;
    }

    /**
     *
     * @return
     * The id
     */
    public int getId() {
        return id;
    }

    /**
     *
     * @param id
     * The id
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     *
     * @return
     * The results
     */
    public List<Films> getResults() {
        return results;
    }

    /**
     *
     * @param results
     * The results
     */
    public void setResults(List<Films> results) {
        this.results = results;
    }

//    @Override
//    public String toString() {
//        return ToStringBuilder.reflectionToString(this);
//    }






}
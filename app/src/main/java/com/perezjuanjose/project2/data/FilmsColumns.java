package com.perezjuanjose.project2.data;

import net.simonvt.schematic.annotation.AutoIncrement;
import net.simonvt.schematic.annotation.DataType;
import net.simonvt.schematic.annotation.NotNull;
import net.simonvt.schematic.annotation.PrimaryKey;

/**
 * Created by perez.juan.jose on 25/09/2015.
 */
public class FilmsColumns {

    @DataType(DataType.Type.INTEGER) @PrimaryKey
    @AutoIncrement
    public static final String _ID ="_id";

    @DataType(DataType.Type.INTEGER)
    public static final String ADULT ="adult";

    @DataType(DataType.Type.TEXT)
    public static final String BACKDROP_PATH = "backdrop_path";

//    private List<Integer> genreIds = new ArrayList<Integer>();  WE DONT NEED THIS INFO

    @DataType(DataType.Type.INTEGER)
    public static final String ID_MOVI_DB = "id";

    @DataType(DataType.Type.TEXT)
    public static final String ORIGINAL_LANGUAGE = "original_language";

    @DataType(DataType.Type.TEXT)
    public static final String ORIGINAL_TITLE = "original_title";

    @DataType(DataType.Type.TEXT)
    public static final String OVERVIEW = "overview";

    @DataType(DataType.Type.TEXT)
    public static final String RELEASSE_DATE= "release_date";

    @DataType(DataType.Type.TEXT)
    public static final String POSTER_PATH= "poster_path";

    @DataType(DataType.Type.REAL)
    public static final String POPULARITY= "popularity";

    @DataType(DataType.Type.TEXT)
    public static final String TITLE= "title";

    @DataType(DataType.Type.INTEGER)
    public static final String VIDEO = "video";

    @DataType(DataType.Type.REAL)
    public static final String VOTE_AVERAGE= "vote_average";

    @DataType(DataType.Type.INTEGER)
    public static final String VOTE_COUNT = "vote_count";

    @DataType(DataType.Type.INTEGER)
    public static final String MOST_POPULAR = "most_popular";

    @DataType(DataType.Type.INTEGER)
    public static final String HIGHEST_RATED = "highest_rated";

    @DataType(DataType.Type.INTEGER)
    public static final String FAVORITE = "favorite";

//
//    private boolean adult;
//    private String backdrop_path;
//    private List<Integer> genreIds = new ArrayList<Integer>();
//    private int id;
//    private String original_language;
//    private String original_title;
//    private String overview;
//    private String release_date;
//    private String poster_path;
//    private float popularity;
//    private String title;
//    private boolean video;
//    private float vote_average;
//    private int vote_count;
}

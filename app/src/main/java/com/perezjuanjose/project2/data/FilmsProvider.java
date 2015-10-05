package com.perezjuanjose.project2.data;

import android.net.Uri;

import net.simonvt.schematic.annotation.ContentProvider;
import net.simonvt.schematic.annotation.ContentUri;
import net.simonvt.schematic.annotation.InexactContentUri;
import net.simonvt.schematic.annotation.TableEndpoint;

/**
 * Created by JJ_PEREZ on 22/09/2015.
 */

@ContentProvider(authority = FilmsProvider.AUTHORITY, database = FilmDataBase.class)
public class FilmsProvider {

    public static final String AUTHORITY =
            "com.perezjuanjose.project2.data.FilmsProvider";
    static final Uri BASE_CONTENT_URI = Uri.parse("content://" + AUTHORITY);

    interface Path{
        String FILMS = "films";
        String TRILERS = "trailers";
        String REVIEWS = "reviews";
        String FAVORITOS = "favoritos";
    }

    private static Uri buildUri(String ... paths){
        Uri.Builder builder = BASE_CONTENT_URI.buildUpon();
        for (String path : paths){
            builder.appendPath(path);
        }
        return builder.build();
    }

    //  Movi's Table

        @TableEndpoint(table = FilmDataBase.FILMS) public static class Films{
            @ContentUri(
                    path = Path.FILMS,
                    type = "vnd.android.cursor.dir/movies",
                    defaultSort = FilmsColumns.POPULARITY + " DESC")//OJO CON Desc lo escribi...
            public static final Uri CONTENT_URI = buildUri(Path.FILMS);

            @InexactContentUri(
                    name = "MOVI_ID",
                    path =  Path.FILMS + "/#",
                    type = "vnd.android.cursor.item/movies",
                    whereColumn =  FilmsColumns._ID,
                    pathSegment = 1)
            public static Uri withId(long id){
                return buildUri(Path.FILMS, String.valueOf(id));
            }

            @InexactContentUri(
                    name = "MOVI_FAVORITOS",
                    path =  Path.FAVORITOS + "/#",
                    type = "vnd.android.cursor.dir/movies",
                    whereColumn =  FilmsColumns.FAVORITE,
                    pathSegment = 1)
            public static Uri withFavoritos(long id){
                return buildUri(Path.FAVORITOS, String.valueOf(id));
            }


        }

        //  Trailer's Table

            @TableEndpoint(table = FilmDataBase.TRILERS) public static class Trailes {
                @ContentUri(
                        path = Path.TRILERS,
                        type = "vnd.android.cursor.dir/trailers",
                        defaultSort = TrailerColumns._ID + " ASC")//OJO CON Desc lo escribi...
                public static final Uri CONTENT_URI = buildUri(Path.TRILERS);

                @InexactContentUri(
                        name = "TRAILER_ID",
                        path = Path.TRILERS + "/#",
                        type = "vnd.android.cursor.item/trailer",
                        whereColumn = TrailerColumns.REF_ID_MOVIE,
                        pathSegment = 1)
                public static Uri withId(long id) {return buildUri(Path.TRILERS, String.valueOf(id));
                }

            }

    //  Reiews's Table

    @TableEndpoint(table = FilmDataBase.REVIEWS) public static class Reviews {
        @ContentUri(
                path = Path.REVIEWS,
                type = "vnd.android.cursor.dir/reviews",
                defaultSort =ReviewColumns._ID + " ASC")//OJO CON Desc lo escribi...
        public static final Uri CONTENT_URI = buildUri(Path.REVIEWS);

        @InexactContentUri(
                name = "TRAILER_ID",
                path = Path.REVIEWS + "/#",
                type = "vnd.android.cursor.item/reviews",
                whereColumn = ReviewColumns.REF_ID_MOVIE,
                pathSegment = 1)
        public static Uri withId(long id) {return buildUri(Path.REVIEWS, String.valueOf(id));
        }

    }

}

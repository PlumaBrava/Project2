package com.perezjuanjose.project2.data;





import net.simonvt.schematic.annotation.AutoIncrement;
import net.simonvt.schematic.annotation.DataType;
import net.simonvt.schematic.annotation.PrimaryKey;

/**
 * Created by perez.juan.jose on 25/09/2015.
 */
public class ReviewColumns {
    //Id de la Tabla
    @DataType(DataType.Type.INTEGER) @PrimaryKey
    @AutoIncrement
    public static final String _ID ="_id";

    //Id de la pelicula en la web
    @DataType(DataType.Type.INTEGER)
    public static final String REF_ID_MOVIE ="ref_id_movie";

    //Id del comentario
    @DataType(DataType.Type.TEXT)
    public static final String ID_REVIEW_DB = "id_review_db";

    //Id del comentario
    @DataType(DataType.Type.TEXT)
    public static final String AUTOR = "autor";

    //Id del comentario
    @DataType(DataType.Type.TEXT)
    public static final String COMENT = "coment";

    //Id del comentario
    @DataType(DataType.Type.TEXT)
    public static final String URL = "url";





//    public int id;
//    public int page;
//    public List<Result> results = new ArrayList<Result>();
//    public int totalPages;
//    public int totalResults;
//
//    public class Result {
//
//        public String id;
//        public String author;
//        public String content;
//        public String url;
//    }

}

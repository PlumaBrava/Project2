package com.perezjuanjose.project2.data;





import net.simonvt.schematic.annotation.AutoIncrement;
import net.simonvt.schematic.annotation.DataType;
import net.simonvt.schematic.annotation.PrimaryKey;
import net.simonvt.schematic.annotation.NotNull;

/**
 * Created by perez.juan.jose on 25/09/2015.
 */
public class TrailerColumns {

    //indice de la tabla
    @DataType(DataType.Type.INTEGER) @PrimaryKey
    @AutoIncrement
    public static final String _ID ="_id";

    //ID de la pelicula en la web
    @DataType(DataType.Type.INTEGER)
    public static final String REF_ID_MOVIE ="ref_id_movie";

    //Id del trailer en la web
    @DataType(DataType.Type.TEXT)
    public static final String ID_TRILER_DB = "id_traile_db";

    @DataType(DataType.Type.TEXT)
    public static final String ISO6391 = "iso6391";

    @DataType(DataType.Type.TEXT)
    public static final String KEY = "key";

    @DataType(DataType.Type.TEXT)
    public static final String NAME = "name";

    @DataType(DataType.Type.INTEGER)
    public static final String SICE ="size";

    @DataType(DataType.Type.TEXT)
    public static final String TYPE = "type";


//    private String id;
//    private String iso6391;
//    private String key;
//    private String name;
//    private String site;
//    private int size;
//    private String type;


}

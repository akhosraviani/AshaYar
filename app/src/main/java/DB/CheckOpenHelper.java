package DB;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by SONY1 on 11/7/2015.
 */
public class CheckOpenHelper  extends SQLiteOpenHelper {
    public static final String DATABASE_NAME = "check";
    public static final int DATABASE_VERSION = 1;
    public static final String TABLE_CHECK = "decide";
    public static final String COLUMN_ID = "id";
    public static final String COLUMN_USER = "user";
    public static final String COLUMN_PASS = "pass";
    public static final String COLUMN_IP = "ip";
    public static final String TABLE_SUPPER_CHECK = "supperDecide";
    public static final String SUPPER_COLUMN_ID = "id";
    public static final String SUPPER_COLUMN_USER = "user";
    public static final String SUPPER_COLUMN_PASS = "pass";
   // public static final String COLUMN_IP = "ip";
    public static final String QUERY_CREATE_TABLE_CONTACT =
            "CREATE TABLE IF NOT EXISTS " + TABLE_CHECK + " ( " +
                    COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    COLUMN_USER + " TEXT, " +
                    COLUMN_PASS + " TEXT, " +
                    COLUMN_IP + " TEXT )";
    public static final String QUERY_CREATE_TABLE_SUPPER_CHECK =
            "CREATE TABLE IF NOT EXISTS " + TABLE_SUPPER_CHECK + " ( " +
                    SUPPER_COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    SUPPER_COLUMN_USER + " TEXT, " +
                    SUPPER_COLUMN_PASS + " TEXT )";
    public static final String QUERY_DROP_TABLE_CONTACT =
            "DROP TABLE IF EXISTS " + TABLE_CHECK;
    public static final String QUERY_DROP_TABLE_SUPPER_CHECK =
            "DROP TABLE IF EXISTS " + TABLE_SUPPER_CHECK;
    public CheckOpenHelper(Context context)
    {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db)
    {try {
        db.execSQL(QUERY_CREATE_TABLE_CONTACT);
        db.execSQL(QUERY_CREATE_TABLE_SUPPER_CHECK);
    }catch (Exception e){
        e.printStackTrace();
    }
    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion)
    {
        db.execSQL(QUERY_DROP_TABLE_CONTACT);
        db.execSQL(QUERY_CREATE_TABLE_CONTACT);
    }
}

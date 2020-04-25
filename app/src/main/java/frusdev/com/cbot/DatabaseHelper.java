package frusdev.com.cbot;


import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {

    // Table Name
    public static final String TABLE_NAME = "ibot";
    private DBManager dbManager;
    Context ctx;

    // Table columns
    public static final String _ID = "_id";
    public static final String question = "question";
    public static final String answer = "answer";
    public static final String date = "date";
    public static final String keyword = "keyword";
    // Database Information
    static final String DB_NAME = "IBotDB.DB";

    // database version
    static final int DB_VERSION = 4;

    // Creating table query
    private static final String CREATE_TABLE = "create table " + TABLE_NAME + "(" + _ID
            + " INTEGER PRIMARY KEY AUTOINCREMENT, " + question + " TEXT NOT NULL, " + answer + " TEXT, "+keyword + " TEXT, "+date+" TEXT);";

    public DatabaseHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE);


    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }





}
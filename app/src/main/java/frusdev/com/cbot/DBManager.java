package frusdev.com.cbot;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.StringTokenizer;

import static frusdev.com.cbot.DatabaseHelper.TABLE_NAME;

public class DBManager {

    private DatabaseHelper dbHelper;

    private Context context;

    private SQLiteDatabase database;

    public DBManager(Context c) {
        context = c;


    }

    public DBManager open() throws SQLException {
        dbHelper = new DatabaseHelper(context);
        database = dbHelper.getWritableDatabase();
        return this;
    }

    public void close() {
        dbHelper.close();
    }

    public void insert(String que, String ans, String key) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy.MM.dd G 'at' HH:mm:ss z");
        String currentDateandTime = sdf.format(new Date());

        ContentValues contentValue = new ContentValues();
        contentValue.put(DatabaseHelper.question, que.toLowerCase());
        contentValue.put(DatabaseHelper.answer, ans.toLowerCase());
        contentValue.put(DatabaseHelper.keyword, key.toLowerCase());
        contentValue.put(DatabaseHelper.date, currentDateandTime);
       // Cursor n= db.rawQuery("create virtual table textsearch using fts4(content);",null);
        database.insert(TABLE_NAME, null, contentValue);
    }

//    public Cursor fetch(String que) {
//        SQLiteDatabase db = dbHelper.getReadableDatabase();
//
//        Cursor cursor =  db.rawQuery( "select * from "+ TABLE_NAME , null );
//
//        if (cursor != null) {
//            cursor.moveToFirst();
//        }
//        return cursor;
//    }


    public Cursor getData(String que)
    {

        SQLiteDatabase db = dbHelper.getReadableDatabase();

        Cursor res =  db.rawQuery( "select * from "+ TABLE_NAME +" where question Like '%"+que+"%'", null );
   //     Cursor res =  db.rawQuery( "select * from "+ TABLE_NAME +" where question match" +que, null );

        return res;
    }

//    public int update(long _id, String que, String ans) {
//        ContentValues contentValues = new ContentValues();
//        contentValues.put(DatabaseHelper.question, que);
//        contentValues.put(DatabaseHelper.answer, ans);
//        int i = database.update(TABLE_NAME, contentValues, DatabaseHelper._ID + " = " + _id, null);
//        return i;
//    }

//    public void delete(long _id) {
//        database.delete(TABLE_NAME, DatabaseHelper._ID + "=" + _id, null);
//    }

    public void deleteData()
    {
        database.delete(TABLE_NAME, null, null);
    }

}
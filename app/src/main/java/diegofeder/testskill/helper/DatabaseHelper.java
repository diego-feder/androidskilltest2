package diegofeder.testskill.helper;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 1;

    private String CREATE_TABLE_USER = "CREATE TABLE IF NOT EXISTS Users (id VARCHAR PRIMARY KEY, email VARCHAR  NOT NULL, name VARCHAR NOT NULL, password VARCHAR NOT NULL );";

    public DatabaseHelper(Context context) {
        super(context, "APP_DATABASE", null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE_USER);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        String sql = "";

        switch (oldVersion) {
            case 1:
                // do something if db upgraded
        }

        db.execSQL(sql);
    }
}

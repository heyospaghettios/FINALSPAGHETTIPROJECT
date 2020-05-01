package com.example.finalspaghettiproject.database;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class TaskdbHelper extends SQLiteOpenHelper {


    public TaskdbHelper(Context context) {
        super(context, dbTasks.DB_NAME, null, dbTasks.DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createTable = "CREATE TABLE " + dbTasks.TaskEntry.TABLE + " ( " +
                dbTasks.TaskEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                dbTasks.TaskEntry.COL_TASK_TITLE + " TEXT NOT NULL);";

        db.execSQL(createTable);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + dbTasks.TaskEntry.TABLE);
        onCreate(db);
    }
}

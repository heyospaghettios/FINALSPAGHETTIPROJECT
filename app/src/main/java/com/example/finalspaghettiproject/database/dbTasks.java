package com.example.finalspaghettiproject.database;

import android.provider.BaseColumns;

//this class defines constants which is used to access the data in the database
//will need a helper class to open the database

public class dbTasks {
    public static final String DB_NAME = "com.example.finalspaghettiproject.database";
    public static final int DB_VERSION = 1;

    public class TaskEntry implements BaseColumns {
        public static final String TABLE = "tasks";

        public static final String COL_TASK_TITLE = "title";
    }
}

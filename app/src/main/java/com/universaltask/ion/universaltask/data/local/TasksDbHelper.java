package com.universaltask.ion.universaltask.data.local;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Ionut on 2/12/2017.
 */

public class TasksDbHelper extends SQLiteOpenHelper {

    public  final  static  int DATABASE_VERSION = 1 ;

    public final static String DATABASE_NAME = "Tasks.db";

    public final static  String TEXT_TYPE = "TEXT";

    public final static String BOOLEAN_TYPE = "INTEGER";

    public final static String COMM_SEP = ",";

    public final static String SQL_CREATE_TABLE =
            String.format("CREATE TABLE %1s ( %2s %3s PRIMARY KEY , %4s %5s , %6s %7s , %8s %9s, %10s %11s) ",
            TasksPersistenceContract.TaskEntry.TABLE_NAME,
            TasksPersistenceContract.TaskEntry._ID, TEXT_TYPE,
            TasksPersistenceContract.TaskEntry.COLUMN_NAME_ENTRY_ID, TEXT_TYPE,
            TasksPersistenceContract.TaskEntry.COLUMN_NAME_TITLE, TEXT_TYPE,
            TasksPersistenceContract.TaskEntry.COLUMN_NAME_DESCRIPTION, TEXT_TYPE,
            TasksPersistenceContract.TaskEntry.COLUMN_NAME_COMPLETED, BOOLEAN_TYPE);

    public TasksDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_TABLE);
    }


    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Not required as at version 1
    }

    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Not required as at version 1
    }

}

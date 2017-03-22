package com.universaltask.ion.universaltask.data.local;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.annotation.NonNull;

import com.universaltask.ion.universaltask.data.ITasksDataSource;
import com.universaltask.ion.universaltask.model.*;

import java.util.ArrayList;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Created by Ionut on 2/12/2017.
 */

public class TasksLocalDataSource  implements ITasksDataSource {

    private static TasksLocalDataSource INSTANCE;

    private TasksDbHelper mDbHelper;

    // prevent direct instantiation
    private TasksLocalDataSource(@NonNull Context context) {
        checkNotNull(context);
        mDbHelper = new TasksDbHelper(context);

    }

    public  static TasksLocalDataSource getInstance (@NonNull Context context) {
        if (INSTANCE == null) {
            INSTANCE = new TasksLocalDataSource(context);
        }
        return INSTANCE;
    }

    @Override
    public void getTasks(@NonNull LoadTasksCallback callback) {
        ArrayList<Task> tasks = new ArrayList<Task>();
        SQLiteDatabase database = mDbHelper.getReadableDatabase();
        Cursor dataCursor = database.query("task", getTaskColumns(), null, null, null, null, null);
        if (dataCursor != null && dataCursor.getCount() > 0) {
            while (dataCursor.moveToNext()) {
                tasks.add(getTaskFromCursor(dataCursor));
            }
            dataCursor.close();
        }
        database.close();
        if (tasks.isEmpty())
            callback.onDataNotAvailable();
        else
            callback.onTasksLoaded(tasks);
    }

    @Override
    public void getTask(@NonNull String id, @NonNull LoadTaskCallback callback) {
        SQLiteDatabase database = mDbHelper.getReadableDatabase();
        String selection =  TasksPersistenceContract.TaskEntry.COLUMN_NAME_ENTRY_ID + " Like ?";
        String[] selectionArgs = { id };
        Cursor dataCursor = database.query("task", getTaskColumns(), selection, selectionArgs, null, null, null);
        Task task = null;
        if (dataCursor != null && dataCursor.getCount() > 0) {
            while (dataCursor.moveToNext()) {
                task = getTaskFromCursor(dataCursor);
            }
            dataCursor.close();
        }
        database.close();
        if (task != null)
            callback.onTaskLoaded(task);
        else
            callback.onDataNotAvailable();
    }

    @Override
    public  void saveTask(@NonNull Task task) {
        SQLiteDatabase database = mDbHelper.getWritableDatabase();

        ContentValues cv = new ContentValues();
        cv.put(TasksPersistenceContract.TaskEntry.COLUMN_NAME_ENTRY_ID, task.getId());
        cv.put(TasksPersistenceContract.TaskEntry.COLUMN_NAME_TITLE, task.getTitle());
        cv.put(TasksPersistenceContract.TaskEntry.COLUMN_NAME_DESCRIPTION, task.getDescription());
        cv.put(TasksPersistenceContract.TaskEntry.COLUMN_NAME_COMPLETED, task.isCompleted());
        database.insert("task", null, cv);
        database.close();
    }

    @Override
    public  void completeActivateTask(@NonNull Task task){
        SQLiteDatabase database = mDbHelper.getWritableDatabase();

        ContentValues cv = new ContentValues();
        cv.put(TasksPersistenceContract.TaskEntry.COLUMN_NAME_COMPLETED, task.isCompleted());
        String[] selectionArgs = { task.getId() };
        database.update("task",cv,TasksPersistenceContract.TaskEntry.COLUMN_NAME_ENTRY_ID +"Like ?",selectionArgs);
        database.close();

    }

    @Override
    public  void clearCompletedTasks() {
        SQLiteDatabase database = mDbHelper.getWritableDatabase();
        //ContentValues cv =new ContentValues();
        // cv.put(TasksPersistenceContract.TaskEntry.COLUMN_NAME_COMPLETED,true);
        String[] selectionArgs = {"1"};
        database.delete("task", TasksPersistenceContract.TaskEntry.COLUMN_NAME_COMPLETED + "Like ?", selectionArgs);
        database.close();
    }

    @Override
    public  void deleteAllTasks() {
        SQLiteDatabase database = mDbHelper.getWritableDatabase();
        database.delete("task", null, null);
        database.close();
    }

    @Override
    public void deleteTask(@NonNull String taskId) {
        SQLiteDatabase database = mDbHelper.getWritableDatabase();
        String[] selectionArgs = {taskId};
        database.delete("task", TasksPersistenceContract.TaskEntry.COLUMN_NAME_COMPLETED + "Like ?", selectionArgs);
    }
    @Override
    public void refreshTasks()
    {

    }




    private String[] getTaskColumns() {
        String[] columns =
                {
                        TasksPersistenceContract.TaskEntry.COLUMN_NAME_ENTRY_ID,
                        TasksPersistenceContract.TaskEntry.COLUMN_NAME_TITLE,
                        TasksPersistenceContract.TaskEntry.COLUMN_NAME_DESCRIPTION,
                        TasksPersistenceContract.TaskEntry.COLUMN_NAME_COMPLETED
                };

        return columns;
    }

    private Task getTaskFromCursor(Cursor inCur) {
        String id = inCur.getString(inCur.getColumnIndexOrThrow(TasksPersistenceContract.TaskEntry.COLUMN_NAME_ENTRY_ID));
        String title = inCur.getString(inCur.getColumnIndexOrThrow(TasksPersistenceContract.TaskEntry.COLUMN_NAME_TITLE));
        String description = inCur.getString(inCur.getColumnIndexOrThrow(TasksPersistenceContract.TaskEntry.COLUMN_NAME_DESCRIPTION));
        Boolean completed = inCur.getInt(inCur.getColumnIndexOrThrow(TasksPersistenceContract.TaskEntry.COLUMN_NAME_COMPLETED)) == 1;
        return new Task(id, title, description, completed);
    }
}


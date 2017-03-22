package com.universaltask.ion.universaltask;

import android.support.annotation.NonNull;

import com.universaltask.ion.universaltask.data.ITasksDataSource;
import com.universaltask.ion.universaltask.model.Task;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Ionut on 2/27/2017.
 */

public class TasksRepository implements ITasksDataSource
{

    private static TasksRepository INSTANCE = null;

    private ITasksDataSource localDatabase;

    private TasksRepository(@NonNull ITasksDataSource inLocalDatabase )
    {
        localDatabase  = inLocalDatabase;
    }

    protected boolean mCacheIsDirty = false;

    protected Map<String,Task> mCachedTasks;

    public static TasksRepository getInsance(ITasksDataSource inLocalDatabase)
    {
        if(INSTANCE == null)
        {
            INSTANCE = new TasksRepository(inLocalDatabase);
        }
        return  INSTANCE;
    }

    public static void destroyInstance()
    {
        INSTANCE = null;
    }

    public void  saveTask(@NonNull Task task) {
        localDatabase.saveTask(task);

        if (mCachedTasks == null) {
            mCachedTasks = new LinkedHashMap<String, Task>();
        }
        //de verificat asta
        if (!mCachedTasks.containsKey(task.getId()))
            mCachedTasks.put(task.getId(), task);
    }

    public void  completeActivateTask(@NonNull Task task) {
        localDatabase.completeActivateTask(task);

        if (mCachedTasks == null) {
            mCachedTasks = new LinkedHashMap<String, Task>();
        }
        //de verificat asta
        if (!mCachedTasks.containsKey(task.getId()))
            mCachedTasks.put(task.getId(), task);
    }

    public void clearCompletedTasks() {
        localDatabase.clearCompletedTasks();
        if (mCachedTasks == null) {
            mCachedTasks = new LinkedHashMap<String, Task>();
        }

        for (Map.Entry<String, Task> t : mCachedTasks.entrySet()
                ) {

            if (t.getValue().isCompleted())
                mCachedTasks.remove(t.getKey());

        }
    }

    public void deleteAllTasks()
    {
        localDatabase.deleteAllTasks();
        if (mCachedTasks == null) {
            mCachedTasks = new LinkedHashMap<String, Task>();
        }
        mCachedTasks.clear();
    }

    public void deleteTask(@NonNull String TaskId) {
        localDatabase.deleteTask(TaskId);
        if (mCachedTasks == null) {
            mCachedTasks = new LinkedHashMap<String, Task>();
        }
        mCachedTasks.remove(TaskId);
    }

    @Override
    public void refreshTasks() {
        mCacheIsDirty = true;
    }

    @Override
    public void getTasks(@NonNull final LoadTasksCallback callback) {
        if (mCachedTasks != null && !mCacheIsDirty) {
            callback.onTasksLoaded(new ArrayList<Task>(mCachedTasks.values()));
        } else {
            localDatabase.getTasks(new LoadTasksCallback() {
                @Override
                public void onTasksLoaded(List<Task> allTasks) {
                    refreshCache(allTasks);
                    callback.onTasksLoaded(allTasks);
                }

                @Override
                public void onDataNotAvailable() {

                }
            });
        }
    }

    @Override
    public void getTask(@NonNull String inId , @NonNull final LoadTaskCallback callback) {

        Task task = getcachedTask(inId);
        if (task != null) {
             callback.onTaskLoaded(task);
            return;
        }
        else
        {
            localDatabase.getTask(inId, new LoadTaskCallback() {
                @Override
                public void onTaskLoaded(Task task) {
                    if (mCachedTasks == null) {
                        mCachedTasks = new LinkedHashMap<String, Task>();
                    }
                    if (task != null) {
                        mCachedTasks.put(task.getId(), task);
                        callback.onTaskLoaded(task);
                    }
                }

                @Override
                public void onDataNotAvailable() {

                }
            });

        }
    }

    private Task getcachedTask(String Id) {
        if (Id == null || mCachedTasks == null || mCachedTasks.isEmpty()) {
            return null;
        } else {
            return mCachedTasks.get(Id);
        }
    }


    private void refreshCache(List<Task> allTasks) {
        if (mCachedTasks == null) {
            mCachedTasks = new LinkedHashMap<String, Task>();
        }
        mCachedTasks.clear();
        for (Task t : allTasks) {
            mCachedTasks.put(t.getId(), t);
        }
        mCacheIsDirty = false;
    }

   //void getTasks(@NonNull LoadTasksCallback callback);

    //void getTask(@NonNull String Id, @NonNull LoadTaskCallback callback);

   //void saveTask(@NonNull Task task);

    //void completeActivateTask(@NonNull Task task);

    //void clearCompletedTasks();

    //void deleteAllTasks();

    //void deleteTask(@NonNull String TaskId);
}

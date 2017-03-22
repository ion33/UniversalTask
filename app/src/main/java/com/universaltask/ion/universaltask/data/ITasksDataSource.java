package com.universaltask.ion.universaltask.data;

import android.support.annotation.NonNull;

import com.universaltask.ion.universaltask.model.*;

import java.util.List;

/**
 * Created by Ionut on 2/12/2017.
 */

public interface ITasksDataSource {

    interface LoadTasksCallback {
        void onTasksLoaded(List<Task> allTasks);
        void onDataNotAvailable();
    }

    interface LoadTaskCallback {
        void onTaskLoaded(Task task);
        void onDataNotAvailable();
    }

    void getTasks(@NonNull LoadTasksCallback callback);

    void getTask(@NonNull String Id, @NonNull LoadTaskCallback callback);

    void saveTask(@NonNull Task task);

    void completeActivateTask(@NonNull Task task);

    void clearCompletedTasks();

    void deleteAllTasks();

    void deleteTask(@NonNull String TaskId);

    void refreshTasks();

}

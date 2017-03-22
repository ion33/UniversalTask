package com.universaltask.ion.universaltask.viewmodels;

import android.content.Context;
import android.databinding.BaseObservable;
import android.databinding.Observable;
import android.databinding.ObservableBoolean;
import android.databinding.ObservableField;

import com.universaltask.ion.universaltask.TasksRepository;
import com.universaltask.ion.universaltask.activitys.TaskAddEditNavigator;
import com.universaltask.ion.universaltask.data.ITasksDataSource;
import com.universaltask.ion.universaltask.model.Task;

/**
 * Created by Ionut on 3/12/2017.
 */

public class TaskAddEditViewModel implements ITasksDataSource.LoadTaskCallback {

    private ObservableField<Task> currentTask = new ObservableField<>();

    public ObservableField<String> title = new ObservableField<>();

    public ObservableField<String> description = new ObservableField<>();

    public final ObservableBoolean dataLoading = new ObservableBoolean(false);

    public final ObservableField<String> snackbartext = new ObservableField<>();

    public final TasksRepository mTasksRepository;

    private final Context mContext; // To avoid leaks, this must be an Application Context.

    private String mTaskId;

    private boolean mIsNewTask;

    private TaskAddEditNavigator mNavigator;

    private boolean mIsDataLoaded = false;


    public  TaskAddEditViewModel(Context context,
                                 TasksRepository tasksRepository,
                                 TaskAddEditNavigator taskNavigator)
    {
        mContext = context.getApplicationContext();
        mTasksRepository = tasksRepository;
        mNavigator = taskNavigator;
    }

    @Override
    public void onTaskLoaded(Task task) {
        title.set(task.getTitle());
        description.set(task.getDescription());
        dataLoading.set(false);
        mIsDataLoaded = true;
    }

    @Override
    public void onDataNotAvailable()
    {
        dataLoading.set(false);
    }

}

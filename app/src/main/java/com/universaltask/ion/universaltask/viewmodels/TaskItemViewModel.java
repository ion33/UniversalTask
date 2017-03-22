package com.universaltask.ion.universaltask.viewmodels;

import android.content.Context;

import com.universaltask.ion.universaltask.TasksRepository;

/**
 * Created by Ionut on 3/6/2017.
 */

public class TaskItemViewModel extends TaskViewModel {

    private final TaskItemNavigator mTaskItemNavigator;

    public TaskItemViewModel(Context context, TasksRepository tasksRepository,
                             TaskItemNavigator itemNavigator) {
        super(context, tasksRepository);
        mTaskItemNavigator = itemNavigator;

    }

    public void taskClicked() {
        String taskId = getTaskId();
        mTaskItemNavigator.openTaksDetails(taskId);
    }
}

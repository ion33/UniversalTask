package com.universaltask.ion.universaltask.viewmodels;

import android.content.Context;
import android.content.Intent;
import android.databinding.BaseObservable;
import android.databinding.Bindable;
import android.databinding.Observable;
import android.databinding.ObservableArrayList;
import android.databinding.ObservableBoolean;
import android.databinding.ObservableField;
import android.databinding.ObservableList;
import android.graphics.drawable.Drawable;

import com.universaltask.ion.universaltask.BR;
import com.universaltask.ion.universaltask.TasksRepository;
import com.universaltask.ion.universaltask.data.ITasksDataSource;
import com.universaltask.ion.universaltask.enums.TasksFilterType;
import com.universaltask.ion.universaltask.model.Task;

import java.util.ArrayList;
import java.util.List;

public class TasksViewModel extends BaseObservable {

    public  ObservableList<Task> items = new ObservableArrayList<Task>();

    public final ObservableBoolean dataLoading = new ObservableBoolean(false);

    public final ObservableField<String> currentFilteringLabel = new ObservableField<String>();

    public final ObservableField<String> noTasksLabel = new ObservableField<String>();

    public final ObservableField<Drawable> noTaskIconRes = new ObservableField<Drawable>();

    public final ObservableBoolean tasksAddViewVisible =new ObservableBoolean();

    private final  ObservableBoolean mIsDataLoadingError = new ObservableBoolean();

    private TasksFilterType mCurrentFiltering = TasksFilterType.ALL_TASKS;

    private Context mContext ;

    private final TasksRepository mTasksRepository;

    private final TasksNavigator mNavigator;

    //public void setItems(ObservableList<Task> items)
   // {
   //     this.items= items;
   // }

    public TasksViewModel(TasksRepository repository,Context context,TasksNavigator navigator)
    {
        mContext = context.getApplicationContext();
        mTasksRepository = repository;
        mNavigator = navigator;
    }

    public void start() {
        loadTasks(true);
    }

    @Bindable
    public boolean isEmpty()
    {
        return false;
    }

    public void addNewTask() {
        mNavigator.addNewTask();
    }

    private void loadTasks(boolean forceUpdate) {
        loadTasks(forceUpdate, true);
    }

    private void loadTasks(boolean forceUpdate, final boolean showLoadingUI) {
        if (showLoadingUI) {
            dataLoading.set(true);
        }

        if(forceUpdate) {
          mTasksRepository.refreshTasks();
        }

        mTasksRepository.getTasks(
                new ITasksDataSource.LoadTasksCallback() {
                    @Override
                    public void onTasksLoaded(List<Task> allTasks) {
                        ArrayList<Task> tasks = new ArrayList<Task>();


                        for (Task task : allTasks) {

                            if (mCurrentFiltering == TasksFilterType.ALL_TASKS) {
                                tasks.add(task);
                            } else if (mCurrentFiltering == TasksFilterType.ACTIVE_TASKS) {
                                if (task.isActive()) {
                                    tasks.add(task);
                                }
                            } else if (mCurrentFiltering == TasksFilterType.COMPLETED_TASKS) {
                                if (task.isCompleted()) {
                                    tasks.add(task);
                                }
                            }
                        }
                        if (showLoadingUI) {
                            dataLoading.set(false);
                        }
                        items.clear();
                        items.addAll(tasks);
                        notifyPropertyChanged(BR.empty);
                    }

                    @Override
                    public void onDataNotAvailable() {

                    }
                }
        );
    }

    void handleActivityResult(int requestCode, int resultCode) {

    }

}

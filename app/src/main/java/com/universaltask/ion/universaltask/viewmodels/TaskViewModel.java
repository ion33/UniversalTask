package com.universaltask.ion.universaltask.viewmodels;

import android.content.Context;
import android.databinding.BaseObservable;
import android.databinding.Bindable;
import android.databinding.Observable;
import android.databinding.ObservableField;
import android.media.TimedText;
import android.support.annotation.Nullable;

import com.universaltask.ion.universaltask.TasksRepository;
import com.universaltask.ion.universaltask.data.ITasksDataSource;
import com.universaltask.ion.universaltask.model.Task;


/**
 * Created by Ionut on 3/6/2017.
 */

public class TaskViewModel extends BaseObservable
        implements ITasksDataSource.LoadTaskCallback {
    public final ObservableField<String> title = new ObservableField<>();

    public final ObservableField<String> description = new ObservableField<>();

    public final ObservableField<Task> mTaskObservable = new ObservableField<>();

    private final TasksRepository mTasksRepository;

    private final Context mContext;

    private boolean mIsdataLoading;

    public TaskViewModel(Context context, TasksRepository tasksRepository) {
        mContext = context.getApplicationContext();
        mTasksRepository = tasksRepository;

        mTaskObservable.addOnPropertyChangedCallback(new OnPropertyChangedCallback() {
            @Override
            public void onPropertyChanged(Observable observable, int i) {
                Task task = mTaskObservable.get();
                if (task != null) {
                    title.set(task.getTitle());
                    description.set(task.getDescription());
                } else {
                    title.set("");
                    description.set("");
                }
            }
        });
    }

    public void start(String taskId) {
        if (taskId != null) {
            mIsdataLoading = true;
            mTasksRepository.getTask(taskId, this);
        }
    }

    public void setTask(Task task) {
        mTaskObservable.set(task);
    }

    @Bindable
    public boolean
    getCompleted() {
        return mTaskObservable.get().isCompleted();
    }

    public void setCompleted(boolean completed)
    {

    }

    @Override
    public void onTaskLoaded(Task task) {
        mTaskObservable.set(task);
        mIsdataLoading = false;
        notifyChange();
    }

    @Override
    public void onDataNotAvailable() {

    }

    @Nullable
    protected String getTaskId() {

        return mTaskObservable.get().getId();
    }

    @Bindable
    public String getTitleForList() {
        return mTaskObservable.get().getFullTitle();
    }
}

package com.universaltask.ion.universaltask.viewmodels;

import android.databinding.BindingAdapter;
import android.widget.ListView;

import com.universaltask.ion.universaltask.fragments.TasksFragment;
import com.universaltask.ion.universaltask.model.Task;

import java.util.List;

/**
 * Created by Ionut on 3/11/2017.
 */

public class TasksListBindings {

    @SuppressWarnings("unchecked")
    @BindingAdapter("app:items")
    public static void setItems(ListView listView, List<Task> items)
    {
        TasksFragment.TasksAdapter adapter = (TasksFragment.TasksAdapter)
                listView.getAdapter();
        if (adapter!=null)
        {
            adapter.replaceData(items);
        }
    }
}



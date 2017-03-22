package com.universaltask.ion.universaltask.fragments;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;

import com.universaltask.ion.universaltask.R;
import com.universaltask.ion.universaltask.TasksRepository;
import com.universaltask.ion.universaltask.data.local.TasksLocalDataSource;
import com.universaltask.ion.universaltask.databinding.TaskItemBinding;
import com.universaltask.ion.universaltask.databinding.TasksFragBinding;
import com.universaltask.ion.universaltask.model.Task;
import com.universaltask.ion.universaltask.viewmodels.TaskItemNavigator;
import com.universaltask.ion.universaltask.viewmodels.TaskItemViewModel;
import com.universaltask.ion.universaltask.viewmodels.TasksViewModel;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Ionut on 3/4/2017.
 */

public class TasksFragment  extends Fragment {

    private TasksViewModel mTasksViewModel;

    private TasksFragBinding mTasksFragBinding;

    public TasksFragment() {

    }

    public static TasksFragment newInstance() {
        return new TasksFragment();
    }

    @Override
    public void onResume() {
        super.onResume();
        mTasksViewModel.start();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mTasksFragBinding = TasksFragBinding.inflate(inflater, container, false);
        mTasksFragBinding.setView(this);
        mTasksFragBinding.setViewmodel(mTasksViewModel);
        setHasOptionsMenu(true);
        View root = mTasksFragBinding.getRoot();
        return root;
    }

    public  void setViewModel(TasksViewModel viewModel) {
        mTasksViewModel = viewModel;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState){
        super.onActivityCreated(savedInstanceState);

        setupFab();
        setupListAdapter();

    }

    public void  onDestroyView(){
        super.onDestroyView();
    }

    private void setupListAdapter() {
        ListView listView = mTasksFragBinding.tasksList;

        TasksAdapter mListAdapter = new TasksAdapter
                (
                        new ArrayList<Task>(0),
                        (TaskItemNavigator) getActivity(),
                        TasksRepository.getInsance(TasksLocalDataSource.getInstance(getContext().getApplicationContext())),
                        mTasksViewModel);
        listView.setAdapter(mListAdapter);
    }

    private void setupFab()
    {
        FloatingActionButton fab = (FloatingActionButton)getActivity().findViewById(R.id.fab_add_task);

        fab.setImageResource(R.drawable.ic_add);
        fab.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                mTasksViewModel.addNewTask();
            }
        });
    }



    public static class TasksAdapter extends BaseAdapter {

        private final TaskItemNavigator mTaskItemNavigator;

        private final TasksViewModel mTasksViewModel;

        private List<Task> mTasks;

        private TasksRepository mTasksRepository;

        public TasksAdapter(List<Task> tasks, TaskItemNavigator taskItemNavigator,
                            TasksRepository tasksRepository,
                            TasksViewModel tasksViewModel) {
            mTaskItemNavigator = taskItemNavigator;
            mTasks = tasks;
            mTasksRepository = tasksRepository;
            mTasksViewModel = tasksViewModel;
        }

        public void replaceData(List<Task> tasks) {
            setList(tasks);
        }

        @Override
        public int getCount() {
            return mTasks != null ? mTasks.size() : 0;
        }

        @Override
        public Task getItem(int i) {
            return mTasks.get(i);
        }

        @Override
        public long getItemId(int i) {
            return i;
        }

        @Override
        public View getView(int i, View view, ViewGroup viewGroup) {
            Task task = getItem(i);
            TaskItemBinding binding;
            if (view == null) {
                //inflate
                LayoutInflater inflater = LayoutInflater.from(viewGroup.getContext());
                // create the binding
                binding = TaskItemBinding.inflate(inflater, viewGroup, false);

            } else {
                //Recycling view
                binding = DataBindingUtil.getBinding(view);
            }

            TaskItemViewModel viewmodel = new TaskItemViewModel(
                    viewGroup.getContext(),
                    mTasksRepository,
                    mTaskItemNavigator
            );

            binding.setViewmodel(viewmodel);

            viewmodel.setTask(task);

            return binding.getRoot();
        }

        private void setList(List<Task> tasks) {
            mTasks = tasks;
            notifyDataSetChanged();
        }
    }
}

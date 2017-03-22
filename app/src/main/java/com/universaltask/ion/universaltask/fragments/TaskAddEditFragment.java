package com.universaltask.ion.universaltask.fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.universaltask.ion.universaltask.R;
import com.universaltask.ion.universaltask.databinding.AddtaskFragBinding;
import com.universaltask.ion.universaltask.viewmodels.TaskAddEditViewModel;

/**
 * Created by Ionut on 3/16/2017.
 */

public class TaskAddEditFragment extends Fragment {

    private TaskAddEditViewModel mViewModel;

    private AddtaskFragBinding mViewDataBinding;

    public TaskAddEditFragment() {

    }

    public TaskAddEditFragment newInstance() {
        return new TaskAddEditFragment();
    }


    //cand se resuma ori setam un task ori facem unul nou cred
    @Override
    public void onResume() {
        super.onResume();

    }

    public void setmViewModel(@NonNull TaskAddEditViewModel vm) {
        mViewModel = vm;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstance) {
        final View root = inflater.inflate(R.layout.addtask_frag, container, false);
        if (mViewDataBinding == null) {
            mViewDataBinding = AddtaskFragBinding.bind(root);
        }

        mViewDataBinding.setViewmodel(mViewModel);

        setHasOptionsMenu(true);
        setRetainInstance(false);
        return mViewDataBinding.getRoot();
    }

    private void setupFab() {

        FloatingActionButton fab =
                (FloatingActionButton) getActivity().findViewById(R.id.fab_edit_task_done);
        fab.setImageResource(R.drawable.ic_done);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //mViewModel.

            }
        });
    }

    private void setupActionBar() {

        ActionBar actionBar = ((AppCompatActivity) getActivity()).getSupportActionBar();
        if(getArguments() !=null) {
            actionBar.setTitle(R.string.edit_task);
        }
        else
        {
            actionBar.setTitle(R.string.add_task);
        }
    }
}

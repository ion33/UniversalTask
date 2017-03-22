package com.universaltask.ion.universaltask.activitys;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;

import com.universaltask.ion.universaltask.R;
import com.universaltask.ion.universaltask.fragments.TaskAddEditFragment;

/**
 * Created by Ionut on 3/12/2017.
 */

public class TaskAddEditActivity extends AppCompatActivity
        implements TaskAddEditNavigator {

    public static final int REQUEST_CODE = 1;

    public static final int ADD_EDIT_RESULT_OK = RESULT_FIRST_USER + 1;

    public static  final String ADD_EDIT_VIEWMODEL_TAG = "ADD_EDIT_VIEWMODEL_TAG";



    public void onTaskSaved() {

    }

    @Override
    protected void onCreate(Bundle savedInstance)
    {
        super.onCreate(savedInstance);
        setContentView(R.layout.addtask_activity);

        //TaskAddEditFragment taskFragment =


    }

    @NonNull
    private TaskAddEditFragment findOrCreateViewFragment()
    {
        TaskAddEditFragment taskFragment =
                (TaskAddEditFragment) getSupportFragmentManager()
                .findFragmentById(R.id.contentFrame);
        //todo
    }

}

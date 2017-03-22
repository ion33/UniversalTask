package com.universaltask.ion.universaltask;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.MenuItemHoverListener;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import com.universaltask.ion.universaltask.activitys.TaskAddEditActivity;
import com.universaltask.ion.universaltask.data.local.TasksLocalDataSource;
import com.universaltask.ion.universaltask.fragments.TasksFragment;
import com.universaltask.ion.universaltask.fragments.ViewModelHolder;
import com.universaltask.ion.universaltask.util.ActivityUtils;
import com.universaltask.ion.universaltask.viewmodels.TaskAddEditViewModel;
import com.universaltask.ion.universaltask.viewmodels.TaskItemNavigator;
import com.universaltask.ion.universaltask.viewmodels.TasksNavigator;
import com.universaltask.ion.universaltask.viewmodels.TasksViewModel;

/**
 * Created by Ionut on 3/1/2017.
 */

public class TasksActivity extends AppCompatActivity implements TasksNavigator,TaskItemNavigator {

    private DrawerLayout mDrawerLayout;

    public static final String TASKS_VIEWMODEL_TAG = "TASKS_VIEWMODEL_TAG";
    private TasksViewModel mViewModel;

    @Override
    public void onCreate(Bundle savedIsnanceState) {
        super.onCreate(savedIsnanceState);
        this.setContentView(R.layout.tasks_activity);


        setupToolbBar();

        setupNavigationDrawer();

        TasksFragment tasksFragment = findOrCreateViewFragment();
        mViewModel = findOrCreateViewModel();
        tasksFragment.setViewModel(mViewModel);
    }

    private void setupNavigationDrawer()
    {
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        mDrawerLayout.setStatusBarBackground(R.color.colorPrimaryDark);
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        if(navigationView !=null) {
            setupDrawerContent(navigationView);
        }

    }

    private void setupDrawerContent(NavigationView navigationView) {
        navigationView.setNavigationItemSelectedListener(
                new NavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(MenuItem menuItem) {
                        menuItem.setChecked(true);
                        mDrawerLayout.closeDrawers();
                        return true;

                    }
                }
        );}

    private TasksViewModel findOrCreateViewModel() {

        @SuppressWarnings("unchecked")
        ViewModelHolder<TasksViewModel> retainedViewModel = (ViewModelHolder<TasksViewModel>) getSupportFragmentManager().findFragmentByTag(TASKS_VIEWMODEL_TAG);


        if (retainedViewModel != null && retainedViewModel.getViewModel() != null) {
            return retainedViewModel.getViewModel();
        } else {
            TasksViewModel viewModel = new TasksViewModel(
                    TasksRepository.getInsance(TasksLocalDataSource.getInstance(getApplicationContext())),
                    getApplicationContext(),
                    this
            );

            ActivityUtils.addFragmentToActivity(getSupportFragmentManager(), ViewModelHolder.createContainer(viewModel),
                    TASKS_VIEWMODEL_TAG);

            return viewModel;

        }
    }

    @NonNull
    private TasksFragment findOrCreateViewFragment() {
        TasksFragment tasksFragment =
                (TasksFragment) getSupportFragmentManager().findFragmentById(R.id.contentFrame);
        if (tasksFragment == null) {
            tasksFragment = TasksFragment.newInstance();
            ActivityUtils.addFragmentToActivity(getSupportFragmentManager(),
                    tasksFragment, R.id.contentFrame
            );

        }
        return tasksFragment;
    }

    private void setupToolbBar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar ab= getSupportActionBar();
        ab.setHomeAsUpIndicator(R.drawable.ic_menu);
        ab.setHomeButtonEnabled(true);


    }

    public void addNewTask() {
        Intent intent = new Intent(this, TaskAddEditActivity.class);
        startActivityForResult(intent,TaskAddEditActivity.REQUEST_CODE);

    }


    public  void openTaksDetails(String taskId) {

    }
}

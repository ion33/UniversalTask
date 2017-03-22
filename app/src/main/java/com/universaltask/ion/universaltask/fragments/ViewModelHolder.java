package com.universaltask.ion.universaltask.fragments;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;

/**
 * Created by Ionut on 3/11/2017.
 */

public class ViewModelHolder<VM> extends Fragment {

    private VM mViewModel;

    public ViewModelHolder()
    {}

    public static <M> ViewModelHolder createContainer(@NonNull M viewModel)
    {
        ViewModelHolder<M> viewModelContainer = new ViewModelHolder<>();
        viewModelContainer.setmViewModel(viewModel);
        return viewModelContainer;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInsanceState)
    {
        super.onCreate(savedInsanceState);
        setRetainInstance(true);
    }

    @Nullable
    public  VM getViewModel()
    {
        return  mViewModel;
    }

    public void setmViewModel(@NonNull VM viewModel)
    {
        mViewModel = viewModel;
    }
}

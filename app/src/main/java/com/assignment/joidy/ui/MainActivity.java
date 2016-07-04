package com.assignment.joidy.ui;


import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import com.assignment.joidy.R;
import com.assignment.joidy.SessionApplication;
import com.assignment.joidy.databinding.ActivityMainBinding;
import com.assignment.joidy.di.components.DaggerFlowComponent;

import org.parceler.Parcels;

import javax.inject.Inject;

public class MainActivity extends AppCompatActivity {

    public static final String VIEW_MODEL_STATE = "viewModelState";

    @Inject
    ViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTheme(R.style.AppTheme_Main);
        super.onCreate(savedInstanceState);
        final ActivityMainBinding activityMainBinding = DataBindingUtil.setContentView(this, R.layout.activity_main);

        activityMainBinding.titleTextView.setText(R.string.title_main);
        Toolbar toolbar = activityMainBinding.toolbar;
        setSupportActionBar(toolbar);

        DaggerFlowComponent.builder()
                .appComponent(SessionApplication.get(this).mAppComponent)
                .build()
                .inject(this);

        ViewModel.State savedViewModelState = null;
        if (savedInstanceState != null) {
            savedViewModelState = Parcels.unwrap(savedInstanceState.getParcelable(VIEW_MODEL_STATE));
        }

        viewModel.onCreate(savedViewModelState);
        activityMainBinding.setViewModel(viewModel);


    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        if (viewModel != null) {
            outState.putParcelable(VIEW_MODEL_STATE, Parcels.wrap(viewModel.onSaveInstanceState()));
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        if(viewModel != null)
            viewModel.onStart();
    }

    @Override
    public void onStop() {
        if(viewModel != null)
            viewModel.onStop();
        super.onStop();
    }

}
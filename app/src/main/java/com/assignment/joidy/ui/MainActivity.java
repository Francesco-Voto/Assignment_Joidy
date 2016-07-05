package com.assignment.joidy.ui;


import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import com.assignment.joidy.R;
import com.assignment.joidy.SessionApplication;
import com.assignment.joidy.data.models.Product;
import com.assignment.joidy.databinding.ActivityMainBinding;
import com.assignment.joidy.di.components.DaggerFlowComponent;

import org.parceler.Parcels;

import java.util.Locale;

import javax.inject.Inject;

import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

/**
 * OneActivity Application,with a ModelView to manage the interaction and the logic of application.
 * The Activity (View) is kept dumb. The interaction si provided via Rxjava ( an example is given with Snackbar)
 */

public class MainActivity extends AppCompatActivity {

    public static final String VIEW_MODEL_STATE = "viewModelState";

    @Inject
    ViewModel viewModel;

    private ActivityMainBinding mActivityMainBinding;
    private Subscription mSubscription;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTheme(R.style.AppTheme_Main);
        super.onCreate(savedInstanceState);
        mActivityMainBinding = DataBindingUtil.setContentView(this, R.layout.activity_main);

        mActivityMainBinding.titleTextView.setText(R.string.title_main);
        Toolbar toolbar = mActivityMainBinding.toolbar;
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
        mActivityMainBinding.setViewModel(viewModel);


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
        mSubscription = viewModel.mSubject
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<Product>() {
                    @Override
                    public void call(Product product) {
                        Snackbar.make(mActivityMainBinding.coordinator, String.format(Locale.getDefault(),"Click on %s with price %.2f €",product.title, product.price), Snackbar.LENGTH_LONG).show();
                    }
                });
    }

    @Override
    public void onStop() {
        if(viewModel != null)
            viewModel.onStop();
        mSubscription.unsubscribe();
        super.onStop();
    }

}
package com.assignment.joidy.ui;


import android.content.Context;
import android.databinding.BaseObservable;
import android.databinding.ObservableField;
import android.databinding.ObservableInt;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.assignment.joidy.R;
import com.assignment.joidy.data.models.Product;
import com.assignment.joidy.data.network.APIMain;
import com.assignment.joidy.data.network.ConnectionStatus;
import com.assignment.joidy.di.scopes.Flow;
import com.assignment.joidy.ui.rv.ClickHandler;
import com.assignment.joidy.ui.rv.ProductAdapter;

import org.parceler.Parcel;

import java.util.ArrayList;

import javax.inject.Inject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import rx.subjects.PublishSubject;
import rx.subjects.Subject;

/**
 * Base ViewModel class to implement.
 * <p>BR</p>
 * The {@link ViewModel.State} serves to save the current state of wrapped objects.
 * It must be @Parcel to be saved via {@link android.app.Activity#onSaveInstanceState(Bundle)}
 * <p>BR</p>
 * The calls {@link ViewModel#onCreate(State)}, {@link ViewModel#onStart} and {@link ViewModel#onStop} work to map the viewModel with the lifecycle of Activity/Fragment.
 */
@Flow
public class ViewModel extends BaseObservable {

    private RecyclerView.LayoutManager layoutManager;
    private ProductAdapter mProductAdapter;
    private Parcelable savedLayoutManagerState;

    private final APIMain mAPIMain;
    private final Context mContext;
    private ArrayList<Product> mProducts;

    public final ObservableInt isDownloading;
    public final ObservableInt isEmpty;
    public final ObservableInt isError;

    public final ObservableField<String> mErrorText;

    public Subject<Product, Product> mSubject;

    /**
     *
     * @param apiMain: for downloading the product
     * @param context: the context
     */
    @Inject
    protected ViewModel(APIMain apiMain, Context context){
        this.mAPIMain = apiMain;
        this.mContext = context;
        this.isDownloading = this.isEmpty = this.isError = new ObservableInt();
        this.mErrorText = new ObservableField<>();

        this.isDownloading.set(View.GONE);
        this.isEmpty.set(View.GONE);
        this.isError.set(View.GONE);
    }

    protected void onCreate(@Nullable State savedInstanceState){
        if(savedInstanceState != null){
            this.mProducts = savedInstanceState.mProducts;
            this.savedLayoutManagerState = savedInstanceState.layoutManagerState;
        }else{
            downloadProducts();
        }

    }

    ViewModel.State onSaveInstanceState(){
        return new State(this);
    }

    protected void onStart(){ mSubject = PublishSubject.create();}

    protected void onStop(){ mSubject = null; }

    public void setRecyclerView(RecyclerView recyclerView){
        layoutManager = new LinearLayoutManager(recyclerView.getContext());
        if (savedLayoutManagerState != null) {
            layoutManager.onRestoreInstanceState(savedLayoutManagerState);
            savedLayoutManagerState = null;
        }
        recyclerView.setLayoutManager(layoutManager);
        this.mProductAdapter = new ProductAdapter(R.layout.list_item_product, clickHandler());
        recyclerView.setAdapter(mProductAdapter);
        if(mProducts != null){
            mProductAdapter.setDataSet(mProducts);
        }
    }

    private ClickHandler<Product> clickHandler()
    {
        return new ClickHandler<Product>() {
            @Override
            public void onClick(Product product, View v) {
                mSubject.onNext(product);
            }
        };
    }

    public void clickOnRetry(View v){
        downloadProducts();
    }

    private void downloadProducts(){
        isDownloading.set(View.VISIBLE);
        mAPIMain.getRecommendedProducts()
                .enqueue(new Callback<ArrayList<Product>>() {
                    @Override
                    public void onResponse(Call<ArrayList<Product>> call, Response<ArrayList<Product>> response) {
                        isDownloading.set(View.GONE);
                        isEmpty.set(response.body().size() == 0 ? View.VISIBLE : View.GONE);
                        mProductAdapter.setDataSet(mProducts = response.body());
                    }

                    @Override
                    public void onFailure(Call<ArrayList<Product>> call, Throwable t) {
                        isDownloading.set(View.GONE);
                        isError.set(View.VISIBLE);
                        mErrorText.set(ConnectionStatus.isNetworkAvailable(mContext) ?
                                mContext.getString(R.string.error_general) : mContext.getString(R.string.error_no_internet));
                    }
                });
    }

    @Parcel
    public static class State{

        public Parcelable layoutManagerState;
        public ArrayList<Product> mProducts;

        public State(ViewModel viewModel){
            if(viewModel.layoutManager != null)
                layoutManagerState = viewModel.layoutManager.onSaveInstanceState();
            mProducts = viewModel.mProducts;
        }

        // For Parceler
        public State(){}
    }
}

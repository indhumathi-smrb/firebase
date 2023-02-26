package com.example.firebaseauthentication;

import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HomeScreen1 extends AppCompatActivity {
    private static final String TAG = "MainActivity";

    PaginationAdapter adapter;
    LinearLayoutManager linearLayoutManager;

    RecyclerView rv;
    ProgressBar progressBar;

    private static final int PAGE_START = 0;
    private boolean isLoading = false;
    private boolean isLastPage = false;
    private int TOTAL_PAGES = 3;
    private int currentPage = PAGE_START;
    ArrayList<ItemListModel> items;
    ApiService apiService = ApiClient.getClient().create(ApiService.class);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home1);

        rv = (RecyclerView) findViewById(R.id.main_recycler);
        progressBar = (ProgressBar) findViewById(R.id.main_progress);

        adapter = new PaginationAdapter(this);

        linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        rv.setLayoutManager(linearLayoutManager);

        rv.setItemAnimator(new DefaultItemAnimator());

        rv.setAdapter(adapter);

        rv.addOnScrollListener(new PaginationScrollListener1(linearLayoutManager) {
            @Override
            protected void loadMoreItems() {
                isLoading = true;
                currentPage += 1;

                // mocking network delay for API call
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        loadNextPage();
                    }
                }, 1000);
            }

            @Override
            public int getTotalPageCount() {
                return TOTAL_PAGES;
            }

            @Override
            public boolean isLastPage() {
                return isLastPage;
            }

            @Override
            public boolean isLoading() {
                return isLoading;
            }
        });


        // mocking network delay for API call
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                loadFirstPage();
            }
        }, 1000);

    }


    private void loadFirstPage() {
        Log.d(TAG, "loadFirstPage: ");
        doApiCall(1,currentPage);
    }

    private void loadNextPage() {
        Log.d(TAG, "loadNextPage: " + currentPage);
        doApiCall(2,currentPage);
    }

    private void doApiCall(int pageType,int limit) {
        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {

                Call<ArrayList<ItemListModel>> call = apiService.getItems(limit);
                call.enqueue(new Callback<ArrayList<ItemListModel>>() {

                    @Override
                    public void onResponse(Call<ArrayList<ItemListModel>> call, Response<ArrayList<ItemListModel>> response) {
                        items = response.body();
                        Log.d("TAG", "Response = " + items);
                        if(pageType==2) {
                            adapter.removeLoadingFooter();
                            isLoading = false;

                            adapter.addAll(items);
                            if (currentPage != TOTAL_PAGES) adapter.addLoadingFooter();
                            else isLastPage = true;
                        }else{
                            progressBar.setVisibility(View.GONE);
                            adapter.addAll(items);

                            if (currentPage <= TOTAL_PAGES) adapter.addLoadingFooter();
                            else isLastPage = true;
                        }


                    }


                    @Override
                    public void onFailure(Call<ArrayList<ItemListModel>> call, Throwable t) {
                        Log.d("TAG", "Response = " + t.toString());
                    }
                });
            }
        }, 1500);
    }
}
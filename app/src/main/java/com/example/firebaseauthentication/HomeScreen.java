package com.example.firebaseauthentication;

import static com.example.firebaseauthentication.PaginationScrollListener.PAGE_START;

import android.os.Bundle;
import android.os.Handler;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HomeScreen extends AppCompatActivity
        implements SwipeRefreshLayout.OnRefreshListener {

    private static final String TAG = "HomeScreen";
    ArrayList<ItemListModel> items;
    @BindView(R.id.recyclerView)
    RecyclerView mRecyclerView;
    @BindView(R.id.swipeRefresh)
    SwipeRefreshLayout swipeRefresh;
    private ItemListAdapter1 adapter;
    private int currentPage = PAGE_START;
    private boolean isLastPage = false;
    private int totalPage = 10;
    private boolean isLoading = false;
    int itemCount = 0;
    ApiService apiService = ApiClient.getClient().create(ApiService.class);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        ButterKnife.bind(this);

        swipeRefresh.setOnRefreshListener(this);
        mRecyclerView.setHasFixedSize(true);
        // use a linear layout manager
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(layoutManager);

        adapter = new ItemListAdapter1(items);
        mRecyclerView.setAdapter(adapter);
        doApiCall(currentPage);

        /**
         * add scroll listener while user reach in bottom load more will call
         */
        mRecyclerView.addOnScrollListener(new PaginationScrollListener(layoutManager) {
            @Override
            protected void loadMoreItems() {
                isLoading = true;
                currentPage++;
                doApiCall(currentPage);
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
    }

    /**
     * do api call here to fetch data from server
     * In example i'm adding data manually
     */
    private void doApiCall(int limit) {

        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {

                Call<ArrayList<ItemListModel>> call = apiService.getItems(limit);
                call.enqueue(new Callback<ArrayList<ItemListModel>>() {

                    @Override
                    public void onResponse(Call<ArrayList<ItemListModel>> call, Response<ArrayList<ItemListModel>> response) {
                        items = response.body();
                        Log.d("TAG", "Response = " + items);
                        adapter.addItems(items);
                        /**
                         * manage progress view
                         */
                       /* if (currentPage != PAGE_START)
                        adapter.removeLoading();
                        adapter.addItems(items);
                        mRecyclerView.setAdapter(adapter);
                        swipeRefresh.setRefreshing(false);

                        // check weather is last page or not
                        *//*if (currentPage < totalPage) {
                            //if(items!=null)
                            adapter.addLoading();
                        } else {
                            isLastPage = true;
                        }*//*
                        isLoading = true;*/
                    }

                    @Override
                    public void onFailure(Call<ArrayList<ItemListModel>> call, Throwable t) {
                        Log.d("TAG", "Response = " + t.toString());
                    }
                });
               /* for (int i = 0; i < 10; i++) {
                    itemCount++;
                    ItemListModel postItem = new ItemListModel();
                    postItem.setTitle("Sample title" + itemCount);
                    postItem.setDescription("Sample Description");
                    items.add(postItem);
                }*/

            }
        }, 15000);
    }

    @Override
    public void onRefresh() {
        itemCount = 0;
        currentPage = PAGE_START;
        isLastPage = false;
        adapter.clear();
        doApiCall(currentPage);
    }
}
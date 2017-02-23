package com.example.mat.dinnerov4;

import android.app.Activity;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class FindedDishesActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    DishAdapter mAdapter;
    MyDatabase db;
    String TAG = "FindedDishesActivity";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_finded_dishes);
        //TOOLBAR
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        recyclerView = (RecyclerView) findViewById(R.id.recycler_view_finded_dishes);


        db = MyDatabase.getInstance(this);
        List<Integer> listOfSelectedIngedientsIDs = new ArrayList<Integer>();
        listOfSelectedIngedientsIDs = getIntent().getIntegerArrayListExtra("ingredients");
        //dokladne dopasowania
        List<Dish> listOfDishes = db.getDishesFromIngerdientsIDs(listOfSelectedIngedientsIDs, listOfSelectedIngedientsIDs.size());
        if(listOfDishes.size()>0) {
            mAdapter = new DishAdapter(listOfDishes);
            RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
            recyclerView.setLayoutManager(mLayoutManager);
            recyclerView.setItemAnimator(new DefaultItemAnimator());
            recyclerView.setAdapter(mAdapter);
            mAdapter.notifyDataSetChanged();
        }
        else
        {
            Snackbar.make(findViewById(R.id.activity_finded_dishes) , "Brak wyników", Snackbar.LENGTH_INDEFINITE).show();
        }
        Log.d(TAG, "onCreate: "+listOfDishes.size());


    }
}

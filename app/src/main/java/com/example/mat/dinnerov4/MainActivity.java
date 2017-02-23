package com.example.mat.dinnerov4;

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private final int SEARCH_FILTER = 0;
    private final int GLUTEN_FREE_FILTER = 1;
    private final int VEGAN_FILTER = 2;
    boolean isGlutenFree = false;
    boolean isVegatarion = false;
    SearchView searchView;
    Button findDish;
    static final String TAG = "MainActivity";
    TextView tv, tvDishes;
    RecyclerView recyclerView;
    MyDatabase db;
    IngredientsAdapter mAdapter;
    ArrayList<Ingredient> ingredientsList;
    List<Ingredient> ingredientsListAll;
    List<Ingredient> filteredList;
    FloatingActionButton floatingActionButton;
    private ArrayList<Ingredient> currentSelectedItems;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        db = MyDatabase.getInstance(this);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        findDish = (Button) findViewById(R.id.findDish);
        floatingActionButton = (FloatingActionButton) findViewById(R.id.floatingActionButton);

        ingredientsListAll = db.getAllIngredients();
        ingredientsList = new ArrayList<Ingredient>();
        ingredientsList.addAll(ingredientsListAll);

        currentSelectedItems = new ArrayList<>();

        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        Log.d(TAG, "onCreate: ingedientsList size: " + ingredientsListAll.size());
        mAdapter = new IngredientsAdapter(ingredientsListAll);
//        mAdapter = new IngredientsAdapter(ingredientsListAll, new IngredientsAdapter.OnItemCheckListener() {
//            @Override
//            public void onItemCheck(Ingredient item) {
//                currentSelectedItems.add(item);
//                Log.d(TAG, "onItemCheck: rozmiar currentSelected: " +currentSelectedItems.size()+ " "+ item.getId());
//            }
//
//            @Override
//            public void onItemUncheck(Ingredient item) {
//                currentSelectedItems.remove(item);
//                Log.d(TAG, "onItemCheck: rozmiar currentSelected: " +currentSelectedItems.size()+ " "+ item.getId());
//            }
//        });

        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.VERTICAL));
        recyclerView.setAdapter(mAdapter);
        mAdapter.notifyDataSetChanged();


        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //TODO: start activity z wyszukanymi potrawami
                ArrayList<Integer> selectedIngediestsIDs = new ArrayList<Integer>(){};
                for(Ingredient ingredient : mAdapter.getSelectedIngredientsList()){
                    selectedIngediestsIDs.add(ingredient.getId());
                }
                Intent i = new Intent(getApplicationContext(), FindedDishesActivity.class);
                i.putIntegerArrayListExtra("ingredients", selectedIngediestsIDs);
                startActivity(i);

//                //ArrayList<Dish> listOfDishes = db.getDishesFromIngredients(mAdapter.getSelectedIngredientsList(), mAdapter.getSelectedIngredientsList().size());
//                Log.d(TAG, "onClick: size of list: "+listOfDishes.size());
//                if(listOfDishes.size()> 0) {
//                    Log.d(TAG, "onClick: element(0) " + listOfDishes.get(0).getName());
//                }
//                else {
//                    //TODO: ERROR
//                }
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.list_of_products_menu, menu);
        final MenuItem myActionMenuItem = menu.findItem(R.id.action_search);
        searchView = (SearchView) myActionMenuItem.getActionView();
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                s = s.toLowerCase();
                ArrayList<Ingredient> newList = new ArrayList<Ingredient>();
                for (Ingredient ingredient : ingredientsList) {
                    String name = ingredient.getName().toLowerCase();
                    if (name.contains(s)) {
                        newList.add(ingredient);
                    }
                }
                mAdapter.setFilter(newList);
                return true;
            }
        });
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        ArrayList<Ingredient> newList = new ArrayList<Ingredient>();
        switch (item.getItemId()) {
            case R.id.cbGlutenFree:
                item.setChecked(!item.isChecked());
                isGlutenFree = item.isChecked();
                filterIngredients();
                return true;
            case R.id.cbVegetarian:
                item.setChecked(!item.isChecked());
                isVegatarion = item.isChecked();
                filterIngredients();
                return true;
            default:
                filterIngredients();
                Log.d("onOptionsItemSelected", " free for all");
                return super.onOptionsItemSelected(item);
        }
    }

    public void filterIngredients(/*int filterType*/) {
        ArrayList<Ingredient> newList = new ArrayList<Ingredient>();

        if (isGlutenFree && !isVegatarion) {
            for (Ingredient ingredient : ingredientsListAll) {
                if (ingredient.isGlutenFree()) {
                    newList.add(ingredient);
                }
            }
        }
        if (isVegatarion && !isGlutenFree) {
            for (Ingredient ingredient : ingredientsListAll) {
                if (ingredient.isVegetarian()) {
                    newList.add(ingredient);
                }
            }
        }
        if (isVegatarion && isGlutenFree) {
            for (Ingredient ingredient : ingredientsListAll) {
                if (ingredient.isVegetarian() && ingredient.isGlutenFree()) {
                    newList.add(ingredient);
                }
            }
        }

        if (!isGlutenFree && !isVegatarion) {
            for (Ingredient ingredient : ingredientsListAll) {
                newList.add(ingredient);
            }
        }
        mAdapter.setFilter(newList);
        ingredientsList.clear();
        ingredientsList.addAll(newList);
        Log.d(TAG, "filterIngredients: rozmiar gluten free " + ingredientsList.size());

    }
}

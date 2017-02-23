package com.example.mat.dinnerov4;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import static android.content.ContentValues.TAG;

/**
 * Created by mat on 15.02.2017.
 */

public class IngredientsAdapter extends RecyclerView.Adapter<IngredientsAdapter.MyViewHolder> {

//    private final OnItemCheckListener onItemClick;
    private List<Ingredient> _ingredientsList;
    private ArrayList<Ingredient> selectedIngredientsList = new ArrayList<>();
//    interface OnItemCheckListener {
//        void onItemCheck(Ingredient item);
//        void onItemUncheck(Ingredient item);
//    }


//    @NonNull
//    private OnItemCheckListener onItemCheckListener;

    public IngredientsAdapter(List<Ingredient> ingredientsList/*, @NonNull OnItemCheckListener onItemCheckListener*/) {
        this._ingredientsList = ingredientsList;
        //this.onItemClick = onItemCheckListener;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView name;
        public CheckBox cb;
        public MyViewHolder(View itemView) {
            super(itemView);
            name = (TextView) itemView.findViewById(R.id.tvIngList);
            cb = (CheckBox) itemView.findViewById(R.id.cbIngList);
        }
        public void setOnClickListener(View.OnClickListener onClickListener) {
            itemView.setOnClickListener(onClickListener);
        }

    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.ingredient_list_row, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, int position) {
        final Ingredient ingredient = _ingredientsList.get(position);
        holder.name.setText(ingredient.getName());
//        holder.cb.setOnCheckedChangeListener(null);
//        holder.cb.setChecked(ingredient.isSelected());
//        holder.cb.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
//            @Override
//            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
//                //set your object's last status
//                ingredient.setSelected(isChecked);
//                if (isChecked) {
//                    //onItemClick.onItemCheck(ingredient);
//                    selectedIngredientsList.add(ingredient);
//                }
//                else {
//                    //onItemClick.onItemUncheck(ingredient);
//                    selectedIngredientsList.remove(ingredient);
//                }
//                Log.d(TAG, "onCheckedChanged: Ingredient NAME: "+ingredient.getName()+" Ingr ID: "+ingredient.getId());
//            }
//        });
        holder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "onClick: "+ingredient.getName());
                (holder).cb.setChecked(!(holder).cb.isChecked());
                if ((holder).cb.isChecked()) {
                    selectedIngredientsList.add(ingredient);

                } else {
                    selectedIngredientsList.remove(ingredient);
                }
            }
        });
//        (holder).setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                (holder).cb.setChecked(!((MyViewHolder) holder).cb.isChecked());
//                if (( holder).cb.isChecked()) {
//                    selectedIngredientsList.add(ingredient);
//
//                } else {
//                    selectedIngredientsList.remove(ingredient);
//                }
//            }
//        });
    }

    @Override
    public int getItemCount() {
        return _ingredientsList.size();
    }
    public ArrayList<Ingredient> getSelectedIngredientsList(){return selectedIngredientsList;}

    public void setFilter(ArrayList<Ingredient> newList){
        _ingredientsList = new ArrayList<>();
        _ingredientsList.addAll(newList);
        notifyDataSetChanged();
    }
}

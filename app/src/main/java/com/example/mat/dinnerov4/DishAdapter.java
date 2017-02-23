package com.example.mat.dinnerov4;

import android.content.Context;
import android.content.Intent;
import android.provider.ContactsContract;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import static android.content.ContentValues.TAG;

/**
 * Created by mat on 15.02.2017.
 */

public class DishAdapter extends RecyclerView.Adapter<DishAdapter.MyViewHolder> {

    //    private final OnItemCheckListener onItemClick;
    private List<Dish> _dishesList;
    private  Context context;
//    interface OnItemCheckListener {
//        void onItemCheck(Ingredient item);
//        void onItemUncheck(Ingredient item);
//    }


//    @NonNull
//    private OnItemCheckListener onItemCheckListener;

    public DishAdapter(List<Dish> ingredientsList/*, @NonNull OnItemCheckListener onItemCheckListener*/) {
        this._dishesList = ingredientsList;
        //this.onItemClick = onItemCheckListener;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView name;
        public TextView description;
        public ImageView imageView;
        public MyViewHolder(View itemView) {
            super(itemView);
            context =itemView.getContext();
            name = (TextView) itemView.findViewById(R.id.card_view_tv_name);
            description = (TextView) itemView.findViewById(R.id.card_view_tv_description);
            imageView = (ImageView) itemView.findViewById(R.id.card_view_coverImageView);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    final Intent intent;
                    intent = new Intent(context, DishActivity.class);
                    context.startActivity(intent);
                    Log.d(TAG, "onClick: DISh adapter");
                }
            });
        }
        public void setOnClickListener(View.OnClickListener onClickListener) {
            itemView.setOnClickListener(onClickListener);
        }
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.dish_card_view, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, int position) {
        final Dish dish = _dishesList.get(position);
        holder.name.setText(dish.getName());
        holder.description.setText(dish.getDescription());
        holder.imageView.setImageBitmap(dish.getImage());
        holder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent;
                intent = new Intent(context, DishActivity.class);
                context.startActivity(intent);
                Log.d(TAG, "onClick: DISh adapter");
            }
        });


//        ((MyViewHolder) holder).setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                ((MyViewHolder) holder).cb.setChecked(!((MyViewHolder) holder).cb.isChecked());
//                if (((MyViewHolder) holder).cb.isChecked()) {
//                    onItemClick.onItemCheck(ingredient);
//                } else {
//                    onItemClick.onItemUncheck(ingredient);
//                }
//            }
//        });
    }

    @Override
    public int getItemCount() {
        return _dishesList.size();
    }

}

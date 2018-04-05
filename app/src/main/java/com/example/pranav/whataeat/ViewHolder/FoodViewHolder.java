package com.example.pranav.whataeat.ViewHolder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import com.example.pranav.whataeat.Interface.ItemClickListener;
import com.example.pranav.whataeat.R;

public class FoodViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
    public ImageView food_image;
    public TextView foodname;

    private ItemClickListener itemClickListener;
    public FoodViewHolder(View itemview)
    {
        super(itemview);
        foodname =itemview.findViewById(R.id.FoodName);
        food_image=itemview.findViewById(R.id.food_image);
        itemview.setOnClickListener(this);
    }
    public void setItemClickListener(ItemClickListener itemClickListener)
    {
        this.itemClickListener=itemClickListener;
    }

    @Override
    public void onClick(View v) {
        itemClickListener.onClick(v,getAdapterPosition(),false);

    }
}

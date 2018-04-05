package com.example.pranav.whataeat;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.pranav.whataeat.Interface.ItemClickListener;
import com.example.pranav.whataeat.Models.Foods;
import com.example.pranav.whataeat.ViewHolder.FoodViewHolder;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
public class FoodList extends AppCompatActivity {
    String CategoryId = "";
    FirebaseRecyclerAdapter recyclerAdapter;
    FirebaseAuth firebaseAuth;
  FirebaseDatabase database = FirebaseDatabase.getInstance();

    DatabaseReference user_data = database.getReference("User Data");
    DatabaseReference foods = database.getReference("Foods");
    RecyclerView FoodRecyclerView;
    RecyclerView.LayoutManager FoodlayoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_food_list);
        firebaseAuth = FirebaseAuth.getInstance();


        FoodRecyclerView=findViewById(R.id.recycler_food);
        FoodRecyclerView.hasFixedSize();
        FoodlayoutManager=new LinearLayoutManager(this);
        FoodRecyclerView.setLayoutManager(FoodlayoutManager);
//      Query query=FirebaseDatabase.getInstance().getReference().child("Foods").orderByChild(CategoryId);
        CategoryId=getIntent().getStringExtra("CategoryID");
        Toast.makeText(FoodList.this,CategoryId,Toast.LENGTH_LONG).show();
        LoadListFood();

        }

    private void LoadListFood() {
        Query query=foods.orderByChild("MenuId").equalTo(CategoryId);
        FirebaseRecyclerOptions<Foods> options=new FirebaseRecyclerOptions.Builder<Foods>().setQuery(query,Foods.class).build();

        recyclerAdapter =new FirebaseRecyclerAdapter<Foods, FoodViewHolder>(options) {
            @NonNull
            @Override
            public FoodViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.food_item,parent,false);
                return new FoodViewHolder(view);
            }

            @Override
            protected void onBindViewHolder(@NonNull FoodViewHolder holder, int position, @NonNull Foods model) {
                holder.foodname.setText(model.getName());
                Glide.with(getBaseContext()).load(model.getImage()).into(holder.food_image);
                final Foods clickitem=model;
                holder.setItemClickListener(new ItemClickListener() {
                    @Override
                    public void onClick(View view, int position, boolean isLongclick) {
                        Toast.makeText(FoodList.this, "" + clickitem.getName(), Toast.LENGTH_LONG).show();
                        Intent fooddetail=new Intent(FoodList.this,FoodDetail.class);
                        fooddetail.putExtra("FoodID",recyclerAdapter.getRef(position).getKey());
                        startActivity(fooddetail);

                    }
                });
            }
        };
        FoodRecyclerView.setAdapter(recyclerAdapter);

    }

    @Override
    protected void onStart() {
        super.onStart();
        recyclerAdapter.startListening();
    }

    @Override
    protected void onStop() {
        super.onStop();
        recyclerAdapter.stopListening();
    }

}
package com.example.pranav.whataeat;

import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.cepheuen.elegantnumberbutton.view.ElegantNumberButton;
import com.example.pranav.whataeat.Models.Foods;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class FoodDetail extends AppCompatActivity {

    TextView food_name, food_price, food_description;
    ImageView food_image;
    CollapsingToolbarLayout collapsingToolbarLayout;
    FloatingActionButton btnCart;
    ElegantNumberButton quantBtn;


    String foodId = "";


    FirebaseDatabase database;
    DatabaseReference foods;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_food_detail);

        //Initialize Firebase Database
        database=FirebaseDatabase.getInstance();
        foods=database.getReference("Foods");

        //Initialize Views
        collapsingToolbarLayout=findViewById(R.id.collapsing);
        btnCart=findViewById(R.id.btnCart);
        quantBtn=findViewById(R.id.number_counter);
        food_name=findViewById(R.id.food_name);
        food_image=findViewById(R.id.food_image);
        food_description=findViewById(R.id.food_description);
        food_price=findViewById(R.id.food_price);


        //Get FoodId from Earlier Activity
        foodId=getIntent().getStringExtra("FoodID");
        getDetailFood(foodId);

    }

    private void getDetailFood(String foodId) {
        foods.child(foodId).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Foods foods=dataSnapshot.getValue(Foods.class);
                assert foods != null;
                Glide.with(getBaseContext()).load(foods.getImage()).into(food_image);
                collapsingToolbarLayout.setTitle(foods.getName());
                food_name.setText(foods.getName());
                food_price.setText(foods.getPrice());
                food_description.setText(foods.getDescription());



            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
}
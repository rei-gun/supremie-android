package com.bintang5.supremie.activity;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.widget.TextView;

import com.bintang5.supremie.R;

import java.util.ArrayList;

import domain.Drink;
import domain.Mie;
import domain.Order;
import domain.Topping;
import utils.OrderServer;
import utils.responses.PostResponse;

public class DiningMethodActivity extends AppCompatActivity {

    private TextView mTextMessage;


    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {

            switch (item.getItemId()) {
                case R.id.navigation_home:
                    mTextMessage.setText(R.string.title_home);
                    return true;
                case R.id.navigation_dashboard:
                    mTextMessage.setText(R.string.title_dashboard);
                    return true;
                case R.id.navigation_notifications:



                    Topping topping = new Topping(1, 1, null, 3000);
                    ArrayList toppings = new ArrayList();
                    toppings.add(topping);
                    Mie mie = new Mie(1, "hard", 2, 1, 3000, "pakai", 2, "", toppings);
                    ArrayList mies = new ArrayList();
                    mies.add(mie);
                    Drink drink = new Drink(1, 1, 2500);
                    ArrayList drinks = new ArrayList();
                    drinks.add(drink);
                    Order order = new Order(10000, "cash", "bungkus", mies, drinks);
                    PostResponse output = OrderServer.getInstance(getBaseContext()).createOrder(order);

                    mTextMessage.setText("Creating order...");
//                    try {
//                        mTextMessage.setText(OrderServer.getInstance(getBaseContext()).createOrder(order).getStatusCode());
//                    } catch (InterruptedException e) {
//                        e.printStackTrace();
//                    }
                    return true;
            }
            return false;
        }

    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dining_method);

        mTextMessage = (TextView) findViewById(R.id.message);
        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
    }


    protected void onResponseReceived(PostResponse response) {
        mTextMessage.setText(response.getMessage());
    }

}

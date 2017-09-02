package com.bintang5.supremie.activity;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import com.bintang5.supremie.R;

import java.util.ArrayList;

import domain.Drink;
import domain.Mie;
import domain.Order;
import domain.Topping;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import utils.OrderServer;
import utils.StockServer;
import utils.responses.GETResponseStock;
import utils.responses.POSTResponseOrder;

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
                    StockServer.getInstance(getBaseContext()).getStock().enqueue(new Callback<GETResponseStock>() {
                        @Override
                        public void onResponse(Call<GETResponseStock> call, Response<GETResponseStock> response) {
                            GETResponseStock output = new GETResponseStock(response.body().getMieStocks(),
                                    response.body().getToppingStocks(),
                                    response.body().getDrinkStocks());
                            Log.v("HERPDERP", output.getMieStocks().toString());
                        }

                        @Override
                        public void onFailure(Call<GETResponseStock> call, Throwable t) {

                        }
                    });
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
                    OrderServer.getInstance(getBaseContext()).createOrder(order).enqueue(new Callback<POSTResponseOrder>() {
                        @Override
                        public void onResponse(Call<POSTResponseOrder> call, Response<POSTResponseOrder> response) {

                            POSTResponseOrder responseOrder = new POSTResponseOrder(response.body().getId(), response.body().getStatusCode(),
                                    response.body().getMessage());
                            Log.v("CUNT", responseOrder.getMessage());
//                            mTextMessage.setText(responseOrder.getStatusCode());
                            Toast.makeText(DiningMethodActivity.this.getApplicationContext(),
                                    "Order Posted!",
                                    Toast.LENGTH_SHORT);
                        }

                        @Override
                        public void onFailure(Call<POSTResponseOrder> call, Throwable t) {

                        }
                    });
                    mTextMessage.setText("Creating order...");
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

}

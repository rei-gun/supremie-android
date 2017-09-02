package com.bintang5.supremie.activity;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTabHost;
import android.widget.Toast;

import com.bintang5.supremie.R;

import domain.Order;
import fragment.DiningMethodFragment;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import utils.StockServer;
import utils.responses.GETResponseStock;

import static android.widget.Toast.LENGTH_SHORT;

/*
 * Supremie's one and only activity. Contains a FragmentTabHost. Calls the GET stock API upon creation.
 */
public class MainActivity extends FragmentActivity {

    GETResponseStock allStock;
    Order order;

    private void getStock() {
        StockServer.getInstance(this).getStock().enqueue(new Callback<GETResponseStock>() {
            @Override
            public void onResponse(Call<GETResponseStock> call, Response<GETResponseStock> response) {
                allStock = new GETResponseStock(response.body().getMieStocks(),
                                                response.body().getToppingStocks(),
                                                response.body().getDrinkStocks());
            }

            @Override
            public void onFailure(Call<GETResponseStock> call, Throwable t) {
                Toast.makeText(getBaseContext(), "Server cannot be found!", LENGTH_SHORT);
            }
        });
    }
    /*
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
                            Toast.makeText(MainActivity.this.getApplicationContext(),
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
*/
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        FragmentTabHost tabHost = (FragmentTabHost)findViewById(R.id.tab_host);
        tabHost.setup(this, getSupportFragmentManager(), R.id.tab_content);

        getStock();

        tabHost.addTab(tabHost.newTabSpec("choose_dining_method").setIndicator("Welcome!"), new DiningMethodFragment().getClass(), null);
        tabHost.addTab(tabHost.newTabSpec("choose_mie").setIndicator("Pilih Mie"), new DiningMethodFragment().getClass(), null);
        tabHost.addTab(tabHost.newTabSpec("choose_topping").setIndicator("Pilih Topping"), new DiningMethodFragment().getClass(), null);
        tabHost.addTab(tabHost.newTabSpec("choose_chili").setIndicator("Pilih Pedas"), new DiningMethodFragment().getClass(), null);
        tabHost.addTab(tabHost.newTabSpec("choose_drink").setIndicator("Pilih Minum"), new DiningMethodFragment().getClass(), null);
        tabHost.addTab(tabHost.newTabSpec("choose_payment").setIndicator("Pilih Pembayaran"), new DiningMethodFragment().getClass(), null);
    }

}

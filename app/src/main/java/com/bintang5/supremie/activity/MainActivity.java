package com.bintang5.supremie.activity;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTabHost;

import com.bintang5.supremie.R;

import fragment.ChooseDrinkFragment;
import fragment.ChooseMieBrandFragment;
import fragment.ChooseMieFlavourFragment;
import fragment.ChooseToppingFragment;
import fragment.DiningMethodFragment;
import model.Order;
import utils.StockServer;
import utils.responses.GETResponseStock;

/*
 * Supremie's one and only activity. Contains a FragmentTabHost. Calls the GET stock API upon creation.
 */
public class MainActivity extends FragmentActivity {

    public GETResponseStock allStock;
    public Order order;
//    public String brand;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        FragmentTabHost tabHost = (FragmentTabHost)findViewById(R.id.tab_host);
        tabHost.setup(this, getSupportFragmentManager(), R.id.tab_content);

        StockServer.getInstance(this).getStock(this);
        order = new Order();

        tabHost.addTab(tabHost.newTabSpec("choose_dining_method").setIndicator("Welcome!"), new DiningMethodFragment().getClass(), null);
        tabHost.addTab(tabHost.newTabSpec("choose_mie_brand").setIndicator("Pilih Mie"), new ChooseMieBrandFragment().getClass(), null);
        tabHost.addTab(tabHost.newTabSpec("choose_mie_flavour").setIndicator("Pilih Rasa"), new ChooseMieFlavourFragment().getClass(), null);
        tabHost.addTab(tabHost.newTabSpec("choose_topping").setIndicator("Pilih Topping"), new ChooseToppingFragment().getClass(), null);
        tabHost.addTab(tabHost.newTabSpec("choose_chili").setIndicator("Pilih Pedas"), new DiningMethodFragment().getClass(), null);
        tabHost.addTab(tabHost.newTabSpec("choose_drink").setIndicator("Pilih Minum"), new ChooseDrinkFragment().getClass(), null);
        tabHost.addTab(tabHost.newTabSpec("summary").setIndicator("Order Summary"), new DiningMethodFragment().getClass(), null);
        tabHost.addTab(tabHost.newTabSpec("choose_payment").setIndicator("Pilih Pembayaran"), new DiningMethodFragment().getClass(), null);
    }

    public Order getOrder() {
        return order;
    }

    public GETResponseStock getAllStock() {
        return allStock;
    }

//    public void setBrand(Fragment caller, String brand) {
//        if (caller instanceof ChooseMieBrandFragment) {
//            this.brand = brand;
//        }
//    }
//
//    public String getBrand() {
//        return this.brand;
//    }
}

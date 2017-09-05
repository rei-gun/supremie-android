package com.bintang5.supremie.activity;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTabHost;
import android.widget.TabHost;

import com.bintang5.supremie.R;

import fragment.ChooseDrinkFragment;
import fragment.ChooseMieBrandFragment;
import fragment.ChooseMieFlavourFragment;
import fragment.ChoosePedasFragment;
import fragment.ChooseToppingFragment;
import fragment.DiningMethodFragment;
import fragment.OrderSummaryFragment;
import utils.StockServer;

/*
 * Supremie's one and only activity. Contains a FragmentTabHost. Calls the GET stock API upon creation.
 */
public class MainActivity extends FragmentActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        FragmentTabHost tabHost = (FragmentTabHost)findViewById(R.id.tab_host);
        tabHost.setup(this, getSupportFragmentManager(), R.id.tab_content);

        StockServer.getInstance(this).getStock(this);

        tabHost.addTab(tabHost.newTabSpec("choose_dining_method").setIndicator("Welcome!"), new DiningMethodFragment().getClass(), null);
        tabHost.addTab(tabHost.newTabSpec("choose_mie_brand").setIndicator("Pilih Mie"), new ChooseMieBrandFragment().getClass(), null);
        tabHost.addTab(tabHost.newTabSpec("choose_mie_flavour").setIndicator("Pilih Rasa"), new ChooseMieFlavourFragment().getClass(), null);
        tabHost.addTab(tabHost.newTabSpec("choose_topping").setIndicator("Pilih Topping"), new ChooseToppingFragment().getClass(), null);
        tabHost.addTab(tabHost.newTabSpec("choose_chili").setIndicator("Pilih Pedas"), new ChoosePedasFragment().getClass(), null);
        tabHost.addTab(tabHost.newTabSpec("choose_drink").setIndicator("Pilih Minum"), new ChooseDrinkFragment().getClass(), null);
        tabHost.addTab(tabHost.newTabSpec("summary").setIndicator("Order Summary"), new OrderSummaryFragment().getClass(), null);

        tabHost.setOnTabChangedListener(new TabHost.OnTabChangeListener() {
            @Override
            public void onTabChanged(String s) {
                if (State.getInstance().isOrderDataSetup()) {
                    State.getInstance().deleteOrderData();
                }
            }
        });
    }
}

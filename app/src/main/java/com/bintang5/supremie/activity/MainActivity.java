package com.bintang5.supremie.activity;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTabHost;
import android.widget.TabHost;
import android.widget.TabWidget;

import com.bintang5.supremie.R;
import com.cashlez.android.sdk.payment.CLPaymentResponse;
import com.cashlez.android.sdk.payment.noncash.ICLPaymentService;

import fragment.ChooseDrinkFragment;
import fragment.ChooseMieBrandFragment;
import fragment.ChooseMieFlavourFragment;
import fragment.ChoosePedasFragment;
import fragment.ChooseToppingFragment;
import fragment.DiningMethodFragment;
import fragment.OrderSummaryFragment;
import model.User;
import utils.CashlezLogin;
import utils.StockServer;

/*
 * Supremie's one and only activity. Contains a FragmentTabHost. Calls the GET stock API upon creation.
 */
public class MainActivity extends FragmentActivity implements ICLPaymentService {

    FragmentTabHost tabHost;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tabHost = (FragmentTabHost)findViewById(R.id.tab_host);
        tabHost.setup(this, getSupportFragmentManager(), R.id.tab_content);
        //Get stock data from server
        StockServer.getInstance(this).getStock(this);
        //Log in to Cashlez
        CashlezLogin cashlezLogin = new CashlezLogin(this);
        cashlezLogin.doLoginAggregator(new User());

        tabHost.addTab(tabHost.newTabSpec("choose_dining_method").setIndicator("Welcome!"), new DiningMethodFragment().getClass(), null);
        tabHost.addTab(tabHost.newTabSpec("choose_mie_brand").setIndicator("Pilih Mie"), new ChooseMieBrandFragment().getClass(), null);
        tabHost.addTab(tabHost.newTabSpec("choose_mie_flavour").setIndicator("Pilih Rasa"), new ChooseMieFlavourFragment().getClass(), null);
        tabHost.addTab(tabHost.newTabSpec("choose_topping").setIndicator("Pilih Topping"), new ChooseToppingFragment().getClass(), null);
        tabHost.addTab(tabHost.newTabSpec("choose_chili").setIndicator("Pilih Pedas"), new ChoosePedasFragment().getClass(), null);
        tabHost.addTab(tabHost.newTabSpec("choose_drink").setIndicator("Pilih Minum"), new ChooseDrinkFragment().getClass(), null);
        tabHost.addTab(tabHost.newTabSpec("summary").setIndicator("Order Summary"), new OrderSummaryFragment().getClass(), null);

        //disable tabs in front of Choose Dining Method
        for (int i=1; i<tabHost.getTabWidget().getTabCount(); i++) {
            tabHost.getTabWidget().getChildTabViewAt(i).setEnabled(false);
        }

        tabHost.setOnTabChangedListener(new TabHost.OnTabChangeListener() {
            @Override
            public void onTabChanged(String s) {
                //coming out of Order Summary tab
                if (State.getInstance().isOrderDataSetup()) {
                    State.getInstance().deleteOrderData();
                }
                //mie flavour has not been chosen, disable Order Summary
                if (State.getInstance().getMieId() == null) {
                    TabWidget tabWidget = tabHost.getTabWidget();
                    tabWidget.getChildTabViewAt(tabWidget.getTabCount()-1);
                }
            }
        });
        //default pedas level to level 1
        State.getInstance().setPedasLevel(0);
    }

    @Override
    public void onGetReaderCompanion(CLPaymentResponse clPaymentResponse) {

    }

    @Override
    public void onGetPaymentResponse(CLPaymentResponse clPaymentResponse) {

    }

    @Override
    public void onProvideSignature(CLPaymentResponse clPaymentResponse) {

    }

    public void enableTab(Integer tabId) {
        tabHost.getTabWidget().getChildTabViewAt(tabId).setEnabled(true);
    }

    public void disableTab(Integer tabId) {
        tabHost.getTabWidget().getChildTabViewAt(tabId).setEnabled(false);
    }
}

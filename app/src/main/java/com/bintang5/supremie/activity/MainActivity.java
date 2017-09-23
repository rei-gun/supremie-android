package com.bintang5.supremie.activity;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTabHost;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.TabHost;
import android.widget.TabWidget;
import android.widget.TextView;

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
        tabHost.setup(this, getSupportFragmentManager(), R.id.realtabcontent);
        //Get stock data from server
        disableUserInput();
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
        tabHost.addTab(tabHost.newTabSpec("summary").setIndicator("Review Pesanan"), new OrderSummaryFragment().getClass(), null);

        //disable and set color of tabs in front of Choose Dining Method
        tabHost.getTabWidget().getChildAt(0).setBackgroundResource(R.drawable.tab_color_selector);
        TextView tabText = (TextView)tabHost.getTabWidget().getChildAt(0).findViewById(android.R.id.title);
        tabText.setTextColor(ContextCompat.getColorStateList(this, R.color.tab_text_selector));
        for (int i=1; i<tabHost.getTabWidget().getTabCount(); i++) {
            tabHost.getTabWidget().getChildTabViewAt(i).setEnabled(false);
            tabText = (TextView)tabHost.getTabWidget().getChildAt(i).findViewById(android.R.id.title);
            tabText.setTextColor(getColor(R.color.pureWhite));
            tabHost.getTabWidget().getChildAt(i).setBackgroundColor(getColor(R.color.darkGrey));
        }
        State.getInstance().setPrevTabId(0);
        tabHost.setOnTabChangedListener(new TabHost.OnTabChangeListener() {
            @Override
            public void onTabChanged(String s) {
                View currView = tabHost.getCurrentView();
                if (tabHost.getCurrentTab() > State.getInstance().getPrevTabId()) {
                    currView.setAnimation(inFromRightAnimation());
                    Log.v("DERP", "DIRT");
                } else {
                    currView.setAnimation(outToRightAnimation());
                    Log.v("DERP", "MIRT");
                }
                State.getInstance().setPrevTabId(tabHost.getCurrentTab());
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
        tabHost.getTabWidget().getChildAt(tabId).setBackgroundResource(R.drawable.tab_color_selector);
        TextView tabText = (TextView)tabHost.getTabWidget().getChildAt(tabId).findViewById(android.R.id.title);
        tabText.setTextColor(ContextCompat.getColorStateList(this, R.color.tab_text_selector));
        tabHost.getTabWidget().getChildTabViewAt(tabId).setEnabled(true);

    }

    public void disableTab(Integer tabId) {
        tabHost.getTabWidget().getChildTabViewAt(tabId).setEnabled(false);
    }

    public Animation inFromRightAnimation()
    {

        Animation inFromRight = new TranslateAnimation(
                Animation.RELATIVE_TO_PARENT, +1.0f,
                Animation.RELATIVE_TO_PARENT, 0.0f,
                Animation.RELATIVE_TO_PARENT, 0.0f,
                Animation.RELATIVE_TO_PARENT, 0.0f);
        inFromRight.setDuration(2000);
        inFromRight.setInterpolator(new AccelerateInterpolator());
        return inFromRight;
    }

    public Animation outToRightAnimation()
    {
        Animation outtoLeft = new TranslateAnimation(
                Animation.RELATIVE_TO_PARENT, -1.0f,
                Animation.RELATIVE_TO_PARENT, 0.0f,
                Animation.RELATIVE_TO_PARENT, 0.0f,
                Animation.RELATIVE_TO_PARENT, 0.0f);
        outtoLeft.setDuration(2000);
        outtoLeft.setInterpolator(new AccelerateInterpolator());
        return outtoLeft;
    }



    /**
     * Disables the entire screen from user input
     */
    public void disableUserInput() {
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE, WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
    }

    /**
     * Enables the entire screen for user input
     */
    public void enableUserInput() {
        getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
    }
}

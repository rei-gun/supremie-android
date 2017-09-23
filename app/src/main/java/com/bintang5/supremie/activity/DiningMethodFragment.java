package com.bintang5.supremie.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageButton;
import android.widget.TabHost;

import com.bintang5.supremie.R;
import com.bintang5.supremie.activity.MainActivity;
import com.bintang5.supremie.activity.State;
import com.bintang5.supremie.activity.ChooseMieBrandFragment;

import model.User;
import utils.CashlezLogin;
import utils.StockServer;

/**
 * Created by rei on 2/09/17.
 */

public class DiningMethodFragment extends AppCompatActivity {

    @Nullable
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_dining_method);

        //Get stock data from server
        disableUserInput();
        StockServer.getInstance(this).getStock(this);
        //Log in to Cashlez
        CashlezLogin cashlezLogin = new CashlezLogin(this);
        cashlezLogin.doLoginAggregator(new User());

        ImageButton dineInButton = (ImageButton)findViewById(R.id.button_dine_in);
        dineInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                State.getInstance().setDiningMethod("makan sini");
//                setAndChangeTab();
                Intent intent = new Intent(DiningMethodFragment.this, ChooseMieBrandFragment.class);
                DiningMethodFragment.this.startActivity(intent);
            }
        });

        ImageButton takeawayButton = (ImageButton)findViewById(R.id.button_takeaway);
        takeawayButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                State.getInstance().setDiningMethod("bungkus");
//                setAndChangeTab();
            }
        });

    }

    private void setAndChangeTab() {
//        ((MainActivity)getActivity()).enableTab(1);
//        TabHost tabHost = (TabHost)getActivity().findViewById(R.id.tab_host);
//        tabHost.setCurrentTab(1);
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

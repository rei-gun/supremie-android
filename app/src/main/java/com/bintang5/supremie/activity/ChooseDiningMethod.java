package com.bintang5.supremie.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageButton;

import com.bintang5.supremie.R;

import model.User;
import utils.CashlezLogin;
import utils.StockServer;

/**
 * Created by rei on 2/09/17.
 */

public class ChooseDiningMethod extends SupremieActivity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.choose_dining_method);
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
//                if (State.getInstance().getMieId() == null) {
                    //initialize all Order items inside State
//                }
                Intent intent = new Intent(ChooseDiningMethod.this, ChooseMieBrand.class);
                ChooseDiningMethod.this.startActivity(intent);
                overridePendingTransition(R.anim.enter, R.anim.exit);
            }
        });

        ImageButton takeawayButton = (ImageButton)findViewById(R.id.button_takeaway);
        takeawayButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                State.getInstance().setDiningMethod("bungkus");
                Intent intent = new Intent(ChooseDiningMethod.this, ChooseMieBrand.class);
                ChooseDiningMethod.this.startActivity(intent);
                overridePendingTransition(R.anim.enter, R.anim.exit);
            }
        });

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

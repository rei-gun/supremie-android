package com.bintang5.supremie.activity;

import android.app.Application;
import com.bintang5.supremie.R;
import cat.ereza.customactivityoncrash.config.CaocConfig;

/**
 * Created by rei on 10/3/17.
 */

public class SupremieApp extends Application {
    @Override
    public void onCreate() {
        super.onCreate();

        CaocConfig.Builder.create()
                .backgroundMode(CaocConfig.BACKGROUND_MODE_SHOW_CUSTOM) //default: CaocConfig.BACKGROUND_MODE_SHOW_CUSTOM
                .enabled(false) //default: true
                .showErrorDetails(true) //default: true
                .showRestartButton(true) //default: true
                .trackActivities(false) //default: false
                .minTimeBetweenCrashesMs(3000) //default: 3000
                .errorDrawable(R.drawable.supremie_logo) //default: bug image
                .restartActivity(ChooseDiningMethod.class) //default: null (your app's launch activity)
                .errorActivity(ChooseDiningMethod.class) //default: null (default error activity)
                .eventListener(null) //default: null
                .apply();
    }
}
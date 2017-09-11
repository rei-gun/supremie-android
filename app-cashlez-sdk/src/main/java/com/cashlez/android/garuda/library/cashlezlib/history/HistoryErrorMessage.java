package com.cashlez.android.garuda.library.cashlezlib.history;

import android.content.Context;

import com.cashlez.android.garuda.library.cashlezlib.R;

/**
 * Created by Taslim_Hartmann on 5/15/2017.
 */

class HistoryErrorMessage {
    public static String get(Throwable e, boolean pullToRefresh, Context c) {
        // TODO distinguish type of exception and return different strings
        if (pullToRefresh) {
            return c.getString(R.string.error_load_history);
        } else {
            return c.getString(R.string.error_load_history_retry);
        }
    }
}

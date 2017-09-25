package utils;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.bintang5.supremie.activity.ChooseDiningMethod;
import com.bintang5.supremie.activity.State;
import com.cashlez.android.sdk.model.CLPrintObject;
import com.cashlez.android.sdk.service.CLPrintAlignEnum;
import com.cashlez.android.sdk.service.CLPrintEnum;

import java.util.ArrayList;

import model.Order;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import utils.responses.POSTResponseOrder;

/**
 * Exposes {@link OrderService}.
 */
public class OrderServer extends Server {
    /**
     * The singleton instance.
     */
    private static OrderServer instance;

    /**
     * The service api conf.
     */
    private OrderService service;

    /**
     * Constructor.
     */
    private OrderServer(Context c) {
        super(c);
        service = createService(OrderService.class);
    }

    /**
     * Obtains singleton instance.
     *
     * @return The singleton instance.
     */
    public static OrderServer getInstance(Context c) {
        if (instance == null)
            instance = new OrderServer(c);
        return instance;
    }

    /**
     * Create a new permintaan.
     *
     * @param order The Order model to be created.
     */
    public void createOrder(final CashlezPayment cashlezPayment, final Activity callingActivity, Order order) {
        Log.v("TEST", "test");

        service.createOrder(order).enqueue(new Callback<POSTResponseOrder>() {
            @Override
            public void onResponse(Call<POSTResponseOrder> call, Response<POSTResponseOrder> response) {
                POSTResponseOrder r = response.body();
                //TODO print the id to Cashlez's printer here

                ArrayList<CLPrintObject> freeText = new ArrayList<>();
                CLPrintObject clPrintObject = new CLPrintObject();
                clPrintObject.setFreeText("Your order id is :"+r.getId());
                clPrintObject.setFormat(CLPrintEnum.TITLE);
                clPrintObject.setAlign(CLPrintAlignEnum.CENTER);
                freeText.add(clPrintObject);
                Log.v("BOOM", clPrintObject.getFreeText());
                cashlezPayment.doPrintFreeText(freeText);
                cashlezPayment.unregisterReceiver();
                State.getInstance().clear();
                Intent intent = new Intent(callingActivity, ChooseDiningMethod.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                callingActivity.startActivity(intent);
            }
            @Override
            public void onFailure(Call<POSTResponseOrder> call, Throwable t) {
        
            }
        });

    }

}

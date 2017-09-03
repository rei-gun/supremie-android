package utils;

import android.content.Context;
import android.util.Log;

import model.Order;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import utils.responses.POSTResponseOrder;

/**
 * Exposes {@link OrderService}.
 */
public class OrderServer extends Server {
    public POSTResponseOrder output;

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
     * @param order The permintaan model to be created.
     * @return The permintaan model that was added.
     */
    public void createOrder(Order order) {

        service.createOrder(order).enqueue(new Callback<POSTResponseOrder>() {
            @Override
            public void onResponse(Call<POSTResponseOrder> call, Response<POSTResponseOrder> response) {
                POSTResponseOrder r = response.body();
                Log.v("CUNT", r.getId().toString()+r.getStatusCode());
//                Intent intent = new Intent( MainActivity.class);
//                startActivity(intent);
            }
            @Override
            public void onFailure(Call<POSTResponseOrder> call, Throwable t) {

            }
        });

    }

}

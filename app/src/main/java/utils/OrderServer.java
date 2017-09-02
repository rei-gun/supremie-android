package utils;

import android.content.Context;

import model.Order;
import retrofit2.Call;
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
     * Get a permintaan, given its id.
     *
     * @param id The permintaan's id.
     * @return The permintaan.

    public Observable<Permintaan> getPermintaan(String id) {
        return service.getPermintaan(id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }
*/

    /**
     * Create a new permintaan.
     *
     * @param order The permintaan model to be created.
     * @return The permintaan model that was added.
     */
    public Call<POSTResponseOrder> createOrder(Order order) {
        return service.createOrder(order);
        /*
        output = null;
        service.createOrder(order).enqueue(new Callback<POSTResponseOrder>() {
            @Override
            public void onResponse(Call<POSTResponseOrder> call, Response<POSTResponseOrder> response) {

                output = new POSTResponseOrder(response.body().getId(), response.body().getStatusCode(),
                        response.body().getMessage());
                Log.v("CUNT", output.toString());
                mTextMessage.setText(output.getStatusCode());
            }

            @Override
            public void onFailure(Call<POSTResponseOrder> call, Throwable t) {

            }
        });
//        return output;
*/
    }

    /**
     * Update a permintaan.
     *
     * @param permintaan The permintaan model to be created.
     * @return The permintaan model that was added.
     *
    public Observable<Boolean> updatePermintaan(Permintaan permintaan) {
        return service.updatePermintaan(permintaan._id, permintaan)
                .map(new Func1<PutResponse, Boolean>() {
                    @Override
                    public Boolean call(PutResponse response) {
                        return response.ok;
                    }
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }
*/

}

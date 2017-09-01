package utils;

import android.content.Context;
import android.util.Log;

import domain.Order;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import utils.responses.PostResponse;

//import com.martabak.kamar.domain.local.permintaan.Permintaan;
//import com.martabak.kamar.service.local.response.AllResponse;
//import com.martabak.kamar.service.local.response.PostResponse;
//import com.martabak.kamar.service.local.response.PutResponse;
//import com.martabak.kamar.service.local.response.ViewResponse;

/**
 * Exposes {@link OrderService}.
 */
public class OrderServer extends Server {
    public PostResponse output;

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
    public PostResponse createOrder(Order order) {
        output = null;
        service.createOrder(order).enqueue(new Callback<PostResponse>() {
            @Override
            public void onResponse(Call<PostResponse> call, Response<PostResponse> response) {

                output = new PostResponse(response.body().getId(), response.body().getStatusCode(),
                        response.body().getMessage());
                Log.v("CUNT", output.toString());
            }

            @Override
            public void onFailure(Call<PostResponse> call, Throwable t) {

            }
        });
        return output;
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

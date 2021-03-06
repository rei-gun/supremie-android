package utils;

import android.content.Context;
import android.content.Intent;
import android.os.SystemClock;
import android.util.Log;
import android.widget.Toast;

import com.bintang5.supremie.activity.ChooseDiningMethod;
import com.bintang5.supremie.activity.State;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import utils.responses.GETResponseStock;
import utils.responses.POSTResponseOrder;

/**
 * Exposes {@link OrderService}.
 */
public class StockServer extends Server {
    public POSTResponseOrder output;

    /**
     * The singleton instance.
     */
    private static StockServer instance;

    /**
     * The service api conf.
     */
    private StockService service;

    /**
     * Constructor.
     */
    private StockServer(Context c) {
        super(c);
        service = createService(StockService.class);
    }

    /**
     * Obtains singleton instance.
     *
     * @return The singleton instance.
     */
    public static StockServer getInstance(Context c) {
        if (instance == null)
            instance = new StockServer(c);
        return instance;
    }

    /**
     * Create a new permintaan.
     *
     * @return The permintaan model that was added.
     * @param callingActivity
     */
    public void getStock(final ChooseDiningMethod callingActivity) {

        service.getStock().enqueue(new Callback<GETResponseStock>() {
            @Override
            public void onResponse(Call<GETResponseStock> call, Response<GETResponseStock> response) {
                State.getInstance().setAllStock(response.body());
                callingActivity.enableUserInput();
                Toast.makeText(
                        callingActivity.getApplicationContext(),
                        "Berhubung dengan server sukses!",
                        Toast.LENGTH_SHORT
                ).show();
            }
            @Override
            public void onFailure(Call<GETResponseStock> call, Throwable t) {
                Log.v("BART", t.toString());
                Intent i = callingActivity.getIntent();
                callingActivity.finish();
                callingActivity.startActivity(i);
            }
        });
    }
}
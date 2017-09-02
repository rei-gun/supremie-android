package utils;

import retrofit2.Call;
import retrofit2.http.GET;
import utils.responses.GETResponseStock;

/**
 * Provides HTTP-related functionality for orders.
 */
public interface StockService {

    @GET("stock")
    Call<GETResponseStock> getStock();

}
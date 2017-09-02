package utils;

import domain.Order;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;
import utils.responses.POSTResponseOrder;

/**
 * Provides HTTP-related functionality for orders.
 */
public interface OrderService {

    @POST("orders")
    Call<POSTResponseOrder> createOrder(@Body Order order);

}
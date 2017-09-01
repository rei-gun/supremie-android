package utils;

import domain.Order;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;
import utils.responses.PostResponse;

/**
 * Provides permintaan (request) related functionality.
 */
public interface OrderService {
/*
    @GET("permintaan/{id}")
    Observable<Permintaan> getPermintaan(@Path("id") String id);

    @GET("permintaan/_design/permintaan/_view/guest")
    Observable<ViewResponse<Permintaan>> getPermintaansForGuest(@Query("key") String guestId);

    @GET("permintaan/_design/permintaan/_view/state")
    Observable<ViewResponse<Permintaan>> getPermintaansOfState(@Query("key") String state);

    @GET("permintaan/_design/permintaan/_view/time")
    Observable<ViewResponse<Permintaan>> getPermintaansofTime(@Query("startKey") Date start, @Query("endKey") Date end);

    @GET("permintaan/_all_docs?include_docs=true&descending=true&skip=1\"")
    Observable<AllResponse<Permintaan>> getAllPermintaans();
*/
//    @FormUrlEncoded
    @POST("orders")
    Call<PostResponse> createOrder(@Body Order order);

//    @PUT("permintaan/{id}")
//    Observable<PutResponse> updatePermintaan(@Path("id") String id, @Body Permintaan permintaan);

}
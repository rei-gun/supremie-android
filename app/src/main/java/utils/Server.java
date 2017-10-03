package utils;


import android.content.Context;

import com.google.gson.GsonBuilder;

import java.util.concurrent.TimeUnit;

import model.Order;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import utils.JSONifies.GETStockJSONify;
import utils.JSONifies.POSTOrderJSONify;
import utils.responses.GETResponseStock;

/**
 * An abstract Retrofit server.
 */
public abstract class Server {

    /**
     * The Retrofit instance.
     */
    private static Retrofit retrofit;

    /**
     * The OkHttpClient instance.
     */
    private static OkHttpClient client;

    /**
     * The pattern used for representing dates in a String.
     */
    private static final String datePattern = "yyyy-MM-dd'T'HH:mm:ss'Z'";

    /**
     * Construct the Retrofit server.
     */
    protected Server(Context c) {
        if (client == null) {
            client = new OkHttpClient.Builder()
//                    .addInterceptor(new AuthorizationInterceptor(c))
                    .addInterceptor(new HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
                    .connectTimeout(10000, TimeUnit.MILLISECONDS)
                    .readTimeout(10000, TimeUnit.MILLISECONDS)
                    .writeTimeout(10000, TimeUnit.MILLISECONDS)
                    .build();
        }
        if (retrofit == null) {
            retrofit = new Retrofit.Builder()
                    .client(client)
                    .baseUrl(getBaseUrl())
                    .addConverterFactory(GsonConverterFactory.create(new GsonBuilder()
                            .registerTypeAdapter(Order.class, new POSTOrderJSONify())
                            .registerTypeAdapter(GETResponseStock.class, new GETStockJSONify(datePattern))
                            .setDateFormat(datePattern)
                            .create()))
            .build();
        }
    }

    /**
     * Create a Retrofit service.
     * @param <T> The service type.
     * @return The service.
     */
    protected final <T> T createService(Class<T> service) {
        return retrofit.create(service);
    }

    /**
     * @return The server's base URL.
     */
    public static String getBaseUrl() {
//        return "http://139.59.96.68:8000"; // DigitalOcean server
       return "http://192.168.0.100:80"; // Raspberry Pi @ Supremie
    }

    /*
     * Fetch an instance of Picasso to load an image.
     * @param c The context.
     * @return The Picasso instance.
     *
    public static Picasso picasso(Context c) {
        Picasso p = new Picasso.Builder(c)
                .downloader(new OkHttp3Downloader(client))
                .build();
        p.setLoggingEnabled(true);
        return p;
    }
*/
}

package model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by rei on 30/08/17.
 */

public class Drink {

    @SerializedName("drink_id") public Integer id;

    public Integer quantity;

    public Integer price;

    public Drink(Integer id, Integer quantity, Integer price) {
        this.id = id;
        this.quantity = quantity;
        this.price = price;
    }
}

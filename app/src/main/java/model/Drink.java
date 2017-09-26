package model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by rei on 30/08/17.
 */

public class Drink {

    @SerializedName("drink_id") public Integer id;

    public String brand;

    public String flavour;

    public Integer quantity;

    public Integer price;

    public Drink(Integer id, String brand, String flavour, Integer quantity, Integer price) {
        this.id = id;
        this.brand = brand;
        this.flavour = flavour;
        this.quantity = quantity;
        this.price = price;
    }
}

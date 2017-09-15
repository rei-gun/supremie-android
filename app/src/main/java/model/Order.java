package model;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * Created by rei on 30/08/17.
 */

public class Order {

    public Integer id;

    @SerializedName("total_price")public Integer totalPrice;

    @SerializedName("payment_method")public String paymentMethod;

    @SerializedName("dining_method")public String diningMethod;

    public ArrayList<Mie> mies;

    public ArrayList<Drink> drinks;

    /*
     * Constructor to POST new Orders
     */
    public Order(Integer totalPrice, String paymentMethod, String diningMethod, ArrayList<Mie> mies, ArrayList<Drink> drinks) {
        this.id = null;
        this.totalPrice = totalPrice;
        this.paymentMethod = paymentMethod;
        this.diningMethod = diningMethod;
        this.mies = mies;
        this.drinks = drinks;
    }

    public Order() {
        this.id = null;
        this.totalPrice = null;
        this.paymentMethod = null;
        this.diningMethod = null;
        this.mies = null;
        this.drinks = null;
    }
}

package model;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * Created by rei on 30/08/17.
 */

public class Mie {

    public Integer id;

    @SerializedName("quantity_mie")public Integer quantityMie;

    @SerializedName("quantity_whole")public Integer quantityWhole;

    public Integer price;

    @SerializedName("extra_chili")public Integer extraChili;

    public String note;

    public ArrayList<Topping> toppings;

    public Mie(Integer id, Integer quantityMie, Integer quantityWhole, Integer price,
               String chiliPowder, Integer extraChili, String note, ArrayList<Topping> toppings) {
        this.id = id;
        this.quantityMie = quantityMie;
        this.quantityWhole = quantityWhole;
        this.price = price;
        this.extraChili = extraChili;
        this.note = note;
        this.toppings = toppings;
    }
}

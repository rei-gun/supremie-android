package domain;

import com.google.gson.annotations.SerializedName;

/**
 * Created by rei on 30/08/17.
 */

public class MieStock {

    public Integer id;

    public String brand;

    public String flavour;

    public Integer stock;

    public Integer price;

    @SerializedName("img_url")public String imgUrl;

    public MieStock(Integer id, String brand, String flavour, Integer stock,
                    Integer price, String imgUrl) {
        this.id = id;
        this.brand = brand;
        this.flavour = flavour;
        this.stock = stock;
        this.price = price;
        this.imgUrl = imgUrl;
    }
}

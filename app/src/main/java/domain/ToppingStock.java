package domain;

import com.google.gson.annotations.SerializedName;

/**
 * Created by rei on 30/08/17.
 */

public class ToppingStock {

    public Integer id;

    public String name;

    public Integer price;

    public Integer stock;

    @SerializedName("img_url")public String imgUrl;

    public ToppingStock(Integer id, Integer stock, String name, Integer price,
                        String imgUrl) {
        this.id = id;
        this.stock = stock;
        this.name = name;
        this.price = price;
        this.imgUrl = imgUrl;
    }
}

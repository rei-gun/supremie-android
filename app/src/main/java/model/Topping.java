package model;

/**
 * Created by rei on 30/08/17.
 */

public class Topping {
    
    public Integer id;

    public String name;

    public Integer quantity;
    
    public String type;

    public Integer price;

    public Topping(Integer id, String name, Integer quantity, String type, Integer price) {
        this.id = id;
        this.name = name;
        this.quantity = quantity;
        this.type = type;
        this.price = price;
    }
}

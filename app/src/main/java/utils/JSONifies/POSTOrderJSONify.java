package utils.JSONifies;

import android.util.Log;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

import java.lang.reflect.Type;

import model.Drink;
import model.Mie;
import model.Order;
import model.Topping;

/**
 * Serializes {@link Order}s.
 */
public class POSTOrderJSONify implements JsonSerializer<Order> {

    @Override
    public JsonElement serialize(Order src, Type srcType, JsonSerializationContext context) {
        JsonObject orderKey = new JsonObject();
        JsonObject orderObject = new JsonObject();
        orderKey.add("order", orderObject);
        orderObject.addProperty("total_price", src.totalPrice);
        orderObject.addProperty("payment_method", src.paymentMethod);
        orderObject.addProperty("dining_method", src.diningMethod);
        Log.v("BIGDOCK", (String.valueOf(src.mies.size())));
        JsonArray mieArray = new JsonArray();
        if (src.mies.size() != 0) {
            for (Mie m : src.mies) {
                JsonObject jsonMie = new JsonObject();
                jsonMie.addProperty("id", m.id);
                jsonMie.addProperty("quantity_mie", m.quantityMie);
                jsonMie.addProperty("quantity_whole", m.quantityWhole);
                jsonMie.addProperty("price", m.price);
                jsonMie.addProperty("extra_chili", m.extraChili);
                jsonMie.addProperty("note", m.note);
                JsonArray toppingArray = new JsonArray();
                if (m.toppings != null) {
                    for (Topping t : m.toppings) {
                        JsonObject jsonTopping = new JsonObject();
                        jsonTopping.addProperty("id", t.id);
                        jsonTopping.addProperty("quantity", t.quantity);
                        jsonTopping.addProperty("price", t.price);
                        if (t.type != null) {
                            jsonTopping.addProperty("type", t.type);
                        }
                        toppingArray.add(jsonTopping);
                    }
                }
                jsonMie.add("toppings", toppingArray);
                mieArray.add(jsonMie);
            }
        }
        orderObject.add("mies", mieArray);
//        Log.v("BIGDICK", mieArray.toString());

        JsonArray drinkArray = new JsonArray();
        if (src.drinks != null) {
            for (Drink d : src.drinks) {
                JsonObject jsonDrink = new JsonObject();
                jsonDrink.addProperty("id", d.id);
                jsonDrink.addProperty("quantity", d.quantity);
                jsonDrink.addProperty("price", d.price);
                drinkArray.add(jsonDrink);
            }
        }
        orderObject.add("drinks", drinkArray);
        Log.v("BIGDICK", orderKey.toString());
        return orderKey;
    }
}

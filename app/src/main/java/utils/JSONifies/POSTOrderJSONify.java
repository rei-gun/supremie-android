package utils.JSONifies;

import android.util.Log;

import com.google.gson.JsonArray;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

import java.lang.reflect.Type;
import java.text.SimpleDateFormat;
import java.util.TimeZone;

import model.Drink;
import model.Mie;
import model.Order;
import model.Topping;
import utils.responses.POSTResponseOrder;

/**
 * Serializes and deserializes {@link Order}s.
 */
public class POSTOrderJSONify implements JsonSerializer<Order>, JsonDeserializer<POSTResponseOrder> {

    private final SimpleDateFormat dateFormat;

    public POSTOrderJSONify(String datePattern) {
        this.dateFormat = new SimpleDateFormat(datePattern);
        this.dateFormat.setTimeZone(TimeZone.getTimeZone("UTC"));
    }

    @Override
    public JsonElement serialize(Order src, Type srcType, JsonSerializationContext context) {
        JsonObject orderKey = new JsonObject();
        JsonObject orderObject = new JsonObject();
        orderKey.add("order", orderObject);
        orderObject.addProperty("total_price", src.totalPrice);
        orderObject.addProperty("payment_method", src.paymentMethod);
        orderObject.addProperty("dining_method", src.diningMethod);

        JsonArray mieArray = new JsonArray();
        for (Mie m : src.mies) {
            JsonObject jsonMie = new JsonObject();
            jsonMie.addProperty("id", m.id);
            jsonMie.addProperty("quantity_mie", m.quantityMie);
            jsonMie.addProperty("quantity_whole", m.quantityWhole);
            jsonMie.addProperty("price", m.price);
            jsonMie.addProperty("extra_chili", m.extraChili);
            jsonMie.addProperty("note", m.note);
            JsonArray toppingArray = new JsonArray();
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
            jsonMie.add("toppings", toppingArray);
            mieArray.add(jsonMie);
        }
        orderObject.add("mies", mieArray);

        JsonArray drinkArray = new JsonArray();
        for (Drink d : src.drinks) {
            JsonObject jsonDrink = new JsonObject();
            jsonDrink.addProperty("id", d.id);
            jsonDrink.addProperty("quantity", d.quantity);
            jsonDrink.addProperty("price", d.price);
            drinkArray.add(jsonDrink);

        }
        orderObject.add("drinks", drinkArray);

        Log.v("JSONCUNT", orderKey.toString());
        return orderKey;
    }

    @Override
    public POSTResponseOrder deserialize(JsonElement json, Type type, JsonDeserializationContext context) throws JsonParseException {
        JsonObject j = (JsonObject)json;
        Integer id = j.getAsJsonPrimitive("id").getAsInt();
        Integer statusCode = j.getAsJsonPrimitive("status_code").getAsInt();
        String message = j.getAsJsonPrimitive("message").getAsString();
        return new POSTResponseOrder(id, statusCode, message);
    }

}

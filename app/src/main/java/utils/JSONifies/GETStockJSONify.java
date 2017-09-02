package utils.JSONifies;

import com.google.gson.JsonArray;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;

import java.lang.reflect.Type;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.TimeZone;

import domain.DrinkStock;
import domain.MieStock;
import domain.ToppingStock;
import utils.responses.GETResponseStock;

/**
 * Deserializes GET stock.
 */
public class GETStockJSONify implements JsonDeserializer<GETResponseStock> {

    private final SimpleDateFormat dateFormat;

    public GETStockJSONify(String datePattern) {
        this.dateFormat = new SimpleDateFormat(datePattern);
        this.dateFormat.setTimeZone(TimeZone.getTimeZone("UTC"));
    }

    @Override
    public GETResponseStock deserialize(JsonElement json, Type type, JsonDeserializationContext context) throws JsonParseException {
        JsonObject j = (JsonObject)json;

        JsonArray toppings = j.getAsJsonArray("toppings");
        ArrayList toppingStock = new ArrayList<>();
        for (JsonElement jsonTopping : toppings) {
            JsonObject jObject = jsonTopping.getAsJsonObject();
            Integer id = jObject.getAsJsonPrimitive("id").getAsInt();
            String name = jObject.getAsJsonPrimitive("name").getAsString();
            Integer stock = jObject.getAsJsonPrimitive("stock").getAsInt();
            Integer price = jObject.getAsJsonPrimitive("price").getAsInt();
            String imgUrl = jObject.getAsJsonPrimitive("img_url").getAsString();
            toppingStock.add(new ToppingStock(id, stock, name, price, imgUrl));
        }

        JsonArray drinks = j.getAsJsonArray("drinks");
        ArrayList drinkStock = new ArrayList<>();
        for (JsonElement jsonDrink : drinks) {
            JsonObject jObject = jsonDrink.getAsJsonObject();
            Integer id = jObject.getAsJsonPrimitive("id").getAsInt();
            String brand = jObject.getAsJsonPrimitive("brand").getAsString();
            String flavour = jObject.getAsJsonPrimitive("flavour").getAsString();
            Integer stock = jObject.getAsJsonPrimitive("stock").getAsInt();
            Integer price = jObject.getAsJsonPrimitive("price").getAsInt();
            String imgUrl = jObject.getAsJsonPrimitive("img_url").getAsString();
            drinkStock.add(new DrinkStock(id, brand, flavour, stock, price, imgUrl));
        }

        JsonArray mies = j.getAsJsonArray("mie");
        ArrayList mieStock = new ArrayList<>();
        for (int i=0; i<mies.size(); i++) {

            JsonObject jsonMie = (JsonObject) mies.get(i);
            JsonObject jObject = jsonMie.getAsJsonObject();
            Integer id = jObject.getAsJsonPrimitive("id").getAsInt();
            String brand = jObject.getAsJsonPrimitive("brand").getAsString();
            String flavour = jObject.getAsJsonPrimitive("flavour").getAsString();
            Integer stock = jObject.getAsJsonPrimitive("stock").getAsInt();
            Integer price = jObject.getAsJsonPrimitive("price").getAsInt();
            String imgUrl = jObject.getAsJsonPrimitive("img_url").getAsString();
            mieStock.add(new MieStock(id, brand, flavour, stock, price, imgUrl));

        }

        return new GETResponseStock(mieStock, toppingStock, drinkStock);
    }

}

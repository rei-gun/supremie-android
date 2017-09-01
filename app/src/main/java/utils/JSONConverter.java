package utils;

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

import domain.Drink;
import domain.Mie;
import domain.Order;
import domain.Topping;
import utils.responses.PostResponse;

/**
 * Serializes and deserializes {@link Order}s.
 */
public class JSONConverter implements JsonSerializer<Order>, JsonDeserializer<PostResponse> {

    private final SimpleDateFormat dateFormat;

    public JSONConverter(String datePattern) {
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
    public PostResponse deserialize(JsonElement json, Type type, JsonDeserializationContext context) throws JsonParseException {
        JsonObject j = (JsonObject)json;
        Integer id = j.getAsJsonPrimitive("id").getAsInt();
        Integer statusCode = j.getAsJsonPrimitive("status_code").getAsInt();
        String message = j.getAsJsonPrimitive("message").getAsString();
        return new PostResponse(id, statusCode, message);
        /*
        JsonObject j = (JsonObject)json;

        String _id = j.getAsJsonPrimitive("_id").getAsString();
        String _rev = j.getAsJsonPrimitive("_rev").getAsString();
        String owner = j.getAsJsonPrimitive("owner").getAsString();
        String creator = j.getAsJsonPrimitive("creator").getAsString();
        String ptype = j.getAsJsonPrimitive("type").getAsString();
        String roomNumber = j.getAsJsonPrimitive("room_number").getAsString();
        String guestId = j.getAsJsonPrimitive("guest_id").getAsString();
        String state = j.getAsJsonPrimitive("state").getAsString();
        String assignee = j.getAsJsonPrimitive("assignee").getAsString();
        Integer eta = j.getAsJsonPrimitive("eta").getAsInt();
        String countryCode = "GB";
        try {
            countryCode = j.getAsJsonPrimitive("country_code").getAsString();
        } catch (ClassCastException | NullPointerException e) {
        }
        Date created;
        try {
            created = dateFormat.parse(j.getAsJsonPrimitive("created").getAsString());
        } catch (ParseException e) {
            throw new JsonParseException(e);
        }
        Date updated = null; // Empty updated time is acceptable.
        try {
            updated = dateFormat.parse(j.getAsJsonPrimitive("updated").getAsString());
        } catch (ParseException | ClassCastException | NullPointerException e) {
        }
        JsonObject c = j.getAsJsonObject("content");
        String message = c.getAsJsonPrimitive("message").getAsString();
        Content content;
        switch (ptype) {
            case Permintaan.TYPE_BELLBOY:
                content = new Bellboy(message);
                break;
            case Permintaan.TYPE_CHECKOUT:
                content = new Checkout(message);
                break;
            case Permintaan.TYPE_ENGINEERING:
                String idEngineering = c.getAsJsonPrimitive("_id").getAsString();
                String revEngineering = c.getAsJsonPrimitive("_rev").getAsString();
                String nameEnEngineering = c.getAsJsonPrimitive("name_en").getAsString();
                String nameInEngineering = c.getAsJsonPrimitive("name_in").getAsString();
                String nameZhEngineering = c.getAsJsonPrimitive("name_zh").getAsString();
                String nameRuEngineering = c.getAsJsonPrimitive("name_ru").getAsString();
                EngineeringOption optionEngineering = new EngineeringOption(idEngineering, revEngineering,
                        nameEnEngineering, nameInEngineering, nameZhEngineering, nameRuEngineering, null, null);
                content = new Engineering(message, optionEngineering);
                break;
            case Permintaan.TYPE_HOUSEKEEPING:
                String idHousekeeping = c.getAsJsonPrimitive("_id").getAsString();
                String revHousekeeping = c.getAsJsonPrimitive("_rev").getAsString();
                String nameEnHousekeeping = c.getAsJsonPrimitive("name_en").getAsString();
                String nameInHousekeeping = c.getAsJsonPrimitive("name_in").getAsString();
                String nameZhHousekeeping = c.getAsJsonPrimitive("name_zh").getAsString();
                String nameRuHousekeeping = c.getAsJsonPrimitive("name_ru").getAsString();
                String sectionEn = c.getAsJsonPrimitive("section_en").getAsString();
                String sectionIn = c.getAsJsonPrimitive("section_in").getAsString();
                String sectionZh = c.getAsJsonPrimitive("section_zh").getAsString();
                String sectionRu = c.getAsJsonPrimitive("section_ru").getAsString();
                Integer max = c.getAsJsonPrimitive("max").getAsInt();
                Integer quantityHousekeeping = c.getAsJsonPrimitive("quantity").getAsInt();
                HousekeepingOption optionHousekeeping = new HousekeepingOption(idHousekeeping, revHousekeeping,
                        nameEnHousekeeping, nameInHousekeeping, nameZhHousekeeping, nameRuHousekeeping,
                        null, sectionEn, sectionIn, sectionZh, sectionRu, null, max);
                content = new Housekeeping(message, quantityHousekeeping, optionHousekeeping);
                break;
            case Permintaan.TYPE_MASSAGE:
            case "MASSAGE": // Backwards compatibility with previous type string
                String idMassage = c.getAsJsonPrimitive("_id").getAsString();
                String revMassage = c.getAsJsonPrimitive("_rev").getAsString();
                String nameEnMassage = c.getAsJsonPrimitive("name_en").getAsString();
                String nameInMassage = c.getAsJsonPrimitive("name_in").getAsString();
                String nameZhMassage = c.getAsJsonPrimitive("name_zh").getAsString();
                String nameRuMassage = c.getAsJsonPrimitive("name_ru").getAsString();
                String descriptionEn = c.getAsJsonPrimitive("description_en").getAsString();
                String descriptionIn = c.getAsJsonPrimitive("description_in").getAsString();
                String descriptionZh = c.getAsJsonPrimitive("description_zh").getAsString();
                String descriptionRu = c.getAsJsonPrimitive("description_ru").getAsString();
                Integer priceMassage = c.getAsJsonPrimitive("price").getAsInt();
                Integer length = c.getAsJsonPrimitive("length").getAsInt();
                MassageOption optionMassage = new MassageOption(idMassage, revMassage, nameEnMassage,
                        nameInMassage, nameZhMassage, nameRuMassage, descriptionEn, descriptionIn,
                        descriptionZh, descriptionRu, null, priceMassage, length, null);
                content = new Massage(message, optionMassage);
                break;
            case Permintaan.TYPE_TRANSPORT:
                Integer passengers = c.getAsJsonPrimitive("passengers").getAsInt();
                String departingIn = c.getAsJsonPrimitive("departing_in").getAsString();
                String destination = c.getAsJsonPrimitive("destination").getAsString();
                content = new Transport(message, passengers, departingIn, destination);
                break;
            case Permintaan.TYPE_RESTAURANT:
                List<OrderItem> restaurantItems = new ArrayList<>();
                for (int i = 0; i < c.getAsJsonArray("items").size(); i++) {
                    JsonObject item = (JsonObject)c.getAsJsonArray("items").get(i);
                    Integer quantityRestaurant = item.getAsJsonPrimitive("quantity").getAsInt();
                    Integer priceRestaurant = item.getAsJsonPrimitive("price").getAsInt();
                    String name = item.getAsJsonPrimitive("name").getAsString();
                    String note = item.getAsJsonPrimitive("note").getAsString();
                    restaurantItems.add(new OrderItem(quantityRestaurant, name, priceRestaurant, note));
                }
                Integer totalRestaurantPrice = c.getAsJsonPrimitive("total_price").getAsInt();
                content = new RestaurantOrder(message, restaurantItems, totalRestaurantPrice);
                break;
            case Permintaan.TYPE_LAUNDRY:
                content = new LaundryOrder(message);
                break;
            default:
                throw new JsonParseException("Unknown Permintaan content type.");
        }

        return new Permintaan(_id, _rev, owner, creator, ptype, roomNumber, guestId, state, created,
                updated, assignee, eta, countryCode, content);
    */
    }

}

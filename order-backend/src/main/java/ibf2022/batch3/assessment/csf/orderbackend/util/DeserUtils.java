package ibf2022.batch3.assessment.csf.orderbackend.util;

import ibf2022.batch3.assessment.csf.orderbackend.models.PizzaOrder;
import jakarta.json.Json;
import jakarta.json.JsonObject;
import jakarta.json.JsonObjectBuilder;

public class DeserUtils {
    public static JsonObject formatPizzaOrder(PizzaOrder order){
        JsonObjectBuilder builder = Json.createObjectBuilder();
        builder.add("orderId", order.getOrderId());
        builder.add("date", order.getDate().toString());
        builder.add("total", order.getTotal());
        builder.add("name", order.getName());
        builder.add("email", order.getEmail());
        return builder.build();
    }
    
    public static JsonObject formatError(String error) {
        JsonObjectBuilder builder = Json.createObjectBuilder();
        builder.add("error", error);
        return builder.build();
    }
}

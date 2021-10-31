package cz.speedy.musicbot.config.serializers;

import com.google.gson.*;
import net.dv8tion.jda.api.entities.Activity;

import java.lang.reflect.Type;

public class ActivitySerializer implements JsonSerializer<Activity>, JsonDeserializer<Activity> {

    @Override
    public JsonElement serialize(Activity src, Type typeOfSrc, JsonSerializationContext context) {
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("type", src.getType().getKey());
        jsonObject.addProperty("text", src.getName());
        return jsonObject;
    }

    @Override
    public Activity deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        JsonObject jsonObject = json.getAsJsonObject();
        return Activity.of(Activity.ActivityType.fromKey(jsonObject.get("type").getAsInt()), jsonObject.get("text").getAsString());
    }

}

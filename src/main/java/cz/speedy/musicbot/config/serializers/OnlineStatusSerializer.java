package cz.speedy.musicbot.config.serializers;

import com.google.gson.*;
import net.dv8tion.jda.api.OnlineStatus;

import java.lang.reflect.Type;

public class OnlineStatusSerializer implements JsonSerializer<OnlineStatus>, JsonDeserializer<OnlineStatus> {

    @Override
    public JsonElement serialize(OnlineStatus src, Type typeOfSrc, JsonSerializationContext context) {
        return new JsonPrimitive(src.getKey());
    }

    @Override
    public OnlineStatus deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        return OnlineStatus.fromKey(json.getAsJsonPrimitive().getAsString());
    }
}

package cz.speedy.musicbot.config;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.annotations.SerializedName;
import cz.speedy.musicbot.Main;
import cz.speedy.musicbot.config.serializers.ActivitySerializer;
import cz.speedy.musicbot.config.serializers.OnlineStatusSerializer;
import net.dv8tion.jda.api.OnlineStatus;
import net.dv8tion.jda.api.entities.Activity;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import java.util.Locale;

public class Config {

    public static final String CONFIG_FILE_NAME = "config.json";
    private static final Logger LOGGER = LoggerFactory.getLogger(Config.class);
    private static final Gson GSON = new GsonBuilder()
            .registerTypeAdapter(Activity.class, new ActivitySerializer())
            .registerTypeAdapter(OnlineStatus.class, new OnlineStatusSerializer())
            .setPrettyPrinting().create();

    @SerializedName("language")
    private Locale language = new Locale("en", "US");

    @SerializedName("bots")
    private List<Bot> bots = List.of(new Bot());

    public Locale getLanguage() {
        return language;
    }

    public List<Bot> getBots() {
        return bots;
    }

    public static Config loadConfig(@NotNull File file) throws IOException {
        Config config;
        if(!file.exists()) {
            config = new Config();
            if(file.createNewFile()) {
                LOGGER.info("Creating new configuration file \"{}\"", file.getAbsolutePath());
                FileWriter fileWriter = new FileWriter(file);
                GSON.toJson(config, fileWriter);
                fileWriter.flush();
                fileWriter.close();
            }
        } else {
            LOGGER.info("Loading configuration file \"{}\"", file.getAbsolutePath());
            FileReader fileReader = new FileReader(file);
            config = GSON.fromJson(fileReader, Config.class);
            fileReader.close();
        }
        return config;
    }

    public static class Bot {

        @SerializedName("token")
        private String token = "token";

        @SerializedName("activity")
        private Activity activity = Activity.listening("music");

        @SerializedName("status")
        private OnlineStatus status = OnlineStatus.ONLINE;

        @SerializedName("prefix")
        private String prefix = "!";

        public String getToken() {
            return token;
        }

        public Activity getActivity() {
            return activity;
        }

        public OnlineStatus getStatus() {
            return status;
        }

        public String getPrefix() {
            return prefix;
        }
    }
}

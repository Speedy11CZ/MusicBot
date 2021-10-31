package cz.speedy.musicbot;

import cz.speedy.musicbot.commands.CommandManager;
import cz.speedy.musicbot.config.Config;
import cz.speedy.musicbot.language.LanguageManager;
import cz.speedy.musicbot.listeners.CommandListener;
import cz.speedy.musicbot.music.MusicManager;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.requests.GatewayIntent;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.security.auth.login.LoginException;

public class MusicBot {

    private static final Logger LOGGER = LoggerFactory.getLogger(MusicBot.class);

    private final Config.Bot config;
    private final LanguageManager languageManager;
    private final CommandManager commandManager;
    private final MusicManager musicManager;
    private JDA jda;

    public MusicBot(Config.@NotNull Bot config, LanguageManager languageManager, CommandManager commandManager) {
        this.config = config;
        this.languageManager = languageManager;
        this.commandManager = commandManager;
        this.musicManager = new MusicManager(this);
        try {
            this.jda = JDABuilder.create(GatewayIntent.GUILD_VOICE_STATES, GatewayIntent.GUILD_MESSAGES)
                    .setToken(config.getToken())
                    .setActivity(config.getActivity())
                    .setStatus(config.getStatus())
                    .addEventListeners(new CommandListener(this))
                    .build().awaitReady();
        } catch (InterruptedException | LoginException e) {
            e.printStackTrace();
        }

        LOGGER.info("You can invite your music bot {} using url https://discord.com/oauth2/authorize?client_id={}&scope=bot%20applications.commands&permissions=305196096", jda.getSelfUser().getAsTag(), jda.getSelfUser().getId());
    }

    public Config.Bot getConfig() {
        return config;
    }

    public LanguageManager getLanguageManager() {
        return languageManager;
    }

    public CommandManager getCommandManager() {
        return commandManager;
    }

    public MusicManager getMusicManager() {
        return musicManager;
    }

    public JDA getJda() {
        return jda;
    }
}

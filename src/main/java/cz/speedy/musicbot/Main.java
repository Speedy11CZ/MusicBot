package cz.speedy.musicbot;

import cz.speedy.musicbot.commands.CommandManager;
import cz.speedy.musicbot.commands.user.JoinCommand;
import cz.speedy.musicbot.commands.user.PlayCommand;
import cz.speedy.musicbot.config.Config;
import cz.speedy.musicbot.language.LanguageManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;

public class Main {

    private static final Logger LOGGER = LoggerFactory.getLogger(Main.class);

    public static void main(String[] args) {
        LOGGER.info("Loading MusicBot v0.0.1");
        try {
            Config config = Config.loadConfig(new File(Config.CONFIG_FILE_NAME));
            LanguageManager languageManager = new LanguageManager(config.getLanguage());
            CommandManager commandManager = new CommandManager()
                    .registerCommand(new JoinCommand())
                    .registerCommand(new PlayCommand());
            for (Config.Bot bot : config.getBots()) {
                new MusicBot(bot, languageManager, commandManager);
            }
        } catch (IOException exception) {
            throw new RuntimeException("An error has occurred while creating config file", exception);
        }
    }
}

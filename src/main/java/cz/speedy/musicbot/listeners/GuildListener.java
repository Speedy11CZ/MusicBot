package cz.speedy.musicbot.listeners;

import cz.speedy.musicbot.MusicBot;
import net.dv8tion.jda.api.events.guild.GuildJoinEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.jetbrains.annotations.NotNull;

public class GuildListener extends ListenerAdapter {

    private final MusicBot musicBot;

    public GuildListener(MusicBot musicBot) {
        this.musicBot = musicBot;
    }

    @Override
    public void onGuildJoin(@NotNull GuildJoinEvent event) {

    }
}

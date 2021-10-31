package cz.speedy.musicbot.music;

import com.sedmelluq.discord.lavaplayer.player.AudioPlayerManager;
import com.sedmelluq.discord.lavaplayer.player.DefaultAudioPlayerManager;
import com.sedmelluq.discord.lavaplayer.source.AudioSourceManagers;
import cz.speedy.musicbot.MusicBot;
import net.dv8tion.jda.api.entities.Guild;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.Map;

public class MusicManager {

    private final MusicBot musicBot;
    private final AudioPlayerManager playerManager;
    private final Map<Long, GuildMusicManager> guildMusicManagers;

    public MusicManager(MusicBot musicBot) {
        this.musicBot = musicBot;
        this.playerManager = new DefaultAudioPlayerManager();
        this.guildMusicManagers = new HashMap<>();

        AudioSourceManagers.registerRemoteSources(playerManager);
        AudioSourceManagers.registerLocalSource(playerManager);
    }

    public MusicBot getMusicBot() {
        return musicBot;
    }

    public AudioPlayerManager getPlayerManager() {
        return playerManager;
    }

    public GuildMusicManager getGuildMusicManager(@NotNull Guild guild) {
        GuildMusicManager guildMusicManager = guildMusicManagers.get(guild.getIdLong());

        if(guildMusicManager == null) {
            guildMusicManager = new GuildMusicManager(this);
            guildMusicManagers.put(guild.getIdLong(), guildMusicManager);
        }

        guild.getAudioManager().setSendingHandler(guildMusicManager.getSendHandler());

        return guildMusicManager;
    }

    public Map<Long, GuildMusicManager> getGuildMusicManagers() {
        return guildMusicManagers;
    }
}

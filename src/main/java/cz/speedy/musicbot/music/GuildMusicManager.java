package cz.speedy.musicbot.music;

import com.sedmelluq.discord.lavaplayer.player.AudioLoadResultHandler;
import com.sedmelluq.discord.lavaplayer.player.AudioPlayer;
import com.sedmelluq.discord.lavaplayer.tools.FriendlyException;
import com.sedmelluq.discord.lavaplayer.track.AudioPlaylist;
import com.sedmelluq.discord.lavaplayer.track.AudioTrack;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.VoiceChannel;
import org.jetbrains.annotations.NotNull;

import java.util.Deque;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.function.Consumer;

public class GuildMusicManager {

    private final MusicManager musicManager;
    private final AudioPlayer audioPlayer;
    private final Deque<AudioTrack> queue;

    public GuildMusicManager(@NotNull MusicManager musicManager) {
        this.musicManager = musicManager;
        this.audioPlayer = musicManager.getPlayerManager().createPlayer();
        this.queue = new LinkedBlockingDeque<>();
    }

    public AudioPlayer getAudioPlayer() {
        return audioPlayer;
    }

    public AudioPlayerSendHandler getSendHandler() {
        return new AudioPlayerSendHandler(audioPlayer);
    }

    public void joinChannel(@NotNull VoiceChannel voiceChannel) {
        voiceChannel.getGuild().getAudioManager().openAudioConnection(voiceChannel);
    }

    public int joinChannel(@NotNull Member member) {
        if(member.getVoiceState().inVoiceChannel()) {
            joinChannel(member.getVoiceState().getChannel());
            return 1;
        }
        return -1;
    }

    public void playTrack(AudioTrack audioTrack) {
        audioPlayer.playTrack(audioTrack);
    }

    public void playTrack(String identifier, Consumer<Integer> callback) {
        musicManager.getPlayerManager().loadItemOrdered(musicManager, identifier, new AudioLoadResultHandler() {
            @Override
            public void trackLoaded(AudioTrack track) {
                System.out.println("D");
                audioPlayer.playTrack(track);
            }

            @Override
            public void playlistLoaded(AudioPlaylist playlist) {
                System.out.println("B");
            }

            @Override
            public void noMatches() {
                System.out.println("NO");
            }

            @Override
            public void loadFailed(FriendlyException exception) {
                exception.printStackTrace();
            }
        });
    }
}

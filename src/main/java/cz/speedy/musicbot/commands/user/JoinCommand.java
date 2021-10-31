package cz.speedy.musicbot.commands.user;

import cz.speedy.musicbot.MusicBot;
import cz.speedy.musicbot.commands.Command;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.*;
import net.dv8tion.jda.api.events.interaction.SlashCommandEvent;
import org.jetbrains.annotations.NotNull;

import java.awt.*;
import java.util.concurrent.TimeUnit;

public class JoinCommand extends Command {

    public JoinCommand() {
        super("join");
        this.aliases = new String[]{"summon"};

    }

    @Override
    public void onLegacyCommand(@NotNull MusicBot musicBot, @NotNull Guild guild, @NotNull Member member, @NotNull TextChannel textChannel, @NotNull Message message, @NotNull String[] args) {
        textChannel.sendMessageEmbeds(onCommand(musicBot, guild, member))
                .delay(10, TimeUnit.SECONDS)
                .flatMap(Message::delete)
                .queue();
    }

    @Override
    public void onSlashCommand(@NotNull MusicBot musicBot, @NotNull SlashCommandEvent event) {
        event.getHook().sendMessageEmbeds(onCommand(musicBot, event.getGuild(), event.getMember()))
                .delay(10, TimeUnit.SECONDS)
                .flatMap(Message::delete)
                .queue();
    }

    private @NotNull MessageEmbed onCommand(@NotNull MusicBot musicBot, Guild guild, Member member) {
        int statusCode = musicBot.getMusicManager().getGuildMusicManager(guild).joinChannel(member);
        if(statusCode == 1) {
            return new EmbedBuilder().setColor(Color.GREEN)
                            .setTitle(musicBot.getLanguageManager().getString("commands.join.success", member.getVoiceState().getChannel().getName()))
                            .build();
        } else if(statusCode == -1) {
            return new EmbedBuilder().setColor(Color.RED)
                    .setTitle(musicBot.getLanguageManager().getString("commands.join.error.no_channel"))
                    .build();
        }
        return new EmbedBuilder().setColor(Color.RED)
                .setTitle(musicBot.getLanguageManager().getString("errors.unknown_error"))
                .build();
    }
}

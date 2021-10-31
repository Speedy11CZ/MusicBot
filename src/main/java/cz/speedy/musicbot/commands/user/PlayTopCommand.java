package cz.speedy.musicbot.commands.user;

import cz.speedy.musicbot.MusicBot;
import cz.speedy.musicbot.commands.Command;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.events.interaction.SlashCommandEvent;

public class PlayTopCommand extends Command {

    public PlayTopCommand() {
        super("playtop");
        this.aliases = new String[]{"pt", "ptop"};
        this.usage = "<link / query>";
    }

    @Override
    public void onLegacyCommand(MusicBot musicBot, Guild guild, Member member, TextChannel textChannel, Message message, String[] args) {
        String identifier = message.getContentRaw().substring(message.getContentRaw().indexOf(" ") + 1);
        musicBot.getMusicManager().getGuildMusicManager(guild).playTrack(identifier, callback -> {
            System.out.println(callback);
        });
    }

    @Override
    public void onSlashCommand(MusicBot musicBot, SlashCommandEvent event) {

    }
}

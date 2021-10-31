package cz.speedy.musicbot.listeners;

import cz.speedy.musicbot.MusicBot;
import cz.speedy.musicbot.commands.Command;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.events.interaction.SlashCommandEvent;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.jetbrains.annotations.NotNull;

import java.awt.*;
import java.util.Arrays;
import java.util.Optional;
import java.util.regex.Pattern;

public class CommandListener extends ListenerAdapter {

    private final MusicBot musicBot;

    public CommandListener(MusicBot musicBot) {
        this.musicBot = musicBot;
    }

    @Override
    public void onGuildMessageReceived(@NotNull GuildMessageReceivedEvent event) {
        if(event.getAuthor().isBot() || event.getAuthor().isSystem() || event.getMember() == null) {
            return;
        }

        String message = event.getMessage().getContentRaw();
        if(message.startsWith(musicBot.getConfig().getPrefix())) {
            String[] split = message.replaceFirst("(?i)" + Pattern.quote(musicBot.getConfig().getPrefix()), "").split("\\s+");
            String commandName = split[0].toLowerCase();

            musicBot.getCommandManager().getCommandByName(commandName).ifPresent(command -> {
                try {
                    command.onLegacyCommand(musicBot, event.getGuild(), event.getMember(), event.getChannel(), event.getMessage(), Arrays.copyOfRange(split, 1, split.length));
                } catch (Exception exception) {
                    event.getChannel().sendMessageEmbeds(new EmbedBuilder().setColor(Color.RED).setTitle(musicBot.getLanguageManager().getString("command.error.unknown")).build()).queue();
                    exception.printStackTrace();
                }
            });
        }
    }

    @Override
    public void onSlashCommand(@NotNull SlashCommandEvent event) {
        musicBot.getCommandManager().getCommandByName(event.getName()).ifPresent(command -> {
            Command finalCommand = command;
            if (event.getSubcommandName() != null) {
                Optional<Command> subcommand = command.getSubcommand(event.getSubcommandName());
                if (subcommand.isEmpty()) {
                    return;
                }
                finalCommand = subcommand.get();
            }

            finalCommand.onSlashCommand(musicBot, event);
        });
    }
}

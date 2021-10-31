package cz.speedy.musicbot.commands;

import cz.speedy.musicbot.MusicBot;
import cz.speedy.musicbot.language.LanguageManager;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.events.interaction.SlashCommandEvent;
import net.dv8tion.jda.api.interactions.commands.build.CommandData;
import net.dv8tion.jda.api.interactions.commands.build.OptionData;
import net.dv8tion.jda.api.interactions.commands.build.SubcommandData;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public abstract class Command {

    protected String name;
    protected String[] aliases;
    protected String usage;
    protected List<Command> subcommands;
    protected List<OptionData> options;

    protected Command(String name) {
        this.name = name;
    }

    public void onLegacyCommand(MusicBot musicBot, Guild guild, Member member, TextChannel textChannel, Message message, String[] args) { }

    public void onSlashCommand(MusicBot musicBot, SlashCommandEvent event) { }

    public String getName() {
        return name;
    }

    public String[] getAliases() {
        return aliases;
    }

    public String getUsage() {
        return usage;
    }

    public List<Command> getSubcommands() {
        return subcommands;
    }

    public Optional<Command> getSubcommand(String name) {
        return subcommands.stream().filter(command -> command.name.equals(name)).findFirst();
    }

    public List<OptionData> getOptions() {
        return options;
    }

    public SubcommandData toSubcommandData(@NotNull LanguageManager languageManager) {
        return new SubcommandData(name, languageManager.getString("commands." + name + ".description")).addOptions(options);
    }

    public CommandData toCommandData(@NotNull LanguageManager languageManager) {
        String description = languageManager.getString("commands." + name + ".description");
        if (!subcommands.isEmpty()) {
            return new CommandData(name, description).addSubcommands(subcommands.stream().map(command -> command.toSubcommandData(languageManager)).collect(Collectors.toList()));
        } else {
            return new CommandData(name, description).addOptions(options);
        }
    }
}

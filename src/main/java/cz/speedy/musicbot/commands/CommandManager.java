package cz.speedy.musicbot.commands;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

public class CommandManager {

    private final Set<Command> commands;

    public CommandManager() {
        this.commands = new HashSet<>();
    }

    public CommandManager registerCommand(Command command) {
        try {
            commands.add(command);
        } catch (Exception exception) {
            exception.printStackTrace();;
        }
        return this;
    }

    public Set<Command> getCommands() {
        return commands;
    }

    public Optional<Command> getCommandByName(String name) {
        return commands.stream().filter(command -> command.getName().equals(name)).findFirst();
    }
}

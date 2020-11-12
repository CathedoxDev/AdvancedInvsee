package be.cathedox.AdvancedInventorySee.handlers;

import be.cathedox.AdvancedInventorySee.AdvancedInvsee;
import be.cathedox.AdvancedInventorySee.commands.CommandInventorySee;
import be.cathedox.AdvancedInventorySee.tabcompleter.TabCompleterInventorySee;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.TabCompleter;

public class CommandHandler {
    private AdvancedInvsee instance;
    private boolean registered = false;

    public CommandHandler(AdvancedInvsee instance) {
        this.instance = instance;
    }

    public void registerCommands() {
        try {
            registerCommand("inventorysee", new CommandInventorySee(instance), new TabCompleterInventorySee());

            registered = true;
        } catch(Exception e) {
            registered = false;
        }
    }

    private void registerCommand(String command, CommandExecutor executor, TabCompleter tabCompleter) {
        this.instance.getCommand(command).setExecutor(executor);
        this.instance.getCommand(command).setTabCompleter(tabCompleter);
    }

    public boolean isCommandsRegistered() {
        return registered;
    }
}

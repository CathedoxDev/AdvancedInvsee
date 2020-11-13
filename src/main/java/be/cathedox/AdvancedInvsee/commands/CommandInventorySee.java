package be.cathedox.AdvancedInvsee.commands;

import be.cathedox.AdvancedInvsee.AdvancedInvsee;
import be.cathedox.AdvancedInvsee.inventory.InventorySee;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;

public class CommandInventorySee implements CommandExecutor {
    private final AdvancedInvsee instance;

    public CommandInventorySee(AdvancedInvsee instance) {
        this.instance = instance;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String s, String[] args) {
        if(sender instanceof Player) {
            if(args.length > 0) {
                Player ptarget = Bukkit.getPlayer(args[0]);
                Player psender = (Player) sender;

                if(psender.hasPermission("advancedinvsee.*") || psender.hasPermission("advancedinvsee.see")) {
                    if(!(ptarget == null)) {
                        if(ptarget.hasPermission("advancedinvsee.*") || psender.hasPermission("advancedinvsee.warn")) {
                            ptarget.sendMessage(ChatColor.RED + psender.getName() + " has opened you inventory!");
                        }
                        InventorySee inventorySee = new InventorySee(instance, psender, ptarget);
                        inventorySee.createInventory();
                        return true;
                    } else {
                        psender.sendMessage(ChatColor.RED + "Could not found the player you're looking for...");
                        return false;
                    }
                }
            }
            return false;
        } else if(sender instanceof ConsoleCommandSender) {
            sender.sendMessage("You Cannot use this command as the console");
            return false;
        }

        return false;
    }
}

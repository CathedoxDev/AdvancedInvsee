package be.cathedox.AdvancedInventorySee.tabcompleter;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class TabCompleterInventorySee implements TabCompleter {
    @Override
    public List<String> onTabComplete(CommandSender commandSender, Command command, String s, String[] strings) {
        if(strings.length == 1) {
            List<String> toReturn = new ArrayList<>();
            for(Player player : Bukkit.getOnlinePlayers()) {
                toReturn.add(player.getName());
            }
            return toReturn;
        } else if(strings.length == 0) {
            List<String> toReturn = new ArrayList<>();
            toReturn.add("inventorysee");
            toReturn.add("invsee");
            toReturn.add("is");
            return toReturn;
        }
        return null;
    }
}

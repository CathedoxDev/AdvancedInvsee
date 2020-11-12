package be.cathedox.AdvancedInventorySee;

import be.cathedox.AdvancedInventorySee.handlers.PluginHandler;
import org.bukkit.plugin.java.JavaPlugin;

public class AdvancedInvsee extends JavaPlugin {
    private static PluginHandler pluginHandler;

    @Override
    public void onEnable() {
        pluginHandler = new PluginHandler(this);
        pluginHandler.onEnable();
    }

    @Override
    public void onDisable() {
        if(pluginHandler != null) {
            pluginHandler.onDisable();
        } else {
            pluginHandler = new PluginHandler(this);
            pluginHandler.onDisable();
        }
    }
}

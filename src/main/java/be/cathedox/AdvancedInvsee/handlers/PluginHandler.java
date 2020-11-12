package be.cathedox.AdvancedInvsee.handlers;

import be.cathedox.AdvancedInvsee.AdvancedInvsee;
import be.cathedox.AdvancedInvsee.utils.Utils;
import be.cathedox.AdvancedInvsee.utils.api.Metrics;
import org.bukkit.Bukkit;
import org.bukkit.plugin.PluginLoader;

import java.util.logging.Level;

public class PluginHandler {
    private boolean loaded = false;

    private final AdvancedInvsee instance;
    private CommandHandler commandHandler;
    private final Utils utils;

    private final int BSTATS_ID = 9376;

    public PluginHandler(AdvancedInvsee instance) {
        this.instance = instance;
        this.utils = new Utils();
    }

    public void onEnable() {
        long start = System.currentTimeMillis();

        utils.spigotNotFound(instance);

        PluginLoader pl = instance.getPluginLoader();

        instance.getLogger().log(Level.INFO, "1. Enabling Plugin Metrics.");
        Metrics metrics = new Metrics(instance, BSTATS_ID);
        if(metrics.isEnabled()) {
            instance.getLogger().log(Level.INFO, ">> Plugin Metrics successfully enabled!");
        } else {
            instance.getLogger().log(Level.SEVERE, "================= FATAL ERROR =================");
            instance.getLogger().log(Level.SEVERE, "An error occured when enabling");
            instance.getLogger().log(Level.SEVERE, "AdvancedInventorySee.Metrics! This plugin need");
            instance.getLogger().log(Level.SEVERE, "this function. Please enable it at ");
            instance.getLogger().log(Level.SEVERE, "plugins/PluginMetrics/config.yml");
            instance.getLogger().log(Level.SEVERE, "================= FATAL ERROR =================");
            instance.getLogger().log(Level.SEVERE, ">> AdvancedInventorySee will now be disabled.");
            pl.disablePlugin(instance);
        }

        instance.getLogger().info("2. Registering Commands.");
        commandHandler = new CommandHandler(instance);
        commandHandler.registerCommands();
        if(commandHandler.isCommandsRegistered()) {
            instance.getLogger().info(">> Commands successfully registered!");
        } else {
            instance.getLogger().severe(">> An ERROR occurred while registering the commands, if this issue persist please contact the developper");
        }

        instance.getLogger().info(">> Successfully loaded in "+(System.currentTimeMillis()-start) + "ms");
        loaded = true;
    }

    public void onDisable() {
        Bukkit.getScheduler().cancelTasks(instance);
    }
}

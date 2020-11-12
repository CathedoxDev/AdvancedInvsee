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

    public PluginHandler(AdvancedInvsee instance) {
        this.instance = instance;
        this.utils = new Utils();
    }

    public void onEnable() {
        long start = System.currentTimeMillis();

        utils.spigotNotFound(instance);

        PluginLoader pl = instance.getPluginLoader();

        instance.getLogger().log(Level.INFO, "1. Enabling BSTATS.");
        Metrics metrics = new Metrics(instance, 9376);

        if(metrics.isEnabled()) {
            instance.getLogger().log(Level.INFO, ">> BSTATS successfully enabled!");
        } else {
            instance.getLogger().log(Level.INFO, ">> An ERROR occured while enabling the stats.");
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

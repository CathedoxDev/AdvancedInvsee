package be.cathedox.AdvancedInvsee.handlers;

import be.cathedox.AdvancedInvsee.AdvancedInvsee;
import be.cathedox.AdvancedInvsee.utils.Utils;
import be.cathedox.AdvancedInvsee.utils.api.Metrics;
import be.cathedox.AdvancedInvsee.utils.api.json.JSONException;
import be.cathedox.AdvancedInvsee.utils.api.json.JSONObject;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Color;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.plugin.PluginLoader;
import org.bukkit.plugin.PluginManager;

import java.io.*;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.logging.Level;

public class PluginHandler implements Listener {
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

        //check version
        try {
            JSONObject json = readJsonFromUrl("https://raw.githubusercontent.com/CathedoxDev/AdvancedInvsee/main/VERSION.json");
            String latest_version = json.get("version").toString();
            if(!(instance.getDescription().getVersion().equals(latest_version))) {
                instance.getLogger().warning("===============================================================");
                instance.getLogger().warning("YOUR VERSION OF AdvancedInvsee IS OUTDATED");
                instance.getLogger().warning("LATEST VERSION: "+latest_version);
                instance.getLogger().warning("YOUR VERSION: "+ instance.getDescription().getVersion());
                instance.getLogger().warning("PLEASE UPDATE BY DOWNLOADING LASTEST VERSION AT");
                instance.getLogger().warning("https://www.spigotmc.org/resources/advancedinvsee.85702/history");
                instance.getLogger().warning("===============================================================");

            }
        } catch (IOException e) {
            return;
        }
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

        PluginManager pm = Bukkit.getPluginManager();
        pm.registerEvents(this, instance);

        instance.getLogger().info(">> Successfully loaded in "+(System.currentTimeMillis()-start) + "ms");
        loaded = true;
    }

    public void onDisable() {
        Bukkit.getScheduler().cancelTasks(instance);
    }

    private static String readAll(Reader rd) throws IOException {
        StringBuilder sb = new StringBuilder();
        int cp;
        while ((cp = rd.read()) != -1) {
            sb.append((char) cp);
        }
        return sb.toString();
    }

    public static JSONObject readJsonFromUrl(String url) throws IOException, JSONException {
        InputStream is = new URL(url).openStream();
        try {
            BufferedReader rd = new BufferedReader(new InputStreamReader(is, Charset.forName("UTF-8")));
            String jsonText = readAll(rd);
            JSONObject json = new JSONObject(jsonText);
            return json;
        } finally {
            is.close();
        }
    }

    @EventHandler
    public void onPlayerJoinEvent(PlayerJoinEvent event) {
        if(event.getPlayer().isOp()) {
            Player p = event.getPlayer();
            try {
                JSONObject json = readJsonFromUrl("https://raw.githubusercontent.com/CathedoxDev/AdvancedInvsee/main/VERSION.json");
                String latest_version = json.get("version").toString();
                if(!(instance.getDescription().getVersion().equals(latest_version))) {
                    p.sendMessage(ChatColor.RED + "=====================================================");
                    p.sendMessage(ChatColor.RED + "Your version of AdvancedInvsee is outdated !");
                    p.sendMessage(ChatColor.RED + "Please Update ! Download latest version at");
                    p.sendMessage(ChatColor.RED + "https://www.spigotmc.org/resources/advancedinvsee.85702/history");
                    p.sendMessage(ChatColor.RED + "=====================================================");

                }
            } catch (IOException e) {
                return;
            }
        }
    }
}

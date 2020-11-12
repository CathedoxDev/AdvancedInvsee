package be.cathedox.AdvancedInvsee.utils;

import be.cathedox.AdvancedInvsee.AdvancedInvsee;
import org.bukkit.plugin.PluginDescriptionFile;

import java.util.logging.Level;
import java.util.logging.Logger;

public class Utils {
    private AdvancedInvsee instance;

    private Logger getLogger(){
        return instance.getLogger();
    }

    private PluginDescriptionFile desc(){
        return instance.getDescription();
    }

    public void banner(AdvancedInvsee instance){
        this.instance = instance;
        getLogger().log(Level.INFO, "==============================");
        getLogger().log(Level.INFO, "AdvancedInventorySee");
        getLogger().log(Level.INFO, "Version: " + desc().getVersion());
        getLogger().log(Level.INFO, "Developped by " + desc().getAuthors());
        getLogger().log(Level.INFO, "==============================");
    }

    public void spigotNotFound(AdvancedInvsee instance){
        this.instance = instance;
        try {
            Class.forName("org.spigotmc.SpigotConfig");
        } catch(ClassNotFoundException e){
            getLogger().log(Level.WARNING, "=============== SPIGOT NOT DETECTED ===============");
            getLogger().log(Level.WARNING, "Some functionalities of LockInventory may be       ");
            getLogger().log(Level.WARNING, "glitched! You can download Spigot here:            ");
            getLogger().log(Level.WARNING, "https://www.spigotmc.org/wiki/spigot-installation/ ");
            getLogger().log(Level.WARNING, "=============== SPIGOT NOT DETECTED ===============");
        }
    }

    public int transliterate(int mode, int input) {
        if(mode == 1) {
            if(input>=0&&input<=8) {
                return 36+input;
            }
            return input;
        } else if(mode == 2) {
            if(input>=36&&input<=45) {
                return input-36;
            }
            return input;
        }
        return -1;
    }
}

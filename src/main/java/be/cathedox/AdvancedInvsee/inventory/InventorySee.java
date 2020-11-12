package be.cathedox.AdvancedInvsee.inventory;

import be.cathedox.AdvancedInvsee.AdvancedInvsee;
import be.cathedox.AdvancedInvsee.utils.Utils;
import org.bukkit.Bukkit;
import org.bukkit.DyeColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.PluginManager;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;


public class InventorySee implements Listener {
    private static Inventory inventory;
    private PlayerInventory targetInventory;

    private final AdvancedInvsee instance;
    private final Utils utils;

    private BukkitTask updateSenderInventory;

    private Player psender;
    private Player ptarget;

    private ItemStack helmetItem;
    private ItemStack chestplateItem;
    private ItemStack leggingsItem;
    private ItemStack bootsItem;
    private ItemStack filler;
    private ItemStack offHandItem;

    private ItemMeta helmetItemMeta;
    private ItemMeta chestplateItemMeta;
    private ItemMeta leggingsItemMeta;
    private ItemMeta bootsItemMeta;
    private ItemMeta fillerMeta;
    private ItemMeta offHandItemMeta;

    private BukkitTask updater;

    public InventorySee(AdvancedInvsee instance, Player psender, Player ptarget) {
        this.psender = psender;
        this.ptarget = ptarget;
        this.utils = new Utils();
        this.instance = instance;
    }

    public void createInventory() {
        targetInventory = ptarget.getInventory();
        inventory = Bukkit.createInventory(psender, 45, ptarget.getName() + "'s Inventory");
        createItems();
        setItems();

        psender.closeInventory();
        psender.openInventory(inventory);

        PluginManager pm = Bukkit.getPluginManager();
        pm.registerEvents(this, instance);

        updateSenderInventory = new BukkitRunnable() {
            @Override
            public void run() {
                createItems();
                setItems();
                psender.updateInventory();
            }
        }.runTaskTimer(instance, 0L, 17L);
    }

    private void createItems() {
        if(targetInventory.getHelmet() == null) {
            helmetItem = new ItemStack(Material.PURPLE_STAINED_GLASS_PANE);
            helmetItemMeta = helmetItem.getItemMeta();
            helmetItemMeta.setDisplayName("No Helmet");
            helmetItem.setItemMeta(helmetItemMeta);
        } else {
            helmetItem = targetInventory.getHelmet();
        }

        if(targetInventory.getChestplate() == null) {
            chestplateItem = new ItemStack(Material.PURPLE_STAINED_GLASS_PANE);
            chestplateItemMeta = chestplateItem.getItemMeta();
            chestplateItemMeta.setDisplayName("No Chestplate");
            chestplateItem.setItemMeta(chestplateItemMeta);
        } else {
            chestplateItem = targetInventory.getChestplate();
        }

        if(targetInventory.getLeggings() == null) {
            leggingsItem = new ItemStack(Material.PURPLE_STAINED_GLASS_PANE);
            leggingsItemMeta = leggingsItem.getItemMeta();
            leggingsItemMeta.setDisplayName("No Leggings");
            leggingsItem.setItemMeta(leggingsItemMeta);
        } else {
            leggingsItem = targetInventory.getLeggings();
        }

        if(targetInventory.getBoots() == null) {
            bootsItem = new ItemStack(Material.PURPLE_STAINED_GLASS_PANE);
            bootsItemMeta = bootsItem.getItemMeta();
            bootsItemMeta.setDisplayName("No Boots");
            bootsItem.setItemMeta(bootsItemMeta);
        } else {
            bootsItem = targetInventory.getBoots();
        }

        filler = new ItemStack(Material.BLACK_STAINED_GLASS_PANE);
        fillerMeta = filler.getItemMeta();
        fillerMeta.setDisplayName(" ");
        filler.setItemMeta(fillerMeta);

        if(targetInventory.getItemInOffHand().getType() == Material.AIR) {
            offHandItem = new ItemStack(Material.ORANGE_STAINED_GLASS_PANE);
            offHandItemMeta = offHandItem.getItemMeta();
            offHandItemMeta.setDisplayName("No Items in OffHand");
            offHandItem.setItemMeta(offHandItemMeta);
        } else {
            offHandItem = targetInventory.getItemInOffHand();
        }
    }

    private void setItems() {
        inventory.setItem(0, helmetItem);
        inventory.setItem(1, chestplateItem);
        inventory.setItem(2, leggingsItem);
        inventory.setItem(3, bootsItem);
        inventory.setItem(4, filler);
        inventory.setItem(5, filler);
        inventory.setItem(6, filler);
        inventory.setItem(7, offHandItem);
        inventory.setItem(8, filler);

        for(int i = 0; i<36; i++) {
            inventory.setItem(utils.transliterate(1, i), targetInventory.getItem(i));
        }
    }


    @EventHandler
    public void onInventoryClick(InventoryClickEvent event) {
        if(event.getWhoClicked().getName() == psender.getName() && psender.getOpenInventory().getTitle().equals(ptarget.getName() + "'s Inventory")) {
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void onInventoryClose(InventoryCloseEvent event) {
        if(event.getPlayer().getName() == psender.getName()) {
            updateSenderInventory.cancel();
        }
    }
}

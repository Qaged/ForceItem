package de.acktstudios.forceitem.Joker;


import de.acktstudios.forceitem.ForceItem.ForceItem;
import de.acktstudios.forceitem.Main;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;

public class JokerListener implements Listener {

    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent event) {

        if (event.getAction().toString().contains("RIGHT")) {
            ItemStack clickedItem = event.getItem();
            Player player = event.getPlayer();

            if (clickedItem != null && clickedItem.getType() == Material.BARRIER) {
                ItemMeta meta = clickedItem.getItemMeta();

                // Überprüfe, ob der benutzerdefinierte Teil im PersistentDataContainer vorhanden ist
                if (meta != null) {
                    NamespacedKey key = new NamespacedKey("de_acktstudios_forceitem", "joker");
                    Byte jokerValue = meta.getPersistentDataContainer().get(key, PersistentDataType.BYTE);

                    if (jokerValue != null && jokerValue == (byte) 1) {
                        player.getInventory().remove(clickedItem);
                        player.sendMessage("§cYou used a joker!");

                        ItemStack newItem = ForceItem.getRandomStack();

                        switch (player.getDisplayName()) {
                            case "SharpChart92853":
                                if (Main.aItemStats.items.contains(newItem.getItemMeta().getDisplayName())) {
                                    newItem = ForceItem.getRandomStack();
                                }

                                Main.aItemStats.addItem(newItem);

                                player.setPlayerListName(player.getDisplayName() + " [§6" + newItem.getItemMeta().getDisplayName() + "§f]");
                                player.sendMessage("§aNächstes Item: §6" + newItem.getItemMeta().getDisplayName());
                                break;
                            case "Gamerspike11":
                                if (Main.cItemStats.items.contains(newItem.getItemMeta().getDisplayName())) {
                                    newItem = ForceItem.getRandomStack();
                                }

                                Main.cItemStats.addItem(newItem);

                                player.setPlayerListName(player.getDisplayName() + " [§6" + newItem.getItemMeta().getDisplayName() + "§f]");
                                player.sendMessage("§aNächstes Item: §6" + newItem.getItemMeta().getDisplayName());
                                break;
                            default:
                                player.sendMessage("§cYou are not registered!");
                        }


                        event.setCancelled(true); // Verhindere, dass der Barrier-Block platziert wird
                    }
                }
            }
        }
    }


    @EventHandler
    public void onBlockPlace(BlockPlaceEvent event) {
        ItemStack itemInHand = event.getItemInHand();

        if (itemInHand != null && itemInHand.getType() == Material.BARRIER) {
            ItemMeta meta = itemInHand.getItemMeta();

            // Überprüfe, ob der benutzerdefinierte Teil im PersistentDataContainer vorhanden ist
            if (meta != null) {
                NamespacedKey key = new NamespacedKey("de_acktstudios_forceitem", "joker");
                Byte jokerValue = meta.getPersistentDataContainer().get(key, PersistentDataType.BYTE);

                if (jokerValue != null && jokerValue == (byte) 1) {
                    // Das Item ist ein Joker
                    event.getPlayer().sendMessage("§cYou used a Joker!");
                    // Hier kannst du zusätzlichen Code für den Joker hinzufügen
                    event.setCancelled(true); // Verhindere, dass der Barrier-Block platziert wird
                }
            }
        }
    }
}

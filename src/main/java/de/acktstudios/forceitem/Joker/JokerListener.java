package de.acktstudios.forceitem.Joker;

import de.acktstudios.forceitem.ForceItem.ForceItem;
import de.acktstudios.forceitem.ForceItem.ItemStats;
import de.acktstudios.forceitem.Main;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;

import java.util.HashSet;
import java.util.Set;

public class JokerListener implements Listener {

    private final Set<Player> jokerUsedPlayers = new HashSet<>();

    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent event) {
        if (Main.getInstance().getTimer().isRunning()) {
            if (event.getAction().toString().contains("RIGHT")) {
                Player player = event.getPlayer();

                // Überprüfen, ob der Spieler keinen Block ansieht (in der Luft klickt)
                if (player.getTargetBlockExact(5) == null) {
                    if (!ForceItem.isEnded()) {
                        ItemStack clickedItem = event.getItem();

                        if (clickedItem != null && clickedItem.getType() == Material.BARRIER) {
                            ItemMeta meta = clickedItem.getItemMeta();

                            // Überprüfe, ob der benutzerdefinierte Teil im PersistentDataContainer vorhanden ist
                            if (meta != null) {
                                NamespacedKey key = new NamespacedKey("de_acktstudios_forceitem", "joker");
                                Byte jokerValue = meta.getPersistentDataContainer().get(key, PersistentDataType.BYTE);

                                if (jokerValue != null && jokerValue == (byte) 1) {
                                    handleJokerInteraction(player);
                                }
                            }
                        }
                    }
                }
            }
        }
    }


    @EventHandler
    public void onBlockPlace(BlockPlaceEvent event) {
        ItemStack itemInHand = event.getItemInHand();

        if (Main.getInstance().getTimer().isRunning()) {
            if (itemInHand != null && itemInHand.getType() == Material.BARRIER) {
                ItemMeta meta = itemInHand.getItemMeta();
                event.setCancelled(true);

                if (meta != null) {
                    NamespacedKey key = new NamespacedKey("de_acktstudios_forceitem", "joker");
                    Byte jokerValue = meta.getPersistentDataContainer().get(key, PersistentDataType.BYTE);

                    if (jokerValue != null && jokerValue == (byte) 1) {
                        handleJokerInteraction(event.getPlayer());
                    }
                }
            }
        }
    }

    private void handleJokerInteraction(Player player) {
        ItemStack hand = player.getInventory().getItemInHand();
        hand.setAmount(hand.getAmount() - 1);
        player.getInventory().setItemInHand(hand);

        player.playSound(player.getLocation(), Sound.BLOCK_NOTE_BLOCK_BASS, 1.0f, 1.0f);

        String displayName = player.getDisplayName();

        switch (displayName) {
            case "SharpChart92853":
                handlePlayerCase(player, Main.aItemStats);
                break;
            case "Gamerspike11":
                handlePlayerCase(player, Main.cItemStats);
                break;
            case "TB_360":
                handlePlayerCase(player, Main.tItemStats);
                break;
            case "TastyHalumi":
                handlePlayerCase(player, Main.kItemStats);
                break;
            case "TJoseph1014":
                handlePlayerCase(player, Main.rItemStats);
                break;
            case "GoldApfel2975":
                handlePlayerCase(player, Main.jItemStats);
                break;
            default:
                player.sendMessage("§cYou are not registered!");
                break;
        }
    }

    private void handlePlayerCase(Player player, ItemStats itemStats) {
        ItemStack newItem;

        if (itemStats.useJoker()) {
            if (player.getInventory().firstEmpty() != -1) {
                player.getInventory().addItem(itemStats.getCurrentItem());
            } else {
                player.getWorld().dropItemNaturally(player.getLocation(), itemStats.getCurrentItem());
            }

            newItem = ForceItem.getRandomStack(itemStats);

            itemStats.addItem(newItem, false);

            player.setPlayerListName(player.getDisplayName() + " [§6" + newItem.getItemMeta().getDisplayName() + "§f]");
            player.sendMessage("§aNächstes Item: §6" + newItem.getItemMeta().getDisplayName());
        }
    }
}

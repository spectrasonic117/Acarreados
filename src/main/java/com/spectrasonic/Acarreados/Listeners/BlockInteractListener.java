package com.spectrasonic.Acarreados.Listeners;

import com.spectrasonic.Acarreados.Utils.SoundUtils;
import com.spectrasonic.Acarreados.Game.GameManager;
import com.spectrasonic.Acarreados.Utils.MessageUtils;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.Sound;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

public class BlockInteractListener implements Listener {

    private final GameManager gameManager;

    public BlockInteractListener(GameManager gameManager) {
        this.gameManager = gameManager;
    }

    @EventHandler
    public void onPlayerInteractBlock(PlayerInteractEvent event) {
        if (event.getClickedBlock() == null)
            return;
        Material type = event.getClickedBlock().getType();
        if (type != Material.YELLOW_CONCRETE && type != Material.WHITE_CONCRETE)
            return;

        Player player = event.getPlayer();
        if (!gameManager.getScoreRegion().contains(player.getLocation()))
            return;
        if (!hasCustomPaper(player))
            return;

        SoundUtils.playerSound(player, Sound.BLOCK_NOTE_BLOCK_PLING, 1, 2);
        MessageUtils.sendActionBar(player, "<green>Has puntuado!</green>");

        removeCustomPaper(player);
    }

    private boolean hasCustomPaper(Player player) {
        for (ItemStack item : player.getInventory().getContents()) {
            if (isCustomPaper(item)) {
                return true;
            }
        }
        return false;
    }

    private void removeCustomPaper(Player player) {
        for (ItemStack item : player.getInventory().getContents()) {
            if (isCustomPaper(item)) {
                player.getInventory().remove(item);
                break; // Remove only one item
            }
        }
    }

    private boolean isCustomPaper(ItemStack item) {
        return item != null && item.getType() == Material.PAPER &&
                item.hasItemMeta() && item.getItemMeta().hasCustomModelData() &&
                item.getItemMeta().getCustomModelData() == 1047;
    }
}
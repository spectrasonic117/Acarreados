package com.spectrasonic.Acarreados.Listeners;

import com.spectrasonic.Acarreados.Utils.SoundUtils;
import com.spectrasonic.Acarreados.Game.GameManager;
import com.spectrasonic.Acarreados.Utils.MessageUtils;
import com.spectrasonic.Acarreados.Utils.PointsManager;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.Sound;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

public class BlockInteractListener implements Listener {

    private final GameManager gameManager;
    private final PointsManager pointsManager;

    public BlockInteractListener(GameManager gameManager, PointsManager pointsManager) {
        this.gameManager = gameManager;
        this.pointsManager = pointsManager;
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

        int currentRound = gameManager.getCurrentRound();
        int pointsToAward = currentRound; 

        SoundUtils.playerSound(player, Sound.BLOCK_NOTE_BLOCK_PLING, 1, 2);
        MessageUtils.sendActionBar(player, "<green><bold>+" + pointsToAward + " Punto" + (pointsToAward > 1 ? "s" : ""));

        pointsManager.addPoints(player, pointsToAward);

        removeCustomPaper(player);
    }

    private boolean hasCustomPaper(Player player) {
        // Check main inventory
        for (ItemStack item : player.getInventory().getContents()) {
            if (isCustomPaper(item)) {
                return true;
            }
        }
        // Check offhand slot
        return isCustomPaper(player.getInventory().getItemInOffHand());
    }

    private void removeCustomPaper(Player player) {
        // Check main inventory
        for (ItemStack item : player.getInventory().getContents()) {
            if (isCustomPaper(item)) {
                player.getInventory().remove(item);
                break; // Remove only one item
            }
        }
        // Check offhand slot
        ItemStack offhand = player.getInventory().getItemInOffHand();
        if (isCustomPaper(offhand)) {
            player.getInventory().setItemInOffHand(null);
        }
    }

    private boolean isCustomPaper(ItemStack item) {
        return item != null && item.getType() == Material.PAPER &&
                item.hasItemMeta() && item.getItemMeta().hasCustomModelData() &&
                item.getItemMeta().getCustomModelData() == 1047;
    }
}
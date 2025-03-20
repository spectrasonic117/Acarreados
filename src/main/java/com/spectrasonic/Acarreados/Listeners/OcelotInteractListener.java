package com.spectrasonic.Acarreados.Listeners;

import com.spectrasonic.Acarreados.Utils.ItemBuilder;
import com.spectrasonic.Acarreados.Utils.MessageUtils;
import org.bukkit.Material;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Ocelot;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.inventory.ItemStack;

public class OcelotInteractListener implements Listener {

    @EventHandler
    public void onPlayerInteractEntity(PlayerInteractEntityEvent event) {
        Entity clicked = event.getRightClicked();
        if (!(clicked instanceof Ocelot))
            return;

        Player player = event.getPlayer();
        if (hasCustomPaper(player)) {
            MessageUtils.sendActionBar(player, "<red>No puedes llevar más de uno!</red>");
            return;
        }

        clicked.remove();
        ItemStack item = ItemBuilder.setMaterial("PAPER")
                .setCustomModelData(1047)
                .setName("<#5069B5><b>Pingúino</b>")
                .build();
        player.getInventory().addItem(item);
    }

    private boolean hasCustomPaper(Player player) {
        for (ItemStack item : player.getInventory().getContents()) {
            if (item != null && item.getType() == Material.PAPER &&
                    item.hasItemMeta() && item.getItemMeta().hasCustomModelData() &&
                    item.getItemMeta().getCustomModelData() == 1047) {
                return true;
            }
        }
        return false;
    }
}

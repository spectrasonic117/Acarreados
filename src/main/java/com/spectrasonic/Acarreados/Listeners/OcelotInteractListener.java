package com.spectrasonic.Acarreados.Listeners;

import com.spectrasonic.Acarreados.Game.GameManager;
import com.spectrasonic.Acarreados.Utils.ItemBuilder;
import com.spectrasonic.Acarreados.Utils.MessageUtils;
import com.spectrasonic.Acarreados.Utils.SoundUtils;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Allay;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.inventory.ItemStack;

public class OcelotInteractListener implements Listener {

    private final GameManager gameManager;

    public OcelotInteractListener(GameManager gameManager) {
        this.gameManager = gameManager;
    }

    @EventHandler
    public void onPlayerInteractEntity(PlayerInteractEntityEvent event) {
        Entity clicked = event.getRightClicked();
        if (!(clicked instanceof Allay))
            return;

        Player player = event.getPlayer();
        if (hasCustomPaper(player)) {
            MessageUtils.sendActionBar(player, "<red>No puedes llevar más de uno!</red>");
            return;
        }

        // En lugar de remover el modelo, se elimina el Allay del mundo
        Allay allay = (Allay) clicked;
        allay.remove();

        // Se genera el item especial y se entrega al jugador usando ItemBuilder
        ItemStack item = ItemBuilder.setMaterial("PAPER")
                .setCustomModelData(1047)
                .setName("<#5069B5><b>Pinguino</b>")
                .build();

        int mainHandSlot = player.getInventory().getHeldItemSlot();
        
        player.getInventory().setItem(mainHandSlot, item);

        // Reproducir sonido
        SoundUtils.playerSound(player, Sound.ENTITY_OCELOT_DEATH, 1.0F, 1.0F);
        
        // Respawn a new entity to maintain the count
        gameManager.respawnAllay();
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

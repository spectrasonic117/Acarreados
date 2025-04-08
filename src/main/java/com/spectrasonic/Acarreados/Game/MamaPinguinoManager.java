package com.spectrasonic.Acarreados.Game;

import com.ticxo.modelengine.api.ModelEngineAPI;
import com.ticxo.modelengine.api.model.ActiveModel;
import com.ticxo.modelengine.api.model.ModeledEntity;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.ArmorStand;

public class MamaPinguinoManager {

    private ArmorStand armorStand;
    private ModeledEntity modeledEntity;

    public void spawnMamaPinguino(Location loc) {
        World world = loc.getWorld();
        if (world == null) return;

        armorStand = world.spawn(loc, ArmorStand.class, stand -> {
            stand.setInvisible(true);
            stand.setMarker(true);
            stand.setGravity(false);
            stand.setInvulnerable(true);
            stand.setSmall(false);
            stand.setSilent(true);
        });

        modeledEntity = ModelEngineAPI.createModeledEntity(armorStand);
        ActiveModel model = ModelEngineAPI.createActiveModel("mama_pinguino");
        modeledEntity.addModel(model, true);
    }

    public void removeMamaPinguino() {
        if (armorStand != null && !armorStand.isDead()) {
            armorStand.remove();
        }
        armorStand = null;
        modeledEntity = null;
    }
}
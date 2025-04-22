package com.spectrasonic.Acarreados.Game;

import com.spectrasonic.Acarreados.Main;
import com.spectrasonic.Acarreados.Utils.Region;
import lombok.Getter;
import org.bukkit.Location;
import org.bukkit.entity.Allay;
import org.bukkit.entity.Entity;

@Getter
public class GameManager {

    private final Main plugin;
    private boolean running;
    private Region spawnRegion;
    private Region scoreRegion;
    private int spawnCount;
    private MamaPinguinoManager mamaPinguinoManager = new MamaPinguinoManager();
    private Location mamaPinguinoLocation;

    public GameManager(Main plugin) {
        this.plugin = plugin;
        loadConfig();
    }

    public void loadConfig() {
        plugin.reloadConfig();

        int spawnX1 = plugin.getConfig().getInt("regions.spawn.pos1.x");
        int spawnY1 = plugin.getConfig().getInt("regions.spawn.pos1.y");
        int spawnZ1 = plugin.getConfig().getInt("regions.spawn.pos1.z");
        int spawnX2 = plugin.getConfig().getInt("regions.spawn.pos2.x");
        int spawnY2 = plugin.getConfig().getInt("regions.spawn.pos2.y");
        int spawnZ2 = plugin.getConfig().getInt("regions.spawn.pos2.z");
        spawnRegion = new Region(spawnX1, spawnY1, spawnZ1, spawnX2, spawnY2, spawnZ2);

        if (plugin.getConfig().isSet("regions.score")) {
            int scoreX1 = plugin.getConfig().getInt("regions.score.pos1.x");
            int scoreY1 = plugin.getConfig().getInt("regions.score.pos1.y");
            int scoreZ1 = plugin.getConfig().getInt("regions.score.pos1.z");
            int scoreX2 = plugin.getConfig().getInt("regions.score.pos2.x");
            int scoreY2 = plugin.getConfig().getInt("regions.score.pos2.y");
            int scoreZ2 = plugin.getConfig().getInt("regions.score.pos2.z");
            scoreRegion = new Region(scoreX1, scoreY1, scoreZ1, scoreX2, scoreY2, scoreZ2);
        } else {
            plugin.getLogger().warning("Score region not configured in config.yml!");
            scoreRegion = new Region(0, 0, 0, 0, 0, 0);
        }

        spawnCount = plugin.getConfig().getInt("spawn-count", 100);

        String worldName = plugin.getConfig().getString("mama_pinguino_spawn.world", "world");
        int x = plugin.getConfig().getInt("mama_pinguino_spawn.x");
        int y = plugin.getConfig().getInt("mama_pinguino_spawn.y");
        int z = plugin.getConfig().getInt("mama_pinguino_spawn.z");
        mamaPinguinoLocation = new Location(plugin.getServer().getWorld(worldName), x, y, z, 0F, 0F);
    }

    public void startGame() {
        running = true;
        spawnAllays();
        mamaPinguinoManager.spawnMamaPinguino(mamaPinguinoLocation);
    }

    public void stopGame() {
        running = false;
        removeAllAllays();
        mamaPinguinoManager.removeMamaPinguino();
    }

    private void spawnAllays() {
        for (int i = 0; i < spawnCount; i++) {
            Location loc = getRandomLocationInRegion(spawnRegion);
            loc.add(0, 1, 0);
            Allay allay = loc.getWorld().spawn(loc, Allay.class);
            allay.setAI(false);
            allay.setInvulnerable(true);
            allay.setSilent(true);
        }
    }

    private void removeAllAllays() {
        plugin.getServer().getWorlds().get(0).getEntities().stream()
                .filter(entity -> entity instanceof Allay)
                .forEach(Entity::remove);
    }

    private Location getRandomLocationInRegion(Region region) {
        int minX = Math.min(region.getX1(), region.getX2());
        int maxX = Math.max(region.getX1(), region.getX2());
        int minY = Math.min(region.getY1(), region.getY2());
        int minZ = Math.min(region.getZ1(), region.getZ2());
        int maxZ = Math.max(region.getZ1(), region.getZ2());
        int x = minX + (int) (Math.random() * (maxX - minX + 1));
        int y = minY;
        int z = minZ + (int) (Math.random() * (maxZ - minZ + 1));
        return new Location(plugin.getServer().getWorlds().get(0), x, y, z);
    }

    public void respawnAllay() {
        if (!running)
            return;

        Location loc = getRandomLocationInRegion(spawnRegion);
        loc.add(0, 1, 0);
        Allay allay = loc.getWorld().spawn(loc, Allay.class);

        allay.setAI(false);
        allay.setInvulnerable(true);
    }
}

package com.spectrasonic.Acarreados.Game;

import com.spectrasonic.Acarreados.Main;
import com.spectrasonic.Acarreados.Utils.Region;
import lombok.Getter;
import org.bukkit.Location;
import org.bukkit.entity.Ocelot;

@Getter
public class GameManager {
    private final Main plugin;
    private boolean running;
    private Region spawnRegion;
    private Region scoreRegion;
    private int spawnCount;

    public GameManager(Main plugin) {
        this.plugin = plugin;
        loadConfig();
    }

    public void loadConfig() {
        plugin.reloadConfig();
        // Lectura de la región de spawn usando el nuevo formato
        int spawnX1 = plugin.getConfig().getInt("regions.spawn.pos1.x");
        int spawnY1 = plugin.getConfig().getInt("regions.spawn.pos1.y");
        int spawnZ1 = plugin.getConfig().getInt("regions.spawn.pos1.z");
        int spawnX2 = plugin.getConfig().getInt("regions.spawn.pos2.x");
        int spawnY2 = plugin.getConfig().getInt("regions.spawn.pos2.y");
        int spawnZ2 = plugin.getConfig().getInt("regions.spawn.pos2.z");

        spawnRegion = new Region(spawnX1, spawnY1, spawnZ1, spawnX2, spawnY2, spawnZ2);

        // Lectura de la región de puntuación usando el nuevo formato
        int scoreX1 = plugin.getConfig().getInt("regions.score.pos1.x");
        int scoreY1 = plugin.getConfig().getInt("regions.score.pos1.y");
        int scoreZ1 = plugin.getConfig().getInt("regions.score.pos1.z");
        int scoreX2 = plugin.getConfig().getInt("regions.score.pos2.x");
        int scoreY2 = plugin.getConfig().getInt("regions.score.pos2.y");
        int scoreZ2 = plugin.getConfig().getInt("regions.score.pos2.z");

        // Leer la cantidad de ocelotes a generar (con valor por defecto de 100)
        spawnCount = plugin.getConfig().getInt("spawn-count", 100);

        scoreRegion = new Region(scoreX1, scoreY1, scoreZ1, scoreX2, scoreY2, scoreZ2);
    }

    public void startGame() {
        running = true;
        spawnOcelots();
    }

    public void stopGame() {
        running = false;
    }

    /**
     * Se generan 100 OCELOT dentro de la región de spawn, ubicándolos 1 bloque por
     * encima para evitar la sofocación.
     */
    private void spawnOcelots() {
        for (int i = 0; i < spawnCount; i++) {
            Location loc = getRandomLocationInRegion(spawnRegion);
            loc.add(0, 1, 0);
            loc.getWorld().spawn(loc, Ocelot.class);
        }
    }

    private Location getRandomLocationInRegion(Region region) {
        int minX = Math.min(region.getX1(), region.getX2());
        int maxX = Math.max(region.getX1(), region.getX2());
        int minY = Math.min(region.getY1(), region.getY2());
        int minZ = Math.min(region.getZ1(), region.getZ2());
        int maxZ = Math.max(region.getZ1(), region.getZ2());
        int x = minX + (int) (Math.random() * (maxX - minX + 1));
        int y = minY; // Se utiliza la coordenada más baja para el spawn.
        int z = minZ + (int) (Math.random() * (maxZ - minZ + 1));
        return new Location(plugin.getServer().getWorlds().get(0), x, y, z);
    }
}

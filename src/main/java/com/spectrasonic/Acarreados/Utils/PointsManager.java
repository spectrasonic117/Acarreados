package com.spectrasonic.Acarreados.Utils;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import com.github.razorplay01.geoware.geowareplugin.api.GeoWarePluginAPI;
import dev.nazer.mediaplayer.spigot.api.MediaPlayerAPI;
import dev.nazer.mediaplayer.model.media.Glow;

public class PointsManager {

    private final JavaPlugin plugin;

    public PointsManager(JavaPlugin plugin) {
        this.plugin = plugin;
    }

    public void addPoints(Player player, int amount) {
        org.bukkit.plugin.Plugin geoWarePlugin = Bukkit.getPluginManager().getPlugin("GeoWarePlugin");
        if (!(geoWarePlugin instanceof GeoWarePluginAPI) || !geoWarePlugin.isEnabled()) {
            plugin.getLogger().warning("No se pudo acceder a GeoWarePlugin: " + (geoWarePlugin == null ? "plugin no cargado" : "API no soportada o deshabilitado"));
            return;
        }

        try {
            ((GeoWarePluginAPI) geoWarePlugin).getPointsManagerAPI().addPoints(player, amount);
            sendGlowEffect(player, "green", 0.5, 1.0, 0.5, 0.50f, 0.50f);
        } catch (Exception e) {
            plugin.getLogger().severe("Error al añadir puntos para " + player.getName() + ": " + e.getMessage());
            e.printStackTrace();
        }
    }

    public void subtractPoints(Player player, int amount) {
        org.bukkit.plugin.Plugin geoWarePlugin = Bukkit.getPluginManager().getPlugin("GeoWarePlugin");
        if (!(geoWarePlugin instanceof GeoWarePluginAPI) || !geoWarePlugin.isEnabled()) {
            plugin.getLogger().warning("No se pudo acceder a GeoWarePlugin: " + (geoWarePlugin == null ? "plugin no cargado" : "API no soportada o deshabilitado"));
            return;
        }

        try {
            ((GeoWarePluginAPI) geoWarePlugin).getPointsManagerAPI().subtractPoints(player, amount);
            sendGlowEffect(player, "red", 0.5, 1.0, 0.5, 0.50f, 0.50f);
        } catch (Exception e) {
            plugin.getLogger().severe("Error al restar puntos para " + player.getName() + ": " + e.getMessage());
            e.printStackTrace();
        }
    }

    private void sendGlowEffect(Player player, String color, double fadeIn, double duration, double fadeOut, float radius, float opacity) {
        int colorValue = switch (color.toLowerCase()) {
            case "green" -> 0x00FF00;
            case "red" -> 0xFF0000;
            default -> 0xFFFFFF;
        };

        Glow glow = new Glow((long) (fadeIn * 1000), (long) (duration * 1000), (long) (fadeOut * 1000), radius, opacity, colorValue);
        MediaPlayerAPI.sendMedia(player, glow);
    }
}

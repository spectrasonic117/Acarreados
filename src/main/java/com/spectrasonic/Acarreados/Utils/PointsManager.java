package com.spectrasonic.Acarreados.Utils;

import me.clip.placeholderapi.PlaceholderAPI;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

/**
 * Clase para gestionar los puntos de los jugadores usando PlaceholderAPI
 * y ejecutar comandos por consola.
 */
public class PointsManager {

    private final JavaPlugin plugin;

    public PointsManager(JavaPlugin plugin) {
        this.plugin = plugin;
    }

    /**
     * Añade puntos a un jugador
     * 
     * @param player El jugador al que añadir puntos
     * @param amount Cantidad de puntos a añadir
     */
    public void addPoints(Player player, int amount) {
        String playerName = player.getName();
        String command = "2dgamepoints add " + playerName + " " + amount;
        executeCommand(command);
    }

    /**
     * Resta puntos a un jugador
     * 
     * @param player El jugador al que restar puntos
     * @param amount Cantidad de puntos a restar
     */
    public void subtractPoints(Player player, int amount) {
        String playerName = player.getName();
        String command = "2dgamepoints subtract " + playerName + " " + amount;
        executeCommand(command);
    }

    /**
     * Añade puntos a un jugador usando un placeholder
     * 
     * @param placeholder El placeholder que se resolverá al nombre del jugador
     * @param amount      Cantidad de puntos a añadir
     */
    public void addPointsWithPlaceholder(String placeholder, int amount) {
        // Reemplaza el placeholder con el valor real
        String resolvedPlaceholder = PlaceholderAPI.setPlaceholders(null, placeholder);
        String command = "2dgamepoints add " + resolvedPlaceholder + " " + amount;
        executeCommand(command);
    }

    /**
     * Resta puntos a un jugador usando un placeholder
     * 
     * @param placeholder El placeholder que se resolverá al nombre del jugador
     * @param amount      Cantidad de puntos a restar
     */
    public void subtractPointsWithPlaceholder(String placeholder, int amount) {
        // Reemplaza el placeholder con el valor real
        String resolvedPlaceholder = PlaceholderAPI.setPlaceholders(null, placeholder);
        String command = "2dgamepoints subtract " + resolvedPlaceholder + " " + amount;
        executeCommand(command);
    }

    /**
     * Ejecuta un comando por consola
     * 
     * @param command El comando a ejecutar
     */
    private void executeCommand(String command) {
        Bukkit.getScheduler().runTask(plugin, () -> Bukkit.dispatchCommand(Bukkit.getConsoleSender(), command));
    }
}
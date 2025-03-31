package com.spectrasonic.Acarreados.Utils;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

public class PointsManager {

    private final JavaPlugin plugin;

    public PointsManager(JavaPlugin plugin) {
        this.plugin = plugin;
    }

    public void addPoints(Player player, int amount) {
        String playerName = player.getName();
        String command = "2dgamepoints add " + playerName + " " + amount;
        String screen_point = "playglow " + playerName + " green 1 3 1 50 75";
        executeCommand(command, screen_point);
    }

    public void subtractPoints(Player player, int amount) {
        String playerName = player.getName();
        String command = "2dgamepoints subtract " + playerName + " " + amount;
        String screen_point = "playglow " + playerName + " red 1 3 1 50 75";
        executeCommand(command, screen_point);
    }

    private void executeCommand(String command, String screen_point) {
        Bukkit.getScheduler().runTask(plugin, () -> Bukkit.dispatchCommand(Bukkit.getConsoleSender(), command));
        Bukkit.getScheduler().runTask(plugin, () -> Bukkit.dispatchCommand(Bukkit.getConsoleSender(), screen_point));
    }
}
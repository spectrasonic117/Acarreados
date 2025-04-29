package com.spectrasonic.Acarreados.Commands;

import co.aikar.commands.BaseCommand;
import co.aikar.commands.annotation.*;
import com.spectrasonic.Acarreados.Game.GameManager;
import com.spectrasonic.Acarreados.Main;
import com.spectrasonic.Acarreados.Utils.MessageUtils;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

@CommandAlias("acarreados")
@CommandPermission("acarreados.admin")
public class GameCommand extends BaseCommand {

    private final Main plugin;
    private final GameManager gameManager;

    public GameCommand(Main plugin, GameManager gameManager) {
        this.plugin = plugin;
        this.gameManager = gameManager;
    }

    @Subcommand("game start")
    @CommandCompletion("@range:1-3") 
    @Description("Starts the Acarreados minigame in a specific round.")
    public void onGameStart(CommandSender sender, @Conditions("limits:min=1,max=3") int round) {
        Player player = (sender instanceof Player) ? (Player) sender : null; 
        gameManager.startGame(round);

        if (player != null) {
            player.performCommand("id false");
        }

        MessageUtils.sendMessage(sender, "<green>Minijuego iniciado! Ronda " + round + "</green>");
    }

    // Define the 'game stop' subcommand
    @Subcommand("game stop")
    @Description("Stops the Acarreados minigame.")
    public void onGameStop(CommandSender sender) {
        Player player = (sender instanceof Player) ? (Player) sender : null; // Get player if sender is one

        gameManager.stopGame();

        if (player != null) {
            player.performCommand("id true"); // Execute command only if sender is a player
        }

        removeCustomPaperFromAllPlayers();
        MessageUtils.sendMessage(sender, "<red>Minijuego detenido!</red>");
    }


    @Subcommand("reload")
    @Description("Reloads the Acarreados configuration.")
    public void onReload(CommandSender sender) {
        gameManager.loadConfig();
        MessageUtils.sendMessage(sender, "<green>Configuración recargada!</green>");
    }

    private void removeCustomPaperFromAllPlayers() {
        for (Player player : Bukkit.getOnlinePlayers()) {
            player.getInventory().clear(); // Clear main inventory
        }
    }
}
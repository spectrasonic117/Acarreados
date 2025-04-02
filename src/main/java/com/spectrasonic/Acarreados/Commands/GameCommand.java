package com.spectrasonic.Acarreados.Commands;

import co.aikar.commands.BaseCommand;
import co.aikar.commands.annotation.CommandAlias;
import co.aikar.commands.annotation.CommandCompletion;
import co.aikar.commands.annotation.Subcommand;
import com.spectrasonic.Acarreados.Game.GameManager;
import com.spectrasonic.Acarreados.Main;
import com.spectrasonic.Acarreados.Utils.MessageUtils;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

@CommandAlias("acarreados")
public class GameCommand extends BaseCommand {

    private final Main plugin;
    private final GameManager gameManager;

    public GameCommand(Main plugin, GameManager gameManager) {
        this.plugin = plugin;
        this.gameManager = gameManager;
    }

    @Subcommand("game")
    @CommandCompletion("start|stop")
    public void onGame(CommandSender sender, String mode) {
        Player player = (Player) sender;
        if (mode.equalsIgnoreCase("start")) {
            gameManager.startGame();
            player.performCommand("id false");
            MessageUtils.sendMessage(sender, "<green>Minijuego iniciado!</green>");
        } else if (mode.equalsIgnoreCase("stop")) {
            gameManager.stopGame();
            player.performCommand("id true");
            removeCustomPaperFromAllPlayers();
            MessageUtils.sendMessage(sender, "<red>Minijuego detenido!</red>");
        } else {
            MessageUtils.sendMessage(sender, "<red>Modo inválido! Usa start o stop.</red>");
        }
    }

    @Subcommand("reload")
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

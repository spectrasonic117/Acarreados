package com.spectrasonic.Acarreados;

import co.aikar.commands.PaperCommandManager;
import com.spectrasonic.Acarreados.Commands.GameCommand;
import com.spectrasonic.Acarreados.Game.GameManager;
import com.spectrasonic.Acarreados.Listeners.BlockInteractListener;
import com.spectrasonic.Acarreados.Listeners.OcelotInteractListener;
import com.spectrasonic.Acarreados.Utils.MessageUtils;
import com.spectrasonic.Acarreados.Utils.PointsManager;
import org.bukkit.plugin.java.JavaPlugin;

public final class Main extends JavaPlugin {

    private GameManager gameManager;
    private PointsManager pointsManager;

    @Override
    public void onEnable() {
        saveDefaultConfig();
        gameManager = new GameManager(this);
        pointsManager = new PointsManager(this);
        registerCommands();
        registerEvents();
        MessageUtils.sendStartupMessage(this);
    }

    @Override
    public void onDisable() {
        MessageUtils.sendShutdownMessage(this);
    }

    public void registerCommands() {
        PaperCommandManager commandManager = new PaperCommandManager(this);
        commandManager.registerCommand(new GameCommand(this, gameManager));
    }

    public void registerEvents() {
        getServer().getPluginManager().registerEvents(new OcelotInteractListener(), this);
        getServer().getPluginManager().registerEvents(new BlockInteractListener(gameManager, pointsManager), this);
    }

    public PointsManager getPointsManager() {
        return pointsManager;
    }
}

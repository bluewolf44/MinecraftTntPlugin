package me.danielwest.test;

import me.danielwest.test.commands.TestCommand;
import me.danielwest.test.events.TestEvents;
import me.danielwest.test.items.TestItems;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Objects;

public final class Test extends JavaPlugin {

    private static Test testThis;

    @Override
    public void onEnable() {
        // Plugin startup logic
        TestCommand commands = new TestCommand();
        Objects.requireNonNull(getCommand("testitem")).setExecutor(commands);

        getServer().getPluginManager().registerEvents(new TestEvents(),this);

        TestItems.createItems();
        Walls.init();

        testThis = this;
        getLogger().info("Please work");
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
    public static Test getTestThis()
    {
        return testThis;
    }

    public static void msgAll(String msg)
    {
        for(Player player: Bukkit.getOnlinePlayers())
        {
            player.sendMessage(msg);
        }
    }
}

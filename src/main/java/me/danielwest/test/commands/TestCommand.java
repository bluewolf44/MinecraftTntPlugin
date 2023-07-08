package me.danielwest.test.commands;

import me.danielwest.test.items.TestItems;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;


public class TestCommand implements CommandExecutor
{
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command cmd, @NotNull String s, @NotNull String[] args) {
        if (sender instanceof Player)
        {
            Player player = (Player) sender;
            if (cmd.getName().equalsIgnoreCase("testitem"))
            {
                player.getInventory().addItem(TestItems.CobbleWall);
            }

        }

        return true;
    }
}

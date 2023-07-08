package me.danielwest.test.items;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextColor;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ShapelessRecipe;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.*;

public class TestItems {
    public static ItemStack CobbleWall;

    public static void createItems()
    {
        createCobbleWall();
    }

    public static void createCobbleWall()
    {
        ItemStack wall = new ItemStack(Material.COBBLESTONE,1);
        ItemMeta meta = wall.getItemMeta();
        meta.displayName(Component.text("Cobble Wall", TextColor.color(0,0,255)));
        List<Component> lore = new ArrayList<>();
        lore.add(Component.text("Right click", TextColor.color(0,0,255)));
        meta.lore(lore);
        meta.addEnchant(Enchantment.LUCK,1,false);
        meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
        wall.setItemMeta(meta);
        CobbleWall = wall;
        wall.setAmount(1);

        ShapelessRecipe sr = new ShapelessRecipe(NamespacedKey.minecraft("wall_base"),wall);
        sr.addIngredient(4,new ItemStack(Material.COBBLESTONE));
        Bukkit.getServer().addRecipe(sr);
        wall.setAmount(1);
    }
}

package me.danielwest.test.events;

import me.danielwest.test.Test;
import me.danielwest.test.Walls;
import me.danielwest.test.items.TestItems;
import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.block.BlockState;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntityExplodeEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.metadata.FixedMetadataValue;

import java.util.ArrayList;
import java.util.List;

public class TestEvents implements Listener
{
    @EventHandler
    public static void onPlayerJoin(PlayerJoinEvent event)
    {
        event.getPlayer().sendMessage(ChatColor.LIGHT_PURPLE + "Welcome to hell :)");
    }
    @EventHandler
    public static void RightClicked(PlayerInteractEvent event) {
        if (event.getItem() == null)
        {
            return;
        }
        if(event.getItem().getType().equals(Material.POTATO))
        {
            BlockState blockState = event.getClickedBlock().getState();
            if (!blockState.hasMetadata("amount")){
                idk(event,blockState);
                return;
            } else if (blockState.getMetadata("amount").size() == 0)
            {
                idk(event,blockState);
                return;
            } else if (blockState.getMetadata("amount").get(0).asInt() == 0)
            {
                idk(event,blockState);
                return;
            }
            event.getPlayer().sendMessage(""+blockState.getMetadata("amount").get(0).asInt());
        }
    }

    private static void idk(PlayerInteractEvent event, BlockState blockState)
    {
        for (Walls.Wall w : Walls.walls) {
            if (w.getMaterial().equals(event.getClickedBlock().getType())) {
                blockState.setMetadata("amount", new FixedMetadataValue(Test.getTestThis(), w.getAmount()));
                event.getPlayer().sendMessage("" + w.getAmount());
                return;
            }
        }
    }

    @EventHandler
    public static void RightPlaced(BlockPlaceEvent event) {
        if(event.getItemInHand().getType().equals(Material.COBBLESTONE)){
            int increase = 1;
            if (event.getItemInHand().getItemMeta().equals(TestItems.CobbleWall.getItemMeta())) {
                increase = 4;
            }
            event.setCancelled(true);
            Block block = event.getBlockAgainst();
            BlockState blockState = block.getState();
            if (!event.getPlayer().isSneaking()) {
                for (Walls.Wall w : Walls.walls) {
                    if (w.getMaterial().equals(block.getType())) {
                        if (!w.isAddMore()) {
                            return;
                        }
                        event.getItemInHand().subtract();
                        if (!blockState.hasMetadata("amount") || blockState.getMetadata("amount").size() == 0) {
                            blockState.setMetadata("amount", new FixedMetadataValue(Test.getTestThis(), w.getAmount()));
                            event.getPlayer().sendMessage("new one:" + (w.getAmount()));
                        }
                        int amount = blockState.getMetadata("amount").get(0).asInt();
                        Walls.Wall wall = w;
                        while (amount + increase >= wall.getAmountToNext()) {
                            block.setType(wall.getNext().getMaterial());
                            wall = wall.getNext();
                        }
                        blockState.setMetadata("amount", new FixedMetadataValue(Test.getTestThis(), amount + increase));
                        event.getPlayer().sendMessage("" + (amount + increase));
                        return;
                    }
                }
            }
            //event.getPlayer().sendMessage("not vaid");
            event.getBlockPlaced().setMetadata("amount", new FixedMetadataValue(Test.getTestThis(), increase));
            event.setCancelled(false);
            Walls.Wall wall = Walls.COBBLESTONE;
            while(increase >= wall.getAmountToNext()) {
                event.getBlockPlaced().setType(wall.getNext().getMaterial());
                wall = wall.getNext();
            }
        }
    }

    @EventHandler
    public static void blockMined(BlockBreakEvent event) {

        if (event.getBlock().getType().equals(Material.COBBLESTONE)) {
            BlockState blockState = event.getBlock().getState();
            World world = event.getBlock().getWorld();
            if (blockState.hasMetadata("amount")) {
                if(blockState.getMetadata("amount").size() != 0) {
                    for (int i = 0; i < blockState.getMetadata("amount").get(0).asInt() - 1; i++) {
                        world.dropItemNaturally(event.getBlock().getLocation(), new ItemStack(Material.COBBLESTONE));
                    }
                }
            }
            return;
        }


        for (Walls.Wall w : Walls.walls) {
            if (w.getMaterial().equals(event.getBlock().getType())) {
                event.setCancelled(true);
                event.setDropItems(false);
                BlockState blockState = event.getBlock().getState();
                World world = event.getBlock().getWorld();
                int amount;
                if(event.getPlayer().getInventory().getItemInMainHand().getEnchantments().containsKey(Enchantment.SILK_TOUCH))
                {
                    if (!blockState.hasMetadata("amount")) {
                        amount = 0;
                    } else if(blockState.getMetadata("amount").size() == 0) {
                        amount = 0;
                    } else if (blockState.getMetadata("amount").get(0).asInt() == 0) {
                        amount = 0;
                    } else{
                        amount = blockState.getMetadata("amount").get(0).asInt() - w.getAmount();
                    }
                    blockState.setMetadata("amount", new FixedMetadataValue(Test.getTestThis(), 0));
                    world.dropItemNaturally(event.getBlock().getLocation(), new ItemStack(event.getBlock().getState().getType()));
                    event.getBlock().setType(Material.AIR);

                }
                else {
                    event.getBlock().setType(w.getPrev().getMaterial());
                    if (!blockState.hasMetadata("amount")) {
                        amount = w.getAmount() - w.getPrev().getAmount();
                        blockState.setMetadata("amount", new FixedMetadataValue(Test.getTestThis(), w.getPrev().getAmount()));
                    } else if (blockState.getMetadata("amount").size() == 0) {
                        amount = w.getAmount() - w.getPrev().getAmount();
                        blockState.setMetadata("amount", new FixedMetadataValue(Test.getTestThis(), w.getPrev().getAmount()));
                    } else if (blockState.getMetadata("amount").get(0).asInt() == 0) {
                        amount = w.getAmount() - w.getPrev().getAmount();
                        blockState.setMetadata("amount", new FixedMetadataValue(Test.getTestThis(), w.getPrev().getAmount()));
                    } else {
                        amount = blockState.getMetadata("amount").get(0).asInt() - w.getPrev().getAmount();
                        blockState.setMetadata("amount", new FixedMetadataValue(Test.getTestThis(), w.getPrev().getAmount()));
                    }
                }

                for (int i = 0; i < amount/4; i++) {
                    world.dropItemNaturally(event.getBlock().getLocation(), TestItems.CobbleWall);
                }
                for (int i = 0; i < amount%4; i++) {
                    world.dropItemNaturally(event.getBlock().getLocation(), new ItemStack(Material.COBBLESTONE));
                }
                return;
            }
        }
    }
    @EventHandler
    public static void blockExplode(EntityExplodeEvent event) {
        event.setCancelled(true);
        List<Block> blocks = new ArrayList<>();
        blocks.addAll(getObsInSphere(event.getLocation(),2));
        blocks.addAll(event.blockList());
       for(Block block: blocks)
       {
           World world = block.getWorld();
           boolean hit = false;
           for (Walls.Wall w : Walls.walls) {
               if(!w.getMaterial().equals(block.getType())) {
                   continue;
               }
               hit = true;
               if(w.getPrev() == null)
               {
                   block.setType(Material.AIR);
                   world.dropItemNaturally(block.getLocation(), new ItemStack(Material.COBBLESTONE));
                   continue;
               }
               block.setType(w.getPrev().getMaterial());

               int amount;
               BlockState blockState = block.getState();

               if (!blockState.hasMetadata("amount")) {
                   amount = w.getAmount() - w.getPrev().getAmount();
                   blockState.setMetadata("amount", new FixedMetadataValue(Test.getTestThis(), w.getPrev().getAmount()));
               } else if (blockState.getMetadata("amount").size() == 0) {
                   amount = w.getAmount() - w.getPrev().getAmount();
                   blockState.setMetadata("amount", new FixedMetadataValue(Test.getTestThis(), w.getPrev().getAmount()));
               } else if (blockState.getMetadata("amount").get(0).asInt() == 0) {
                   amount = w.getAmount() - w.getPrev().getAmount();
                   blockState.setMetadata("amount", new FixedMetadataValue(Test.getTestThis(), w.getPrev().getAmount()));
               } else {
                   amount = blockState.getMetadata("amount").get(0).asInt() - w.getPrev().getAmount();
                   blockState.setMetadata("amount", new FixedMetadataValue(Test.getTestThis(), w.getPrev().getAmount()));
               }

               for (int i = 0; i < amount / 4; i++) {
                   world.dropItemNaturally(block.getLocation(), TestItems.CobbleWall);
               }
               for (int i = 0; i < amount % 4; i++) {
                   world.dropItemNaturally(block.getLocation(), new ItemStack(Material.COBBLESTONE));
               }
           }
           if (!hit)
           {
               block.breakNaturally();
           }
       }
    }
    public static List<Block> getObsInSphere(Location centerBlock, int radius) {
        List<Block> obiBlocks = new ArrayList<>();

        int bx = centerBlock.getBlockX();
        int by = centerBlock.getBlockY();
        int bz = centerBlock.getBlockZ();

        for(int x = bx - radius; x <= bx + radius; x++) {
            for(int y = by - radius; y <= by + radius; y++) {
                for(int z = bz - radius; z <= bz + radius; z++) {

                    double distance = ((bx-x) * (bx-x) + ((bz-z) * (bz-z)) + ((by-y) * (by-y)));

                    if(distance < radius * radius) {
                        Block block = centerBlock.getWorld().getBlockAt(x,y,z);
                        if (block.getType().equals(Material.OBSIDIAN)) {
                            obiBlocks.add(block);
                        }
                    }
                }
            }
        }

        return obiBlocks;
    }

}

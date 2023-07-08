package me.danielwest.test;

import org.bukkit.Material;

import java.util.ArrayList;
import java.util.List;

public class Walls {

    public static List<Wall> walls = new ArrayList<Wall>();
    public static Wall COBBLESTONE;
    public static Wall ANDESITE;
    public static Wall CRACKED_STONE_BRICKS;
    public static Wall STONE_BRICKS;
    public static Wall CRACKED_DEEPSLATE_BRICKS;
    public static Wall DEEPSLATE_BRICKS;
    public static Wall CRACKED_POLISHED_BLACKSTONE_BRICKS;
    public static Wall POLISHED_BLACKSTONE_BRICKS;
    public static Wall OBSIDIAN;

    public static class Wall
    {
        private Material material;
        private int amount;
        private Wall prev;
        private Wall next;

        private boolean addMore = true;

        private Wall(Material material,int amount,Wall prev)
        {
            this.material = material;
            this.amount = amount;
            this.prev = prev;
            if (prev != null) {
                prev.next = this;
            }
            if(material.equals(Material.OBSIDIAN))
            {
                addMore = false;
            }
        }

        public Material getMaterial() {
            return material;
        }

        public int getAmount() {
            return amount;
        }

        public Wall getPrev() {
            return prev;
        }

        public int getAmountToNext()
        {
           return next.amount;
        }

        public Wall getNext() {
            return next;
        }

        public boolean isAddMore() {
            return addMore;
        }
    }
    public static void init()
    {
        walls.add(new Wall(Material.COBBLESTONE,1,null));
        walls.add(new Wall(Material.ANDESITE,4,walls.get(0)));
        walls.add(new Wall(Material.CRACKED_STONE_BRICKS,8,walls.get(1)));
        walls.add(new Wall(Material.STONE_BRICKS,16,walls.get(2)));
        walls.add(new Wall(Material.CRACKED_DEEPSLATE_BRICKS,32,walls.get(3)));
        walls.add(new Wall(Material.DEEPSLATE_BRICKS,64,walls.get(4)));
        walls.add(new Wall(Material.CRACKED_POLISHED_BLACKSTONE_BRICKS,128,walls.get(5)));
        walls.add(new Wall(Material.POLISHED_BLACKSTONE_BRICKS,192,walls.get(6)));
        walls.add(new Wall(Material.OBSIDIAN,256,walls.get(7)));

        COBBLESTONE = walls.get(0);
        ANDESITE = walls.get(1);
        CRACKED_STONE_BRICKS = walls.get(2);
        STONE_BRICKS =walls.get(3);
        CRACKED_DEEPSLATE_BRICKS = walls.get(4);
        DEEPSLATE_BRICKS = walls.get(5);
        CRACKED_POLISHED_BLACKSTONE_BRICKS = walls.get(6);
        POLISHED_BLACKSTONE_BRICKS = walls.get(7);
        OBSIDIAN = walls.get(8);
    }

}

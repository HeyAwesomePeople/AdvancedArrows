package me.HeyAwesomePeople.AdvancedArrows;


import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;

public class Methods {
    private AdvancedArrows plugin = AdvancedArrows.instance;

    public static String traileffect = ChatColor.GOLD + "[AA] Effect";

    //* ****** For Trails ****** *//
    public void setTrail(ItemStack i, String trail) {
        ItemMeta im = i.getItemMeta();
        List<String> list = new ArrayList<String>();
        list.add(traileffect);
        list.add(trail);
        im.setLore(list);
        i.setItemMeta(im);
    }

    /* TODO

        Arrows: Use Lore. Set the lore of the arrows, and make it so arrow lores cannot be edited.

        Bows: Use lore here too. Set the lore of the bows and make it so

     */


}

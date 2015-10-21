package me.HeyAwesomePeople.AdvancedArrows.enchantments;


import org.bukkit.enchantments.Enchantment;
import org.bukkit.enchantments.EnchantmentTarget;
import org.bukkit.inventory.ItemStack;

public class BasicEnchantment extends Enchantment {

    public BasicEnchantment(int id) {
        super(id);
    }

    @Override
    public boolean canEnchantItem(ItemStack item) {
        return true; //can it enchant items?
    }

    @Override
    public boolean conflictsWith(Enchantment other) {
        return false; //will it conflict? In this case, nope
    }

    @Override
    public EnchantmentTarget getItemTarget() {
        return null; //you can define a target here, I just setted it to null...
    }

    @Override
    public int getMaxLevel() {
        return 1; //the maxmimum level.
    }

    @Override
    public String getName() {
        return "Basic Enchantment"; //the name
    }

    @Override
    public int getId() {
        return 152;//the id, in this case I choose 69 because no serious person would ever choose that number, lucky me! Oh yeah, the max id = 256
    }

    @Override
    public int getStartLevel() {
        return 1; //the start level of your enchantment
    }

}
package me.HeyAwesomePeople.AdvancedArrows;


import me.HeyAwesomePeople.AdvancedArrows.enchantments.BasicEnchantment;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;

import java.lang.reflect.Field;
import java.util.HashMap;

public class AdvancedArrows extends JavaPlugin implements CommandExecutor {
    public static AdvancedArrows instance;
    public BasicEnchantment basicEnch;

    public HashMap<ItemStack, String> trailing = new HashMap<ItemStack, String>();
    public HashMap<ItemStack, String> blocking = new HashMap<ItemStack, String>();
    public HashMap<ItemStack, String> potioning = new HashMap<ItemStack, String>();

    public FileConfiguration config;

    public TrailCreator trails;

    @Override
    public void onEnable() {
        instance = this;
        config = this.getConfig();
        trails = new TrailCreator();

        this.allowEnchants();

        basicEnch = new BasicEnchantment(152);
    }

    @Override
    public void onDisable() {
        this.reloadConfig();

        try {
            Field byIdField = Enchantment.class.getDeclaredField("byId");
            Field byNameField = Enchantment.class.getDeclaredField("byName");

            byIdField.setAccessible(true);
            byNameField.setAccessible(true);

            @SuppressWarnings("unchecked")
            HashMap<Integer, Enchantment> byId = (HashMap<Integer, Enchantment>) byIdField.get(null);
            @SuppressWarnings("unchecked")
            HashMap<String, Enchantment> byName = (HashMap<String, Enchantment>) byNameField.get(null);

            if (byId.containsKey(152))
                byId.remove(152);

            if (byName.containsKey(getName()))
                byName.remove(getName());
        } catch (Exception ignored) {
        }
    }

    public void allowEnchants() {
        try {
            try {
                Field f = Enchantment.class.getDeclaredField("acceptingNew");
                f.setAccessible(true);
                f.set(null, true);
            } catch (Exception e) {
                e.printStackTrace();
            }
            try {
                Enchantment.registerEnchantment(basicEnch);
            } catch (IllegalArgumentException ignored) {
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public boolean onCommand(final CommandSender sender, Command cmd,
                             String commandLabel, final String[] args) {
        if (commandLabel.equalsIgnoreCase("advancedarrows") || commandLabel.equalsIgnoreCase("advanceda") ||
                commandLabel.equalsIgnoreCase("aarrow") || commandLabel.equalsIgnoreCase("aarrows") || commandLabel.equalsIgnoreCase("aa")) {
            if (args.length == 0) {
                if (!sender.hasPermission("arrows.arrow")) {
                    sender.sendMessage(ChatColor.RED + "You do not have permissions to do this!");
                    return false;
                }
                sender.sendMessage(ChatColor.GREEN + "==== Advanced Arrows Cmds ====");
                sender.sendMessage(ChatColor.GREEN + "Commands in " + ChatColor.BLUE + "blue" + ChatColor.GREEN + " need to be ran while holding a bow or stack of arrows in your hand!");
                sender.sendMessage(ChatColor.AQUA + "/aa, /advancedarrows, /aarrows, /aarrow, /advanceda - " + ChatColor.DARK_AQUA + "Be brought to this menu.");
                sender.sendMessage(ChatColor.AQUA + "/aa trail <effect> - " + ChatColor.DARK_AQUA + "Add trailing effect to bow or stack of arrows");
                sender.sendMessage(ChatColor.AQUA + "/aa block <blockname> - " + ChatColor.DARK_AQUA + "Set what block the arrow becomes when it lands. Applies to bow or stack of arrows.");
                sender.sendMessage(ChatColor.AQUA + "/aa potion <potionname> - " + ChatColor.DARK_AQUA + "Apply a potion to anything hit, or simulate a dropped potion if the arrow hits the ground. Applies to bow or stack of arrows.");
                sender.sendMessage(ChatColor.AQUA + "/aa clear - " + ChatColor.DARK_AQUA + "Clears all effects on bow or stack of arrows.");
                sender.sendMessage(ChatColor.BLUE + "/aa reload - " + ChatColor.DARK_AQUA + "Reload the configuration");
            } else {
                if (args[0].equalsIgnoreCase("reload")) {
                    if (!sender.hasPermission("arrows.reload")) {
                        sender.sendMessage(ChatColor.RED + "You do not have permissions to do this!");
                        return false;
                    }
                    this.reloadConfig();
                    this.config = this.getConfig();
                    sender.sendMessage(ChatColor.GOLD + "[Advanced Arrows] Configuration reloaded.");
                    return false;
                }

                if (sender instanceof ConsoleCommandSender) {
                    sender.sendMessage(ChatColor.RED + "[Advanced Arrows] This command can only be run in game!");
                    return false;
                }
                Player p = (Player) sender;

                if (!p.getItemInHand().getType().equals(Material.BOW) || !p.getItemInHand().getType().equals(Material.ARROW)) {
                    p.sendMessage(ChatColor.RED + "When running that command, hold a stack of arrows or a bow!");
                    return false;
                }

                if (args[0].equalsIgnoreCase("clear")) {
                    if (trailing.containsKey(p.getItemInHand())) {
                        trailing.remove(p.getItemInHand());
                    }
                    p.sendMessage(ChatColor.GOLD + "All effects removed.");
                    return false;
                }

                if (args[0].equalsIgnoreCase("trail")) {
                    if (args.length == 1) {
                        p.sendMessage(ChatColor.GREEN + "Available Trails: " + trails.getAllEffects());
                        return false;
                    } else if (args.length == 2) {
                        if (!this.trails.doesEffectExist(args[1])) {
                            p.sendMessage(ChatColor.GREEN + "Available Trails: " + trails.getAllEffects());
                            return false;
                        }
                        trailing.put(p.getItemInHand(), args[1]);
                        return false;
                    } else {
                        p.sendMessage(ChatColor.RED + "Invalid argument count!");
                        return false;
                    }
                } else if (args[0].equalsIgnoreCase("block")) {
                    if (args.length == 1) {
                        //TODO show available blocks
                        return false;
                    } else if (args.length == 2) {
                        //TODO set block
                        return false;
                    } else {
                        p.sendMessage(ChatColor.RED + "Invalid argument count!");
                        return false;
                    }
                } else if (args[0].equalsIgnoreCase("potion")) {
                    if (args.length == 1) {
                        //TODO show available potions
                        return false;
                    } else if (args.length == 2) {
                        //TODO set potion
                        return false;
                    } else {
                        p.sendMessage(ChatColor.RED + "Invalid argument count!");
                        return false;
                    }
                } else {
                    p.sendMessage(ChatColor.RED + "Invalid subcommand!");
                    return false;
                }
            }
        }
        return false;
    }
}

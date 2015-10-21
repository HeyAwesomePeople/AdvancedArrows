package me.HeyAwesomePeople.AdvancedArrows.listeners;

import me.HeyAwesomePeople.AdvancedArrows.AdvancedArrows;
import org.bukkit.Bukkit;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.ProjectileLaunchEvent;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.scheduler.BukkitTask;

import java.util.HashMap;

public class PotionListener implements Listener {
    private AdvancedArrows plugin = AdvancedArrows.instance;

    public HashMap<Arrow, BukkitTask> potionTask = new HashMap<Arrow, BukkitTask>();

    @EventHandler
    public void arrowFireEvent(ProjectileLaunchEvent e) {
        if (e.getEntity() instanceof Arrow) {
            if (e.getEntity().getShooter() instanceof Player) {
                Player p = (Player) e.getEntity().getShooter();
                if (p.getItemInHand().getType().equals(org.bukkit.Material.BOW)) {
                    if (p.getItemInHand().getItemMeta().getLore().contains(plugin.methods.potioneffect)) {
                        for (String s : p.getItemInHand().getItemMeta().getLore()) {
                            if (plugin.potions.doesPotionExist(s)) {
                                createTask((Arrow) e.getEntity(), s);
                                Arrow a = (Arrow) e.getEntity();
                                a.setMetadata("potionEffect", new FixedMetadataValue(plugin, s));
                                break;
                            }
                        }
                    } else {
                        //TODO apply to arrow if possible
                    }
                }
            }
        }
    }

    @EventHandler
    public void onPlayerHitByArrow(EntityDamageByEntityEvent e) {
        if (e.getDamager() instanceof Arrow) {
            Arrow a = (Arrow) e.getDamager();
            if (!a.hasMetadata("potionEffect")) return;
            if (potionTask.containsKey(a)) {
                potionTask.remove(a);
            }
            if (a.getShooter() instanceof Player) {
                Player p = (Player) a.getShooter();
                plugin.potions.infectEntity(p, a.getMetadata("potionEffect").get(0).asString());
            }
        }

    }

    public void createTask(final Arrow entity, final String potion) {
        BukkitTask bt = Bukkit.getScheduler().runTaskTimer(plugin, new Runnable() {
            public void run() {
                if (entity.isOnGround()) {
                    if (potionTask.containsKey(entity)) {
                        potionTask.get(entity).cancel();
                        plugin.potions.breakBottledEffect(entity.getLocation(), potion);
                    }
                }
            }
        }, 0L, 2L);
        potionTask.put(entity, bt);
    }

}

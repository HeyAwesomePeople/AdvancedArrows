package me.HeyAwesomePeople.AdvancedArrows.listeners;

import me.HeyAwesomePeople.AdvancedArrows.AdvancedArrows;
import me.HeyAwesomePeople.AdvancedArrows.Methods;
import org.bukkit.Bukkit;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.ProjectileLaunchEvent;
import org.bukkit.scheduler.BukkitTask;

import java.util.HashMap;

public class TrailListener implements Listener {
    private AdvancedArrows plugin = AdvancedArrows.instance;

    public HashMap<Arrow, BukkitTask> trailTask = new HashMap<Arrow, BukkitTask>();

    @EventHandler
    public void arrowFireEvent(ProjectileLaunchEvent e) {
        if (e.getEntity() instanceof Arrow) {
            if (e.getEntity().getShooter() instanceof Player) {
                Player p = (Player) e.getEntity().getShooter();
                if (p.getItemInHand().getType().equals(org.bukkit.Material.BOW)) {
                    if (p.getItemInHand().getItemMeta().getLore().contains(plugin.methods.traileffect)) {
                        for (String s : p.getItemInHand().getItemMeta().getLore()) {
                            if (plugin.trails.doesEffectExist(s)) {
                                createTask((Arrow) e.getEntity(), s);
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

    public void createTask(final Arrow entity, final String effect) {
        BukkitTask bt = Bukkit.getScheduler().runTaskTimer(plugin, new Runnable() {
            public void run() {
                if (entity.isOnGround() || !entity.isValid()) {
                    if (trailTask.containsKey(entity)) { trailTask.get(entity).cancel(); }
                    return;
                }
                plugin.trails.createParticle(effect, entity.getLocation());
            }
        }, 0L, 2L);
        trailTask.put(entity, bt);
    }

}

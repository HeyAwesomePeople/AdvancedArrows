package me.HeyAwesomePeople.AdvancedArrows.listeners;

import me.HeyAwesomePeople.AdvancedArrows.AdvancedArrows;
import me.HeyAwesomePeople.AdvancedArrows.Methods;
import me.HeyAwesomePeople.AdvancedArrows.TrailCreator;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityShootBowEvent;
import org.bukkit.event.entity.ProjectileLaunchEvent;

public class TrailListener implements Listener {
    private AdvancedArrows plugin = AdvancedArrows.instance;

    @EventHandler
    public void arrowFireEvent(ProjectileLaunchEvent e) {
        if (e.getEntity() instanceof Arrow) {
            if (e.getEntity().getShooter() instanceof Player) {
                Player p = (Player) e.getEntity().getShooter();
                if (p.getItemInHand().getType().equals(org.bukkit.Material.BOW)) {
                    if (p.getItemInHand().getItemMeta().getLore().contains(Methods.traileffect)) {
                        for (String s : p.getItemInHand().getItemMeta().getLore()) {
                            if (plugin.trails.doesEffectExist(s)) {
                                //TODO add effect to arrow
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
    public void bowShot(EntityShootBowEvent e) {

    }

}

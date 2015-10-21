package me.HeyAwesomePeople.AdvancedArrows.types;


import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.ThrownPotion;
import org.bukkit.potion.Potion;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.potion.PotionType;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.Hashtable;

public class PotionCreator {

    public void infectEntity(Entity e, String effect) {
        if (PotionEffectType.getByName(effect) == null) return;
        PotionEffectType p = PotionEffectType.getByName(effect);
        if (!e.isValid()) return;
        if (e instanceof LivingEntity) {
            LivingEntity en = (LivingEntity) e;
            Bukkit.broadcastMessage("Duration: " + p.getDurationModifier());
            //TODO test duraation modifier
            en.addPotionEffect(new PotionEffect(p, (int) p.getDurationModifier(), 1));
        }
    }

    public void breakBottledEffect(Location l, String effect) {
        if (PotionEffectType.getByName(effect) == null) return;
        Potion pot = (Potion) l.getWorld().spawn(l, ThrownPotion.class);
        pot.setType(PotionType.getByEffect(PotionEffectType.getByName(effect)));
        pot.setSplash(true);
    }

    //TODO test
    public String getAllEffects() {
        try {
            Field f = PotionEffectType.class.getClass().getDeclaredField("byName");
            f.setAccessible(true);
            Hashtable map = (Hashtable) f.get(PotionEffectType.class.getClass());
            return Arrays.toString(map.keySet().toArray());
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return null;
    }

    public boolean doesPotionExist(String name) {
        if (PotionEffectType.getByName(name) == null) {
            return false;
        }
        return true;
    }

}

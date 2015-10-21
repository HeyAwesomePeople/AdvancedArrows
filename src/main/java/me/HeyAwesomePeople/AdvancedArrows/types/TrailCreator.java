package me.HeyAwesomePeople.AdvancedArrows.types;


import me.HeyAwesomePeople.AdvancedArrows.libs.ParticleEffect;
import org.bukkit.Location;

import java.util.Arrays;
import java.util.Random;

public class TrailCreator {

    public float r(Double min, Double max) {
        Float n = (float) Math.random();
        return (float) (min + (n * (max - min)));
    }

    public float r2(Double max, Double min) {
        Float n = (float) Math.random();
        return (float) (min + (n * (max - min)));
    }

    public int r3(Integer max, Integer min) {
        Random random = new Random();
        return random.nextInt((max - min) + min) + 1;
    }

    // Offset 0.5
    // Speed 0.1
    // Amount 2
    public void createParticle(String effectName, Location l) {
        if (ParticleEffect.fromName(effectName) != null) {
            //noinspection ConstantConditions
            ParticleEffect.fromName(effectName).display(
                    r(0.2, 0.5),
                    r(0.2, 0.5),
                    r(0.2, 0.5),
                    r2(0.1, 0.2),
                    r3(1, 3),
                    l.add(0.5D, 0.5D, 0.5D),
                    100D);
        }
    }

    public String getAllEffects() {
        return Arrays.toString(ParticleEffect.NAME_MAP.keySet().toArray());
    }

    public boolean doesEffectExist(String effectName) {
        if (ParticleEffect.fromName(effectName) == null) {
            return false;
        }
        return true;
    }

}

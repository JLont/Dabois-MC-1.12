package me.freakjoost.Dabois.module.combat;

import java.util.Iterator;

import me.freakjoost.Dabois.event.EventTarget;
import me.freakjoost.Dabois.event.events.EventUpdate;
import me.freakjoost.Dabois.module.Category;
import me.freakjoost.Dabois.module.Module;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.EnumHand;

import org.lwjgl.input.Keyboard;

public class Killaura extends Module {

	public Killaura() {
		super("Killaura", Keyboard.KEY_R, Category.COMBAT);
	}

	public void onEnable() {
		super.onEnable();
	}

	public void onDisable() {
		super.onDisable();
	}

	@EventTarget
	public void onUpdate(EventUpdate e) {
		if(!this.isToggled())
            return;
		
        for(Iterator<Entity> entities = mc.world.loadedEntityList.iterator(); entities.hasNext();) {
            Object theObject = entities.next();
            if(theObject instanceof EntityLivingBase) {
                EntityLivingBase entity = (EntityLivingBase) theObject;
               
                if(entity instanceof EntityPlayerSP) continue;
                
                if(mc.player.getDistanceToEntity(entity) <= 4.5F) {
                    if(entity.isEntityAlive()) {
                        mc.playerController.attackEntity(mc.player, entity);
                        mc.player.swingArm(EnumHand.MAIN_HAND);
                        continue;
                    }
                }
            }
        }
	}
}

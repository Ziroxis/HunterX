package com.izako.hunterx.data.hunterdata;

import com.izako.hunterx.Main;

import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

public class HunterDataEvent {

	public ResourceLocation key = new ResourceLocation(Main.MODID, "hunter_data");
	  @SubscribeEvent
	  public void registerCapability(AttachCapabilitiesEvent<Entity> event) {
		  if(event.getObject() instanceof LivingEntity) {
			  if(!event.getCapabilities().containsKey(key)) {
			  event.addCapability(key, new HunterDataProvider());
			  }
		  }
	  }
}

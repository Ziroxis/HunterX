package com.izako.hunterx.data.abilitydata;

import com.izako.hunterx.Main;
import com.izako.hunterx.data.hunterdata.HunterDataCapability;
import com.izako.hunterx.data.hunterdata.IHunterData;
import com.izako.hunterx.init.ModKeybindings;
import com.izako.hunterx.izapi.ability.Ability;
import com.izako.hunterx.izapi.ability.Ability.AbilityType;
import com.izako.hunterx.izapi.ability.ChargeableAbility;
import com.izako.hunterx.izapi.ability.PassiveAbility;
import com.izako.hunterx.networking.ModidPacketHandler;
import com.izako.hunterx.networking.packets.AbilityChargingEndPacket;

import net.minecraft.client.settings.KeyBinding;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraftforge.event.entity.living.LivingEvent.LivingUpdateEvent;
import net.minecraftforge.eventbus.api.Event.Result;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = Main.MODID)
public class AbilityDataUpdateEvent {

	@SubscribeEvent
	public static void livingUpdate(LivingUpdateEvent e) {
		if (e.getEntityLiving() instanceof PlayerEntity) {
			PlayerEntity p = (PlayerEntity) e.getEntityLiving();
			IHunterData datas = HunterDataCapability.get(p);
			IAbilityData data = AbilityDataCapability.get(p);
			for (int i = 0; i < 8; i++) {
				Ability a = data.getAbilityInSlot(i);
				if (a != null) {
					if (a.getCooldown() > 0) {
						a.setCooldown(a.getCooldown() - 1);
					}
					if (a.isCharging()) {
						for (KeyBinding kb : ModKeybindings.ABILITYSLOTS) {
							if (kb.getKey().getKeyCode() == ModKeybindings.getKeybindFromSlot(a.getSlot()).getKey()
									.getKeyCode()) {
								if (!kb.isKeyDown()) {
									a.setCharging(false);
									((ChargeableAbility) a).onEndCharging(p);
									a.setCooldown(a.props.maxCooldown);
									a.setChargingTimer(0);
									ModidPacketHandler.INSTANCE.sendToServer(new AbilityChargingEndPacket(a.getSlot()));
								} else if(a.getChargingTimer() >= a.props.maxCharging) {

									((ChargeableAbility) a).onEndCharging(p);
									a.setCooldown(a.props.maxCooldown);
									a.setCharging(false);
									a.setChargingTimer(0);
									ModidPacketHandler.INSTANCE.sendToServer(new AbilityChargingEndPacket(a.getSlot()));

								}
							}
						}
						if(a.getChargingTimer() < a.props.maxCharging) {
							a.setChargingTimer(a.getChargingTimer() + 1);
							((ChargeableAbility) a).duringCharging(p);
							a.consumeAura(p);
						}
					}
					if (a.props.type == AbilityType.PASSIVE) {
						if (a.isInPassive()) {
							if (a.getPassiveTimer() > 0) {
								a.setPassiveTimer(a.getPassiveTimer() - 1);
							}
							if (a.getPassiveTimer() == 0 && a.props.maxPassive != Integer.MAX_VALUE) {
								a.setInPassive(false);
								((PassiveAbility) a).onEndPassive(p);
								a.setCooldown(a.props.maxCooldown);
								a.setPassiveTimer(a.props.maxPassive);
								continue;

							}
							((PassiveAbility) a).duringPassive(p);
							a.consumeAura(p);
						}
					}
				}
			}
			if(data.getCurrentNen() < data.getNenCapacity()) {
				if(Ability.canRegenAura(p)) {
				data.setCurrentNen((int) (data.getCurrentNen() + Math.ceil(data.getNenCapacity() / 200.0)));
				}
				}
			
		}
		e.setResult(Result.DEFAULT);
	}
}
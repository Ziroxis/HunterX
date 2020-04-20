package com.izako.hunterx.izapi.ability;

import net.minecraft.entity.player.PlayerEntity;

public abstract class ChargeableAbility  extends Ability{

	public ChargeableAbility() {
		super();
		this.setType(AbilityType.CHARGING);
	}
	public abstract void onStartCharging(PlayerEntity p);
	public abstract void duringCharging(PlayerEntity p);
	public abstract void onEndCharging(PlayerEntity p);
}

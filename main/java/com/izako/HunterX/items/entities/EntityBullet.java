package com.izako.HunterX.items.entities;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.IProjectile;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.projectile.EntityThrowable;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.DamageSource;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.World;

public class EntityBullet extends EntityThrowable {
	private EntityPlayer owner;

	public EntityBullet(World worldIn) {
		super(worldIn);
		
	}

	public EntityBullet(World worldIn, EntityLivingBase throwerIn) {
		super(worldIn, throwerIn);
		
	}

	public EntityBullet(World worldIn, EntityPlayer playerIn, double x, double y, double z) {
		super(worldIn, x, y, z);
		owner = playerIn;
		

	}
	
	@Override
	public void onEntityUpdate() {
		super.onEntityUpdate();
		
		if(this.ticksExisted >= 40) {
			this.setDead();
		}
	}

	@Override
	protected void onImpact(RayTraceResult result) {
		
		if (!this.world.isRemote) {

			if (!this.world.isRemote) {
				this.world.setEntityState(this, (byte) 3);
				
				if (result.entityHit != null && result.entityHit != owner) {
					int damageAmount = 12;
					result.entityHit.attackEntityFrom(DamageSource.causeThrownDamage(this, this.thrower), (float) damageAmount);
					this.setDead();

				}
				
			}
			
			
		
	}
		
	}

	
	
	

}

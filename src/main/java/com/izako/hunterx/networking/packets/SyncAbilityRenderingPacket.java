package com.izako.hunterx.networking.packets;

import java.util.function.Supplier;

import com.izako.hunterx.data.abilitydata.AbilityDataCapability;
import com.izako.hunterx.data.abilitydata.IAbilityData;
import com.izako.hunterx.init.ModAbilities;
import com.izako.hunterx.izapi.IZAHelper;
import com.izako.hunterx.izapi.ability.Ability;

import net.minecraft.client.Minecraft;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.network.PacketBuffer;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.fml.network.NetworkDirection;
import net.minecraftforge.fml.network.NetworkEvent;

public class SyncAbilityRenderingPacket {



	
	public SyncAbilityRenderingPacket() {}

	String id;
	int entity;
	boolean turnOn;
	public SyncAbilityRenderingPacket(String id, int entity, boolean turnOn) {

		this.id = id;
		this.entity = entity;
		this.turnOn = turnOn;
	}
	
	public void encode(PacketBuffer buf) {

		buf.writeInt(id.length());
		buf.writeString(id);
		buf.writeInt(entity);
		buf.writeBoolean(turnOn);
	}
	
	public static SyncAbilityRenderingPacket decode(PacketBuffer buf) {
		SyncAbilityRenderingPacket msg = new SyncAbilityRenderingPacket();
		int length = buf.readInt();
		msg.id = buf.readString(length);
		msg.entity = buf.readInt();
		msg.turnOn = buf.readBoolean();
		return msg;
	}
	public static void handle(SyncAbilityRenderingPacket msg,  final Supplier<NetworkEvent.Context> ctx) {
		if(ctx.get().getDirection() == NetworkDirection.PLAY_TO_CLIENT) {
			ctx.get().enqueueWork(() -> {
				ClientHandler.handle(msg);
			});
		}
		ctx.get().setPacketHandled(true);
	}
	public static class ClientHandler{
		@OnlyIn(Dist.CLIENT)
		public static void handle(SyncAbilityRenderingPacket msg) {

			PlayerEntity p = Minecraft.getInstance().player;
			LivingEntity abilityUser = (LivingEntity) p.world.getEntityByID(msg.entity);
			IAbilityData data = AbilityDataCapability.get(abilityUser);
			IAbilityData clientData = AbilityDataCapability.get(p);
			if(clientData.isNenUser()) {
				Ability abl;
			 if(abilityUser instanceof PlayerEntity) {
             abl = data.getSlotAbility(ModAbilities.getAbilityOfId(msg.id));
			 } else {
				 abl = data.getAbility(ModAbilities.getAbilityOfId(msg.id));
			 }
            try {
			if(msg.turnOn) {
				abl.initiateAbility();
			} else {
				abl.stopAbility(p);
			}
            } catch(NullPointerException e) {
            	abl = IZAHelper.addSlotAbility(ModAbilities.getNewInstanceFromId(msg.id), abilityUser);
            	if(msg.turnOn) {
            		abl.initiateAbility();
            	} else {
            		abl.stopAbility(p);
            	}
            }
			}
		}
	}

}

package com.izako.wypi.network.packets.client;

import java.util.function.Supplier;

import com.izako.wypi.data.ability.AbilityDataCapability;
import com.izako.wypi.data.ability.IAbilityData;
import com.izako.wypi.network.WyNetwork;
import com.izako.wypi.network.packets.server.SSyncAbilityDataPacket;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.INBT;
import net.minecraft.network.PacketBuffer;
import net.minecraftforge.fml.network.NetworkDirection;
import net.minecraftforge.fml.network.NetworkEvent;

public class CSyncAbilityDataPacket
{
	private INBT data;

	public CSyncAbilityDataPacket() {}

	public CSyncAbilityDataPacket(IAbilityData props)
	{
		this.data = new CompoundNBT();
		this.data = AbilityDataCapability.INSTANCE.getStorage().writeNBT(AbilityDataCapability.INSTANCE, props, null);
	}

	public void encode(PacketBuffer buffer)
	{
		buffer.writeCompoundTag((CompoundNBT) this.data);
	}

	public static CSyncAbilityDataPacket decode(PacketBuffer buffer)
	{
		CSyncAbilityDataPacket msg = new CSyncAbilityDataPacket();
		msg.data = buffer.readCompoundTag();
		return msg;
	}

	public static void handle(CSyncAbilityDataPacket message, final Supplier<NetworkEvent.Context> ctx)
	{
		if (ctx.get().getDirection() == NetworkDirection.PLAY_TO_SERVER)
		{
			ctx.get().enqueueWork(() ->
			{
				PlayerEntity player = ctx.get().getSender();
				IAbilityData props = AbilityDataCapability.get(player);

				AbilityDataCapability.INSTANCE.getStorage().readNBT(AbilityDataCapability.INSTANCE, props, null, message.data);
				
				WyNetwork.sendTo(new SSyncAbilityDataPacket(props), (ServerPlayerEntity) player);				
			});
		}
		ctx.get().setPacketHandled(true);
	}

}

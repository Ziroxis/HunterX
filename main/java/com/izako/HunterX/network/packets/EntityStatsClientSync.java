package com.izako.HunterX.network.packets;

import javax.annotation.Nullable;

import io.netty.buffer.ByteBuf;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;

public class EntityStatsClientSync implements IMessage{

	  // A default constructor is always required
	  public EntityStatsClientSync(){}

	  
	 //the amount for the current 4 stats is, 1 for health, 2 for defense, 3 for speed, 4 for attack, 5 for hasKilledKiriko
	//6 for hasStarted2ndPhase, 7 for timeHasRun
	  public Double amount;
	public int statType;
	public boolean value;
	  public EntityStatsClientSync(@Nullable Double amount, int statType, @Nullable boolean value) {
	    this.amount = amount;
	    this.statType = statType;
	    this.value = value;
	  }

	  @Override public void toBytes(ByteBuf buf) {
	    // Writes the int into the buf
	    buf.writeDouble(amount);
	    buf.writeInt(statType);
	    buf.writeBoolean(value);
	  }

	  @Override public void fromBytes(ByteBuf buf) {
	    // Reads the int back from the buf. Note that if you have multiple values, you must read in the same order you wrote.
	    amount = buf.readDouble();
	    statType = buf.readInt();
	    value = buf.readBoolean();
	  }
}

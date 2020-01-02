package com.izako.HunterX.stats.capabilities;

import java.util.HashMap;
import java.util.List;

import javax.annotation.Nullable;

import com.izako.HunterX.abilities.EnumNenType;
import com.izako.HunterX.izapi.abilities.Ability;

import net.minecraft.entity.player.EntityPlayer;

public interface IEntityStats {
	
	//AuraCapacity
	double getAuraCapacity();
	
	void setAuraCapacity(double value);
	
	//CurrentAura
	double getAura();
	
	void setAura(double value);

	//healthstat
	double getHealthStat();
	
	void setHealthStat(double value);
	
	//speedstat
	double getSpeedStat();
	
	void setSpeedStat(double value);
	
	//defensestat
	double getDefenseStat();
	
	void setDefenseStat(double value);
	
	//attackstat
	double getAttackStat();
	
	void setAttackStat(double value);
	
	//exam
	boolean isHunter();
	void setIsHunter(boolean value);
	
	boolean hasKilledKiriko();
	void setHasKilledKiriko(boolean value);
	
	boolean hasStarted2ndPhase();
	void setHasStarted2ndPhase(boolean value);
	
	Double timeHasRun();
	void setTimeHasRun(Double value);
	
	boolean hasStarted3rdPhase();
	void setHasStarted3rdPhase(boolean value);
	
	boolean hasKilledBoss();
	void setHasKilledBoss(boolean value);
	
	//quest api
	
	void giveQuest(String str, Integer value);
	
	HashMap<String, Integer> getQuests();

	void setProgress(String str, Integer value);
	
	
	Integer getProgress(String value);
	
	void giveAbility(Ability ability);
	void removeAbility(Ability ability);
	List<Ability> getAbilities();
	
	boolean isPassiveActive(String str);
	boolean isOnCooldown(String str);
	HashMap<String, Boolean> getIsPassiveActiveAll();
	HashMap<String, Boolean> getIsOnCooldownAll();
	void setIsPassiveActive(boolean value, String str);
	void setIsOnCooldown(boolean value, String str);
	void setAbilityToSlot(Integer slot, Ability a);
	void removeAbilityFromSlot(Ability a);
	Ability[] getSlotsList();
	 Ability getAbilityNonNull(Integer slot);
	 int getNenCapacity();
	 void setNenCapacity(int value);
	 EnumNenType getNenType();
	 void setNenType(EnumNenType value);
	
	
	
	
}

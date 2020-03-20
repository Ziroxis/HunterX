package com.izako.hunterx.items;

import java.util.List;
import java.util.UUID;

import javax.annotation.Nullable;

import com.izako.hunterx.Main;
import com.izako.hunterx.init.ModItems;

import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.ai.attributes.IAttributeInstance;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.IItemPropertyGetter;
import net.minecraft.item.IItemTier;
import net.minecraft.item.ItemStack;
import net.minecraft.item.SwordItem;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.ActionResult;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Hand;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class KurapikaSwordItem extends SwordItem {

	private final UUID attackUUID = UUID.fromString("b34f7773-0533-48ae-8d7b-807bba9983e2");
	
	public KurapikaSwordItem(IItemTier tier, int attackDamageIn, float attackSpeedIn, Properties builder) {
		super(tier, attackDamageIn, attackSpeedIn, builder);
		this.addPropertyOverride(new ResourceLocation("sheath"), this.sheathProperty);
	}

	@Override
	public ActionResult<ItemStack> onItemRightClick(World worldIn, PlayerEntity playerIn, Hand handIn) {

		if (!worldIn.isRemote()) {

			IAttributeInstance attribute = ((LivingEntity) playerIn)
					.getAttribute(SharedMonsterAttributes.ATTACK_DAMAGE);

			AttributeModifier attr = new AttributeModifier(attackUUID, ATTACK_DAMAGE_MODIFIER.toString(), 10,
					AttributeModifier.Operation.ADDITION);
			ItemStack itemstack = playerIn.getHeldItem(handIn);
			if (!itemstack.hasTag()) {
				itemstack.setTag(new CompoundNBT());
			}

			CompoundNBT nbt = itemstack.getTag();
			if (playerIn.isSneaking()) {
				if (nbt.getBoolean("sheathed")) {
					nbt.putBoolean("sheathed", false);

					attribute.removeModifier(attackUUID);
					attribute.applyModifier(attr);
					return new ActionResult<ItemStack>(ActionResultType.SUCCESS, itemstack);

				} else if (!nbt.getBoolean("sheathed")) {
					nbt.putBoolean("sheathed", true);
					attr = new AttributeModifier(attackUUID ,ATTACK_DAMAGE_MODIFIER.toString(), 1,
							AttributeModifier.Operation.ADDITION);

					attribute.removeModifier(attackUUID);
					attribute.applyModifier(attr);
					System.out.print(attribute.getModifiers().toString());
					return new ActionResult<ItemStack>(ActionResultType.SUCCESS, itemstack);

				} else {
				}
				return new ActionResult<ItemStack>(ActionResultType.SUCCESS, itemstack);

			}
		}
		return new ActionResult<ItemStack>(ActionResultType.PASS, playerIn.getHeldItem(handIn));
	}

	private IItemPropertyGetter sheathProperty = (itemStack, world, livingEntity) -> {
		if (itemStack.hasTag()) {
			CompoundNBT nbt = itemStack.getTag();

			if (!nbt.getBoolean("sheathed")) {

				return 1.0f;
			}
		} else {
			return 0.0f;
		}
		return 0.0f;
	};
	   @Override
	   @OnlyIn(Dist.CLIENT)
	   public void addInformation(ItemStack stack, @Nullable World worldIn, List<ITextComponent> tooltip, ITooltipFlag flagIn) {
	  
		   if(stack.hasTag()) {
		   if(!stack.getTag().getBoolean("sheathed")) {
		   tooltip.add(new StringTextComponent("unsheathed"));
		   } else {
			   tooltip.add(new StringTextComponent("sheathed"));
		   }
		   
		   }
	 }


}

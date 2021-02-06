package com.izako.hunterx.items.nen;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableMap.Builder;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.CampfireBlock;
import net.minecraft.block.RotatedPillarBlock;
import net.minecraft.block.material.Material;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.IItemTier;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUseContext;
import net.minecraft.item.ToolItem;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Direction;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.common.ToolType;

public class ConjurerTool extends ToolItem {


	   protected static final Map<Block, Block> BLOCK_STRIPPING_MAP = (new Builder<Block, Block>()).put(Blocks.OAK_WOOD, Blocks.STRIPPED_OAK_WOOD).put(Blocks.OAK_LOG, Blocks.STRIPPED_OAK_LOG).put(Blocks.DARK_OAK_WOOD, Blocks.STRIPPED_DARK_OAK_WOOD).put(Blocks.DARK_OAK_LOG, Blocks.STRIPPED_DARK_OAK_LOG).put(Blocks.ACACIA_WOOD, Blocks.STRIPPED_ACACIA_WOOD).put(Blocks.ACACIA_LOG, Blocks.STRIPPED_ACACIA_LOG).put(Blocks.BIRCH_WOOD, Blocks.STRIPPED_BIRCH_WOOD).put(Blocks.BIRCH_LOG, Blocks.STRIPPED_BIRCH_LOG).put(Blocks.JUNGLE_WOOD, Blocks.STRIPPED_JUNGLE_WOOD).put(Blocks.JUNGLE_LOG, Blocks.STRIPPED_JUNGLE_LOG).put(Blocks.SPRUCE_WOOD, Blocks.STRIPPED_SPRUCE_WOOD).put(Blocks.SPRUCE_LOG, Blocks.STRIPPED_SPRUCE_LOG).build();
	   protected static final Map<Block, BlockState> SHOVEL_LOOKUP = Maps.newHashMap(ImmutableMap.of(Blocks.GRASS_BLOCK, Blocks.GRASS_PATH.getDefaultState()));

	   public ConjurerTool(IItemTier tier, int attackDamageIn, float attackSpeedIn, Item.Properties builder, List<ToolType> types) {
	      super((float)attackDamageIn, attackSpeedIn, tier, ConjurerTool.getEffectiveBlocks(types), ConjurerTool.addToolTypes(builder, types));
	      
	   }

	   /**
	    * Check whether this Item can harvest the given Block
	    */
	   public boolean canHarvestBlock(BlockState blockIn) {
	      Block block = blockIn.getBlock();
	      int i = this.getTier().getHarvestLevel();
	      if (this.getToolTypes(new ItemStack(this)).contains(blockIn.getHarvestTool())) {
	         return i >= blockIn.getHarvestLevel();
	      }
	      Material material = blockIn.getMaterial();
	      return material == Material.ROCK || material == Material.IRON || material == Material.ANVIL;
	   }

	   public float getDestroySpeed(ItemStack stack, BlockState state) {
	      Material material = state.getMaterial();
	      boolean finalFlag = false;
	      boolean pickaxeFlag =  material == Material.IRON || material == Material.ANVIL || material == Material.ROCK;
	      boolean axeFlag =  material == Material.WOOD || material == Material.PLANTS || material == Material.TALL_PLANTS || material == Material.BAMBOO;
	      if(this.getToolTypes(new ItemStack(this)).contains(ToolType.PICKAXE)) {
	    	  finalFlag = finalFlag || pickaxeFlag;
	      }
	      if(this.getToolTypes(new ItemStack(this)).contains(ToolType.AXE)) {
	    	  finalFlag = finalFlag || axeFlag;
	      }
	      System.out.println(state.getMaterial() == Material.IRON);
	      if(!finalFlag) {
	          if (getToolTypes(stack).stream().anyMatch(e -> state.isToolEffective(e))) {return this.getEfficiency(stack);}
	          return ConjurerTool.getEffectiveBlocks(Arrays.asList(this.getToolTypes(stack).toArray(new ToolType[0]))).contains(state.getBlock()) ? this.getEfficiency(stack) : 1.0F;

	      } else {
	      return this.getEfficiency(stack);
	      }
	   }
	   
	   public static Set<Block> getEffectiveBlocks(List<ToolType> types) {
		   Set<Block> pickaxeBlocks = ImmutableSet.of(Blocks.ACTIVATOR_RAIL, Blocks.COAL_ORE, Blocks.COBBLESTONE, Blocks.DETECTOR_RAIL, Blocks.DIAMOND_BLOCK, Blocks.DIAMOND_ORE, Blocks.POWERED_RAIL, Blocks.GOLD_BLOCK, Blocks.GOLD_ORE, Blocks.ICE, Blocks.IRON_BLOCK, Blocks.IRON_ORE, Blocks.LAPIS_BLOCK, Blocks.LAPIS_ORE, Blocks.MOSSY_COBBLESTONE, Blocks.NETHERRACK, Blocks.PACKED_ICE, Blocks.BLUE_ICE, Blocks.RAIL, Blocks.REDSTONE_ORE, Blocks.SANDSTONE, Blocks.CHISELED_SANDSTONE, Blocks.CUT_SANDSTONE, Blocks.CHISELED_RED_SANDSTONE, Blocks.CUT_RED_SANDSTONE, Blocks.RED_SANDSTONE, Blocks.STONE, Blocks.GRANITE, Blocks.POLISHED_GRANITE, Blocks.DIORITE, Blocks.POLISHED_DIORITE, Blocks.ANDESITE, Blocks.POLISHED_ANDESITE, Blocks.STONE_SLAB, Blocks.SMOOTH_STONE_SLAB, Blocks.SANDSTONE_SLAB, Blocks.PETRIFIED_OAK_SLAB, Blocks.COBBLESTONE_SLAB, Blocks.BRICK_SLAB, Blocks.STONE_BRICK_SLAB, Blocks.NETHER_BRICK_SLAB, Blocks.QUARTZ_SLAB, Blocks.RED_SANDSTONE_SLAB, Blocks.PURPUR_SLAB, Blocks.SMOOTH_QUARTZ, Blocks.SMOOTH_RED_SANDSTONE, Blocks.SMOOTH_SANDSTONE, Blocks.SMOOTH_STONE, Blocks.STONE_BUTTON, Blocks.STONE_PRESSURE_PLATE, Blocks.POLISHED_GRANITE_SLAB, Blocks.SMOOTH_RED_SANDSTONE_SLAB, Blocks.MOSSY_STONE_BRICK_SLAB, Blocks.POLISHED_DIORITE_SLAB, Blocks.MOSSY_COBBLESTONE_SLAB, Blocks.END_STONE_BRICK_SLAB, Blocks.SMOOTH_SANDSTONE_SLAB, Blocks.SMOOTH_QUARTZ_SLAB, Blocks.GRANITE_SLAB, Blocks.ANDESITE_SLAB, Blocks.RED_NETHER_BRICK_SLAB, Blocks.POLISHED_ANDESITE_SLAB, Blocks.DIORITE_SLAB, Blocks.SHULKER_BOX, Blocks.BLACK_SHULKER_BOX, Blocks.BLUE_SHULKER_BOX, Blocks.BROWN_SHULKER_BOX, Blocks.CYAN_SHULKER_BOX, Blocks.GRAY_SHULKER_BOX, Blocks.GREEN_SHULKER_BOX, Blocks.LIGHT_BLUE_SHULKER_BOX, Blocks.LIGHT_GRAY_SHULKER_BOX, Blocks.LIME_SHULKER_BOX, Blocks.MAGENTA_SHULKER_BOX, Blocks.ORANGE_SHULKER_BOX, Blocks.PINK_SHULKER_BOX, Blocks.PURPLE_SHULKER_BOX, Blocks.RED_SHULKER_BOX, Blocks.WHITE_SHULKER_BOX, Blocks.YELLOW_SHULKER_BOX,Blocks.OBSIDIAN);
		   Set<Block> axeBlocks = Sets.newHashSet(Blocks.OAK_PLANKS, Blocks.SPRUCE_PLANKS, Blocks.BIRCH_PLANKS, Blocks.JUNGLE_PLANKS, Blocks.ACACIA_PLANKS, Blocks.DARK_OAK_PLANKS, Blocks.BOOKSHELF, Blocks.OAK_WOOD, Blocks.SPRUCE_WOOD, Blocks.BIRCH_WOOD, Blocks.JUNGLE_WOOD, Blocks.ACACIA_WOOD, Blocks.DARK_OAK_WOOD, Blocks.OAK_LOG, Blocks.SPRUCE_LOG, Blocks.BIRCH_LOG, Blocks.JUNGLE_LOG, Blocks.ACACIA_LOG, Blocks.DARK_OAK_LOG, Blocks.CHEST, Blocks.PUMPKIN, Blocks.CARVED_PUMPKIN, Blocks.JACK_O_LANTERN, Blocks.MELON, Blocks.LADDER, Blocks.SCAFFOLDING, Blocks.OAK_BUTTON, Blocks.SPRUCE_BUTTON, Blocks.BIRCH_BUTTON, Blocks.JUNGLE_BUTTON, Blocks.DARK_OAK_BUTTON, Blocks.ACACIA_BUTTON, Blocks.OAK_PRESSURE_PLATE, Blocks.SPRUCE_PRESSURE_PLATE, Blocks.BIRCH_PRESSURE_PLATE, Blocks.JUNGLE_PRESSURE_PLATE, Blocks.DARK_OAK_PRESSURE_PLATE, Blocks.ACACIA_PRESSURE_PLATE);
		   Set<Block> shovelBlocks = Sets.newHashSet(Blocks.CLAY, Blocks.DIRT, Blocks.COARSE_DIRT, Blocks.PODZOL, Blocks.FARMLAND, Blocks.GRASS_BLOCK, Blocks.GRAVEL, Blocks.MYCELIUM, Blocks.SAND, Blocks.RED_SAND, Blocks.SNOW_BLOCK, Blocks.SNOW, Blocks.SOUL_SAND, Blocks.GRASS_PATH, Blocks.WHITE_CONCRETE_POWDER, Blocks.ORANGE_CONCRETE_POWDER, Blocks.MAGENTA_CONCRETE_POWDER, Blocks.LIGHT_BLUE_CONCRETE_POWDER, Blocks.YELLOW_CONCRETE_POWDER, Blocks.LIME_CONCRETE_POWDER, Blocks.PINK_CONCRETE_POWDER, Blocks.GRAY_CONCRETE_POWDER, Blocks.LIGHT_GRAY_CONCRETE_POWDER, Blocks.CYAN_CONCRETE_POWDER, Blocks.PURPLE_CONCRETE_POWDER, Blocks.BLUE_CONCRETE_POWDER, Blocks.BROWN_CONCRETE_POWDER, Blocks.GREEN_CONCRETE_POWDER, Blocks.RED_CONCRETE_POWDER, Blocks.BLACK_CONCRETE_POWDER);
		   Set<Block> finalSet = ImmutableSet.of();
		   if(types.contains(ToolType.PICKAXE)) {
			   List<Block> list = new ArrayList(Arrays.asList(finalSet.toArray(new Block[0])));
			   list.addAll(pickaxeBlocks);
			   
			   finalSet = ImmutableSet.copyOf(list.toArray(new Block[0]));
		   }
		   
		   if(types.contains(ToolType.AXE)) {
			   List<Block> list = new ArrayList(Arrays.asList(finalSet.toArray(new Block[0])));
			   list.addAll(axeBlocks);
			   
			   finalSet = ImmutableSet.copyOf(list.toArray(new Block[0]));
		   }
		   
		   if(types.contains(ToolType.SHOVEL)) {
			   List<Block> list = new ArrayList(Arrays.asList(finalSet.toArray(new Block[0])));
			   list.addAll(shovelBlocks);
			   
			   finalSet = ImmutableSet.copyOf(list.toArray(new Block[0]));

		   }

		   return finalSet;
	   }

	   public boolean hitEntity(ItemStack stack, LivingEntity target, LivingEntity attacker) {
		      return true;
		   }

	   public boolean onBlockDestroyed(ItemStack stack, World worldIn, BlockState state, BlockPos pos, LivingEntity entityLiving) {
		      if (!worldIn.isRemote && state.getBlockHardness(worldIn, pos) != 0.0F) {
		      }

		      return true;
		   }

	   
	   private static Item.Properties addToolTypes(Item.Properties props, List<ToolType> types) {
		   for(ToolType type : types) {
			   props.addToolType(type, 3);
		   }
		   return props;
	   }

	   public ActionResultType onItemUse(ItemUseContext context) {
		      World world = context.getWorld();
		      BlockPos blockpos = context.getPos();
		      BlockState blockstate = world.getBlockState(blockpos);
		      Block block = BLOCK_STRIPPING_MAP.get(blockstate.getBlock());
		      if (block != null && this.getToolTypes(new ItemStack(this)).contains(ToolType.AXE)) {
		         PlayerEntity playerentity = context.getPlayer();
		         world.playSound(playerentity, blockpos, SoundEvents.ITEM_AXE_STRIP, SoundCategory.BLOCKS, 1.0F, 1.0F);
		         if (!world.isRemote) {
		            world.setBlockState(blockpos, block.getDefaultState().with(RotatedPillarBlock.AXIS, blockstate.get(RotatedPillarBlock.AXIS)), 11);
		            if (playerentity != null) {
		               context.getItem().damageItem(1, playerentity, (p_220040_1_) -> {
		                  p_220040_1_.sendBreakAnimation(context.getHand());
		               });
		            }
		         }

		         return ActionResultType.SUCCESS;
		      } else {
		    	  
		          if (context.getFace() == Direction.DOWN) {
		              return ActionResultType.PASS;
		           } else if(this.getToolTypes(new ItemStack(this)).contains(ToolType.SHOVEL)) {
		              PlayerEntity playerentity = context.getPlayer();
		              BlockState blockstate1 = SHOVEL_LOOKUP.get(blockstate.getBlock());
		              BlockState blockstate2 = null;
		              if (blockstate1 != null && world.isAirBlock(blockpos.up())) {
		                 world.playSound(playerentity, blockpos, SoundEvents.ITEM_SHOVEL_FLATTEN, SoundCategory.BLOCKS, 1.0F, 1.0F);
		                 blockstate2 = blockstate1;
		              } else if (blockstate.getBlock() instanceof CampfireBlock && blockstate.get(CampfireBlock.LIT)) {
		                 world.playEvent((PlayerEntity)null, 1009, blockpos, 0);
		                 blockstate2 = blockstate.with(CampfireBlock.LIT, Boolean.valueOf(false));
		              }

		              if (blockstate2 != null) {
		                 if (!world.isRemote) {
		                    world.setBlockState(blockpos, blockstate2, 11);
		                    if (playerentity != null) {
		                       context.getItem().damageItem(1, playerentity, (p_220041_1_) -> {
		                          p_220041_1_.sendBreakAnimation(context.getHand());
		                       });
		                    }
		                 }

		                 return ActionResultType.SUCCESS;
		              } else {
		                 return ActionResultType.PASS;
		              }
		           }
	              return ActionResultType.PASS;
		      }
		   }

	   
	   @Override
	public void inventoryTick(ItemStack stack, World worldIn, Entity entityIn, int itemSlot, boolean isSelected) {
		
		   if(stack.hasTag() && stack.getTag().hasUniqueId("nenowner")) {
			   UUID ownerId = stack.getTag().getUniqueId("nenowner");
			   UUID otherId = entityIn.getUniqueID();
			   if(ownerId.compareTo(entityIn.getUniqueID()) != 0) {
				   entityIn.replaceItemInInventory(itemSlot, ItemStack.EMPTY);
			   }
		   }
		   
		   super.inventoryTick(stack, worldIn, entityIn, itemSlot, isSelected);
	}

	public float getEfficiency(ItemStack stack) {
		   if(stack.hasTag() && stack.getTag().contains("efficiency")) {
			  return stack.getTag().getFloat("efficiency");
		   }
		   return this.efficiency;
	   }
	   
	   
}

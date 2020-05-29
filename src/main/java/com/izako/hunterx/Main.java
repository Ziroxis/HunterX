package com.izako.hunterx;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.izako.hunterx.commands.AbilityArgument;
import com.izako.hunterx.commands.HunterXCommand;
import com.izako.hunterx.data.abilitydata.AbilityDataCapability;
import com.izako.hunterx.data.hunterdata.HunterDataCapability;
import com.izako.hunterx.events.EventsHandler;
import com.izako.hunterx.init.ModAbilities;
import com.izako.hunterx.init.ModKeybindings;
import com.izako.hunterx.init.ModQuests;
import com.izako.hunterx.init.ModStructures;
import com.izako.hunterx.networking.ModidPacketHandler;
import com.izako.hunterx.registerers.ClientSideRegistry;
import com.izako.wypi.WyRegistry;

import net.minecraft.world.biome.Biome;
import net.minecraft.world.gen.GenerationStage;
import net.minecraft.world.gen.feature.IFeatureConfig;
import net.minecraft.world.gen.placement.IPlacementConfig;
import net.minecraft.world.gen.placement.Placement;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.event.server.FMLServerStartingEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.ForgeRegistries;

@Mod(Main.MODID)
public final class Main {

	public static final String MODID = "hntrx";
	public static final Logger LOGGER = LogManager.getLogger(MODID);

	public Main() {
		EventsHandler.registerEvents();
		ModidPacketHandler.registerPackets();
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::clientSetup);
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::commonSetup);
        MinecraftForge.EVENT_BUS.addListener(this::serverStart);
	
        IEventBus bus = FMLJavaModLoadingContext.get().getModEventBus();
        WyRegistry.PARTICLE_TYPES.register(bus);
	}
	
	private void clientSetup(FMLClientSetupEvent event) {
		ClientSideRegistry.RegisterEntityRenderers();
		ModKeybindings.init();
	}

	private  void commonSetup(FMLCommonSetupEvent event) {
		EventsHandler.registerEvents();
		ModAbilities.register();
		AbilityArgument.register();
		HunterDataCapability.register();
		AbilityDataCapability.register();
		ModQuests.questRegister();
		
        ForgeRegistries.BIOMES.getValues().stream().forEach((biome -> {
            if (biome.getCategory() == Biome.Category.FOREST || biome.getCategory() == Biome.Category.PLAINS) {
                biome.addStructure(ModStructures.BLIMP, IFeatureConfig.NO_FEATURE_CONFIG);
                biome.addFeature(GenerationStage.Decoration.SURFACE_STRUCTURES, Biome.createDecoratedFeature(ModStructures.BLIMP, IFeatureConfig.NO_FEATURE_CONFIG, Placement.NOPE, IPlacementConfig.NO_PLACEMENT_CONFIG));

            }
        }));
        

	}


	private void serverStart(FMLServerStartingEvent event) {
		HunterXCommand.register(event.getCommandDispatcher());
	}
}

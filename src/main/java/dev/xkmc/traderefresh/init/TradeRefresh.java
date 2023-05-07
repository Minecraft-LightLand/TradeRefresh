package dev.xkmc.traderefresh.init;

import dev.xkmc.l2serial.network.BasePacketHandler;
import dev.xkmc.traderefresh.common.RestockEventHandler;
import dev.xkmc.traderefresh.network.RefreshToServer;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.network.NetworkDirection;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

// The value here should match an entry in the META-INF/mods.toml file
@Mod(TradeRefresh.MODID)
public class TradeRefresh {

	public static final String MODID = "traderefresh";
	public static final Logger LOGGER = LogManager.getLogger();

	public static final BasePacketHandler HANDLER = new BasePacketHandler(
			new ResourceLocation(MODID, "main"), 1
			, e -> e.create(RefreshToServer.class, NetworkDirection.PLAY_TO_SERVER)
	);

	private static void registerForgeEvents() {
		MinecraftForge.EVENT_BUS.register(RestockEventHandler.class);
	}

	private static void registerModBusEvents(IEventBus bus) {
	}

	public TradeRefresh() {
		FMLJavaModLoadingContext ctx = FMLJavaModLoadingContext.get();
		IEventBus bus = ctx.getModEventBus();
		registerModBusEvents(bus);
		DistExecutor.unsafeRunWhenOn(Dist.CLIENT, () -> () -> TradeRefreshClient.onCtorClient(bus, MinecraftForge.EVENT_BUS));
		registerForgeEvents();
	}

}

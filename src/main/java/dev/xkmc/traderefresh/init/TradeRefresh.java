package dev.xkmc.traderefresh.init;

import dev.xkmc.l2serial.network.PacketHandler;
import dev.xkmc.l2serial.serialization.custom_handler.Handlers;
import dev.xkmc.traderefresh.network.RefreshToServer;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@Mod(TradeRefresh.MODID)
@EventBusSubscriber(modid = TradeRefresh.MODID)
public class TradeRefresh {

	public static final String MODID = "traderefresh";
	public static final Logger LOGGER = LogManager.getLogger();

	public static final PacketHandler HANDLER = new PacketHandler(MODID, 1,
			e -> e.create(RefreshToServer.class, PacketHandler.NetDir.PLAY_TO_SERVER)
	);

	public static ResourceLocation loc(String path) {
		return ResourceLocation.fromNamespaceAndPath(MODID, path);
	}

	public TradeRefresh() {
		Handlers.register();
		TRConfig.init();
	}

	@SubscribeEvent
	public static void onCommonInit(FMLCommonSetupEvent event) {
	}

}

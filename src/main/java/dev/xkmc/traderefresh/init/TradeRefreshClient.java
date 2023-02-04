package dev.xkmc.traderefresh.init;

import dev.xkmc.traderefresh.client.TradeScreenEventHandler;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.event.RegisterKeyMappingsEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;

public class TradeRefreshClient {

	public static void onCtorClient(IEventBus bus, IEventBus eventBus) {
		bus.addListener(TradeRefreshClient::clientSetup);
		bus.addListener(TradeRefreshClient::registerKeys);
		eventBus.register(TradeScreenEventHandler.class);
	}

	@SubscribeEvent
	public static void clientSetup(FMLClientSetupEvent event) {
	}

	@OnlyIn(Dist.CLIENT)
	public static void registerKeys(RegisterKeyMappingsEvent event) {
		event.register(Keys.REFRESH.map);
	}

}

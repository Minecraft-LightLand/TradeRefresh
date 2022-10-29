package dev.xkmc.mastertrader.init;

import dev.xkmc.mastertrader.client.TradeScreenEventHandler;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.event.RegisterGuiOverlaysEvent;
import net.minecraftforge.client.event.RegisterKeyMappingsEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;

public class MasterTraderClient {

	public static void onCtorClient(IEventBus bus, IEventBus eventBus) {
		bus.addListener(MasterTraderClient::clientSetup);
		bus.addListener(MasterTraderClient::registerOverlay);
		bus.addListener(MasterTraderClient::registerKeys);
		eventBus.register(TradeScreenEventHandler.class);
	}

	@SubscribeEvent
	public static void clientSetup(FMLClientSetupEvent event) {
		event.enqueueWork(() -> {
		});
	}

	@OnlyIn(Dist.CLIENT)
	public static void registerOverlay(RegisterGuiOverlaysEvent event) {
	}

	@OnlyIn(Dist.CLIENT)
	public static void registerKeys(RegisterKeyMappingsEvent event) {
		event.register(Keys.REFRESH.map);
	}

}

package dev.xkmc.traderefresh.init;

import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.ModLoadingContext;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;
import net.neoforged.neoforge.client.event.RegisterKeyMappingsEvent;
import net.neoforged.neoforge.client.gui.ConfigurationScreen;
import net.neoforged.neoforge.client.gui.IConfigScreenFactory;

@EventBusSubscriber(value = Dist.CLIENT, modid = TradeRefresh.MODID)
public class TradeRefreshClient {

	@SubscribeEvent
	public static void clientSetup(FMLClientSetupEvent event) {
		ModLoadingContext.get().registerExtensionPoint(
				IConfigScreenFactory.class,
				() -> ConfigurationScreen::new
		);
	}

	@SubscribeEvent
	public static void registerKeys(RegisterKeyMappingsEvent event) {
		event.register(Keys.REFRESH.map);
	}

}

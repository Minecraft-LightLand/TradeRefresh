package dev.xkmc.traderefresh.init;

import net.minecraft.client.KeyMapping;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;
import net.neoforged.neoforge.client.event.RegisterKeyMappingsEvent;

@EventBusSubscriber(value = Dist.CLIENT, modid = TradeRefresh.MODID)
public class TradeRefreshClient {

	public static final KeyMapping.Category CATEGORY = new KeyMapping.Category(TradeRefresh.loc("main"));

	@SubscribeEvent
	public static void clientSetup(FMLClientSetupEvent event) {
	}

	@SubscribeEvent
	public static void registerKeys(RegisterKeyMappingsEvent event) {
		event.registerCategory(CATEGORY);
		event.register(Keys.REFRESH.map);
	}

}

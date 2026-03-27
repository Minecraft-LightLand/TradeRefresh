package dev.xkmc.traderefresh.client;

import dev.xkmc.traderefresh.init.Keys;
import dev.xkmc.traderefresh.init.TRConfig;
import dev.xkmc.traderefresh.init.TradeRefresh;
import dev.xkmc.traderefresh.network.RefreshToServer;
import net.minecraft.client.gui.screens.inventory.MerchantScreen;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.ScreenEvent;

@EventBusSubscriber(value = Dist.CLIENT, modid = TradeRefresh.MODID)
public class TradeScreenEventHandler {

	@SubscribeEvent
	public static void onInventoryGuiInit(ScreenEvent.Init.Post evt) {
		if (evt.getScreen() instanceof MerchantScreen gui)
			if (TRConfig.SERVER.alwaysAllowRefresh.get() || gui.getMenu().getTraderXp() == 0)
				evt.addListener(new RefreshButton(gui));

	}

	@SubscribeEvent
	public static void onKeyPressed(ScreenEvent.KeyPressed.Pre evt) {
		if (evt.getScreen() instanceof MerchantScreen gui) {
			if (TRConfig.SERVER.alwaysAllowRefresh.get() || gui.getMenu().getTraderXp() == 0)
				if (Keys.REFRESH.map.matches(evt.getKeyEvent()))
					TradeRefresh.HANDLER.toServer(new RefreshToServer());
		}
	}

}

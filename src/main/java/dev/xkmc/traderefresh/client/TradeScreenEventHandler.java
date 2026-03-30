package dev.xkmc.traderefresh.client;

import dev.xkmc.traderefresh.compat.JEIMenuTest;
import dev.xkmc.traderefresh.init.Keys;
import dev.xkmc.traderefresh.init.TradeRefresh;
import dev.xkmc.traderefresh.network.RefreshToServer;
import net.minecraft.client.gui.screens.inventory.MerchantScreen;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.EventPriority;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.ScreenEvent;

@EventBusSubscriber(value = Dist.CLIENT, modid = TradeRefresh.MODID, bus = EventBusSubscriber.Bus.GAME)
public class TradeScreenEventHandler {

	@SubscribeEvent
	public static void onInventoryGuiInit(ScreenEvent.Init.Post evt) {
		if (evt.getScreen() instanceof MerchantScreen gui && gui.getMenu().getTraderXp() == 0) {
			evt.addListener(new RefreshButton(gui));
		}
	}

	@SubscribeEvent(priority = EventPriority.HIGH)
	public static void onKeyPressed(ScreenEvent.KeyPressed.Pre evt) {
		if (evt.getScreen() instanceof MerchantScreen gui && gui.getMenu().getTraderXp() == 0) {
			if (Keys.REFRESH.map.matches(evt.getKeyCode(), evt.getScanCode())) {
				if (JEIMenuTest.anythingMatched(gui.getMenu())) {
					evt.setCanceled(true);
					return;
				}
				TradeRefresh.HANDLER.toServer(new RefreshToServer());
			}
		}
	}

}

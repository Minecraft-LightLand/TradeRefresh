package dev.xkmc.traderefresh.client;

import dev.xkmc.traderefresh.init.Keys;
import dev.xkmc.traderefresh.init.TradeRefresh;
import dev.xkmc.traderefresh.network.RefreshToServer;
import net.minecraft.client.gui.screens.inventory.MerchantScreen;
import net.minecraftforge.client.event.ScreenEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

public class TradeScreenEventHandler {

	@SubscribeEvent
	public static void onInventoryGuiInit(ScreenEvent.Init.Post evt) {
		if (evt.getScreen() instanceof MerchantScreen gui && gui.getMenu().getTraderXp() == 0) {
			evt.addListener(new RefreshButton(gui));
		}
	}

	@SubscribeEvent
	public static void onKeyPressed(ScreenEvent.KeyPressed evt) {
		if (evt.getScreen() instanceof MerchantScreen gui && gui.getMenu().getTraderXp() == 0) {
			if (Keys.REFRESH.map.matches(evt.getKeyCode(), evt.getScanCode()))
				TradeRefresh.HANDLER.toServer(new RefreshToServer());
		}
	}

}

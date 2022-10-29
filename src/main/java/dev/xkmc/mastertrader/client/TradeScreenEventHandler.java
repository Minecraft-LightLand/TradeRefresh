package dev.xkmc.mastertrader.client;

import dev.xkmc.mastertrader.init.Keys;
import dev.xkmc.mastertrader.init.MasterTrader;
import dev.xkmc.mastertrader.network.RefreshToServer;
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
				MasterTrader.HANDLER.toServer(new RefreshToServer());
		}
	}

}

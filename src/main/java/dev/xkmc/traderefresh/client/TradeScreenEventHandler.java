package dev.xkmc.traderefresh.client;

import dev.xkmc.traderefresh.compat.RecipeViewerMenu;
import dev.xkmc.traderefresh.init.Keys;
import dev.xkmc.traderefresh.init.TRConfig;
import dev.xkmc.traderefresh.init.TradeRefresh;
import dev.xkmc.traderefresh.network.RefreshToServer;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.inventory.MerchantScreen;
import net.minecraft.client.resources.sounds.SimpleSoundInstance;
import net.minecraft.sounds.SoundEvents;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.EventPriority;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.ClientTickEvent;
import net.neoforged.neoforge.client.event.ScreenEvent;

@EventBusSubscriber(value = Dist.CLIENT, modid = TradeRefresh.MODID, bus = EventBusSubscriber.Bus.GAME)
public class TradeScreenEventHandler {

	private static boolean pendingRecipeViewerCheck = false;

	@SubscribeEvent
	public static void onInventoryGuiInit(ScreenEvent.Init.Post evt) {
		if (evt.getScreen() instanceof MerchantScreen gui)
			evt.addListener(new RefreshButton(gui));
	}

	@SubscribeEvent(priority = EventPriority.HIGH)
	public static void onKeyPressed(ScreenEvent.KeyPressed.Pre evt) {
		if (evt.getScreen() instanceof MerchantScreen gui) {
			if (Keys.REFRESH.map.matches(evt.getKeyCode(), evt.getScanCode())) {
				tryRefresh(gui, true);
			}
		}
	}

	public static void tryRefresh(MerchantScreen gui, boolean playSound) {
		if (RecipeViewerMenu.anythingMatched(gui.getMenu())) {
			return;
		}
		if (!TRConfig.SERVER.alwaysAllowRefresh.get() && gui.getMenu().getTraderXp() > 0) {
			return;
		}
		if (playSound) {
			Minecraft.getInstance().getSoundManager().play(SimpleSoundInstance.forUI(SoundEvents.UI_BUTTON_CLICK, 1.0F));
		}
		TradeRefresh.HANDLER.toServer(new RefreshToServer());
		pendingRecipeViewerCheck = true;
	}

	@SubscribeEvent
	public static void onClientTick(ClientTickEvent.Post evt) {
		if (!pendingRecipeViewerCheck) return;
		pendingRecipeViewerCheck = false;
		if (Minecraft.getInstance().screen instanceof MerchantScreen gui) {
			if (RecipeViewerMenu.anythingMatched(gui.getMenu())) {
				Minecraft.getInstance().getSoundManager().play(SimpleSoundInstance.forUI(SoundEvents.NOTE_BLOCK_CHIME.value(), 1.0F));
			}
		}
	}

}

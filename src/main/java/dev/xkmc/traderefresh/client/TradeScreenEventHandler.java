package dev.xkmc.traderefresh.client;

import dev.xkmc.traderefresh.compat.EMIMenuTest;
import dev.xkmc.traderefresh.compat.JEIMenuTest;
import dev.xkmc.traderefresh.init.Keys;
import dev.xkmc.traderefresh.init.TRConfig;
import dev.xkmc.traderefresh.init.TradeRefresh;
import dev.xkmc.traderefresh.network.RefreshToServer;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.components.events.GuiEventListener;
import net.minecraft.client.gui.screens.inventory.MerchantScreen;
import net.minecraft.client.resources.sounds.SimpleSoundInstance;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.inventory.MerchantMenu;
import net.minecraftforge.client.event.ScreenEvent;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

public class TradeScreenEventHandler {

	private static boolean pendingRecipeViewerCheck = false;

	@SubscribeEvent
	public static void onInventoryGuiInit(ScreenEvent.Init.Post evt) {
		if (evt.getScreen() instanceof MerchantScreen gui)
			evt.addListener(new RefreshButton(gui));
	}

	@SubscribeEvent
	public static void onKeyPressed(ScreenEvent.KeyPressed.Pre evt) {
		if (evt.getScreen() instanceof MerchantScreen gui) {
			if (Keys.REFRESH.map.matches(evt.getKeyCode(), evt.getScanCode())) {
				if (isRecipeViewerMatched(gui.getMenu())) {
					return;
				}
				if (!TRConfig.COMMON.alwaysAllowRefresh.get() && gui.getMenu().getTraderXp() > 0) {
					return;
				}
				Minecraft.getInstance().getSoundManager().play(SimpleSoundInstance.forUI(SoundEvents.UI_BUTTON_CLICK, 1.0F));
				TradeRefresh.HANDLER.toServer(new RefreshToServer());
				pendingRecipeViewerCheck = true;
			}
		}
	}

	@SubscribeEvent
	public static void onClientTick(TickEvent.ClientTickEvent evt) {
		if (evt.phase != TickEvent.Phase.END) return;
		if (!pendingRecipeViewerCheck) return;
		pendingRecipeViewerCheck = false;
		if (Minecraft.getInstance().screen instanceof MerchantScreen gui) {
			if (isRecipeViewerMatched(gui.getMenu())) {
				Minecraft.getInstance().getSoundManager().play(SimpleSoundInstance.forUI(SoundEvents.NOTE_BLOCK_CHIME, 1.0F));
			}
		}
	}

	@SubscribeEvent
	public static void onMouseReleased(ScreenEvent.MouseButtonReleased.Post evt) {
		if (!(evt.getScreen() instanceof MerchantScreen)) return;
		if (RefreshButton.staticPressed == null) return;
		GuiEventListener hovered = findHovered(evt.getScreen(), evt.getMouseX(), evt.getMouseY());
		if (hovered == RefreshButton.staticPressed && RefreshButton.staticPressed.active) {
			TradeRefresh.HANDLER.toServer(new RefreshToServer());
		}
		RefreshButton.staticPressed = null;
	}

	@SubscribeEvent
	public static void onMouseClicked(ScreenEvent.MouseButtonPressed.Post evt) {
		if (!(evt.getScreen() instanceof MerchantScreen)) return;
		GuiEventListener hovered = findHovered(evt.getScreen(), evt.getMouseX(), evt.getMouseY());
		if (hovered instanceof RefreshButton btn && btn.active) {
			RefreshButton.staticPressed = btn;
		}
	}

	private static boolean isRecipeViewerMatched(MerchantMenu menu) {
		return JEIMenuTest.anythingMatched(menu) || EMIMenuTest.anythingMatched(menu);
	}

	private static GuiEventListener findHovered(net.minecraft.client.gui.screens.Screen screen, double mouseX, double mouseY) {
		for (GuiEventListener child : screen.children()) {
			if (child.isMouseOver(mouseX, mouseY)) {
				return child;
			}
		}
		return null;
	}

}

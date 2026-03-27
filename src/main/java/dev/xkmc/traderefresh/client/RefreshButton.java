package dev.xkmc.traderefresh.client;

import dev.xkmc.traderefresh.init.TRConfig;
import dev.xkmc.traderefresh.init.TradeRefresh;
import dev.xkmc.traderefresh.network.RefreshToServer;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.screens.inventory.MerchantScreen;
import net.minecraft.client.input.InputWithModifiers;
import net.minecraft.network.chat.Component;

public class RefreshButton extends Button.Plain {

	private static final Component TITLE = Component.literal("R");

	private final MerchantScreen parent;

	public RefreshButton(MerchantScreen parent) {
		super(parent.getGuiLeft() + parent.getXSize() - 24, parent.getGuiTop() + 4, 20, 20,
				TITLE, e -> {
				}, DEFAULT_NARRATION);
		this.parent = parent;
	}

	@Override
	public void onPress(InputWithModifiers input) {
		active = false;
		TradeRefresh.HANDLER.toServer(new RefreshToServer());
	}

	@Override
	public boolean isActive() {
		return super.isActive();
	}

	@Override
	public void extractContents(GuiGraphicsExtractor g, int mouseX, int mouseY, float a) {
		this.active = TRConfig.SERVER.alwaysAllowRefresh.get() || parent.getMenu().getTraderXp() == 0;
		super.extractContents(g, mouseX, mouseY, a);
	}

}

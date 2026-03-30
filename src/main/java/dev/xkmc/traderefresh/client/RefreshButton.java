package dev.xkmc.traderefresh.client;

import dev.xkmc.traderefresh.init.Keys;
import dev.xkmc.traderefresh.init.TRConfig;
import dev.xkmc.traderefresh.init.TradeRefresh;
import dev.xkmc.traderefresh.network.RefreshToServer;
import net.minecraft.ChatFormatting;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.components.Tooltip;
import net.minecraft.client.gui.screens.inventory.MerchantScreen;
import net.minecraft.network.chat.Component;

public class RefreshButton extends Button {

	private static final Component TITLE = Component.literal("R");

	private final MerchantScreen parent;

	public RefreshButton(MerchantScreen parent) {
		super(parent.getGuiLeft() + parent.getXSize() - 24, parent.getGuiTop() + 4, 20, 20,
				TITLE, e -> {
				}, DEFAULT_NARRATION);
		this.parent = parent;
		setTooltip(Tooltip.create(Component.translatable(
				"traderefresh.tooltip_jei", Keys.REFRESH.map.getKey().getDisplayName()
						.copy().withStyle(ChatFormatting.YELLOW)
		).withStyle(ChatFormatting.GRAY)));
	}

	@Override
	public void onPress() {
		active = false;
		TradeRefresh.HANDLER.toServer(new RefreshToServer());
	}

	@Override
	public boolean isActive() {
		return super.isActive();
	}

	@Override
	public void renderWidget(GuiGraphics g, int pMouseX, int pMouseY, float pPartialTick) {
		this.active = TRConfig.SERVER.alwaysAllowRefresh.get() || parent.getMenu().getTraderXp() == 0;
		super.renderWidget(g, pMouseX, pMouseY, pPartialTick);
	}
}

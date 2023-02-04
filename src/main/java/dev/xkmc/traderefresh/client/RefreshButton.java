package dev.xkmc.traderefresh.client;

import com.mojang.blaze3d.vertex.PoseStack;
import dev.xkmc.traderefresh.init.TradeRefresh;
import dev.xkmc.traderefresh.network.RefreshToServer;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.screens.inventory.MerchantScreen;
import net.minecraft.network.chat.Component;

public class RefreshButton extends Button {

	private static final Component TITLE = Component.literal("R");

	private final MerchantScreen parent;

	public RefreshButton(MerchantScreen parent) {
		super(parent.getGuiLeft() + parent.getXSize() - 24, parent.getGuiTop() + 4, 20, 20,
				TITLE, e -> {
				});
		this.parent = parent;
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
	public void renderButton(PoseStack pPoseStack, int pMouseX, int pMouseY, float pPartialTick) {
		this.active = parent.getMenu().getTraderXp() == 0;
		super.renderButton(pPoseStack, pMouseX, pMouseY, pPartialTick);
	}
}

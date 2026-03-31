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
import net.minecraft.resources.ResourceLocation;
import net.neoforged.fml.ModList;

public class RefreshButton extends Button {

	private static final ResourceLocation TEXTURE_NORMAL = ResourceLocation.fromNamespaceAndPath(TradeRefresh.MODID, "textures/gui/refresh_button.png");
	private static final ResourceLocation TEXTURE_DISABLED = ResourceLocation.fromNamespaceAndPath(TradeRefresh.MODID, "textures/gui/refresh_button_disabled.png");
	private static final ResourceLocation TEXTURE_HIGHLIGHTED = ResourceLocation.fromNamespaceAndPath(TradeRefresh.MODID, "textures/gui/refresh_button_highlighted.png");
	private static final ResourceLocation TEXTURE_PRESSED = ResourceLocation.fromNamespaceAndPath(TradeRefresh.MODID, "textures/gui/refresh_button_pressed.png");
	private static final ResourceLocation TEXTURE_PRESSED_HIGHLIGHTED = ResourceLocation.fromNamespaceAndPath(TradeRefresh.MODID, "textures/gui/refresh_button_pressed_highlighted.png");

	private final MerchantScreen parent;
	private boolean pressed;

	public RefreshButton(MerchantScreen parent) {
		super(getButtonX(parent), getButtonY(parent), 18, 18,
				Component.translatable("traderefresh.button.narration"),
				b -> {},
				DEFAULT_NARRATION);
		this.parent = parent;
	}

	private static int getButtonX(MerchantScreen parent) {
		if (TRConfig.CLIENT.buttonSide.get() == TRConfig.ButtonSide.LEFT) {
			return parent.getGuiLeft() + 102;
		}
		return parent.getGuiLeft() + parent.getXSize() - 22;
	}

	private static int getButtonY(MerchantScreen parent) {
		if (TRConfig.CLIENT.buttonSide.get() == TRConfig.ButtonSide.LEFT) {
			return parent.getGuiTop() + 17;
		}
		return parent.getGuiTop() + 4;
	}

	@Override
	public boolean mouseClicked(double mouseX, double mouseY, int button) {
		if (this.active && this.isMouseOver(mouseX, mouseY)) {
			pressed = true;
		}
		return super.mouseClicked(mouseX, mouseY, button);
	}

	@Override
	public boolean mouseReleased(double mouseX, double mouseY, int button) {
		if (this.isHovered() && this.active) {
			TradeRefresh.HANDLER.toServer(new RefreshToServer());
		}
		pressed = false;
		return super.mouseReleased(mouseX, mouseY, button);
	}

	@Override
	public void renderWidget(GuiGraphics g, int mouseX, int mouseY, float partialTick) {
		this.active = TRConfig.SERVER.alwaysAllowRefresh.get() || parent.getMenu().getTraderXp() == 0;

		if (this.active) {
			Component tooltip = Component.translatable("traderefresh.button.tooltip",
					Keys.REFRESH.map.getTranslatedKeyMessage());
			if (ModList.get().isLoaded("jei")) {
				tooltip = tooltip.copy().append("\n")
						.append(Component.translatable("traderefresh.tooltip_jei",
								Keys.REFRESH.map.getKey().getDisplayName()
										.copy().withStyle(ChatFormatting.YELLOW))
								.withStyle(ChatFormatting.GRAY));
			}
			this.setTooltip(Tooltip.create(tooltip));
		} else {
			this.setTooltip(Tooltip.create(Component.translatable("traderefresh.button.disabled")));
		}

		ResourceLocation texture;
		if (!this.active) {
			texture = TEXTURE_DISABLED;
		} else if (pressed) {
			texture = this.isHovered() ? TEXTURE_PRESSED_HIGHLIGHTED : TEXTURE_PRESSED;
		} else if (this.isHovered()) {
			texture = TEXTURE_HIGHLIGHTED;
		} else {
			texture = TEXTURE_NORMAL;
		}

		g.blit(texture, this.getX(), this.getY(), 0, 0, 18, 18, 18, 18);
	}
}

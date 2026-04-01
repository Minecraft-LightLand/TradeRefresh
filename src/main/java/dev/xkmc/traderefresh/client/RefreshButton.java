package dev.xkmc.traderefresh.client;

import dev.xkmc.traderefresh.init.Keys;
import dev.xkmc.traderefresh.init.TRConfig;
import dev.xkmc.traderefresh.init.TradeRefresh;
import net.minecraft.ChatFormatting;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.components.Tooltip;
import net.minecraft.client.gui.screens.inventory.MerchantScreen;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.fml.ModList;

public class RefreshButton extends Button {

	private static final ResourceLocation TEXTURE_NORMAL = new ResourceLocation(TradeRefresh.MODID, "textures/gui/refresh_button.png");
	private static final ResourceLocation TEXTURE_DISABLED = new ResourceLocation(TradeRefresh.MODID, "textures/gui/refresh_button_disabled.png");
	private static final ResourceLocation TEXTURE_HIGHLIGHTED = new ResourceLocation(TradeRefresh.MODID, "textures/gui/refresh_button_highlighted.png");
	private static final ResourceLocation TEXTURE_PRESSED = new ResourceLocation(TradeRefresh.MODID, "textures/gui/refresh_button_pressed.png");
	private static final ResourceLocation TEXTURE_PRESSED_HIGHLIGHTED = new ResourceLocation(TradeRefresh.MODID, "textures/gui/refresh_button_pressed_highlighted.png");

	static RefreshButton staticPressed;

	private final MerchantScreen parent;

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
	public void renderWidget(GuiGraphics g, int mouseX, int mouseY, float partialTick) {
		this.active = TRConfig.COMMON.alwaysAllowRefresh.get() || parent.getMenu().getTraderXp() == 0;

		boolean pressed = staticPressed == this;

		if (this.active) {
			Component tooltip = Component.translatable("traderefresh.button.tooltip",
					Keys.REFRESH.map.getTranslatedKeyMessage());
			String recipeViewerName = getRecipeViewerName();
			if (recipeViewerName != null) {
				tooltip = tooltip.copy().append("\n")
						.append(Component.translatable("traderefresh.tooltip_recipe_viewer", recipeViewerName,
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
			texture = this.isHoveredOrFocused() ? TEXTURE_PRESSED_HIGHLIGHTED : TEXTURE_PRESSED;
		} else if (this.isHovered()) {
			texture = TEXTURE_HIGHLIGHTED;
		} else {
			texture = TEXTURE_NORMAL;
		}

		g.blit(texture, this.getX(), this.getY(), 0, 0, 18, 18, 18, 18);
	}

	private static String getRecipeViewerName() {
		boolean jei = ModList.get().isLoaded("jei");
		boolean emi = ModList.get().isLoaded("emi");
		if (emi) return "EMI";
		if (jei) return "JEI";
		return null;
	}
}

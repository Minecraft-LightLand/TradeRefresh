package dev.xkmc.traderefresh.client;

import dev.xkmc.traderefresh.init.Keys;
import dev.xkmc.traderefresh.init.TRConfig;
import dev.xkmc.traderefresh.init.TradeRefresh;
import net.minecraft.ChatFormatting;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.components.Tooltip;
import net.minecraft.client.gui.screens.inventory.MerchantScreen;
import net.minecraft.client.input.MouseButtonEvent;
import net.minecraft.client.renderer.RenderPipelines;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.Identifier;
import net.neoforged.fml.ModList;

public class RefreshButton extends Button {

	private static final Identifier TEXTURE_NORMAL = TradeRefresh.loc("textures/gui/refresh_button.png");
	private static final Identifier TEXTURE_DISABLED = TradeRefresh.loc("textures/gui/refresh_button_disabled.png");
	private static final Identifier TEXTURE_HIGHLIGHTED = TradeRefresh.loc("textures/gui/refresh_button_highlighted.png");
	private static final Identifier TEXTURE_PRESSED = TradeRefresh.loc("textures/gui/refresh_button_pressed.png");
	private static final Identifier TEXTURE_PRESSED_HIGHLIGHTED = TradeRefresh.loc("textures/gui/refresh_button_pressed_highlighted.png");

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
	public boolean mouseClicked(MouseButtonEvent event, boolean isDoubleClick) {
		if (this.active && this.isMouseOver(event.x(), event.y())) {
			pressed = true;
		}
		return super.mouseClicked(event, isDoubleClick);
	}

	@Override
	public void onRelease(MouseButtonEvent event) {
		if (this.isHovered() && this.active) {
			TradeScreenEventHandler.tryRefresh(parent, false);
		}
		pressed = false;
	}

	@Override
	public void extractContents(GuiGraphicsExtractor guiGraphics, int mouseX, int mouseY, float partialTick) {
		this.active = TRConfig.SERVER.alwaysAllowRefresh.get() || parent.getMenu().getTraderXp() == 0;

		if (this.active) {
			Component tooltip = Component.translatable("traderefresh.button.tooltip",
					Keys.REFRESH.map.getTranslatedKeyMessage());
			String recipeViewerName = getRecipeViewerName();
			if (recipeViewerName != null) {
				tooltip = tooltip.copy().append("\n")
						.append(Component.translatable("traderefresh.tooltip_recipe_viewer", recipeViewerName)
								.withStyle(ChatFormatting.GRAY));
			}
			this.setTooltip(Tooltip.create(tooltip));
		} else {
			this.setTooltip(Tooltip.create(Component.translatable("traderefresh.button.disabled")));
		}

		Identifier texture;
		if (!this.active) {
			texture = TEXTURE_DISABLED;
		} else if (pressed) {
			texture = this.isHovered() ? TEXTURE_PRESSED_HIGHLIGHTED : TEXTURE_PRESSED;
		} else if (this.isHovered()) {
			texture = TEXTURE_HIGHLIGHTED;
		} else {
			texture = TEXTURE_NORMAL;
		}

		guiGraphics.blit(RenderPipelines.GUI_TEXTURED, texture, this.getX(), this.getY(), 0, 0, 18, 18, 18, 18);
	}

	private static String getRecipeViewerName() {
		if (ModList.get().isLoaded("jei")) return "JEI";
		return null;
	}

}

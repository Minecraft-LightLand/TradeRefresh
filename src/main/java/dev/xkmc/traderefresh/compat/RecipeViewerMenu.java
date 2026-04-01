package dev.xkmc.traderefresh.compat;

import net.minecraft.world.inventory.MerchantMenu;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.trading.MerchantOffer;
import net.neoforged.fml.ModList;

public class RecipeViewerMenu {

	public static boolean anythingMatched(MerchantMenu menu) {
		if (ModList.get().isLoaded("jei")) {
			try {
				if (JeiImpl.anythingMatched(menu)) return true;
			} catch (Throwable ignored) {
			}
		}
		if (ModList.get().isLoaded("emi")) {
			try {
				if (EmiImpl.anythingMatched(menu)) return true;
			} catch (Throwable ignored) {
			}
		}
		if (ModList.get().isLoaded("roughlyenoughitems")) {
			try {
				if (ReiImpl.anythingMatched(menu)) return true;
			} catch (Throwable ignored) {
			}
		}
		return false;
	}

	static boolean matchesOffers(MerchantMenu menu, ItemStack stack) {
		for (MerchantOffer e : menu.trader.getOffers()) {
			if (matchItem(e.getResult(), stack)) return true;
			if (matchItem(e.getCostA(), stack)) return true;
			if (matchItem(e.getCostB(), stack)) return true;
		}
		return false;
	}

	private static boolean matchItem(ItemStack avail, ItemStack chosen) {
		return chosen.getComponentsPatch().isEmpty() && chosen.getItem() == avail.getItem() ||
				ItemStack.isSameItemSameComponents(chosen, avail);
	}

	private static class JeiImpl {

		static boolean anythingMatched(MerchantMenu menu) {
			var runtime = MyJeiPlugin.getRuntime();
			if (runtime == null) return false;
			var stack = runtime.getBookmarkOverlay().getItemStackUnderMouse();
			if (stack == null || stack.isEmpty()) {
				stack = runtime.getIngredientListOverlay().getIngredientUnderMouse(
						mezz.jei.api.constants.VanillaTypes.ITEM_STACK);
				if (stack == null || stack.isEmpty()) return false;
			}
			return matchesOffers(menu, stack);
		}
	}

	private static class EmiImpl {

		static boolean anythingMatched(MerchantMenu menu) {
			var interaction = dev.emi.emi.api.EmiApi.getHoveredStack(true);
			if (interaction == null || interaction.isEmpty()) return false;
			var emiIngredient = interaction.getStack();
			if (emiIngredient == null || emiIngredient.isEmpty()) return false;
			var stacks = emiIngredient.getEmiStacks();
			if (stacks.isEmpty()) return false;
			ItemStack stack = stacks.get(0).getItemStack();
			if (stack == null || stack.isEmpty()) return false;
			return matchesOffers(menu, stack);
		}
	}

	private static class ReiImpl {

		static boolean anythingMatched(MerchantMenu menu) {
			var overlay = me.shedaniel.rei.api.client.REIRuntime.getInstance().getOverlay();
			if (overlay.isEmpty()) return false;
			var screenOverlay = overlay.get();
			var focusedStack = screenOverlay.getEntryList().getFocusedStack();
			if (!focusedStack.isEmpty() && checkStack(menu, focusedStack)) return true;
			var favoritesList = screenOverlay.getFavoritesList();
			if (favoritesList.isPresent()) {
				focusedStack = favoritesList.get().getFocusedStack();
				if (!focusedStack.isEmpty() && checkStack(menu, focusedStack)) return true;
			}
			return false;
		}
		private static boolean checkStack(MerchantMenu menu, me.shedaniel.rei.api.common.entry.EntryStack<?> focusedStack) {
			Object value = focusedStack.getValue();
			if (value instanceof ItemStack itemStack && !itemStack.isEmpty()) {
				return matchesOffers(menu, itemStack);
			}
			return false;
		}

	}

}

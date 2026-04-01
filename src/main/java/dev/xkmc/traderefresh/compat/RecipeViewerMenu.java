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

}

package dev.xkmc.traderefresh.compat;

import mezz.jei.api.constants.VanillaTypes;
import net.minecraft.world.inventory.MerchantMenu;
import net.minecraft.world.item.ItemStack;
import net.neoforged.fml.ModList;

public class JEIMenuTest {

	public static boolean anythingMatched(MerchantMenu menu) {
		if (!ModList.get().isLoaded("jei")) return false;
		try {
			return anythingMatchedImpl(menu);
		} catch (Throwable ignored) {

		}
		return false;
	}

	public static boolean anythingMatchedImpl(MerchantMenu menu) {
		var runtime = MyJeiPlugin.getRuntime();
		if (runtime == null) return false;
		var stack = runtime.getBookmarkOverlay().getItemStackUnderMouse();
		if (stack == null || stack.isEmpty()) {
			stack = runtime.getIngredientListOverlay().getIngredientUnderMouse(VanillaTypes.ITEM_STACK);
			if (stack == null || stack.isEmpty())
				return false;
		}
		for (var e : menu.trader.getOffers()) {
			if (match(e.getResult(), stack))
				return true;
			if (match(e.getCostA(), stack))
				return true;
			if (match(e.getCostB(), stack))
				return true;
		}
		return false;
	}

	public static boolean match(ItemStack avail, ItemStack chosen) {
		return chosen.isComponentsPatchEmpty() && chosen.getItem() == avail.getItem() ||
				ItemStack.isSameItemSameComponents(chosen, avail);
	}

}

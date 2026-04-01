package dev.xkmc.traderefresh.compat;

import dev.emi.emi.api.EmiApi;
import net.minecraft.world.inventory.MerchantMenu;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.fml.ModList;

public class EMIMenuTest {

	public static boolean anythingMatched(MerchantMenu menu) {
		if (!ModList.get().isLoaded("emi")) return false;
		try {
			return anythingMatchedImpl(menu);
		} catch (Throwable ignored) {

		}
		return false;
	}

	public static boolean anythingMatchedImpl(MerchantMenu menu) {
		var interaction = EmiApi.getHoveredStack(true);
		if (interaction == null || interaction.isEmpty()) return false;
		var emiIngredient = interaction.getStack();
		if (emiIngredient == null || emiIngredient.isEmpty()) return false;
		var stacks = emiIngredient.getEmiStacks();
		if (stacks.isEmpty()) return false;
		var emiStack = stacks.get(0);
		ItemStack stack = emiStack.getItemStack();
		if (stack == null || stack.isEmpty()) return false;
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
		return chosen.getTag() == null && chosen.getItem() == avail.getItem() ||
				ItemStack.isSameItemSameTags(chosen, avail);
	}

}

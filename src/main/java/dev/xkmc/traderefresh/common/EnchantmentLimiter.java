package dev.xkmc.traderefresh.common;

import dev.xkmc.traderefresh.init.TRConfig;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.List;

public class EnchantmentLimiter {

	public static boolean tradable(Enchantment e) {
		return isAllowed(e, TRConfig.COMMON.banAllTradeEnchantmentsByDefault.get(),
				TRConfig.COMMON.tradeIdBlacklist.get(),
				TRConfig.COMMON.tradeIdWhitelist.get(),
				TRConfig.COMMON.tradeNamespaceBlacklist.get(),
				TRConfig.COMMON.tradeNamespaceWhitelist.get());
	}

	public static boolean enchantable(Enchantment e) {
		return isAllowed(e, TRConfig.COMMON.banAllTableEnchantmentsByDefault.get(),
				TRConfig.COMMON.tableIdBlacklist.get(),
				TRConfig.COMMON.tableIdWhitelist.get(),
				TRConfig.COMMON.tableNamespaceBlacklist.get(),
				TRConfig.COMMON.tableNamespaceWhitelist.get());
	}

	private static boolean isAllowed(Enchantment e, boolean banAll,
									 List<String> idBlack, List<String> idWhite,
									 List<String> modBlack, List<String> modWhite) {
		ResourceLocation rl = ForgeRegistries.ENCHANTMENTS.getKey(e);
		if (rl == null) return false;
		if (idWhite.contains(rl.toString())) return true;
		if (idBlack.contains(rl.toString())) return false;
		if (modWhite.contains(rl.getNamespace())) return true;
		if (modBlack.contains(rl.getNamespace())) return false;
		return !banAll;
	}

}

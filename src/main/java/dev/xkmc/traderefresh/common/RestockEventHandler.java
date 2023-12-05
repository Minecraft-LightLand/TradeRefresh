package dev.xkmc.traderefresh.common;

import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.EntityEvent;
import net.minecraft.world.entity.npc.Villager;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraftforge.event.entity.player.ItemTooltipEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

public class RestockEventHandler {

	@SubscribeEvent
	public static void onMobInteract(PlayerInteractEvent.EntityInteract event) {
		if (event.getItemStack().is(Items.EMERALD_BLOCK) && event.getTarget() instanceof Villager villager) {
			if (event.getLevel().isClientSide()) {
				event.setCancellationResult(InteractionResult.SUCCESS);
				event.setCanceled(true);
				return;
			}
			if (villager.getVillagerXp() > 0 && villager.needsToRestock()) {
				villager.resetNumberOfRestocks();
				villager.restock();
				event.getItemStack().shrink(1);
				event.setCancellationResult(InteractionResult.CONSUME);
				event.setCanceled(true);
				event.getLevel().broadcastEntityEvent(villager, EntityEvent.VILLAGER_HAPPY);
				villager.playSound(SoundEvents.VILLAGER_YES, 1, villager.getVoicePitch());
			} else {
				event.setCancellationResult(InteractionResult.FAIL);
				event.setCanceled(true);
				villager.setUnhappyCounter(40);
				villager.playSound(SoundEvents.VILLAGER_NO, 1, villager.getVoicePitch());
			}
		}
	}

	@SubscribeEvent
	public static void onItemTooltip(ItemTooltipEvent event) {
		if (!event.getItemStack().is(Items.ENCHANTED_BOOK)) return;
		var map = EnchantmentHelper.getEnchantments(event.getItemStack());
		if (map.size() != 1) return;
		var opt = map.entrySet().stream().findFirst();
		if (opt.isEmpty()) return;
		Enchantment e = opt.get().getKey();
		boolean enchatable = e.isDiscoverable() && !e.isTreasureOnly();
		boolean tradable = e.isTradeable();
		event.getToolTip().add(getComp("enchantable", enchatable));
		event.getToolTip().add(getComp("tradable", tradable));
	}

	private static MutableComponent getComp(String str, boolean enabled) {
		return Component.translatable("traderefresh." + str + "." + enabled)
				.withStyle(enabled ? ChatFormatting.DARK_GREEN : ChatFormatting.DARK_RED);
	}

}

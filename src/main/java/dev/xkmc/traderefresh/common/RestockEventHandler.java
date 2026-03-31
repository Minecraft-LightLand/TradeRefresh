package dev.xkmc.traderefresh.common;

import dev.xkmc.traderefresh.init.TRConfig;
import dev.xkmc.traderefresh.init.TradeRefresh;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.tags.EnchantmentTags;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.EntityEvent;
import net.minecraft.world.entity.npc.Villager;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.ModList;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.player.ItemTooltipEvent;
import net.neoforged.neoforge.event.entity.player.PlayerInteractEvent;


@EventBusSubscriber(modid = TradeRefresh.MODID)
public class RestockEventHandler {

	@SubscribeEvent
	public static void onMobInteract(PlayerInteractEvent.EntityInteract event) {
		if (!TRConfig.SERVER.allowEmeraldBlockForceRestock.get()) return;
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
		if (ModList.get().isLoaded("apotheosis")) return;
		if (!TRConfig.CLIENT.showEnchProperties.get()) return;
		if (!event.getItemStack().is(Items.ENCHANTED_BOOK)) return;
		var map = EnchantmentHelper.getEnchantmentsForCrafting(event.getItemStack());
		if (map.size() != 1) return;
		var opt = map.entrySet().stream().findFirst();
		if (opt.isEmpty()) return;
		var e = opt.get().getKey();
		boolean enchatable = e.is(EnchantmentTags.IN_ENCHANTING_TABLE);
		boolean tradable = e.is(EnchantmentTags.TRADEABLE);
		var id = e.unwrapKey().orElseThrow();
		event.getToolTip().add(getComp("enchantable", enchatable));
		event.getToolTip().add(getComp("tradable", tradable));
		event.getToolTip().add(Component.literal(id.toString()).withStyle(ChatFormatting.DARK_GRAY));
	}

	private static MutableComponent getComp(String str, boolean enabled) {
		return Component.translatable("traderefresh." + str + "." + enabled)
				.withStyle(enabled ? ChatFormatting.DARK_GREEN : ChatFormatting.DARK_RED);
	}

}

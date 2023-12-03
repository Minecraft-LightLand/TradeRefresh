package dev.xkmc.traderefresh.mixin;

import dev.xkmc.l2serial.util.Wrappers;
import dev.xkmc.traderefresh.common.EnchantmentLimiter;
import net.minecraft.world.item.enchantment.Enchantment;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Enchantment.class)
public class EnchantmentMixin {

	@Inject(at = @At("HEAD"), method = "isTradeable", cancellable = true)
	public void l2complements$isTradeable$enchantedBookFilter(CallbackInfoReturnable<Boolean> cir) {
		Enchantment e = Wrappers.cast(this);
		if (!EnchantmentLimiter.tradable(e)) {
			cir.setReturnValue(false);
		}
	}

	@Inject(at = @At("HEAD"), method = "isDiscoverable", cancellable = true)
	public void l2complements$isDiscoverable$enchantedBookFilter(CallbackInfoReturnable<Boolean> cir) {
		Enchantment e = Wrappers.cast(this);
		if (!EnchantmentLimiter.enchantable(e)) {
			cir.setReturnValue(false);
		}
	}

}

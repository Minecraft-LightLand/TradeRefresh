package dev.xkmc.traderefresh.compat;

import dev.xkmc.traderefresh.init.TradeRefresh;
import mezz.jei.api.IModPlugin;
import mezz.jei.api.JeiPlugin;
import mezz.jei.api.runtime.IJeiRuntime;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.Nullable;

@JeiPlugin
public class MyJeiPlugin implements IModPlugin {

	private static IJeiRuntime jeiRuntime;

	@Override
	public ResourceLocation getPluginUid() {
		return ResourceLocation.fromNamespaceAndPath(TradeRefresh.MODID, "main");
	}

	@Override
	public void onRuntimeAvailable(IJeiRuntime runtime) {
		jeiRuntime = runtime;
	}

	@Nullable
	public static IJeiRuntime getRuntime() {
		return jeiRuntime;
	}

}
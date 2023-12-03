package dev.xkmc.traderefresh.init;

import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.config.IConfigSpec;
import net.minecraftforge.fml.config.ModConfig;
import org.apache.commons.lang3.tuple.Pair;

import java.util.ArrayList;
import java.util.List;

public class TRConfig {

	public static class Client {

		Client(ForgeConfigSpec.Builder builder) {
		}

	}

	public static class Common {

		public final ForgeConfigSpec.BooleanValue banAllTradeEnchantmentsByDefault;
		public final ForgeConfigSpec.ConfigValue<List<String>> tradeNamespaceBlacklist;
		public final ForgeConfigSpec.ConfigValue<List<String>> tradeNamespaceWhitelist;
		public final ForgeConfigSpec.ConfigValue<List<String>> tradeIdBlacklist;
		public final ForgeConfigSpec.ConfigValue<List<String>> tradeIdWhitelist;
		public final ForgeConfigSpec.BooleanValue banAllTableEnchantmentsByDefault;
		public final ForgeConfigSpec.ConfigValue<List<String>> tableNamespaceBlacklist;
		public final ForgeConfigSpec.ConfigValue<List<String>> tableNamespaceWhitelist;

		public final ForgeConfigSpec.ConfigValue<List<String>> tableIdBlacklist;
		public final ForgeConfigSpec.ConfigValue<List<String>> tableIdWhitelist;

		Common(ForgeConfigSpec.Builder builder) {
			banAllTradeEnchantmentsByDefault = builder.define("banAllTradeEnchantmentsByDefault", false);
			tradeNamespaceBlacklist = builder.define("tradeNamespaceBlacklist", new ArrayList<>(List.of()));
			tradeNamespaceWhitelist = builder.define("tradeNamespaceWhitelist", new ArrayList<>(List.of("minecraft")));
			tradeIdBlacklist = builder.define("tradeIdBlacklist", new ArrayList<>(List.of()));
			tradeIdWhitelist = builder.define("tradeIdWhitelist", new ArrayList<>(List.of("minecraft:unbreaking")));

			banAllTableEnchantmentsByDefault = builder.define("banAllTableEnchantmentsByDefault", false);
			tableNamespaceBlacklist = builder.define("tableNamespaceBlacklist", new ArrayList<>(List.of()));
			tableNamespaceWhitelist = builder.define("tableNamespaceWhitelist", new ArrayList<>(List.of("minecraft")));
			tableIdBlacklist = builder.define("tableIdBlacklist", new ArrayList<>(List.of()));
			tableIdWhitelist = builder.define("tableIdWhitelist", new ArrayList<>(List.of("minecraft:unbreaking")));

		}

	}

	public static final ForgeConfigSpec CLIENT_SPEC;
	public static final Client CLIENT;

	public static final ForgeConfigSpec COMMON_SPEC;
	public static final Common COMMON;

	static {
		final Pair<Client, ForgeConfigSpec> client = new ForgeConfigSpec.Builder().configure(Client::new);
		CLIENT_SPEC = client.getRight();
		CLIENT = client.getLeft();

		final Pair<Common, ForgeConfigSpec> common = new ForgeConfigSpec.Builder().configure(Common::new);
		COMMON_SPEC = common.getRight();
		COMMON = common.getLeft();
	}

	public static void init() {
		register(ModConfig.Type.CLIENT, CLIENT_SPEC);
		register(ModConfig.Type.COMMON, COMMON_SPEC);
	}

	private static void register(ModConfig.Type type, IConfigSpec<?> spec) {
		var mod = ModLoadingContext.get().getActiveContainer();
		String path = "l2_configs/" + mod.getModId() + "-" + type.extension() + ".toml";
		ModLoadingContext.get().registerConfig(type, spec, path);
	}

}

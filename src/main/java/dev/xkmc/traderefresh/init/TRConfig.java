package dev.xkmc.traderefresh.init;

import net.neoforged.fml.ModLoadingContext;
import net.neoforged.fml.config.IConfigSpec;
import net.neoforged.fml.config.ModConfig;
import net.neoforged.neoforge.common.ModConfigSpec;
import org.apache.commons.lang3.tuple.Pair;

public class TRConfig {

	public static class Client {

		public final ModConfigSpec.BooleanValue showEnchProperties;

		Client(ModConfigSpec.Builder builder) {
			showEnchProperties = builder.comment("Show enchantment properties like tradeable and enchantable")
					.comment("Will not work when Apotheosis is installed")
					.define("showEnchProperties", false);
		}

	}

	public static class Server {

		public final ModConfigSpec.BooleanValue alwaysAllowRefresh;
		public final ModConfigSpec.BooleanValue allowEmeraldBlockForceRestock;

		Server(ModConfigSpec.Builder builder) {
			alwaysAllowRefresh = builder.comment("Always allow refreshing trade.")
					.comment(" Only trades added at current villager level will be refreshed.")
					.define("alwaysAllowRefresh", false);
			allowEmeraldBlockForceRestock = builder.comment("Allow player to use emerald block to force restock")
					.define("allowEmeraldBlockForceRestock", true);
		}

	}

	public static final ModConfigSpec CLIENT_SPEC;
	public static final Client CLIENT;

	public static final ModConfigSpec SERVER_SPEC;
	public static final Server SERVER;

	static {
		final Pair<Client, ModConfigSpec> client = new ModConfigSpec.Builder().configure(Client::new);
		CLIENT_SPEC = client.getRight();
		CLIENT = client.getLeft();

		final Pair<Server, ModConfigSpec> common = new ModConfigSpec.Builder().configure(Server::new);
		SERVER_SPEC = common.getRight();
		SERVER = common.getLeft();
	}

	public static void init() {
		register(ModConfig.Type.CLIENT, CLIENT_SPEC);
		register(ModConfig.Type.SERVER, SERVER_SPEC);
	}

	private static void register(ModConfig.Type type, IConfigSpec spec) {
		var mod = ModLoadingContext.get().getActiveContainer();
		String path = mod.getModId() + "-" + type.extension() + ".toml";
		ModLoadingContext.get().getActiveContainer().registerConfig(type, spec, path);
	}

}

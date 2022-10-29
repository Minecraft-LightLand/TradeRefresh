package dev.xkmc.mastertrader.init;

import dev.xkmc.l2library.repack.registrate.providers.RegistrateLangProvider;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.network.chat.contents.TranslatableContents;

import java.util.Locale;
import java.util.function.BiConsumer;

public class LangData {

	public enum IDS {
		;

		final String id, def;
		final int count;

		IDS(String id, int count, String def) {
			this.id = id;
			this.def = def;
			this.count = count;
		}

		public MutableComponent get(Object... objs) {
			if (objs.length != count)
				throw new IllegalArgumentException("for " + name() + ": expect " + count + " parameters, got " + objs.length);
			return MutableComponent.create(new TranslatableContents(MasterTrader.MODID + "." + id, objs));
		}

	}

	public static void addTranslations(RegistrateLangProvider pvd) {
		for (IDS id : IDS.values()) {
			pvd.add(MasterTrader.MODID + "." + id.id, id.def);
		}
		pvd.add("key.categories.mastertrader", "Master Trader Keys");
		pvd.add(Keys.REFRESH.id, "Refresh Trade");
	}

	public static String asId(String name) {
		return name.toLowerCase(Locale.ROOT);
	}

}

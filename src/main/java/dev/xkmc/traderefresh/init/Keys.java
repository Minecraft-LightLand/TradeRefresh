
package dev.xkmc.traderefresh.init;

import net.minecraft.client.KeyMapping;
import org.lwjgl.glfw.GLFW;

public enum Keys {
	REFRESH("key.traderefresh.refresh", GLFW.GLFW_KEY_T);

	public final KeyMapping map;

	Keys(String id, int key) {
		map = new KeyMapping(id, key, "key.categories.traderefresh");
	}

}

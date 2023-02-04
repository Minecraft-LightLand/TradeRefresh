
package dev.xkmc.traderefresh.init;

import net.minecraft.client.KeyMapping;
import org.lwjgl.glfw.GLFW;

public enum Keys {
	REFRESH("key.traderefresh.refresh", GLFW.GLFW_KEY_R);

	public final String id;
	public final int key;
	public final KeyMapping map;

	Keys(String id, int key) {
		this.id = id;
		this.key = key;
		map = new KeyMapping(id, key, "key.categories.traderefresh");
	}

}

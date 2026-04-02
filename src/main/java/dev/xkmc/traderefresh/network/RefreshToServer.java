package dev.xkmc.traderefresh.network;

import dev.xkmc.l2serial.network.SerialPacketBase;
import dev.xkmc.l2serial.serialization.marker.SerialClass;
import dev.xkmc.traderefresh.init.TRConfig;
import net.minecraft.world.entity.npc.Villager;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.MerchantMenu;

@SerialClass
public record RefreshToServer() implements SerialPacketBase<RefreshToServer> {

	@Override
	public void handle(Player player) {
		if (!(player.containerMenu instanceof MerchantMenu gui)) return;
		if (!(gui.trader instanceof Villager villager)) return;
		if (TRConfig.SERVER.alwaysAllowRefresh.get()) {
			player.doCloseContainer();
			var offer = villager.getOffers();
			offer.removeLast();
			if (offer.size() % 2 != 0) offer.removeLast();
			villager.setOffers(offer);
			villager.updateTrades();
			villager.startTrading(player);
		} else {
			if (villager.getVillagerXp() > 0) return;
			player.doCloseContainer();
			villager.setOffers(null);
			villager.startTrading(player);
		}
	}

}

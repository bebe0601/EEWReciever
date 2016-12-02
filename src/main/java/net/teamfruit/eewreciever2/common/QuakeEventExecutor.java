package net.teamfruit.eewreciever2.common;

import java.util.List;

import com.google.common.collect.Lists;

import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.TickEvent.ServerTickEvent;
import net.teamfruit.eewreciever2.Reference;

public class QuakeEventExecutor {

	private final List<IQuake> quakes = Lists.newLinkedList();

	public void register(final IQuake quake) {
		this.quakes.add(quake);
	}

	public void unregister(final IQuake quake) {
		this.quakes.remove(quake);
	}

	@SubscribeEvent
	public void onServerTick(final ServerTickEvent event) {
		try {
			for (final IQuake quake : this.quakes) {
				final List<IQuakeNode> nodes = quake.getQuakeUpdate();
				for (final IQuakeNode node : nodes)
					FMLCommonHandler.instance().bus().post(node.getEvent());
			}
		} catch (final QuakeException e) {
			Reference.logger.error(e.getMessage(), e);
		}
	}

	public static List<IQuakeNode> getUpdate(final List<IQuakeNode> older, final List<IQuakeNode> newer) {
		final List<IQuakeNode> list = Lists.newLinkedList(newer);
		list.removeAll(older);
		return list;
	}
}

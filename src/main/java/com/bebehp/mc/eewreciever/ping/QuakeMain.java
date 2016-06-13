package com.bebehp.mc.eewreciever.ping;

import java.util.List;

import com.bebehp.mc.eewreciever.ConfigurationHandler;
import com.bebehp.mc.eewreciever.EEWRecieverMod;
import com.bebehp.mc.eewreciever.p2pquake.P2PQuake;
import com.bebehp.mc.eewreciever.twitter.TweetQuake;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.TickEvent.ServerTickEvent;

public class QuakeMain {

	final IQuake p2pQuake = new P2PQuake();
	final IQuake tweetQuake = new TweetQuake();

	long lasttime;

	@SubscribeEvent
	public void onServerTick(final ServerTickEvent event) {
		try {
			final List<AbstractQuakeNode> p2pQuakeNode = this.p2pQuake.getQuakeUpdate();
			if (ConfigurationHandler.p2pQuakeEnable)
				checkUpdate(p2pQuakeNode);

			final List<AbstractQuakeNode> tweetQuakeNode = this.tweetQuake.getQuakeUpdate();
			if (ConfigurationHandler.twitterEnable)
				checkUpdate(tweetQuakeNode);
		} catch (final QuakeException e) {
			EEWRecieverMod.logger.error(e);
		}
	}

	public void checkUpdate(final List<AbstractQuakeNode> update) {
		for (final AbstractQuakeNode up : update) {
			if (ConfigurationHandler.debugMode) {
				EEWRecieverMod.sendServerChat(up.toString());
			} else if (!up.istraining()){
				if (ConfigurationHandler.forceLevel || up.isAlarm()) {
					EEWRecieverMod.sendServerChat(up.toString());
				}
			}
		}
	}
}

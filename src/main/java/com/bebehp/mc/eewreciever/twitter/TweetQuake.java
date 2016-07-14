package com.bebehp.mc.eewreciever.twitter;

import java.io.UnsupportedEncodingException;
import java.util.LinkedList;
import java.util.List;

import com.bebehp.mc.eewreciever.ConfigurationHandler;
import com.bebehp.mc.eewreciever.Reference;
import com.bebehp.mc.eewreciever.ping.AbstractQuakeNode;
import com.bebehp.mc.eewreciever.ping.IQuake;
import com.bebehp.mc.eewreciever.ping.QuakeException;

import twitter4j.FilterQuery;
import twitter4j.Status;
import twitter4j.StatusAdapter;
import twitter4j.StatusListener;
import twitter4j.TwitterStream;
import twitter4j.TwitterStreamFactory;

public class TweetQuake implements IQuake {
	private final List<AbstractQuakeNode> updatequeue = new LinkedList<AbstractQuakeNode>();
	private final TwitterStream twitterStream;
	private final StatusListener listener;

	public TweetQuake() {
		this.twitterStream = new TwitterStreamFactory().getInstance();
		this.listener = new StatusAdapter() {
			@Override
			public void onStatus(final Status status) {
				if (!status.isRetweet() && status.getInReplyToScreenName() == null) {
					try {
						final String str = new String(status.getText().getBytes("UTF-8"), "UTF-8").intern();
						TweetQuake.this.updatequeue.add(new TweetQuakeNode().parseString(str));
					} catch (final UnsupportedEncodingException e) {
						Reference.logger.error("Encode Error", e);
					} catch (final QuakeException e) {
						Reference.logger.error("Recieve Error", e);
					}
				}
			}
		};
		this.twitterStream.addListener(this.listener);
		if (ConfigurationHandler.twitterEnable) {
			final long[] list = new long[2];
			list[0] = 214358709L; //from:eewbot
			if (ConfigurationHandler.debugMode)
				list[1] = 4893957312L; //from:EEWReciever
			final FilterQuery query = new FilterQuery(list);
			this.twitterStream.filter(query);
		}
	}

	@Override
	public List<AbstractQuakeNode> getQuakeUpdate() throws QuakeException {
		if (this.updatequeue.isEmpty())
			return this.updatequeue;

		final List<AbstractQuakeNode> ret = new LinkedList<AbstractQuakeNode>(this.updatequeue);
		this.updatequeue.clear();
		return ret;
	}
}
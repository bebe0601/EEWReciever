package net.teamfruit.eewreciever2.common.quake.p2pquake;

import java.text.ParseException;
import java.util.List;

import com.google.common.collect.Lists;
import com.google.gson.JsonParseException;

import net.minecraft.util.EnumChatFormatting;
import net.teamfruit.eewreciever2.common.quake.IQuakeNode;
import net.teamfruit.eewreciever2.common.quake.QuakeEvent;
import net.teamfruit.eewreciever2.common.quake.QuakeEvent.TsunamiWarnEvent;
import net.teamfruit.eewreciever2.common.quake.QuakeException;
import net.teamfruit.eewreciever2.common.quake.p2pquake.P2PQuakeJson.TsunamiInfo;
import net.teamfruit.eewreciever2.common.quake.p2pquake.P2PQuakeJson.TsunamiInfo.Area;

public class P2PQuakeTsunamiInfoNode extends P2PQuakeNode<P2PQuakeJson.TsunamiInfo> {

	public String issue;
	public boolean cancell;
	public List<Area> areas;

	@Override
	public IQuakeNode parseString(final String source) throws QuakeException {
		try {
			this.data = P2PQuake.gson.fromJson(source, TsunamiInfo.class);

			this.date = dateFormat.parse(this.data.time);
			this.code = this.data.code;
			this.issue = this.data.issue.type;
			this.cancell = this.data.cancelled;
			this.areas = this.data.areas;
		} catch (final JsonParseException e) {
			throw new QuakeException("Parse Error", e);
		} catch (final ParseException e) {
			throw new QuakeException("Parse Error", e);
		}
		return this;
	}

	@Override
	public QuakeEvent getEvent() {
		return new TsunamiWarnEvent(this);
	}

	@Override
	public String getChatFormat() {
		if (!this.issue.equalsIgnoreCase("Focus"))
			return "[EEWReciever2] 不明な情報を受信しました。";
		if (this.data.cancelled)
			return "[津波情報] 発表されていた津波予報は全て解除されました。";
		if (this.areas.isEmpty())
			return "[EEWReciever2] エリア不明の津波予報を受信しました。";

		final List<Area> major = Lists.newArrayList();
		final List<Area> warn = Lists.newArrayList();
		final List<Area> watch = Lists.newArrayList();
		for (final Area area : this.areas) {
			if (area.name.equals("MajorWarning"))
				major.add(area);
			else if (area.name.equals("Warning"))
				warn.add(area);
			else if (area.name.equals("Watch"))
				watch.add(area);
		}

		final StringBuilder sb = new StringBuilder();
		if (!major.isEmpty())
			sb.append("大津波警報 ");
		if (!warn.isEmpty())
			sb.append("津波警報 ");
		if (!watch.isEmpty())
			sb.append("津波注意報 ");
		sb.append("が以下の地域に発表されました！\n");
		if (!major.isEmpty())
			sb.append(EnumChatFormatting.LIGHT_PURPLE).append("【大津波警報】").append(EnumChatFormatting.RESET).append(formatTsunamiArea(major)).append("\n");
		if (!warn.isEmpty())
			sb.append(EnumChatFormatting.RED).append("【津波警報】").append(EnumChatFormatting.RESET).append(formatTsunamiArea(warn)).append("\n");
		if (!watch.isEmpty())
			sb.append(EnumChatFormatting.GOLD).append("【津波注意報】").append(EnumChatFormatting.RESET).append(formatTsunamiArea(watch)).append("\n");
		sb.append("メディア等から情報を入手し、適切な行動を行ってください。");
		return sb.toString();
	}

	private String formatTsunamiArea(final List<Area> area) {
		final StringBuilder sb = new StringBuilder();
		for (final Area line : area) {
			sb.append(line.name);
			if (line.immediate)
				sb.append(EnumChatFormatting.RED).append("[すぐ来る！]").append(EnumChatFormatting.RESET);
		}
		return sb.toString();
	}

	@Override
	public boolean canChat() {
		return super.canChat()&&this.issue.equalsIgnoreCase("Focus")&&!this.areas.isEmpty();
	}
}
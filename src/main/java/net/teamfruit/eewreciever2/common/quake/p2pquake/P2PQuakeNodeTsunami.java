package net.teamfruit.eewreciever2.common.quake.p2pquake;

import net.minecraft.util.EnumChatFormatting;

public enum P2PQuakeNodeTsunami {
	/*@formatter:off*/
	NONE("この地震による津波の心配はありません。"),
	UNKNOWN("津波に関する情報は不明です。"),
	CHECKING("震源が海底ですと、津波の恐れがあります。念のため津波に警戒して下さい。"),
	NONEFFECTIVE("この地震により、若干の海面変動が発生する可能性がありますが、被害の心配はありません。"),
	WATCH("この地震により、津波注意報が発表されています。注意報が発表されている地域の方は、海岸や川の河口付近に近づかないで下さい。"),
	WARNING(EnumChatFormatting.RED+"この地震により、津波警報又は大津波警報が発表されました！情報を確認し、すぐに適切な行動を行ってください！"),
	;
	/*@formatter;on*/

	private final String format;

	private P2PQuakeNodeTsunami(final String format) {
		this.format = format;
	}

	@Override
	public String toString() {
		return this.format;
	}
}

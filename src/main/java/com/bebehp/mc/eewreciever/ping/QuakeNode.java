package com.bebehp.mc.eewreciever.ping;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

public class QuakeNode {
	public long uptime;
	public String type;
	public String time;
	public int strong;
	public boolean tsunami;
	public String quaketype;
	public String where;
	public String deep;
	public float magnitude;
	public boolean modified;
	public String[] point;

	@Override
	public boolean equals(Object o)
	{
		if (o instanceof QuakeNode)
			return ((QuakeNode)o).uptime == this.uptime;
		else
			return false;
	}
	
	@Override
	public String toString()
	{
		return "[" + this.quaketype + "]" +
				this.time + "に" +
				this.where + "で" +
				"震度" + this.strong + "、" +
				"マグニチュード" + this.magnitude + "の" +
				"地震が" +
				"深さ" + this.deep + "で発生しました。" +
				(this.tsunami ?
					"津波にご注意ください" :
						"この地震による津波の心配はございません。") +
				"[" + this.point[0] + ":" + this.point[1] + "]";
	}

	public static List<QuakeNode> getUpdate(List<QuakeNode> older, List<QuakeNode> newer)
	{
		ArrayList<QuakeNode> list = new ArrayList<QuakeNode>(newer);
		for (Iterator<QuakeNode> it = older.iterator(); it.hasNext();) {
			list.remove(it.next());
		}
        return list;
	}
	
	public static <E> Collection<E> subtract(final Collection<E> a, final Collection<E> b) {
        ArrayList<E> list = new ArrayList<E>( a );
        for (Iterator<E> it = b.iterator(); it.hasNext();) {
            list.remove(it.next());
        }
        return list;
    }
}

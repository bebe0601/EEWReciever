package com.bebehp.mc.eewreciever.common;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.bebehp.mc.eewreciever.Reference;

public abstract class AbstractQuakeNode {
	/**
	 * 識別子
	 */
	protected String id;
	/**
	 * 更新日時
	 */
	protected Date announcementtime;
	/**
	 * 発生日時
	 */
	protected Date time;
	/**
	 * 震度
	 */
	protected String strong;
	/**
	 * 震央
	 */
	protected String where;
	/**
	 * 深さ
	 */
	protected String deep;
	/**
	 * マグニチュード
	 */
	protected String magnitude;
	/**
	 * EEW警報
	 */
	protected boolean alarm;
	/**
	 * 訓練報
	 */
	protected boolean training;

	public boolean isAlarm() {
		return this.alarm;
	}

	public boolean isTraining() {
		return this.training;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((this.id == null) ? 0 : this.id.hashCode());
		return result;
	}

	@Override
	public boolean equals(final Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (!(obj instanceof AbstractQuakeNode))
			return false;
		final AbstractQuakeNode other = (AbstractQuakeNode) obj;
		if (this.id == null) {
			if (other.id != null)
				return false;
		} else if (!this.id.equals(other.id))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return Reference.NAME;
	}

	public abstract AbstractQuakeNode parseString(String source) throws QuakeException;

	public static List<AbstractQuakeNode> getUpdate(final List<AbstractQuakeNode> older,
			final List<AbstractQuakeNode> newer) {
		final ArrayList<AbstractQuakeNode> list = new ArrayList<AbstractQuakeNode>(newer);
		list.removeAll(older);

		return list;
	}
}
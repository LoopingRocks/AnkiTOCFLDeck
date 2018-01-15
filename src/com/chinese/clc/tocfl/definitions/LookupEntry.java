package com.chinese.clc.tocfl.definitions;

public class LookupEntry {
	protected String zh;

	protected String pinyin;

	public LookupEntry(final String zh, final String pinyin) {
		this.zh = zh;
		this.pinyin = pinyin;
	}

	@Override
	public boolean equals(final Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		LookupEntry other = (LookupEntry) obj;
		if (pinyin == null) {
			if (other.pinyin != null) {
				return false;
			}
		} else if (!pinyin.equals(other.pinyin)) {
			return false;
		}
		if (zh == null) {
			if (other.zh != null) {
				return false;
			}
		} else if (!zh.equals(other.zh)) {
			return false;
		}
		return true;
	}

	public String getPinyin() {
		return pinyin;
	}

	public String getZh() {
		return zh;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (pinyin == null ? 0 : pinyin.hashCode());
		result = prime * result + (zh == null ? 0 : zh.hashCode());
		return result;
	}

	@Override
	public String toString() {
		return String.format("LookupEntry [zh=%s, pinyin=%s]", zh, pinyin);
	}
}

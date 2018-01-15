package com.chinese.clc.tocfl;

public class Term implements Comparable<Term> {

	private int index;

	private String zh;
	private String pinyin;
	private String domain;
	private String type;
	private String level;

	public Term(final int number, final String zh, final String pinyin, final String domain, final String type,
			final String level) {
		super();
		this.index = number;
		this.zh = zh;
		this.pinyin = pinyin;
		this.domain = domain;
		this.type = type;
		this.level = level;
	}

	@Override
	public int compareTo(final Term o) {
		int levelCompare = level.compareTo(o.level);
		return levelCompare != 0 ? levelCompare : Integer.signum(index - o.index);
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
		Term other = (Term) obj;
		if (index != other.index) {
			return false;
		}
		if (level == null) {
			if (other.level != null) {
				return false;
			}
		} else if (!level.equals(other.level)) {
			return false;
		}
		if (type == null) {
			if (other.type != null) {
				return false;
			}
		} else if (!type.equals(other.type)) {
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

	public String getDomain() {
		return domain;
	}

	public int getIndex() {
		return index;
	}

	public String getLevel() {
		return level;
	}

	public String getPinyin() {
		return pinyin;
	}

	public String getType() {
		return type;
	}

	public String getZh() {
		return zh;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + index;
		result = prime * result + (level == null ? 0 : level.hashCode());
		result = prime * result + (type == null ? 0 : type.hashCode());
		result = prime * result + (zh == null ? 0 : zh.hashCode());
		return result;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("TOCFLTerm [index=");
		builder.append(index);
		builder.append(", zh=");
		builder.append(zh);
		builder.append(", pinyin=");
		builder.append(pinyin);
		builder.append(", domain=");
		builder.append(domain);
		builder.append(", type=");
		builder.append(type);
		builder.append(", level=");
		builder.append(level);
		builder.append("]");
		return builder.toString();
	}
}
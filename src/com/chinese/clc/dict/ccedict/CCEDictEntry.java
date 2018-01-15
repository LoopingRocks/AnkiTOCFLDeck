package com.chinese.clc.dict.ccedict;

public class CCEDictEntry {
	private String zhTR;

	private String zhSM;
	
	private String pinyin;
	private String pinyinTW;
	
	private String definition;

	public CCEDictEntry(String zh_tr, String zh_sm, String pinyin, String pinyinTW, String definition) {
		super();
		this.zhTR = zh_tr;
		this.zhSM = zh_sm;
		this.pinyin = pinyin;
		this.pinyinTW = pinyinTW;
		this.definition = definition;
	}
	
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		CCEDictEntry other = (CCEDictEntry) obj;
		if (pinyin == null) {
			if (other.pinyin != null)
				return false;
		} else if (!pinyin.equals(other.pinyin))
			return false;
		if (pinyinTW == null) {
			if (other.pinyinTW != null)
				return false;
		} else if (!pinyinTW.equals(other.pinyinTW))
			return false;
		if (zhSM == null) {
			if (other.zhSM != null)
				return false;
		} else if (!zhSM.equals(other.zhSM))
			return false;
		if (zhTR == null) {
			if (other.zhTR != null)
				return false;
		} else if (!zhTR.equals(other.zhTR))
			return false;
		return true;
	}
	public String getDefinition() {
		return definition;
	}
	public String getPinyin() {
		return pinyin;
	}
	public String getPinyinTW() {
		return pinyinTW;
	}
	public String getZhSM() {
		return zhSM;
	}
	public String getZhTR() {
		return zhTR;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((pinyin == null) ? 0 : pinyin.hashCode());
		result = prime * result + ((pinyinTW == null) ? 0 : pinyinTW.hashCode());
		result = prime * result + ((zhSM == null) ? 0 : zhSM.hashCode());
		result = prime * result + ((zhTR == null) ? 0 : zhTR.hashCode());
		return result;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("CCEDictEntry [zhTR=");
		builder.append(zhTR);
		builder.append(", zhSM=");
		builder.append(zhSM);
		builder.append(", pinyin=");
		builder.append(pinyin);
		builder.append(", pinyinTW=");
		builder.append(pinyinTW);
		builder.append(", definition=");
		builder.append(definition);
		builder.append("]");
		return builder.toString();
	}
}
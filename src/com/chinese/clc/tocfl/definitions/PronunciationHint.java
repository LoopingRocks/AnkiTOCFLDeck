package com.chinese.clc.tocfl.definitions;

import java.util.Collections;
import java.util.List;

/**
 * same meaning but has a bopomofo part within (). ex: 沒關係(ㄒㄧ˙)
 */
public class PronunciationHint extends Simple {

	@Override
	public List<LookupEntry> analyse(final LookupEntry entry) {
		String cutZh = entry.getZh().substring(0, entry.getZh().indexOf("("));

		return Collections.singletonList(new LookupEntry(cutZh, entry.getPinyin()));
	}

	@Override
	public boolean match(final LookupEntry entry) {
		return entry.getZh().contains("(") && !entry.getPinyin().contains("(");
	}
}

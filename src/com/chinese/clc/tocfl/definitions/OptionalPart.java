package com.chinese.clc.tocfl.definitions;

import java.util.Collections;
import java.util.List;

/**
 * same meaning but has a optional character part within (). ex: 一點(兒)
 */
public class OptionalPart extends Simple {

	@Override
	public List<LookupEntry> analyse(final LookupEntry entry) {
		String cutZh = entry.getZh().substring(0, entry.getZh().indexOf("("));
		String cutPY = entry.getPinyin().substring(0, entry.getPinyin().indexOf("("));

		return Collections.singletonList(new LookupEntry(cutZh, cutPY));
	}

	@Override
	public boolean match(final LookupEntry entry) {
		return entry.getZh().contains("(") && entry.getPinyin().contains("(");
	}
}

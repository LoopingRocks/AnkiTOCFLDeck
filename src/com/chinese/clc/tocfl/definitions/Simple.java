package com.chinese.clc.tocfl.definitions;

import java.util.Collections;
import java.util.List;

public class Simple implements TermParser {

	@Override
	public List<LookupEntry> analyse(final LookupEntry entry) {
		return Collections.singletonList(new LookupEntry(entry.getZh(), entry.getPinyin()));
	}

	@Override
	public boolean match(final LookupEntry entry) {
		return true;
	}
}

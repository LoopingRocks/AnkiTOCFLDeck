package com.chinese.clc.tocfl.definitions;

import java.util.ArrayList;
import java.util.List;

/**
 * same pronunciation and meaning but different character. ex: 你/妳
 */
public class Variants extends Simple {

	@Override
	public List<LookupEntry> analyse(final LookupEntry entry) {
		List<LookupEntry> lookups = new ArrayList<>();

		String[] words = entry.getZh().split("/");
		for (String word : words) {
			lookups.add(new LookupEntry(word, entry.getPinyin()));
		}

		return lookups;
	}

	@Override
	public boolean match(final LookupEntry entry) {
		return entry.getZh().contains("/") && !entry.getPinyin().contains("/");
	}
}

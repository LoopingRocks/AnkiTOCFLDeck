package com.chinese.clc.tocfl.definitions;

import java.util.ArrayList;
import java.util.List;

/**
 * same meaning but different character and pronunciation. ex: 星期天/星期日
 */
public class Synonyms extends Variants {

	@Override
	public List<LookupEntry> analyse(final LookupEntry entry) {
		List<LookupEntry> lookups = new ArrayList<>();

		try {
			String[] words = entry.getZh().split("/");
			String[] pinyins = entry.getPinyin().split("/");
			for (int i = 0; i < words.length; i++) {
				lookups.add(new LookupEntry(words[i], pinyins[i]));
			}
		} catch (ArrayIndexOutOfBoundsException e) {
			//TODO log
			e.printStackTrace();
		}

		return lookups;
	}

	@Override
	public boolean match(final LookupEntry entry) {
		return entry.getZh().contains("/") && entry.getPinyin().contains("/");
	}
}

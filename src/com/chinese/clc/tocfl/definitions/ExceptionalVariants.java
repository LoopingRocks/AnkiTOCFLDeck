package com.chinese.clc.tocfl.definitions;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * same pronunciation and meaning but different character. ex: 你/妳
 */
public class ExceptionalVariants extends Simple {

	private Map<String,String> specials = specialMappings();
	
	private Map<String,String> specialMappings()
	{
		Map<String,String> specials = new HashMap<>();
		specials.put("古蹟", "古跡");
		
		return specials;
	}
	
	@Override
	public List<LookupEntry> analyse(final LookupEntry entry) {
		return Collections.singletonList(new LookupEntry(specials.get(entry.getZh()), entry.getPinyin()));
	}

	@Override
	public boolean match(final LookupEntry entry) {
		return specials.containsKey(entry.getZh());
	}
}

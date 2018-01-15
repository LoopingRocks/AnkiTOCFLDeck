package com.chinese.clc.tocfl.definitions;

import java.util.List;

public interface TermParser {

	public List<LookupEntry> analyse(final LookupEntry entry);

	public boolean match(final LookupEntry entry);
}

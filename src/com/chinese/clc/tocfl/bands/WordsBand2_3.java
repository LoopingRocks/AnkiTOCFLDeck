package com.chinese.clc.tocfl.bands;

import com.chinese.clc.tocfl.Term;

public class WordsBand2_3 extends WordsBand {

	protected static final String DOMAIN = "詞彙";
	
	public WordsBand2_3(String level) {
		super(level);
	}

	@Override
	protected Term parseLine(String line) {
		String[] parts = line.split(";");
		String zh = parts[0].trim();
		String pinyin = parts[1].trim();
		String type = parts[2].trim();
		String number = parts[3].trim();
		
		Term term = new Term(Integer.parseInt(number), zh, null, pinyin, DOMAIN, type, level, "");
		return term;
	}
}

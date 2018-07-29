package com.chinese.clc.tocfl.bands;

import com.chinese.clc.tocfl.Term;

public class WordsBand1 extends WordsBand {

	public WordsBand1(String level) {
		super(level);
	}

	@Override
	protected Term parseLine(String line) {

		String[] parts = line.split(";");
		String domain = parts[0].trim();
		String zh = parts[1].trim();
		String pinyin = parts[2].trim();
		String type = parts[3].trim();
		String number = parts[4].trim();
		
		Term term = new Term(Integer.parseInt(number), zh, pinyin, domain, type, level, "");
		return term;
	}
}

package com.chinese.clc.tocfl.bands;

import java.nio.file.Path;

import com.chinese.clc.tocfl.Term;

public class WordsBand0 extends WordsBand {

	private int index = -1;

	public WordsBand0(String level) {
		super(level);
	}

	private String getWordType(String[] parts) {
		String type = "Uknw";

		if (parts.length == 4)
			type = parts[3].trim();

		return type;
	}

	public void load(Path path) {
		index = 1;
		super.load(path);
		index = -1;
	}

	@Override
	protected Term parseLine(String line) {
		String[] parts = line.split(";");
		String domain = parts[0].trim();
		String zh = parts[1].trim();
		String pinyin = parts[2].trim();
		String type = getWordType(parts);

		Term term = new Term(index++, zh, pinyin, domain, type, level);
		return term;
	}
}

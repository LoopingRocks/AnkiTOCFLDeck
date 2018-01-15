package com.chinese.clc.tocfl;

import com.github.pffy.chinese.Bopomofo;
import com.github.pffy.chinese.HanyuPinyin;
import com.github.pffy.chinese.Tone;

public class PinyinTools {

	private static PinyinTools instance;

	public static synchronized PinyinTools getInstance() {
		if (instance == null) {
			instance = new PinyinTools();
		}

		return instance;
	}

	private HanyuPinyin hanyupinyin;
	private Bopomofo bopomofo;

	private PinyinTools() {
		initialize();
	}

	private void initialize() {
		hanyupinyin = new HanyuPinyin();
		bopomofo = new Bopomofo(hanyupinyin);
	}

	// TODO this operation is slow, needs refactoring
	public String markToNumber(final String pinyin) {
		hanyupinyin.setMode(Tone.TONE_MARKS);
		hanyupinyin.setInput(pinyin);
		hanyupinyin.setMode(Tone.TONE_NUMBERS);

		return hanyupinyin.toString().trim();
	}

	// TODO this operation is slow, needs refactoring
	public String numberToBopomofo(final String pinyin) {
		hanyupinyin.setMode(Tone.TONE_NUMBERS);
		hanyupinyin.setInput(pinyin);
		// hanyupinyin.setMode(Tone.TONE_MARKS);
		bopomofo.setInput(hanyupinyin);

		return bopomofo.toString().trim();
	}

	// TODO this operation is slow, needs refactoring
	public String numberToMark(final String pinyin) {
		hanyupinyin.setMode(Tone.TONE_NUMBERS);
		hanyupinyin.setInput(pinyin);
		hanyupinyin.setMode(Tone.TONE_MARKS);

		return hanyupinyin.toString().trim();
	}
}

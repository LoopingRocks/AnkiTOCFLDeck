package com.chinese.clc.tocfl;

import static org.junit.Assert.*;

import org.junit.Test;

import com.github.pffy.chinese.Bopomofo;
import com.github.pffy.chinese.HanyuPinyin;
import com.github.pffy.chinese.Tone;

import junit.framework.Assert;

public class PinyinToolsTest {

	@Test
	public void testBopomofo() {
		String pinyin = "shàngwǔ";

		String bopomofo = PinyinTools.getInstance().markToBopomofo(pinyin);

		assertEquals("ㄕㄤˋ ㄨˇ", bopomofo);

		pinyin = "zhōngnián";

		bopomofo = PinyinTools.getInstance().markToBopomofo(pinyin);

		assertEquals("ㄓㄨㄥ ㄋㄧㄢˊ", bopomofo);
	}
}

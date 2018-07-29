package com.chinese.clc.tocfl;

import org.junit.Test;

import com.chinese.clc.tocfl.bands.WordsBand1;

public class TOCFLBand1Test {

	@Test
	public void testLoad() {
		WordsBand1 band1l1 = new WordsBand1("Band1-L1 入門級");
		band1l1.load(TOCFLBand1Test.class.getResource("/tocfl/tocfl3.csv"));
		
		WordsBand1 band1l2 = new WordsBand1("Band1-L2 基礎級");
		band1l2.load(TOCFLBand1Test.class.getResource("/tocfl/tocfl4.csv"));
	}
}

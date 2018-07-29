package com.chinese.clc.tocfl;

import org.junit.Test;

import com.chinese.clc.tocfl.bands.WordsBand0;

public class TOCFLBand0Test {

	@Test
	public void testLoad() {
		WordsBand0 band0 = new WordsBand0("Band0 準備一級");
		
		band0.load(TOCFLBand0Test.class.getResource("/tocfl/tocfl1.csv"));
		band0.load(TOCFLBand0Test.class.getResource("/tocfl/tocfl2.csv"));
	}
}

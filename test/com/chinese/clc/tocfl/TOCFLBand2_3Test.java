package com.chinese.clc.tocfl;

import org.junit.Test;

import com.chinese.clc.tocfl.bands.WordsBand2_3;

public class TOCFLBand2_3Test {

	@Test
	public void testLoadBand2() {
		WordsBand2_3 band2l3 = new WordsBand2_3("Band2-L3 進階級");
		band2l3.load(TOCFLBand2_3Test.class.getResource("/tocfl/tocfl5.csv"));
		
		WordsBand2_3 band2l4 = new WordsBand2_3("Band2-L4 高階級");
		band2l4.load(TOCFLBand2_3Test.class.getResource("/tocfl/tocfl6.csv"));
	}
	
	@Test
	public void testLoadBand3() {
		WordsBand2_3 band3l56 = new WordsBand2_3("Band3-L5-6 流利級");
		band3l56.load(TOCFLBand2_3Test.class.getResource("/tocfl/tocfl7.csv"));
	}
}

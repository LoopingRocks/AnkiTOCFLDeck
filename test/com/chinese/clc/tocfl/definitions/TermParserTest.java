package com.chinese.clc.tocfl.definitions;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

import com.chinese.clc.tocfl.Term;

public class TermParserTest {

	Definitions defs = null;

	@Before
	public void setDefinitions() {
		defs = new Definitions();
	}

	@Test
	public void testMulti() {
		assertEquals("hello (when answering the phone) / hey; to feed (an animal, baby, invalid etc)",
				defs.augment(new Term(0, "喂(ㄨㄟˊ)/喂", null, "wéi/wèi", "", "", "", "")).getDefinition());
	}

	@Test
	public void testOptionalPart() {
		assertEquals("car; vehicle; CL:輛|辆[liang4]; machine; to shape with a lathe",
				defs.augment(new Term(0, "車(子)", null, "chē(zi)", "", "", "", "")).getDefinition());
	}

	@Test
	public void testPronunciationHint() {
		assertEquals("it doesn't matter", defs.augment(new Term(0, "沒關係(ㄒㄧ˙)", null, "méiguānxi", "", "", "", "")).getDefinition());
	}

	@Test
	public void testSimple() {
		assertEquals("I; me; my", defs.augment(new Term(0, "我", null, "wǒ", "", "", "", "")));
		assertEquals("rain; CL:陣|阵[zhen4],場|场[chang2]", defs.augment(new Term(0, "雨", null, "yǔ", "", "", "", "")).getDefinition());
		assertEquals(
				"of; ~'s (possessive particle); (used after an attribute); (used to form a nominal expression); (used at the end of a declarative sentence for emphasis)",
				defs.augment(new Term(0, "的", null, "de", "", "", "", "")).getDefinition());
	}

	@Test
	public void testSynonyms() {
		assertEquals("Sunday; CL:個|个[ge4]",
				defs.augment(new Term(0, "星期天/星期日", null, "xīngqítiān/xīngqírì", "", "", "", "")).getDefinition());
	}

	@Test
	public void testVariants() {
		Definitions defs = new Definitions();

		assertEquals("you (informal, as opposed to courteous 您[nin2]) / you (female); variant of 你[ni3]",
				defs.augment(new Term(0, "你/妳", null, "nǐ", "", "", "", "")).getDefinition());
	}
}

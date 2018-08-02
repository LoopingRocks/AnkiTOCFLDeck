package com.chinese.clc.tocfl.definitions;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import org.junit.Before;
import org.junit.Test;

import com.chinese.clc.tocfl.Levels;
import com.chinese.clc.tocfl.TOCFL;
import com.chinese.clc.tocfl.Term;
import com.chinese.clc.tocfl.bands.WordsBand;

public class DefinitionsTest {

	public static final Set<String> EXCLUDEDS = new HashSet<>(Arrays.asList("不用說", "還要"));

	Definitions defs = null;

	@Before
	public void setDefinitions() {
		defs = new Definitions();
	}

	@Test
	public void testBand0() {
		TOCFL tocfl = new TOCFL();
		WordsBand words = tocfl.load(Levels.Band.Band0, Levels.Level.L0);
		for (Term term : words.getEntries()) {
			if (!EXCLUDEDS.contains(term.getZhTrad())) {
				assertNotEquals(term.toString(), "", defs.augment(term).getDefinition());
			}
		}
	}

	@Test
	public void testBand1() {
		TOCFL tocfl = new TOCFL();
		WordsBand words1 = tocfl.load(Levels.Band.Band1, Levels.Level.L1);
		for (Term term : words1.getEntries()) {
			if (!EXCLUDEDS.contains(term.getZhTrad())) {
				assertNotEquals(term.toString(), "", defs.augment(term).getDefinition());
			}
		}

		WordsBand words2 = tocfl.load(Levels.Band.Band1, Levels.Level.L2);
		for (Term term : words2.getEntries()) {
			if (!EXCLUDEDS.contains(term.getZhTrad())) {
				assertNotEquals(term.toString(), "", defs.augment(term).getDefinition());
			}
		}
	}

	@Test
	public void testBand2() {
		TOCFL tocfl = new TOCFL();
		WordsBand words1 = tocfl.load(Levels.Band.Band2, Levels.Level.L3);
		for (Term term : words1.getEntries()) {
			if (!EXCLUDEDS.contains(term.getZhTrad())) {
				if (defs.augment(term).getDefinition().equals(""))
					System.out.println(term);
				// assertNotEquals(term.toString(), "", defs.getDefinition(term));
			}
		}

		WordsBand words2 = tocfl.load(Levels.Band.Band2, Levels.Level.L4);
		for (Term term : words2.getEntries()) {
			if (!EXCLUDEDS.contains(term.getZhTrad())) {
				if (defs.augment(term).getDefinition().equals(""))
					System.out.println(term);
				// assertNotEquals(term.toString(), "", defs.getDefinition(term));
			}
		}
	}

	@Test
	public void testBand3() {
		TOCFL tocfl = new TOCFL();
		WordsBand words = tocfl.load(Levels.Band.Band3, Levels.Level.L3);
		for (Term term : words.getEntries()) {
			if (!EXCLUDEDS.contains(term.getZhTrad())) {
				if (defs.augment(term).getDefinition().equals(""))
					System.out.println(term);
				// assertNotEquals(term.toString(), "", defs.getDefinition(term));
			}
		}
	}

	@Test
	public void testSpecialCases() {
		// some terms don't agree on tone
		// for example: lookup zh=便宜, pinyin=piányí | ccedict 便宜 便宜 [pian2 yi5]
		assertEquals("cheap; inexpensive; small advantages; to let sb off lightly",
				defs.augment(new Term(0, "便宜", null, "piányí", "", "", "", "")).getDefinition());

		// ccedict uses "u:" for "ü"
		// and the hanyupinyin lib uses "uu" for "ü"
		assertEquals("female; woman; daughter", defs.augment(new Term(0, "女", null, "nǚ", "", "", "", "")).getDefinition());
	}
}

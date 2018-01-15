package com.chinese.clc.dict.ccedict;

import static org.junit.Assert.*;

import java.net.URISyntaxException;
import java.nio.file.Paths;
import java.util.regex.Matcher;

import org.junit.Test;

import com.chinese.clc.dict.ccedict.CCEDict;

public class CCEDictTest {

	@Test
	public void testPattern1() {
		String line = "一場空 一场空 [yi1 chang2 kong1] /all one's hopes and efforts come to nothing/futile/";

		CCEDict dict = new CCEDict();

		CCEDictEntry entry = dict.parseLine(line);

		assertNotNull(entry);

		// trad
		assertEquals("一場空", entry.getZhTR());
		// simpl
		assertEquals("一场空", entry.getZhSM());
		// pinyin
		assertEquals("yi1 chang2 kong1", entry.getPinyin());
		// pinyin
		assertEquals("yi1 chang2 kong1", entry.getPinyinTW());
		// def
		assertEquals("all one's hopes and efforts come to nothing/futile", entry.getDefinition());
	}

	@Test
	public void testPattern2() {
		String line = "一回生兩回熟 一回生两回熟 [yi1 hui2 sheng1 liang3 hui2 shu2] /see 一回生二回熟[yi1 hui2 sheng1 er4 hui2 shu2]/";

		CCEDict dict = new CCEDict();

		CCEDictEntry entry = dict.parseLine(line);

		assertNotNull(entry);

		// trad
		assertEquals("一回生兩回熟", entry.getZhTR());
		// simpl
		assertEquals("一回生两回熟", entry.getZhSM());
		// pinyin
		assertEquals("yi1 hui2 sheng1 liang3 hui2 shu2", entry.getPinyin());
		// pinyin
		assertEquals("yi1 hui2 sheng1 liang3 hui2 shu2", entry.getPinyinTW());
		// def
		assertEquals("see 一回生二回熟[yi1 hui2 sheng1 er4 hui2 shu2]", entry.getDefinition());
	}

	@Test
	public void testPattern3() {
		String line = "識 识 [shi2] /to know/knowledge/Taiwan pr. [shi4]/";

		CCEDict dict = new CCEDict();

		CCEDictEntry entry = dict.parseLine(line);

		assertNotNull(entry);

		// trad
		assertEquals("識", entry.getZhTR());
		// simpl
		assertEquals("识", entry.getZhSM());
		// pinyin
		assertEquals("shi2", entry.getPinyin());
		// pinyin
		assertEquals("shi4", entry.getPinyinTW());
		// def
		assertEquals("to know/knowledge/Taiwan pr. [shi4]", entry.getDefinition());
	}

	@Test
	public void testLoad() {
		CCEDict dict = new CCEDict();

		try {
			dict.load(Paths.get(CCEDict.class.getResource("/dicts/cedict_ts.u8").toURI()));
		} catch (URISyntaxException e) {
			fail(e.getMessage());
		}

		assertNotNull(dict.entries);
		assertEquals(115431, dict.entries.size());
	}

}

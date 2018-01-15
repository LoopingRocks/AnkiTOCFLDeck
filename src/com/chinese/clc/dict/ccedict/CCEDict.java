package com.chinese.clc.dict.ccedict;

import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class CCEDict {

	protected static Pattern EntryPattern = Pattern.compile("^(.+) (.+) \\[(.+)\\] /(.+)/$");
	protected static Pattern TaiwanPrPattern = Pattern.compile("Taiwan pr. \\[(.+)\\]");

	List<CCEDictEntry> entries = new ArrayList<>();

	Map<String, List<CCEDictEntry>> byTraditional, byPinyinTW;

	public CCEDict() {
	}

	private String formatDefinition(final String definition) {
		// slash "/" are changed to semicolon ";" to deal with the fact that "/" is
		// already used as a separator for term variants
		return definition.replaceAll("/", "; ");
	}

	public List<CCEDictEntry> getByPinyinTW(final String pinyin) {
		return byPinyinTW.get(pinyin);
	}

	public List<CCEDictEntry> getByTrad(final String trad) {
		return byTraditional.get(trad);
	}

	private void initializeLookups() {
		// TODO DESIGN make it lazy
		byTraditional = entries.stream().collect(Collectors.groupingBy(CCEDictEntry::getZhTR));
		byPinyinTW = entries.stream().collect(Collectors.groupingBy(CCEDictEntry::getPinyinTW));
	}

	private boolean isComment(final String line) {
		return line.startsWith("#");
	}

	private boolean isEntry(final String line) {
		return !isComment(line);
	}

	public void load() {
		try {
			URL url = CCEDict.class.getResource("/dicts/cedict_ts.u8");
			Path filename = Paths.get(url.toURI());
			load(filename);
		} catch (URISyntaxException e) {
			// TODO log
			e.printStackTrace();
		}
	}

	public void load(final Path filename) {
		parseFile(filename);
		initializeLookups();
	}

	private void parseFile(final Path path) {
		try (Stream<String> lines = Files.lines(path, Charset.forName("UTF-8"))) {
			Stream<CCEDictEntry> parseds = lines.filter(this::isEntry).map(l -> parseLine(l));
			entries = parseds.filter(e -> e != null).collect(Collectors.toList());
		} catch (IOException e) {
			// TODO log
			e.printStackTrace();
		}
	}

	protected CCEDictEntry parseLine(final String line) {

		CCEDictEntry entry = null;

		Matcher matcher = CCEDict.EntryPattern.matcher(line);
		if (matcher.matches()) {
			entry = new CCEDictEntry(matcher.group(1), matcher.group(2), matcher.group(3),
					parseTWPinyin(matcher.group(3), matcher.group(4)), formatDefinition(matcher.group(4)));
		} else {
			// TODO log
			System.out.println(line);
		}

		return entry;
	}

	private String parseTWPinyin(final String defaultPinyin, final String definition) {
		String pinyin = defaultPinyin;

		Matcher matcher = TaiwanPrPattern.matcher(definition);
		if (matcher.find()) {
			pinyin = matcher.group(1);
		}

		return pinyin;
	}
}

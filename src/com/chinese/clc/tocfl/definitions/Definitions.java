package com.chinese.clc.tocfl.definitions;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import com.chinese.clc.dict.ccedict.CCEDict;
import com.chinese.clc.dict.ccedict.CCEDictEntry;
import com.chinese.clc.tocfl.PinyinTools;
import com.chinese.clc.tocfl.Term;

public class Definitions {

	private CCEDict dict = new CCEDict();

	// the best would be to parse the expression into some lexical tree but a simple
	// priority system (with "match(term)" pattern matching) works
	private List<TermParser> parsers = Arrays.asList(new Variants(), new Synonyms(), new OptionalPart(),
			new PronunciationHint(), new ExceptionalVariants(), new Simple());

	public Definitions() {
		dict.load();
	}

	private String changeV(final String pinyin) {
		// ccedict uses "u:" for "ü"
		// and the hanyupinyin lib uses "uu" for "ü"
		return pinyin.replaceAll("uu", "u:");
	}

	public String getDefinition(final Term term) {
		String definition = "";

		List<LookupEntry> entries = parse(term);
		
		List<String> definitions = entries.stream().map(entry -> lookupCharacter(entry)).filter(def -> !"".equals(def)).distinct().collect(Collectors.toList());

		definition = String.join(" / ", definitions);

		return definition;
	}

	public CCEDict getDict() {
		return dict;
	}

	private String lookupCharacter(final LookupEntry lookup) {
		String definition = "";

		List<CCEDictEntry> characterMatches = dict.getByTrad(lookup.getZh());

		if (characterMatches == null || characterMatches.size() == 0) {
		} else if (characterMatches.size() == 1) {
			CCEDictEntry found = characterMatches.get(0);
			definition = found.getDefinition();
		} else {
			definition = lookupPinyin(lookup, characterMatches);
		}

		return definition;
	}

	private String lookupPinyin(final LookupEntry lookup, List<CCEDictEntry> characterMatches) {
		String definition = "";

		String lpinyin = normalizeTOCFLPinyin(lookup);

		List<CCEDictEntry> pinyinMatches = characterMatches.stream()
				.filter(entry -> lpinyin.equals(normalizeCCEDictPinyin(entry))).collect(Collectors.toList());

		if (pinyinMatches == null || pinyinMatches.size() == 0) {
			definition = lookupPronunciation(lookup, characterMatches, lpinyin);
		} else if (pinyinMatches.size() == 1) {
			CCEDictEntry found = pinyinMatches.get(0);
			definition = found.getDefinition();
		} else {
			definition = pinyinMatches.stream().map(entry -> entry.getDefinition()).collect(Collectors.joining("; "));
		}
		return definition;
	}

	private String lookupPronunciation(final LookupEntry lookup, List<CCEDictEntry> characterMatches, String lpinyin) {
		String definition = "";

		String lpinyinNoTone = replaceAllTone(lpinyin);
		
		// some terms don't agree on tone
		// for example: lookup zh=便宜, pinyin=piányí | ccedict 便宜 便宜 [pian2 yi5]
		// so check pronunciation (without tone)
		List<CCEDictEntry> pronunciationMatches = characterMatches.stream()
				.filter(entry -> lpinyinNoTone.equals(replaceAllTone(normalizeCCEDictPinyin(entry)))).collect(Collectors.toList());
		
		if (pronunciationMatches == null || pronunciationMatches.size() == 0) {
		} else if (pronunciationMatches.size() == 1) {
			CCEDictEntry found = pronunciationMatches.get(0);
			definition = found.getDefinition();
		} else {
			definition = pronunciationMatches.stream().map(entry -> entry.getDefinition()).collect(Collectors.joining("; "));
		}
		
		return definition;
	}

	private String normalizeCCEDictPinyin(final CCEDictEntry entry) {
		return replaceNeutralTone(entry.getPinyin()).toLowerCase();
	}

	private String normalizeTOCFLPinyin(final LookupEntry lookup) {
		PinyinTools pyt = PinyinTools.getInstance();
		return changeV(pyt.markToNumber(lookup.getPinyin())).toLowerCase();
	}

	public List<LookupEntry> parse(final Term term) {
		LookupEntry first = new LookupEntry(term.getZh(), term.getPinyin());

		List<LookupEntry> olderPass = Collections.singletonList(first);
		List<LookupEntry> newerPass = olderPass;

		do {
			olderPass = newerPass;
			newerPass = new ArrayList<>();

			for (LookupEntry entry : olderPass) {
				Optional<TermParser> strategy = parsers.stream().filter(s -> s.match(entry)).findFirst();
				if (strategy.isPresent()) {
					newerPass.addAll(strategy.get().analyse(entry));
				}
			}
		} while (olderPass.size() < newerPass.size());

		return newerPass;
	}

	private String replaceAllTone(final String pinyin) {
		return pinyin.replaceAll("\\d", "");
	}

	private String replaceNeutralTone(final String pinyin) {
		return pinyin.replace("5", "");
	}

	public String getZhuyin(Term term) {
		String zhuyin = "";
		
		List<LookupEntry> entries = parse(term);
		List<String> definitions = entries.stream().map(entry -> PinyinTools.getInstance().markToBopomofo(entry.getPinyin())).distinct().collect(Collectors.toList());
		zhuyin = String.join(" / ", definitions);
		
		return zhuyin;
	}
}

package com.chinese.clc.tocfl.definitions;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

import com.chinese.clc.dict.ccedict.CCEDict;
import com.chinese.clc.dict.ccedict.CCEDictEntry;
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

	public CCEDict getDict() {
		return dict;
	}

	public Term augment(final Term term) {
		List<LookupEntry> entries = parse(term);
		List<Map<String,String>> infos = entries.stream().map(entry -> lookupCharacters(entry))
				.filter(e -> !"".equals(e.get("definition"))).distinct().collect(Collectors.toList());

		String zhSimpl = String.join("/", infos.stream().map(e -> e.get("simplified")).collect(Collectors.toList()));
		String definition = String.join(" / ", infos.stream().map(e -> e.get("definition")).collect(Collectors.toList()));

		return new Term(term.getIndex(), term.getZhTrad(), zhSimpl, term.getPinyin(), term.getDomain(), term.getType(),
				term.getLevel(), definition);
	}

	private Map<String, String> lookupCharacters(final LookupEntry lookup) {
		List<CCEDictEntry> matches = dict.getByTrad(lookup.getZh());

		Map<String, String> dictInfos = new HashMap<>();

		dictInfos.put("definition", getInfo(matches, m -> String.format("[%s] %s", m.getPinyin(), m.getDefinition())));
		dictInfos.put("simplified", getInfo(matches, m -> String.format("%s", m.getZhSM())));

		return dictInfos;
	}

	private String getInfo(List<CCEDictEntry> dictEntries, Function<CCEDictEntry, String> extractor) {
		String info = "";

		if (dictEntries != null && dictEntries.size() > 0) {

			// take all the competing entries, even if pinyin doesn't match
			// and separate them, this way the deck is complete and irrelevant definitions
			// can be easily deleted on review
			List<String> lookups = dictEntries.stream().map(extractor).sorted().distinct().collect(Collectors.toList());
			// seems like the cedict is ordered by the relevant last
			//Collections.reverse(lookups);

			info = String.join(" | ", lookups);
		}

		return info;
	}

	public List<LookupEntry> parse(final Term term) {
		LookupEntry first = new LookupEntry(term.getZhTrad(), term.getPinyin());

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
}

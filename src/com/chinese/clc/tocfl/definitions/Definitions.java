package com.chinese.clc.tocfl.definitions;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
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

	public String getDefinition(final Term term) {
		String definition = "";

		List<LookupEntry> entries = parse(term);

		List<String> definitions = entries.stream().map(entry -> lookupCharacters(entry)).filter(def -> !"".equals(def))
				.distinct().collect(Collectors.toList());

		definition = String.join(" / ", definitions);

		return definition;
	}

	public CCEDict getDict() {
		return dict;
	}

	private String lookupCharacters(final LookupEntry lookup) {
		List<CCEDictEntry> matches = dict.getByTrad(lookup.getZh());
		String definition = "";

		if (matches != null && matches.size() > 0) {
			
			//take all the competing entries, even if pinyin doesn't match
			//and separate them, this way the deck is complete and irrelevant definitions can be easily deleted on review
			List<String> lookups = matches.stream().map(m -> String.format("%s", m.getDefinition())).distinct()
					.collect(Collectors.toList());
			//seems like the cedict is ordered by the relevant last
			Collections.reverse(lookups);
			
			definition = String.join(" | ", lookups);
		}

		return definition;
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
}

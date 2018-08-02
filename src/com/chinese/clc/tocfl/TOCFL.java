package com.chinese.clc.tocfl;

import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import com.chinese.clc.tocfl.Levels.Band;
import com.chinese.clc.tocfl.bands.WordsBand;
import com.chinese.clc.tocfl.definitions.Definitions;

public class TOCFL {

	public static void main(String[] args) {
		TOCFL tocfl = new TOCFL();
		List<Term> terms = tocfl.load();

		Definitions definitions = new Definitions();
		List<Term> definedTerms = terms.stream().map(t -> definitions.augment(t)).collect(Collectors.toList());

		DeckWriter dw = new DeckWriter();
		dw.dumpToFile(definedTerms);
	}

	public List<Term> load() {

		List<Term> terms = new ArrayList<Term>();

		for (Levels.Band band : Levels.Band.values()) {
			for (Levels.Level level : band.getLevels()) {
				WordsBand wordsBand = load(band, level);
				List<Term> entries = wordsBand.getEntries();
				if (!band.equals(Band.Band0))
					Collections.shuffle(entries);
				terms.addAll(entries);
			}
		}

		return terms;
	}

	public WordsBand load(final Levels.Band band, final Levels.Level level) {
		Levels levels = new Levels();

		List<String> paths = levels.getPaths(level);
		WordsBand words = levels.make(band, level);
		if (words != null) {
			for (String path : paths) {
				try {
					URL resource = TOCFL.class.getResource(path);
					words.load(Paths.get(resource.toURI()));
				} catch (URISyntaxException e) {
					// TODO log
					e.printStackTrace();
				}
			}
		}

		return words;
	}
}

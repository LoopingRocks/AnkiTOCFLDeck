package com.chinese.clc.tocfl;

import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import com.chinese.clc.tocfl.bands.WordsBand;

public class TOCFL {

	public static void main(String[] args)
	{
		TOCFL tocfl = new TOCFL();
		List<WordsBand> bands = tocfl.load();
		DeckWriter writer = new DeckWriter("./output/tocfl.txt");
		
		List<Term> allTerms = new ArrayList<>();
		bands.stream().map(b -> b.getEntries()).forEach(ts -> {
			allTerms.addAll(ts);
		});
		
		writer.dump(allTerms);
	}
	
	public List<WordsBand> load() {
		List<WordsBand> bands = new ArrayList<>();
		
		for (Levels.Band band : Levels.Band.values()) {
			for (Levels.Level level : band.getLevels()) {
				WordsBand wordsBand = load(band, level);
				bands.add(wordsBand);
			}
		}
		
		return bands;
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

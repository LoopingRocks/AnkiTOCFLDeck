package com.chinese.clc.tocfl.bands;

import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import com.chinese.clc.tocfl.Term;

public abstract class WordsBand {

	protected String level = "";
	protected List<Term> entries = new ArrayList<>();

	public String getLevel() {
		return level;
	}

	public List<Term> getEntries() {
		return entries;
	}

	public WordsBand(String level) {
		this.level = level;
	}

	protected boolean isEntry(String line) {
		return line.length() > 0 && !(line.contains("詞彙") && line.contains("詞類"));
	}

	public void load(Path path) {
		try (Stream<String> lines = Files.lines(path, Charset.forName("UTF-8"))) {

			Stream<Term> parseds = lines.filter(this::isEntry).map(l -> parseLine(l));
			entries.addAll(parseds.filter(e -> e != null).collect(Collectors.toList()));

		} catch (IOException e) {
			// TODO log
			e.printStackTrace();
		}
	}

	public void load(URL url) {
		try {
			Path filename = Paths.get(url.toURI());
			load(filename);
		} catch (URISyntaxException e) {
			// TODO log
			e.printStackTrace();
		}
	}

	protected abstract Term parseLine(String line);
}
